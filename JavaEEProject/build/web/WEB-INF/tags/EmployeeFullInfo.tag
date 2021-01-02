<%-- 
    Document   : EmployeeFullInfo
    Created on : 9-Nov-2020, 8:32:44 PM
    Author     : Alena Selezneva
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="employee" type="models.EmployeeModel"%>

<%-- any content can be specified here e.g.: --%>
<div>
    <table class="table">               
        <tr>                    
            <td>First Name: </td>
            <td><%= employee.getFirstName() %></td>
        </tr>
        <tr>                    
            <td>Last Name: </td>
            <td><%= employee.getLastName()%></td>
        </tr>
        <tr>                    
            <td>SIN: </td>
            <!-- work with SIN, change part of it to * -->
            <td><%= employee.getSin() %></td>
        </tr>
        <tr>                    
            <td>Hourly Rate: </td>
            <td><%= employee.getHourlyPayRate()%></td>
        </tr>
        <tr>                    
            <td>Was Added On: </td>
            <td><%= employee.getCreationDate()%></td>
        </tr>   
        <tr>                    
            <td>Last Update On: </td>
            <td><%= employee.getLastUpdateDate()%></td>
        </tr> 
    </table>
    <% if (employee.isPartOfTeam()){  %>
        <h4>Teams: </h4>
        <ul>
        <% for (int i = 0; i < employee.getTeamNames().size(); i++) { %>
            <li><%=employee.getTeam(i)%>    </li>
        <% } %>   
        </ul>
    <% } %>
    <% if (employee.hasSkills()){  %>
        <h4>Skills: </h4>
        <ul>
        <% for (int i = 0; i < employee.getSkillNames().size(); i++) { %>
            <li><%=employee.getSkill(i)%>  </li>  
        <% } %>   
        </ul>
    <% } %>
</div>