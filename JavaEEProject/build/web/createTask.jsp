
<%@page import="javaclasses.TaskUtility"%>
<%@page import="models.TaskModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
   TaskModel task = TaskUtility.retrieveTaskFromRequest(request);
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Task</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        
        <h2>Create Task</h2>
    <% if (task != null && task.isValid()) { %>
    <h3>Task <%= task.getTaskName() %> is saved</h3>
            
    <%  } else {  %>
    <form action="TaskController" method="post">
        <input type="hidden" name ="id" value ='<%= task.getId()  %>' />   
        <input type="hidden" name ="creationDate" value ='<%= task.getCreationDate()%>' />  
        <table class="table">
            <tr>                    
                <td>Task Name:</td>
                <td><input type="text" name="taskname" value='<%= task.getTaskName()%>'/></td>
            </tr>
            <tr>                    
                <td>Description:</td>
                <td><textarea id="description" name="description" value='<%= task.getDescription()%>' rows="5" cols="50" ></textarea></td>
            </tr>
            <tr>                    
                <td>Duration (minutes):</td>
                <td><input type="text" name="duration" value='<%= task.getDuration()%>'/></td>
            </tr>                         
        </table>

        <div class="p-4">
            <%if (task.getErrors().size() > 0) {%>
            <ul class="alert alert-danger errors" role="alert">
                <% for (String err : task.getErrors()) {%>
                <li class="errors"><%= err%></li>
                    <%}%>
            </ul>
            <%}%>
        </div>

        <button name="btnCreateTask"  class="btn btn-primary">Submit</button>
    </form> 
    <%  } %>    
        
        <%@include file="WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
