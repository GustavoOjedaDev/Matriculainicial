package dao;

import dto.MatriculaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {

    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public void insertar(int idNino, int idSeccion, int anio) throws SQLException {
        String sql = "INSERT INTO matricula (id_nino, id_seccion, anio_escolar) VALUES (?, ?, ?)";
        try (Connection cn = ConexionBD.getConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idNino);
            ps.setInt(2, idSeccion);
            ps.setInt(3, anio);
            ps.executeUpdate();
        }
    }

    // RN03: ¿el niño ya tiene una matrícula activa este año?
    public boolean existeActiva(int idNino, int anio) throws SQLException {
        String sql = "SELECT COUNT(*) FROM matricula WHERE id_nino = ? AND anio_escolar = ? AND estado = 'ACTIVA'";
        try (Connection cn = ConexionBD.getConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idNino);
            ps.setInt(2, anio);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public List<MatriculaDTO> listar(int anio) throws SQLException {
        String sql = "SELECT m.id, CONCAT(n.nombres, ' ', n.apellidos) AS nino, n.fecha_nacimiento, "
                + "CONCAT(p.nombres, ' ', p.apellidos) AS padre, p.dni, "
                + "s.nombre AS seccion, s.edad, m.estado, m.fecha_matricula "
                + "FROM matricula m "
                + "JOIN nino n ON n.id = m.id_nino "
                + "JOIN padre p ON p.id = n.id_padre "
                + "JOIN seccion s ON s.id = m.id_seccion "
                + "WHERE m.anio_escolar = ? "
                + "ORDER BY m.id DESC";
        List<MatriculaDTO> lista = new ArrayList<>();
        try (Connection cn = ConexionBD.getConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, anio);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new MatriculaDTO(
                            rs.getInt("id"),
                            rs.getString("nino"),
                            rs.getDate("fecha_nacimiento").toLocalDate().format(FECHA),
                            rs.getString("padre"),
                            rs.getString("dni"),
                            rs.getString("seccion"),
                            rs.getInt("edad"),
                            rs.getString("estado"),
                            rs.getTimestamp("fecha_matricula").toLocalDateTime().format(FECHA_HORA)));
                }
            }
        }
        return lista;
    }

    // RN05: al anular, la matrícula deja de contar y la vacante queda libre
    public int anular(int id) throws SQLException {
        String sql = "UPDATE matricula SET estado = 'ANULADA', fecha_anulacion = NOW() "
                + "WHERE id = ? AND estado = 'ACTIVA'";
        try (Connection cn = ConexionBD.getConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }
}