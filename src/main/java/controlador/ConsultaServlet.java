package controlador;

import dto.SeccionDTO;
import facade.MatriculaFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet(name = "ConsultaServlet", urlPatterns = {"/ConsultaServlet"})
public class ConsultaServlet extends HttpServlet {

    private final MatriculaFacade facade = new MatriculaFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Si entran directo al servlet, los mandamos al inicio
        response.sendRedirect(request.getContextPath() + "/inicio");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Recibimos el parámetro del formulario
        String fecha = request.getParameter("fechaNacimiento");
        request.setAttribute("anioEscolar", MatriculaFacade.ANIO_ESCOLAR);

        if (fecha == null || fecha.trim().isEmpty()) {
            request.setAttribute("error", "Ingresa la fecha de nacimiento del niño.");
        } else {
            try {
                LocalDate nacimiento = LocalDate.parse(fecha);
                if (nacimiento.isAfter(LocalDate.now())) {
                    request.setAttribute("error", "La fecha de nacimiento no puede ser futura.");
                } else {
                    // 2. Pedimos la información a la fachada
                    int edad = facade.calcularEdad(nacimiento);
                    SeccionDTO seccion = facade.consultarSeccion(nacimiento);

                    // 3. Enviamos los datos al JSP con attributes
                    request.setAttribute("edad", edad);
                    request.setAttribute("seccion", seccion);
                }
            } catch (DateTimeParseException e) {
                request.setAttribute("error", "La fecha no tiene un formato válido.");
            } catch (SQLException e) {
                request.setAttribute("error", "Error con la base de datos: " + e.getMessage());
            }
        }
        request.getRequestDispatcher("/WEB-INF/vistas/resultado.jsp").forward(request, response);
    }
}