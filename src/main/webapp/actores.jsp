<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Actores</title>
        <link rel="stylesheet" href="css/estilos.css">
    </head>
    <body>
        <%@include file="menu.jsp" %>
        <main>
            <h2>Actores de los procesos</h2>
            <table>
                <tr>
                    <th>Actor</th>
                    <th>Descripción</th>
                </tr>
                <tr>
                    <td>Padre de familia o apoderado</td>
                    <td>Se registra en el sistema. Registra los datos de su hijo y realiza la matrícula o su anulación.</td>
                </tr>
                <tr>
                    <td>Personal de secretaría</td>
                    <td>Registra las secciones. Establece las vacantes y supervisa las matrículas realizadas.</td>
                </tr>
                <tr>
                    <td>Director o administrador</td>
                    <td>Supervisa el proceso general de matrícula y administra el acceso de los usuarios al sistema.</td>
                </tr>
            </table>
        </main>
        <%@include file="pie.jsp" %>
    </body>
</html>