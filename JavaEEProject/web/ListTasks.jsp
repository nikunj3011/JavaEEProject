<%@page import="javaclasses.TaskUtility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.TaskModel"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    ArrayList<TaskModel> tasks = TaskUtility.retrieveTaskListFromRequest(request.getSession());
    ArrayList<Boolean> showFullInfo = TaskUtility.retrieveIsFullInfoShownFromRequest(request.getSession());
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List of Tasks</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <h2>All Tasks:</h2>
        
        <form action="TaskController" method="post">
        <%  for (int i = 0; i < tasks.size(); i++){  %>
            <div style="border:1px solid #ffcccc; margin: 15px; clear: both;">
                <%  if (showFullInfo.get(i))  {%>
                    <tags:taskinfo task="<%= tasks.get(i) %>"  />
                    <button type="submit" name="btnFlipShowFullInfo" value="<%= i %>"> Show Less</button>
                <% } else { %>
                    <tags:taskinfoEssential task="<%= tasks.get(i) %>" />
                    <button type="submit" name="btnFlipShowFullInfo" value="<%= i %>"> Show More</button>
                <% }  %> 
                
                <hr>
                <button type="submit" name="btnUpdateTask" value="<%= i %>"> Change </button>
                <button type="submit" name="btnDeleteTask" value="<%= i %>"> Delete </button> 
            </div>  
        <%  } %>   
        </form>
        
        <%@include file="WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
