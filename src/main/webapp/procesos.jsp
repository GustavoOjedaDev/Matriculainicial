<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Procesos</title>
        <link rel="stylesheet" href="css/estilos.css">
    </head>
    <body>
        <%@include file="menu.jsp" %>
        <main>
            <h2>Descripción de procesos</h2>
            <table>
                <tr>
                    <th>N°</th>
                    <th>Proceso</th>
                    <th>Descripción</th>
                </tr>
                <tr>
                    <td>1</td>
                    <td>Registro del padre de familia</td>
                    <td>El padre o apoderado registra sus datos personales como nombres, apellidos, DNI, teléfono y correo.</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>Registro del niño</td>
                    <td>Se registran los datos del niño como nombres, apellidos y fecha de nacimiento para determinar la sección que le corresponde.</td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>Consulta de secciones</td>
                    <td>El padre de familia puede consultar las secciones disponibles y conocer las vacantes de cada aula.</td>
                </tr>
                <tr>
                    <td>4</td>
                    <td>Matrícula del niño</td>
                    <td>El padre realiza la matrícula en la sección correspondiente. El sistema verifica que exista una vacante.</td>
                </tr>
                <tr>
                    <td>5</td>
                    <td>Gestión de secciones</td>
                    <td>El personal de secretaría registra las secciones y establece el número máximo de alumnos por aula.</td>
                </tr>
                <tr>
                    <td>6</td>
                    <td>Anulación de matrícula</td>
                    <td>El padre o el personal de secretaría puede anular una matrícula cuando sea necesario y liberar la vacante.</td>
                </tr>
            </table>
        </main>
        <%@include file="pie.jsp" %>
    </body>
</html>