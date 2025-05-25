package co.edu.unicauca.distribuidos.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Database connection wrapper for the application
 * This class provides database connectivity using Spring's DataSource
 */
@Component
public class ConexionBD {
    
    @Autowired
    private DataSource dataSource;
    
    public ConexionBD() {}
    
    /**
     * Get a connection to the database
     * 
     * @return the database connection
     * @throws SQLException if an error occurs when opening the connection
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    /**
     * Get a connection to the database (alias for getConnection)
     * 
     * @return the database connection
     * @throws SQLException if an error occurs when opening the connection
     */
    public Connection conectar() throws SQLException {
        return dataSource.getConnection();
    }
    
    /**
     * Close the connection to the database
     * 
     * @param connection the connection to close
     */
    public void desconectar(Connection connection) {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Close the connection to the database
     * No-arg version for backward compatibility
     */
    public void desconectar() {
        // This is intentionally empty as connection management is handled by the DataSource
    }
}