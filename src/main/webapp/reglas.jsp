<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Reglas de negocio</title>
        <link rel="stylesheet" href="css/estilos.css">
    </head>
    <body>
        <%@include file="menu.jsp" %>
        <main>
            <h2>Reglas de negocio</h2>
            <table>
                <tr>
                    <th>Código</th>
                    <th>Regla de negocio</th>
                </tr>
                <tr>
                    <td>RN01</td>
                    <td>Un niño solo puede matricularse en la sección que corresponde según su edad: 3, 4 o 5 años.</td>
                </tr>
                <tr>
                    <td>RN02</td>
                    <td>No se puede registrar una matrícula si la sección ya alcanzó el número máximo de vacantes.</td>
                </tr>
                <tr>
                    <td>RN03</td>
                    <td>Un niño no puede tener dos matrículas activas en el mismo año escolar.</td>
                </tr>
                <tr>
                    <td>RN04</td>
                    <td>Una sección aparece como llena cuando llega al número máximo de alumnos.</td>
                </tr>
                <tr>
                    <td>RN05</td>
                    <td>Al anular una matrícula la vacante queda disponible nuevamente.</td>
                </tr>
                <tr>
                    <td>RN06</td>
                    <td>Solo el personal de secretaría puede crear, editar o dar de baja una sección.</td>
                </tr>
            </table>
        </main>
        <%@include file="pie.jsp" %>
    </body>
</html>