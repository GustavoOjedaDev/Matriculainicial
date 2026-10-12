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

@WebServlet(name = "SeccionServlet", urlPatterns = {"/SeccionServlet"})
public class SeccionServlet extends HttpServlet {

    private final MatriculaFacade facade = new MatriculaFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("secciones", facade.listarSecciones());
        } catch (SQLException e) {
            request.setAttribute("mensaje", "Error con la base de datos: " + e.getMessage());
            request.setAttribute("tipo", "error");
        }
        request.setAttribute("anioEscolar", MatriculaFacade.ANIO_ESCOLAR);
        request.getRequestDispatcher("/WEB-INF/vistas/secciones.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        try {
            if ("crear".equals(accion)) {
                String nombre = request.getParameter("nombre") == null ? "" : request.getParameter("nombre").trim();
                int edad = Integer.parseInt(request.getParameter("edad"));
                int capacidad = Integer.parseInt(request.getParameter("capacidad"));
                facade.crearSeccion(nombre, edad, capacidad);
                Aviso.guardar(request, "Sección Aula " + nombre + " creada.", "ok");
            } else if ("baja".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                facade.darBajaSeccion(id);
                Aviso.guardar(request, "La sección fue dada de baja.", "ok");
            }
        } catch (NumberFormatException e) {
            Aviso.guardar(request, "Ingresa números válidos en edad y capacidad.", "error");
        } catch (ReglaNegocioException e) {
            Aviso.guardar(request, e.getMessage(), "error");
        } catch (SQLException e) {
            Aviso.guardar(request, "Error con la base de datos: " + e.getMessage(), "error");
        }
        response.sendRedirect(request.getContextPath() + "/SeccionServlet");
    }
}