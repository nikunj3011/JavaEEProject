<%-- 
    Document   : CreateJob
    Created on : Nov 24, 2020, 2:09:15 PM
    Author     : Anastasiia Egorova
--%>

<%@page import="models.TeamModel"%>
<%@page import="javaclasses.TeamUtility"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="javaclasses.JobUtility"%>
<%@page import="java.time.LocalDate"%>
<%@page import="models.TaskModel"%>
<%@page import="javaclasses.TaskUtility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="javaclasses.ClientUtility"%>
<%@page import="models.ClientModel"%>
<%@page import="models.JobModel"%>
<!DOCTYPE html>

<%
   JobModel job = JobUtility.retrieveJobFromRequest(request);

   ArrayList<ClientModel> clients = ClientUtility.selectClientsFromDB();
   ArrayList<TaskModel> tasks = TaskUtility.selectTasksFromDB();
   ArrayList<TeamModel> teams = TeamUtility.selectTeamsFromDB();
   
   TeamModel chosenTeam = TeamUtility.retrieveTeamFromRequest(request);
   if (chosenTeam.getTeamName() == null)
         chosenTeam = teams.get(0);
   
   LocalDate tomorrowDate = LocalDate.now().plusDays(1);
   
   NumberFormat formatter = NumberFormat.getCurrencyInstance();
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
      <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Create Job</title>
      </head>
      <body>
            <%@include file="WEB-INF/jspf/header.jspf" %>
            
            <h2>Create a Job</h2>
            
            <% if (job.getIsSaved()) { %>
                  
                   <h3>Job <%= job.getName() %> is created</h3>
            
            <% } else { %>
            
                  <form action="JobController" method="POST">
                        <input type="hidden" name ="id" value ='<%= job.getId()  %>' />   
                        <table>
                              <tr>
                                    <td><label>Job Name:</label></td>
                                    <td><input type="text" name="name" value='<%= job.getName()%>'/></td>
                              </tr>

                              <tr>
                                    <td><label>Job Description:</label></td>
                                    <td><textarea name="description" cols="40" rows="4"><%= job.getDescription()%></textarea></td>
                              </tr>

                              <tr>
                                    <td><label for="clients">Client:</label></td>
                                   <td>
                                         <select name="clients" id="clients">
                                               <% for (ClientModel client : clients) { %>
                                                      <% if (client.getId() == job.getClientId()) { %>
                                                            <option selected="selected" value="<%= client.getId() %>"><%= client.getName() %></option>
                                                      <% } else { %>
                                                            <option value="<%= client.getId() %>"><%= client.getName() %></option>
                                                      <% } %>
                                               <% } %>
                                         </select>
                                   </td>
                              </tr>

                               <tr>
                                    <td><label for="tasks">Tasks:</label></td>
                                   <td>
                                          <% for (TaskModel task : tasks) { %>
                                                <% if (job.getTasks() != null && job.getTasks().contains(task.getId())) { %>
                                                            <input type="checkbox"  name="task<%= task.getId() %>" value="<%= task.getId() %>" checked>
                                                            <label for="<%= task.getId() %>"> <%= task.getTaskName() + " (" + task.getDuration() +" min)" %></label><br/>
                                                <% } else { %>
                                                      <input type="checkbox"  name="task<%= task.getId() %>" value="<%= task.getId() %>" >
                                                      <label for="<%= task.getId() %>"> <%= task.getTaskName() + " (" + task.getDuration() +" min)" %></label><br/>
                                                <% } %>
                                          <% } %>
                                   </td>
                              </tr>

                              <tr>
                                    <td><label for="teams">Team:</label></td>
                                    
                                    <td>
                                          <select name="teams" id="teams">
                                                <% for (TeamModel team : teams) { %>
                                                      <% if ( job.getTeam() == team.getId()) { %>
                                                            <option value="<%= team.getId() %>" selected><%= team.getTeamName() %></option>
                                                     <% } else { %>
                                                            <option value="<%= team.getId() %>"><%= team.getTeamName() %></option>
                                                     <% } %>
                                                <% } %>
                                          </select>
                                    </td>
                                    
                                    
                              </tr>

                              <tr>
                                    <td><label for="startTime">Start Time:</label></td>
                                    <td>
                                          <input type="datetime-local" id="startTime"
                                                      name="startTime" value="<%= job.getStartTime() != null ? job.getStartTime() : ""%>"
                                                      min="<%= tomorrowDate%> + T08:00" max="2100-01-01T15:00">
                                    </td>
                              </tr>

                              <tr>
                                    <% if (job.getIsOnSite()) { %>
                                          <td><input type="checkbox"  name="onSite" value="<%= job.getIsOnSite() %>" checked> Job On Site</td>
                                    <% } else { %>
                                          <td><input type="checkbox"  name="onSite" value="<%= job.getIsOnSite() %>"> Job On Site</td>
                                    <% } %>
                              </tr>

                              <% if (job.getIsEmergencyCall()) { %>
                                    <label>Emergent Call</label>
                              <% } %>

                        </table>

                       <div class="p-4 errors">
                                  <%if (job.getErrors().size() > 0) {%>
                                    <ul class="alert alert-danger" role="alert">
                                          <% for (String err : job.getErrors()) {%>
                                                <li><%= err%></li>
                                          <%}%>
                                    </ul>
                                  <%}%>
                              </div>

                       <button name="btnCalculateCost"  class="btn btn-primary">Calculate</button>             

                       <% if (job.getIsValid()) {%>
                              <table>
                                    <tr>
                                          <td><label>Job Duration: <%= job.getDuration()  %>  mins </label></td>
                                    </tr>
                                    <tr>
                                          <td><label>Job Cost: <%= formatter.format(job.getCost()) %></label></td>
                                    </tr>
                                    <tr>
                                          <% if (job.getIsEmergencyCall()) { %>
                                                <td><label>Job Revenue: <%= formatter.format(job.getCost() * 4) %></label></td>
                                          <% } else { %>
                                                <td><label>Job Revenue: <%= formatter.format(job.getCost() * 3) %></label></td>
                                         <% } %>
                                    </tr>
                                    <tr>
                                          <% if (job.getIsEmergencyCall()) { %>
                                                <p>Emergency Call (the hours of job not within working hours interval.) The price for the call will be higher.</p>
                                          <% } %>
                                    </tr>
                              </table>
                               <button name="btnCreateJob"  class="btn btn-primary">Create Job</button>   
                       <% } %>

                        </form>
                       
           <% } %>
            
            <%@include file="WEB-INF/jspf/footer.jspf"%>
      </body>
</html>
