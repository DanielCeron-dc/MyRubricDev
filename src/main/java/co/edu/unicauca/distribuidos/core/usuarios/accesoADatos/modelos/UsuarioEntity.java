package co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.modelos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import co.edu.unicauca.distribuidos.core.evaluacion.accesoADatos.modelos.EvaluacionEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Entity representing a user in the system
 * Implements Spring Security's UserDetails for authentication
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "USUARIOS")
public class UsuarioEntity implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;
    
    @Column(name = "PASSWORD_HASH", nullable = false)
    private String passwordHash;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    
    // TODO: Quitar las evaluaciones
    @Transient
    private List<EvaluacionEntity> evaluaciones;
    
    @OneToOne(mappedBy = "usuario")
    private DocenteEntity docente;
    
    @Builder.Default
    @Column(name = "ENABLED", nullable = false)
    private boolean enabled = true;
    
    @Builder.Default
    @Column(name = "ACCOUNT_NON_EXPIRED", nullable = false)
    private boolean accountNonExpired = true;
    
    @Builder.Default
    @Column(name = "ACCOUNT_NON_LOCKED", nullable = false)
    private boolean accountNonLocked = true;
    
    @Builder.Default
    @Column(name = "CREDENTIALS_NON_EXPIRED", nullable = false)
    private boolean credentialsNonExpired = true;
    
    /**
     * Returns the authorities granted to the user based on their role
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (rol == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    /**
     * Returns the password used to authenticate the user
     */
    @Override
    public String getPassword() {
        return passwordHash;
    }

    /**
     * Returns the username used to authenticate the user
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Indicates whether the user's account has expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    /**
     * Indicates whether the user is locked or unlocked
     */
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * Indicates whether the user's credentials (password) has expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * Indicates whether the user is enabled or disabled
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    /**
     * Initialize empty collections for transient fields
     */
    public void initializeCollections() {
        if (evaluaciones == null) {
            evaluaciones = new ArrayList<>();
        }
    }
}
