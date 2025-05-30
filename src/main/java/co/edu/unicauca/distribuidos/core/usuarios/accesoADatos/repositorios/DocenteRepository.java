package co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.repositorios;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import co.edu.unicauca.distribuidos.core.config.ConexionBD;
import co.edu.unicauca.distribuidos.core.usuarios.accesoADatos.modelos.DocenteEntity;
import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class DocenteRepository {

    private ConexionBD conexionABaseDeDatos;

    // Listar todos los salones
    public Optional<Collection<DocenteEntity>> findAll() {
        System.out.println("Listando docentes desde la base de datos...");
        Collection<DocenteEntity> salones = new LinkedList<>();

        
        try {
            conexionABaseDeDatos.conectar();
            PreparedStatement sentencia = null;
            String consulta = "SELECT * FROM docentes";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            ResultSet res = sentencia.executeQuery();

            while (res.next()) {
                DocenteEntity docente = new DocenteEntity();
                docente.setId(res.getInt("id"));
                docente.setNombre(res.getString("nombre"));
                docente.setApellido(res.getString("apellido"));
                docente.setCorreo(res.getString("correo"));
                //docente.setTelefono(res.getString("telefono"));
                docente.setFechaNacimiento(res.getDate("fecha_nacimiento"));
                docente.setFechaIngreso(res.getDate("fecha_ingreso"));
                docente.setFechaRetiro(res.getDate("fecha_retiro"));
                //docente.add(docente);
            }
            sentencia.close();
            conexionABaseDeDatos.desconectar();
        } catch (SQLException e) {
            System.out.println("Error en la consulta de salones: " + e.getMessage());
        }

        return salones.isEmpty() ? Optional.empty() : Optional.of(salones);
    }
}