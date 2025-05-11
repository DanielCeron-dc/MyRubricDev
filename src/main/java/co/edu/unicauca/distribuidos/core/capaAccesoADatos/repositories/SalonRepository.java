package co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.SalonEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.conexion.ConexionBD;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

@Repository
public class SalonRepository {

    private final ConexionBD conexion;

    public SalonRepository() {
        this.conexion = new ConexionBD();
    }

    public Optional<Collection<SalonEntity>> findAll() {
        String sql = "SELECT * FROM salones";
        Collection<SalonEntity> salones = new LinkedList<>();
        conexion.conectar();
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SalonEntity s = new SalonEntity();
                s.setId(rs.getInt("id"));
                s.setCode(rs.getString("code"));
                s.setName(rs.getString("name"));
                s.setLocation(rs.getString("location"));
                salones.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Error listing salones: " + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return salones.isEmpty() ? Optional.empty() : Optional.of(salones);
    }

    public Optional<SalonEntity> findById(Integer id) {
        String sql = "SELECT * FROM salones WHERE id = ?";
        conexion.conectar();
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SalonEntity s = new SalonEntity();
                    s.setId(rs.getInt("id"));
                    s.setCode(rs.getString("code"));
                    s.setName(rs.getString("name"));
                    s.setLocation(rs.getString("location"));
                    return Optional.of(s);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding salon by id: " + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return Optional.empty();
    }

    public SalonEntity save(SalonEntity salon) {
        String sql = "INSERT INTO salones(code, name, location) VALUES(?, ?, ?)";
        conexion.conectar();
        try (PreparedStatement ps = conexion.getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, salon.getCode());
            ps.setString(2, salon.getName());
            ps.setString(3, salon.getLocation());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    salon.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error saving salon: " + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return salon;
    }

    public Optional<SalonEntity> update(Integer id, SalonEntity salon) {
        String sql = "UPDATE salones SET code = ?, name = ?, location = ? WHERE id = ?";
        conexion.conectar();
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setString(1, salon.getCode());
            ps.setString(2, salon.getName());
            ps.setString(3, salon.getLocation());
            ps.setInt(4, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                salon.setId(id);
                return Optional.of(salon);
            }
        } catch (SQLException e) {
            System.out.println("Error updating salon: " + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return Optional.empty();
    }

    public boolean delete(Integer id) {
        String sql = "DELETE FROM salones WHERE id = ?";
        conexion.conectar();
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting salon: " + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return false;
    }

    /**
     * Checks if a salon exists by its ID.
     * @param id the salon ID
     * @return true if exists, false otherwise
     */
    public boolean existsById(Integer id) {
        String sql = "SELECT COUNT(*) FROM salones WHERE id = ?";
        conexion.conectar();
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking existence of salon: " + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return false;
    }

    /**
     * Removes a salon by its ID.
     * @param id the salon ID
     * @return true if removed successfully, false otherwise
     */
    public boolean removeById(Integer id) {
        return delete(id);
    }
}