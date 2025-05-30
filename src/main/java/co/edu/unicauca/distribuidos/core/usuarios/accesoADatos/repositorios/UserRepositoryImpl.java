package co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.repositorios;

import co.edu.unicauca.distribuidos.core.config.ConexionBD;
import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.modelos.Rol;
import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.modelos.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Implementation of UserRepository for database operations
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final ConexionBD conexionBD;

    /**
     * Find a user by their username
     *
     * @param username the username to search for
     * @return an Optional containing the user if found, or empty if not found
     */
    @Override
    public Optional<UsuarioEntity> findByUsername(String username) {
        String sql = "SELECT id, username, password_hash, rol FROM usuarios WHERE username = ?";
        
        try (Connection connection = conexionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                UsuarioEntity usuario = new UsuarioEntity();
                usuario.setId(resultSet.getInt("id"));
                usuario.setUsername(resultSet.getString("username"));
                usuario.setPasswordHash(resultSet.getString("password_hash"));
                usuario.setRol(Rol.valueOf(resultSet.getString("rol")));
                usuario.setAsignacionesDocente(new ArrayList<>());
                usuario.setEvaluaciones(new ArrayList<>());
                
                return Optional.of(usuario);
            }
            
            return Optional.empty();
        } catch (SQLException e) {
            log.error("Error finding user by username: {}", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Save a user to the database
     *
     * @param usuario the user entity to save
     * @return the saved user entity with any database-generated values (like ID)
     */
    @Override
    public UsuarioEntity save(UsuarioEntity usuario) {
        if (usuario.getId() == null) {
            return insert(usuario);
        } else {
            return update(usuario);
        }
    }

    /**
     * Insert a new user into the database
     *
     * @param usuario the user entity to insert
     * @return the inserted user entity with the generated ID
     */
    private UsuarioEntity insert(UsuarioEntity usuario) {
        String sql = "INSERT INTO usuarios (username, password_hash, rol, enabled, account_non_expired, account_non_locked, credentials_non_expired) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = conexionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, usuario.getUsername());
            statement.setString(2, usuario.getPasswordHash());
            statement.setString(3, usuario.getRol().name());
            statement.setBoolean(4, true); // enabled
            statement.setBoolean(5, true); // account_non_expired
            statement.setBoolean(6, true); // account_non_locked
            statement.setBoolean(7, true); // credentials_non_expired
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuario.setId(generatedKeys.getInt(1));
                    }
                }
            }
            
            return usuario;
        } catch (SQLException e) {
            log.error("Error inserting user: {}", e.getMessage());
            throw new RuntimeException("Error inserting user", e);
        }
    }

    /**
     * Update an existing user in the database
     *
     * @param usuario the user entity to update
     * @return the updated user entity
     */
    private UsuarioEntity update(UsuarioEntity usuario) {
        String sql = "UPDATE usuarios SET username = ?, password_hash = ?, rol = ? WHERE id = ?";
        
        try (Connection connection = conexionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, usuario.getUsername());
            statement.setString(2, usuario.getPasswordHash());
            statement.setString(3, usuario.getRol().name());
            statement.setInt(4, usuario.getId());
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new SQLException("Update failed, no rows affected");
            }
            
            return usuario;
        } catch (SQLException e) {
            log.error("Error updating user: {}", e.getMessage());
            throw new RuntimeException("Error updating user", e);
        }
    }

    /**
     * Check if a user with the given username exists
     * 
     * @param username the username to check
     * @return true if a user with the given username exists, false otherwise
     */
    @Override
    public boolean existsByUsername(String username) {
        boolean exists = false;
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ?";
        
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    exists = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            log.error("Error checking if username exists: {}", e.getMessage());
        }
        
        return exists;
    }
}

