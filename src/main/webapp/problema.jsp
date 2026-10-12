<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Problema</title>
        <link rel="stylesheet" href="css/estilos.css">
    </head>
    <body>
        <%@include file="menu.jsp" %>
        <main>
            <h2>Presentación del problema</h2>
            <p>En un colegio de nivel inicial el proceso de matrícula se puede realizar de forma manual.
               Los padres de familia tienen que acercarse a secretaría para llenar una ficha con los datos
               del niño. Luego el personal encargado revisa si existen vacantes en las aulas de 3, 4 o 5 años.</p>
            <p>Este proceso puede generar demoras durante la época de matrícula. También pueden existir
               errores al momento de controlar las vacantes disponibles y se podría llegar a matricular a un
               niño en un aula que ya alcanzó su capacidad máxima.</p>

            <h2>Objetivo del sistema</h2>
            <p>El objetivo del sistema es facilitar y automatizar el proceso de matrícula del nivel inicial.
               Los padres de familia podrán registrar sus datos y los de sus hijos y realizar la matrícula en
               la sección que les corresponde. También permitirá que el personal de secretaría controle las
               vacantes disponibles en cada aula y mantenga la información más ordenada.</p>

            <h2>Alcance del avance 1</h2>
            <p>El sistema se plantea como una aplicación web con arquitectura Java EE. Para la interfaz se
               utilizarán JSP, HTML5 y CSS3, y para la conexión con la base de datos se utilizará JDBC.
               El desarrollo se realiza en NetBeans con Apache Tomcat.</p>
        </main>
        <%@include file="pie.jsp" %>
    </body>
</html>