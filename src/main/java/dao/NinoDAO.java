package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import modelo.Nino;

public class NinoDAO {

    public int insertar(Nino n) throws SQLException {
        String sql = "INSERT INTO nino (id_padre, nombres, apellidos, fecha_nacimiento) VALUES (?, ?, ?, ?)";
        try (Connection cn = ConexionBD.getConexion();
                PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, n.getIdPadre());
            ps.setString(2, n.getNombres());
            ps.setString(3, n.getApellidos());
            ps.setDate(4, Date.valueOf(n.getFechaNacimiento()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    // Busca si ese niño ya fue registrado por el mismo padre (para no duplicarlo)
    public Nino buscar(int idPadre, String nombres, String apellidos, LocalDate fechaNacimiento) throws SQLException {
        String sql = "SELECT id, id_padre, nombres, apellidos, fecha_nacimiento FROM nino "
                + "WHERE id_padre = ? AND nombres = ? AND apellidos = ? AND fecha_nacimiento = ?";
        try (Connection cn = ConexionBD.getConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPadre);
            ps.setString(2, nombres);
            ps.setString(3, apellidos);
            ps.setDate(4, Date.valueOf(fechaNacimiento));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Nino n = new Nino(rs.getInt("id_padre"), rs.getString("nombres"),
                            rs.getString("apellidos"), rs.getDate("fecha_nacimiento").toLocalDate());
                    n.setId(rs.getInt("id"));
                    return n;
                }
            }
        }
        return null;
    }
}