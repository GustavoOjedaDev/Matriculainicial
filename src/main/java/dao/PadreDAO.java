package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Padre;

public class PadreDAO {

    public int insertar(Padre p) throws SQLException {
        String sql = "INSERT INTO padre (nombres, apellidos, dni, telefono, correo) VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = ConexionBD.getConexion();
                PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombres());
            ps.setString(2, p.getApellidos());
            ps.setString(3, p.getDni());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getCorreo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public Padre buscarPorDni(String dni) throws SQLException {
        String sql = "SELECT id, nombres, apellidos, dni, telefono, correo FROM padre WHERE dni = ?";
        try (Connection cn = ConexionBD.getConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Padre p = new Padre(rs.getString("nombres"), rs.getString("apellidos"),
                            rs.getString("dni"), rs.getString("telefono"), rs.getString("correo"));
                    p.setId(rs.getInt("id"));
                    return p;
                }
            }
        }
        return null;
    }

    public boolean existeCorreo(String correo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM padre WHERE correo = ?";
        try (Connection cn = ConexionBD.getConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }
}