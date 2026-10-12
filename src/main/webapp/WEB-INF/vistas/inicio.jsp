<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Inicio - Matrícula Inicial</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <%@include file="/menu.jsp" %>

        <main>
            <%@include file="/WEB-INF/vistas/alerta.jsp" %>

            <h2>Matrícula en el nivel inicial</h2>
            <p>Registra a tu hijo y matricúlalo en la sección que le corresponde según su edad (3, 4 o 5 años).</p>

            <h2>Aulas y vacantes ${anioEscolar}</h2>
            <div class="aulas">
                <c:forEach var="s" items="${secciones}">
                    <div class="aula ${s.nombre.toLowerCase()}">
                        <h3>Aula <c:out value="${s.nombre}"/></h3>
                        <p>${s.edad} años<br>
                            <c:choose>
                                <c:when test="${s.llena}">Aula llena (${s.capacidad} de ${s.capacidad})</c:when>
                                <c:otherwise>Vacantes: ${s.vacantes} de ${s.capacidad}</c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </c:forEach>
                <c:if test="${empty secciones}">
                    <p>No hay secciones para mostrar.</p>
                </c:if>
            </div>

            <h2>Consultar sección</h2>
            <form action="ConsultaServlet" method="post">
                <label for="fechaNacimiento">Fecha de nacimiento del niño:</label>
                <input type="date" id="fechaNacimiento" name="fechaNacimiento" required>
                <button type="submit">Consultar sección</button>
            </form>
        </main>

        <%@include file="/pie.jsp" %>
    </body>
</html>