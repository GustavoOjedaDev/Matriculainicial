package controlador;

import facade.MatriculaFacade;
import facade.ReglaNegocioException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ListadoServlet", urlPatterns = {"/ListadoServlet"})
public class ListadoServlet extends HttpServlet {

    private final MatriculaFacade facade = new MatriculaFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("matriculas", facade.listarMatriculas());
        } catch (SQLException e) {
            request.setAttribute("mensaje", "Error con la base de datos: " + e.getMessage());
            request.setAttribute("tipo", "error");
        }
        request.setAttribute("anioEscolar", MatriculaFacade.ANIO_ESCOLAR);
        request.getRequestDispatcher("/WEB-INF/vistas/listado.jsp").forward(request, response);
    }

    // Anular una matrícula (RN05: la vacante queda libre)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            facade.anularMatricula(id);
            Aviso.guardar(request, "Matrícula anulada. La vacante quedó disponible.", "ok");
        } catch (NumberFormatException e) {
            Aviso.guardar(request, "La matrícula indicada no es válida.", "error");
        } catch (ReglaNegocioException e) {
            Aviso.guardar(request, e.getMessage(), "error");
        } catch (SQLException e) {
            Aviso.guardar(request, "Error con la base de datos: " + e.getMessage(), "error");
        }
        response.sendRedirect(request.getContextPath() + "/ListadoServlet");
    }
}