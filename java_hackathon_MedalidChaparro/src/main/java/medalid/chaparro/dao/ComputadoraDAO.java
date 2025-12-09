package medalid.chaparro.dao;

import medalid.chaparro.model.Computadora;
import medalid.chaparro.database.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComputadoraDAO {

    public boolean insertar(Computadora c) {
        String sql = "INSERT INTO computadoras (tipo_equipo, marca, modelo, sistema_operativo, ram, almacenamiento, fecha_mantenimiento, fecha_registro, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getTipoEquipo());
            stmt.setString(2, c.getMarca());
            stmt.setString(3, c.getModelo());
            stmt.setString(4, c.getSistemaOperativo());
            stmt.setInt(5, c.getRam());
            stmt.setInt(6, c.getAlmacenamiento());
            stmt.setDate(7, c.getFechaMantenimiento());
            stmt.setDate(8, c.getFechaRegistro());
            stmt.setString(9, c.getEstado());

            stmt.executeUpdate();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public List<Computadora> listarActivos() {
        List<Computadora> lista = new ArrayList<>();
        String sql = "SELECT * FROM computadoras WHERE estado='activo'";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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
                lista.add(c);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public Computadora buscarPorId(int id) {
        String sql = "SELECT * FROM computadoras WHERE id=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
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
                return c;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

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
            stmt.setInt(9, c.getId());

            stmt.executeUpdate();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean eliminarLogico(int id) {
        String sql = "UPDATE computadoras SET estado='inactivo' WHERE id=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}