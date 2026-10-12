package controlador;

import dto.SeccionDTO;
import facade.MatriculaFacade;
import facade.ReglaNegocioException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet(name = "MatriculaServlet", urlPatterns = {"/MatriculaServlet"})
public class MatriculaServlet extends HttpServlet {

    private final MatriculaFacade facade = new MatriculaFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("anioEscolar", MatriculaFacade.ANIO_ESCOLAR);
        request.getRequestDispatcher("/WEB-INF/vistas/matricular.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String dni = texto(request, "dniPadre");
        String nombres = texto(request, "nombres");
        String apellidos = texto(request, "apellidos");
        String fecha = texto(request, "fechaNacimiento");

        try {
            LocalDate nacimiento = LocalDate.parse(fecha);
            SeccionDTO seccion = facade.matricular(dni, nombres, apellidos, nacimiento);

            Aviso.guardar(request, "Matrícula registrada: " + nombres + " " + apellidos
                    + " quedó en el Aula " + seccion.getNombre() + " (" + seccion.getEdad() + " años).", "ok");
            response.sendRedirect(request.getContextPath() + "/ListadoServlet");
            return;
        } catch (DateTimeParseException e) {
            request.setAttribute("mensaje", "Ingresa una fecha de nacimiento válida.");
        } catch (ReglaNegocioException e) {
            request.setAttribute("mensaje", e.getMessage());
        } catch (SQLException e) {
            request.setAttribute("mensaje", "Error con la base de datos: " + e.getMessage());
        }

        // Si algo falló, volvemos al formulario con el mensaje
        request.setAttribute("tipo", "error");
        request.setAttribute("dniPadre", dni);
        request.setAttribute("nombres", nombres);
        request.setAttribute("apellidos", apellidos);
        request.setAttribute("fechaNacimiento", fecha);
        request.setAttribute("anioEscolar", MatriculaFacade.ANIO_ESCOLAR);
        request.getRequestDispatcher("/WEB-INF/vistas/matricular.jsp").forward(request, response);
    }

    private String texto(HttpServletRequest request, String nombre) {
        String valor = request.getParameter(nombre);
        return valor == null ? "" : valor.trim();
    }
}