<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- Muestra el mensaje (de request o de la sesión) una sola vez --%>
<c:if test="${not empty mensaje}">
    <div class="mensaje ${tipo}" role="alert"><c:out value="${mensaje}"/></div>
    <c:remove var="mensaje" scope="session"/>
    <c:remove var="tipo" scope="session"/>
</c:if>