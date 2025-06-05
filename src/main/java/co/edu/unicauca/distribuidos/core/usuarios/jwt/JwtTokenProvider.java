package co.edu.unicauca.distribuidos.core.usuarios.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret:defaultSecretKeyToBeReplacedInProduction}")
    private String jwtSecret;

    @Value("${jwt.expiration:3600000}") // Default: 1 hour in milliseconds
    private long jwtExpirationMs;

    /**
     * Generate a JWT token based on the authentication object
     *
     * @param authentication the authentication object containing the user details
     * @return the JWT token as a string
     */
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return generateToken(userDetails);
    }

    /**
     * Generate a JWT token based on the user details
     *
     * @param userDetails the user details
     * @return the JWT token as a string
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        log.debug("Generating token for user: {} with authorities: {}", userDetails.getUsername(), authorities);
        claims.put("authorities", authorities);

        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Create a token with the given claims and subject
     *
     * @param claims  additional claims to include in the token
     * @param subject the subject (typically username)
     * @return the JWT token as a string
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Get the signing key for JWT token generation and validation
     *
     * @return the signing key
     */
    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extract the username from a JWT token
     *
     * @param token the JWT token
     * @return the username
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Extract the expiration date from a JWT token
     *
     * @param token the JWT token
     * @return the expiration date
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Extract a specific claim from a JWT token
     *
     * @param token          the JWT token
     * @param claimsResolver the function to extract a specific claim
     * @return the extracted claim
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from a JWT token
     *
     * @param token the JWT token
     * @return all claims
     */
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Check if a JWT token is expired
     *
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Validate a JWT token
     *
     * @param token       the JWT token
     * @param userDetails the user details
     * @return true if the token is valid, false otherwise
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Validate a JWT token without user details
     *
     * @param token the JWT token
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        @SuppressWarnings("unchecked")
        List<String> authorities = (List<String>) claims.get("authorities");
        log.debug("Validating token with authorities: {}", authorities);

        // Check if token is expired and has authorities
        if (isTokenExpired(token)) {
            log.error("JWT token is expired");
            return false;
        }

        if (authorities == null || authorities.isEmpty()) {
            log.error("JWT token does not contain authorities");
            return false;
        }

        return true;
    }

    /**
     * Get the JWT token expiration time in milliseconds
     *
     * @return the JWT token expiration time in milliseconds
     */
    public long getJwtExpirationMs() {
        return jwtExpirationMs;
    }

    /**
     * Get authorities from the JWT token
     *
     * @param token the JWT token
     * @return list of granted authorities
     */
    public List<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        @SuppressWarnings("unchecked")
        List<String> authorities = (List<String>) claims.get("authorities");
        log.debug("Retrieved authorities from token: {}", authorities);
        if (authorities == null) {
            return Collections.emptyList();
        }
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

