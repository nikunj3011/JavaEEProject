
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="team" type="models.TeamModel"%>
<%@attribute name="employees" type="java.util.Collection"%>
<%-- any content can be specified here e.g.:
<%= employee %>
--%>
<div>
    <table class="table">               
        <tr>                    
            <td>Team Name: </td>
            <td><%= team.getTeamName() %></td>
        </tr>
        
        <tr>                    
            <td>Is On Call: </td>
            <td><%= team.getisOnCall()%></td>
        </tr>
        <tr>                    
            <td>Team Members: </td>
            <td><%= employees%></td>
        </tr>
        <tr>                    
            <td>Was Added On: </td>
            <td><%= team.getCreationDate()%></td>
        </tr>   
    </table>
</div>