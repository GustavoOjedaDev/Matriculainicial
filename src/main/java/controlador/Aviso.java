package controlador;

import jakarta.servlet.http.HttpServletRequest;

/** Guarda en la sesión un mensaje que se muestra una sola vez en la siguiente página. */
public class Aviso {

    public static void guardar(HttpServletRequest request, String mensaje, String tipo) {
        request.getSession().setAttribute("mensaje", mensaje);
        request.getSession().setAttribute("tipo", tipo);
    }
}