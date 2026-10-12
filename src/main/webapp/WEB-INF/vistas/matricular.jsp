<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Matricular</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <%@include file="/menu.jsp" %>

        <main>
            <%@include file="/WEB-INF/vistas/alerta.jsp" %>

            <h2>Matrícula del niño - año ${anioEscolar}</h2>
            <p>El padre debe estar registrado. La sección se asigna según la edad del niño al 31 de marzo de ${anioEscolar}.</p>

            <form action="MatriculaServlet" method="post">
                <label for="dniPadre">DNI del padre o apoderado:</label>
                <input type="text" id="dniPadre" name="dniPadre" maxlength="8" pattern="[0-9]{8}" required
                       title="8 números" value="<c:out value='${dniPadre}'/>">

                <label for="nombres">Nombres del niño:</label>
                <input type="text" id="nombres" name="nombres" maxlength="60" required
                       value="<c:out value='${nombres}'/>">

                <label for="apellidos">Apellidos del niño:</label>
                <input type="text" id="apellidos" name="apellidos" maxlength="60" required
                       value="<c:out value='${apellidos}'/>">

                <label for="fechaNacimiento">Fecha de nacimiento:</label>
                <input type="date" id="fechaNacimiento" name="fechaNacimiento" required
                       value="<c:out value='${fechaNacimiento}'/>">

                <button type="submit">Matricular</button>
            </form>
        </main>

        <%@include file="/pie.jsp" %>
    </body>
</html>