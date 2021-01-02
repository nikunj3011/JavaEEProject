<%-- 
    Document   : error
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <H1>ERROR PAGE</H1>
        <!-- This displays the fully-qualified name of the exception and its message--> 
        <%= exception.toString() %> 
        <br> 
        <!-- This displays the exception's descriptive message --> 
        <%= exception.getMessage() %><br />
        <a href="index.jsp">Home</a>
    </body>
</html>
