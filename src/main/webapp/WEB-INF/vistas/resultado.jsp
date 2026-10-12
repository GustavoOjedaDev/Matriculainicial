<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Resultado</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <%@include file="/menu.jsp" %>

        <main>
            <h2>Resultado de la consulta</h2>

            <c:choose>
                <c:when test="${not empty error}">
                    <div class="mensaje error"><c:out value="${error}"/></div>
                </c:when>
                <c:when test="${empty seccion}">
                    <div class="mensaje error">
                        No hay una sección para esta edad. El niño tendrá ${edad} años al 31 de marzo
                        de ${anioEscolar} y el nivel inicial solo atiende niños de 3, 4 o 5 años (RN01).
                    </div>
                </c:when>
                <c:when test="${seccion.llena}">
                    <div class="mensaje lleno">
                        El Aula ${seccion.nombre} (${seccion.edad} años) está llena.
                        No se puede registrar la matrícula (RN02).
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="mensaje ok">
                        Al niño le corresponde el Aula ${seccion.nombre} (${seccion.edad} años).
                        Quedan ${seccion.vacantes} vacantes de ${seccion.capacidad}.
                    </div>
                </c:otherwise>
            </c:choose>

            <p><a href="inicio">Hacer otra consulta</a></p>
        </main>

        <%@include file="/pie.jsp" %>
    </body>
</html>