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
import modelo.Padre;

@WebServlet(name = "PadreServlet", urlPatterns = {"/PadreServlet"})
public class PadreServlet extends HttpServlet {

    private final MatriculaFacade facade = new MatriculaFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/vistas/padre.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Padre padre = new Padre(
                texto(request, "nombres"),
                texto(request, "apellidos"),
                texto(request, "dni"),
                texto(request, "telefono"),
                texto(request, "correo"));

        try {
            facade.registrarPadre(padre);
            Aviso.guardar(request, "Padre registrado: " + padre.getNombres() + " " + padre.getApellidos()
                    + ". Ya puedes matricular a su hijo.", "ok");
            response.sendRedirect(request.getContextPath() + "/PadreServlet");
        } catch (ReglaNegocioException e) {
            volverConError(request, response, padre, e.getMessage());
        } catch (SQLException e) {
            volverConError(request, response, padre, "Error con la base de datos: " + e.getMessage());
        }
    }

    private void volverConError(HttpServletRequest request, HttpServletResponse response,
            Padre padre, String mensaje) throws ServletException, IOException {
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("tipo", "error");
        request.setAttribute("padre", padre);   // para que el formulario conserve lo escrito
        request.getRequestDispatcher("/WEB-INF/vistas/padre.jsp").forward(request, response);
    }

    private String texto(HttpServletRequest request, String nombre) {
        String valor = request.getParameter(nombre);
        return valor == null ? "" : valor.trim();
    }
}