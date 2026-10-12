<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Matrículas</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <%@include file="/menu.jsp" %>

        <main>
            <%@include file="/WEB-INF/vistas/alerta.jsp" %>

            <h2>Matrículas del año ${anioEscolar}</h2>

            <c:choose>
                <c:when test="${empty matriculas}">
                    <p>Todavía no hay matrículas registradas.</p>
                </c:when>
                <c:otherwise>
                    <div class="tabla-scroll">
                        <table>
                            <tr>
                                <th>N°</th>
                                <th>Niño</th>
                                <th>Nacimiento</th>
                                <th>Padre / apoderado</th>
                                <th>Sección</th>
                                <th>Fecha</th>
                                <th>Estado</th>
                                <th>Acción</th>
                            </tr>
                            <c:forEach var="m" items="${matriculas}">
                                <tr>
                                    <td>${m.id}</td>
                                    <td><c:out value="${m.nino}"/></td>
                                    <td>${m.fechaNacimiento}</td>
                                    <td><c:out value="${m.padre}"/><br>DNI ${m.dni}</td>
                                    <td>Aula <c:out value="${m.seccion}"/> (${m.edad} años)</td>
                                    <td>${m.fechaMatricula}</td>
                                    <td class="${m.activa ? 'estado-activa' : 'estado-anulada'}">${m.estado}</td>
                                    <td>
                                        <c:if test="${m.activa}">
                                            <form class="enlinea" action="ListadoServlet" method="post"
                                                  onsubmit="return confirm('¿Anular esta matrícula?');">
                                                <input type="hidden" name="id" value="${m.id}">
                                                <button type="submit" class="btn-peligro">Anular</button>
                                            </form>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>

        <%@include file="/pie.jsp" %>
    </body>
</html>