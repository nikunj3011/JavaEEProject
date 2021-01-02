<%-- 
    Document   : CreateEmployee
    Created on : 5-Nov-2020, 7:55:42 PM
    Author     : Alena Selezneva
--%>

<%@page import="javaclasses.EmployeeUtility"%>
<%@page import="models.EmployeeModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
   EmployeeModel employee = EmployeeUtility.retrieveEmployeeFromRequest(request);
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Employee</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        
        <h2>Create an Employee</h2>
    <% if (employee != null && employee.isValid()) { %>
    <h3>Employee <%= employee.getFullName() %> is saved </h3>
            
    <%  } else {  %>
    <form action="EmployeeController" method="post">
        <input type="hidden" name ="id" value ='<%= employee.getId()  %>' />   
        <input type="hidden" name ="creationDate" value ='<%= employee.getCreationDate()%>' />  
        <table class="table">
            <tr>                    
                <td> First Name</td>
                <td><input type="text" name="firstName" value='<%= employee.getFirstName()%>'/></td>
            </tr>
            <tr>                    
                <td>Last Name</td>
                <td><input type="text" name="lastName" value='<%= employee.getLastName()%>'/></td>
            </tr>
            <tr>                    
                <td>SIN</td>
                <td><input type="text" name="sin" value='<%= employee.getSin()%>'/></td>
            </tr>
            <tr>                    
                <td>Hourly Pay Rate</td>
                <td><input type="text" name="hourlyPayRate" value='<%= employee.getHourlyPayRate()%>'/></td>
            </tr>                          
        </table>

        <div class="p-4 errors">
            <%if (employee.getErrors().size() > 0) {%>
            <ul class="alert alert-danger" role="alert">
                <% for (String err : employee.getErrors()) {%>
                <li><%= err%></li>
                    <%}%>
            </ul>
            <%}%>
        </div>

        <button name="btnSaveEmployee"  class="btn btn-primary">Save</button>
    </form> 
    <%  } %>    
        
        <%@include file="WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
