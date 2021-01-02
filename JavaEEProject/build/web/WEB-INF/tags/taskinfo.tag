
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="task" type="models.TaskModel"%>

<%-- any content can be specified here e.g.: --%>
<div>
    <table class="table">               
        <tr>                    
            <td>Task Name: </td>
            <td><%= task.getTaskName() %></td>
        </tr>
        <tr>                    
            <td>Description: </td>
            <td><%= task.getDescription()%></td>
        </tr>
        <tr>                    
            <td>Duration: </td>
            <td><%= task.getDuration() %></td>
        </tr>
        <tr>                    
            <td>Was Added On: </td>
            <td><%= task.getCreationDate()%></td>
        </tr>   
        <td>Last Update On: </td>
            <td><%= task.getLastUpdateDate()%></td>
        </tr> 
    </table> 
</div>
        
