<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Secciones</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <%@include file="/menu.jsp" %>

        <main>
            <%@include file="/WEB-INF/vistas/alerta.jsp" %>

            <h2>Gestión de secciones - año ${anioEscolar}</h2>

            <div class="tabla-scroll">
                <table>
                    <tr>
                        <th>Sección</th>
                        <th>Edad</th>
                        <th>Capacidad</th>
                        <th>Matriculados</th>
                        <th>Vacantes</th>
                        <th>Acción</th>
                    </tr>
                    <c:forEach var="s" items="${secciones}">
                        <tr>
                            <td>Aula <c:out value="${s.nombre}"/></td>
                            <td>${s.edad} años</td>
                            <td>${s.capacidad}</td>
                            <td>${s.matriculados}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${s.llena}"><span class="estado-anulada">Llena</span></c:when>
                                    <c:otherwise>${s.vacantes}</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <form class="enlinea" action="SeccionServlet" method="post"
                                      onsubmit="return confirm('¿Dar de baja esta sección?');">
                                    <input type="hidden" name="accion" value="baja">
                                    <input type="hidden" name="id" value="${s.id}">
                                    <button type="submit" class="btn-peligro">Dar de baja</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <h2>Crear sección</h2>
            <form action="SeccionServlet" method="post">
                <input type="hidden" name="accion" value="crear">

                <label for="nombre">Nombre del aula:</label>
                <input type="text" id="nombre" name="nombre" maxlength="30" required>

                <label for="edad">Edad:</label>
                <select id="edad" name="edad">
                    <option value="3">3 años</option>
                    <option value="4">4 años</option>
                    <option value="5">5 años</option>
                </select>

                <label for="capacidad">Máximo de alumnos:</label>
                <input type="number" id="capacidad" name="capacidad" min="1" max="60" value="25" required>

                <button type="submit">Crear sección</button>
            </form>
        </main>

        <%@include file="/pie.jsp" %>
    </body>
</html>