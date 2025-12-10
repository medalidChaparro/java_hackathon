package medalid.chaparro.dao;

import medalid.chaparro.model.Computadora;
import medalid.chaparro.database.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComputadoraDAO {

    // INSERTAR UNA NUEVA COMPUTADORA
    public boolean insertar(Computadora c) {
        // Consulta SQL para insertar datos
        String sql = "INSERT INTO computadoras (tipo_equipo, marca, modelo, sistema_operativo, ram, almacenamiento, fecha_mantenimiento, fecha_registro, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // try-with-resources → cierra conexión y statement automáticamente
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Asignando cada valor al placeholder ?
            stmt.setString(1, c.getTipoEquipo());
            stmt.setString(2, c.getMarca());
            stmt.setString(3, c.getModelo());
            stmt.setString(4, c.getSistemaOperativo());
            stmt.setInt(5, c.getRam());
            stmt.setInt(6, c.getAlmacenamiento());
            stmt.setDate(7, c.getFechaMantenimiento());
            stmt.setDate(8, c.getFechaRegistro());
            stmt.setString(9, c.getEstado());

            stmt.executeUpdate(); // Ejecuta el INSERT
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // LISTAR COMPUTADORAS ACTIVAS
    public List<Computadora> listarActivos() {
        List<Computadora> lista = new ArrayList<>();
        String sql = "SELECT * FROM computadoras WHERE estado='activo'";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Convertir cada fila de BD en un objeto Computadora
            while (rs.next()) {
                Computadora c = new Computadora();
                c.setId(rs.getInt("id"));
                c.setTipoEquipo(rs.getString("tipo_equipo"));
                c.setMarca(rs.getString("marca"));
                c.setModelo(rs.getString("modelo"));
                c.setSistemaOperativo(rs.getString("sistema_operativo"));
                c.setRam(rs.getInt("ram"));
                c.setAlmacenamiento(rs.getInt("almacenamiento"));
                c.setFechaMantenimiento(rs.getDate("fecha_mantenimiento"));
                c.setFechaRegistro(rs.getDate("fecha_registro"));
                c.setEstado(rs.getString("estado"));
                lista.add(c); // Agregar a la lista final
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // BUSCAR POR ID
    public Computadora buscarPorId(int id) {
        String sql = "SELECT * FROM computadoras WHERE id=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id); // Pasar ID a la consulta
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Computadora c = new Computadora();
                c.setId(rs.getInt("id"));
                c.setTipoEquipo(rs.getString("tipo_equipo"));
                c.setMarca(rs.getString("marca"));
                c.setModelo(rs.getString("modelo"));
                c.setSistemaOperativo(rs.getString("sistema_operativo"));
                c.setRam(rs.getInt("ram"));
                c.setAlmacenamiento(rs.getInt("almacenamiento"));
                c.setFechaMantenimiento(rs.getDate("fecha_mantenimiento"));
                c.setFechaRegistro(rs.getDate("fecha_registro"));
                c.setEstado(rs.getString("estado"));
                return c; // Devuelve el objeto encontrado
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentra nada
    }

    // ACTUALIZAR COMPUTADORA
    public boolean actualizar(Computadora c) {
        String sql = "UPDATE computadoras SET tipo_equipo=?, marca=?, modelo=?, sistema_operativo=?, ram=?, almacenamiento=?, fecha_mantenimiento=?, estado=? WHERE id=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getTipoEquipo());
            stmt.setString(2, c.getMarca());
            stmt.setString(3, c.getModelo());
            stmt.setString(4, c.getSistemaOperativo());
            stmt.setInt(5, c.getRam());
            stmt.setInt(6, c.getAlmacenamiento());
            stmt.setDate(7, c.getFechaMantenimiento());
            stmt.setString(8, c.getEstado());
            stmt.setInt(9, c.getId()); // Identifica qué registro actualizar

            stmt.executeUpdate(); // Ejecuta el UPDATE
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINACIÓN LÓGICA (NO BORRA LA FILA)
    public boolean eliminarLogico(int id) {
        String sql = "UPDATE computadoras SET estado='inactivo' WHERE id=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id); // ID a desactivar
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}