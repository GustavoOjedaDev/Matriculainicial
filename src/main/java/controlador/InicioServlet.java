package controlador;

import facade.MatriculaFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "InicioServlet", urlPatterns = {"/inicio"})
public class InicioServlet extends HttpServlet {

    private final MatriculaFacade facade = new MatriculaFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("secciones", facade.listarSecciones());
        } catch (SQLException e) {
            request.setAttribute("mensaje", "No se pudo conectar con la base de datos: " + e.getMessage());
            request.setAttribute("tipo", "error");
        }
        request.setAttribute("anioEscolar", MatriculaFacade.ANIO_ESCOLAR);
        request.getRequestDispatcher("/WEB-INF/vistas/inicio.jsp").forward(request, response);
    }
}