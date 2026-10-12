package dao;

import dto.SeccionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Seccion;

public class SeccionDAO {

    // Cuenta solo las matrículas ACTIVAS del año escolar (así, al anular, la vacante se libera: RN05)
    private static final String SQL_BASE
            = "SELECT s.id, s.nombre, s.edad, s.capacidad, COUNT(m.id) AS matriculados "
            + "FROM seccion s "
            + "LEFT JOIN matricula m ON m.id_seccion = s.id AND m.estado = 'ACTIVA' AND m.anio_escolar = ? "
            + "WHERE s.estado = 'ACTIVA' ";

    private static final String SQL_GRUPO = "GROUP BY s.id, s.nombre, s.edad, s.capacidad ";

    public List<SeccionDTO> listar(int anio) throws SQLException {
        String sql = SQL_BASE + SQL_GRUPO + "ORDER BY s.edad, s.nombre";
        List<SeccionDTO> lista = new ArrayList<>();
        try (Connection cn = ConexionBD.getConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, anio);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(armar(rs));
                }
            }
        }
        return lista;
    }

    // Si hay varias secciones de la misma edad, devuelve la que tiene más vacantes
    public SeccionDTO buscarPorEdad(int edad, int anio) throws SQLException {
        String sql = SQL_BASE + "AND s.edad = ? " + SQL_GRUPO
                + "ORDER BY (s.capacidad - COUNT(m.id)) DESC LIMIT 1";
        try (Connection cn = ConexionBD.getConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, anio);
            ps.setInt(2, edad);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return armar(rs);
                }
            }
        }
        return null;
    }

    public SeccionDTO buscarPorId(int id, int anio) throws SQLException {
        String sql = SQL_BASE + "AND s.id = ? " + SQL_GRUPO;
        try (Connection cn = ConexionBD.getConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, anio);
            ps.setInt(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return armar(rs);
                }
            }
        }
        return null;
    }

    public void insertar(Seccion s) throws SQLException {
        String sql = "INSERT INTO seccion (nombre, edad, capacidad) VALUES (?, ?, ?)";
        try (Connection cn = ConexionBD.getConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, s.getNombre());
            ps.setInt(2, s.getEdad());
            ps.setInt(3, s.getCapacidad());
            ps.executeUpdate();
        }
    }

    public void darDeBaja(int id) throws SQLException {
        String sql = "UPDATE seccion SET estado = 'BAJA' WHERE id = ?";
        try (Connection cn = ConexionBD.getConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private SeccionDTO armar(ResultSet rs) throws SQLException {
        return new SeccionDTO(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getInt("edad"),
                rs.getInt("capacidad"),
                rs.getInt("matriculados"));
    }
}