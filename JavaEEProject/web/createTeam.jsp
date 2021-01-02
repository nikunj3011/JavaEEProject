<%@page import="javaclasses.TeamUtility"%>
<%@page import="models.TeamModel"%>
<%@page import="javaclasses.EmployeeUtility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.EmployeeModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
   TeamModel team = TeamUtility.retrieveTeamFromRequest(request);
   ArrayList<EmployeeModel> employees = EmployeeUtility.selectEmployeesFromDB();
%>

<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Team</title>
    </head>
    <body>
    <%@include file="WEB-INF/jspf/header.jspf" %>
        
        <h2>Create Team</h2>
    <% if (team != null && team.isValid()) { %>
    <h3>Team <%= team.getTeamName() %> is saved</h3>
            
    <%  } else {  %>
    
    <form action="TeamController" method="post">
 
        <input type="hidden" name ="id" value ='<%= team.getId()  %>' />   
        <input type="hidden" name ="creationDate" value ='<%= team.getCreationDate()%>' />  
        <table class="table">
            <tr>                    
                <td>Team Name:</td>
                <td><input type="text" name="teamname" value='<%= team.getTeamName()%>'/></td>
            </tr>   
            <tr>                    
                <td>Is Currently on Call:</td>
                <td><input type="checkbox"  name="isOnCall" value="<%= team.getisOnCall()%>" checked> </td>
            </tr>
            <tr>
                <td><label for="employees">Employee: </label></td>
                <td>
                    <% for (EmployeeModel employee : employees) { %> 
                        <input type="checkbox"  name="employee<%= employee.getId() %>" value="<%= employee.getId() %>" >
                        <label for="<%= employee.getId() %>"> <%= employee.getFirstName() + "  " + employee.getLastName() %></label><br/>
                                       
                    <% } %>
                    
                    
                </select>
                </td>                                   
                                  
            </tr>  
            <tr>
                                    
        </table>
        
        <div class="p-4">
            <%if (team.getErrors().size() > 0) {%>
            <ul class="alert alert-danger errors" role="alert">
                <% for (String err : team.getErrors()) {%>
                <li class="errors"><%= err%></li>
                    <%}%>
            </ul>
            <%}%>
        </div>
        <button name="btnCreateTeam"  class="btn btn-primary">Submit</button>
    </form> 
         
    <%  } %>    
        
        <%@include file="WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
