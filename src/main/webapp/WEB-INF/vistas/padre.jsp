<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Registrar padre</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <%@include file="/menu.jsp" %>

        <main>
            <%@include file="/WEB-INF/vistas/alerta.jsp" %>

            <h2>Registro del padre de familia o apoderado</h2>
            <form action="PadreServlet" method="post">
                <label for="nombres">Nombres:</label>
                <input type="text" id="nombres" name="nombres" maxlength="60" required
                       value="<c:out value='${padre.nombres}'/>">

                <label for="apellidos">Apellidos:</label>
                <input type="text" id="apellidos" name="apellidos" maxlength="60" required
                       value="<c:out value='${padre.apellidos}'/>">

                <label for="dni">DNI:</label>
                <input type="text" id="dni" name="dni" maxlength="8" pattern="[0-9]{8}" required
                       title="8 números" value="<c:out value='${padre.dni}'/>">

                <label for="telefono">Teléfono:</label>
                <input type="tel" id="telefono" name="telefono" maxlength="9" pattern="[0-9]{7,9}" required
                       title="Entre 7 y 9 números" value="<c:out value='${padre.telefono}'/>">

                <label for="correo">Correo:</label>
                <input type="email" id="correo" name="correo" maxlength="80" required
                       value="<c:out value='${padre.correo}'/>">

                <button type="submit">Registrar padre</button>
            </form>
        </main>

        <%@include file="/pie.jsp" %>
    </body>
</html>