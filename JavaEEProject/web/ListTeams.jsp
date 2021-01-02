<%@page import="models.EmployeeModel"%>
<%@page import="factories.EmployeeFactory"%>
<%@page import="Interfaces.IEmployeeRepo"%>
<%@page import="javaclasses.EmployeeUtility"%>
<%@page import="models.TaskModel"%>
<%@page import="javaclasses.TeamUtility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.TeamModel"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    ArrayList<TeamModel> teams = TeamUtility.retrieveTeamListFromRequest(request.getSession());
    ArrayList<Boolean> showFullInfo = TeamUtility.retrieveIsFullInfoShownFromRequest(request.getSession());
    IEmployeeRepo employees = EmployeeFactory.makeEmployeeRepo();  
    ArrayList<EmployeeModel> employee = EmployeeUtility.retrieveEmployeeListFromSession(request.getSession());
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List of Teams</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <h2>All Teams:</h2>
        
        <form action="TeamController" method="post"> 
            
        <%  for (int i = 0; i < teams.size(); i++){  %>
        
            <div style="border:1px solid #ffcccc; margin: 15px; clear: both;">
                <%  if (showFullInfo.get(i))  {%>  
                <% ArrayList<String> teamMember = new ArrayList<String>();%>
                    <% for (int j : teams.get(i).getTeamMembers()) { %>
                        <%teamMember.add(employees.getEmployee(j).getFirstName() +" "+ employees.getEmployee(j).getLastName());%>
                <% } %>
                
                <tags:teaminfo team="<%= teams.get(i) %>"  employees="<%= teamMember %>"/>
                    <button type="submit" name="btnFlipShowFullInfo" value="<%= i %>"> Show Less</button>
                <% } else { %>
                <tags:teaminfoEssential team="<%= teams.get(i) %>"/>
                    <button type="submit" name="btnFlipShowFullInfo" value="<%= i %>"> Show More</button>
                <% }  %> 
                
                <hr>
                <button type="submit" name="btnUpdateTeam" value="<%= i %>"> Change </button>
                <button type="submit" name="btnDeleteTeam" value="<%= i %>"> Delete </button> 
            </div>  
        <%  } %>   
        </form>
        
        <%@include file="WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
