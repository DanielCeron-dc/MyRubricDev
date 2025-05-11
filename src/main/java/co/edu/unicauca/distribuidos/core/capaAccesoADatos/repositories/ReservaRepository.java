package co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.ReservaEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.SalonEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.conexion.ConexionBD;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

@Repository
public class ReservaRepository {

    private final ConexionBD conexion;

    public ReservaRepository() {
        this.conexion = new ConexionBD();
    }

    public Optional<Collection<ReservaEntity>> findAll() {
        String sql = "SELECT r.*, s.code as salon_code, s.name as salon_name, s.location as salon_location " +
                "FROM reservas r " +
                "LEFT JOIN salones s ON r.salon_id = s.id";
        Collection<ReservaEntity> reservas = new LinkedList<>();
        conexion.conectar();
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ReservaEntity r = mapRow(rs);
                reservas.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Error listing reservas: " + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return reservas.isEmpty() ? Optional.empty() : Optional.of(reservas);
    }

    public Optional<ReservaEntity> findById(Integer id) {
        String sql = "SELECT r.*, s.code as salon_code, s.name as salon_name, s.location as salon_location " +
                "FROM reservas r " +
                "LEFT JOIN salones s ON r.salon_id = s.id " +
                "WHERE r.id = ?";
        conexion.conectar();
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding reserva by id: " + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return Optional.empty();
    }

    public ReservaEntity save(ReservaEntity reserva) {
        String sql = "INSERT INTO reservas(name, surname, location, people_amount, date, start_time, end_time, salon_id) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        conexion.conectar();
        try (PreparedStatement ps = conexion.getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            fillStatement(ps, reserva);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    reserva.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error saving reserva: " + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        // now re-fetch the record with its joined Salon data
        return findById(reserva.getId()).orElse(reserva);
    }

    public Optional<ReservaEntity> update(Integer id, ReservaEntity reserva) {
        String sql = "UPDATE reservas SET name=?, surname=?, location=?, people_amount=?, date=?, start_time=?, end_time=?, salon_id=? "
                + "WHERE id=?";
        conexion.conectar();
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            fillStatement(ps, reserva);
            ps.setInt(9, id);
            if (ps.executeUpdate() > 0) {
                // return the freshly joined record
                return findById(id);
            }
        } catch (SQLException e) {
            System.out.println("Error updating reserva: " + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return Optional.empty();
    }

    public boolean delete(Integer id) {
        String sql = "DELETE FROM reservas WHERE id = ?";
        conexion.conectar();
        try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting reserva: " + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return false;
    }

    private ReservaEntity mapRow(ResultSet rs) throws SQLException {
        ReservaEntity r = new ReservaEntity();
        r.setId(rs.getInt("id"));
        r.setName(rs.getString("name"));
        r.setSurname(rs.getString("surname"));
        r.setLocation(rs.getString("location"));
        r.setPeopleAmount(rs.getInt("people_amount"));
        r.setDate(rs.getDate("date"));
        r.setStartTime(rs.getString("start_time"));
        r.setEndTime(rs.getString("end_time"));

        SalonEntity s = new SalonEntity();
        s.setId(rs.getInt("salon_id"));
        // Map the salon properties from the joined table
        s.setCode(rs.getString("salon_code"));
        s.setName(rs.getString("salon_name"));
        s.setLocation(rs.getString("salon_location"));
        r.setObjSalon(s);
        return r;
    }

    private void fillStatement(PreparedStatement ps, ReservaEntity r) throws SQLException {
        ps.setString(1, r.getName());
        ps.setString(2, r.getSurname());
        ps.setString(3, r.getLocation());
        ps.setInt(4, r.getPeopleAmount());
        ps.setDate(5, r.getDate());
        ps.setString(6, r.getStartTime());
        ps.setString(7, r.getEndTime());
        ps.setInt(8, r.getObjSalon().getId());
    }
}