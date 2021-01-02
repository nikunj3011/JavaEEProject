<%-- 
    Document   : Dashboard
    Created on : Dec 10, 2020, 6:27:47 PM
    Author     : Anastasiia Egorova
--%>

<%@page import="factories.TaskFactory"%>
<%@page import="Interfaces.ITaskRepo"%>
<%@page import="factories.TeamFactory"%>
<%@page import="Interfaces.ITeamRepo"%>
<%@page import="factories.ClientFactory"%>
<%@page import="Interfaces.IClientRepo"%>
<%@page import="javaclasses.JobUtility"%>
<%@page import="models.JobModel"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<!DOCTYPE html>
<%
          IClientRepo clients = ClientFactory.makeClientRepo();
          ITeamRepo teams = TeamFactory.makeTeamRepo();
          ITaskRepo tasks = TaskFactory.makeTaskRepo(); 

          ArrayList<JobModel> jobs = (ArrayList<JobModel>)request.getAttribute("JobList");
          
            if (jobs == null) {
                  getServletContext().getRequestDispatcher("/DashboardController").forward(request,response); 
            }                      
            
            double totalCost = 0;
            double totalRevenue = 0;
%>
<html>
      <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Dashboard</title>
      </head>
      
      <body>
            <%@include file="WEB-INF/jspf/header.jspf" %>
            
                  <form action="DashboardController" method="post">

                        <br/>
                        <br/>
                        <br/>
                        <br/>
                        
                        <table>
                              <tr>
                                    <td><label for="startTime">Start Time:</label></td>
                                    <td><input type="datetime-local" id="startTime"
                                                name="startTime" value=""
                                                min="2020-01-14T15:00 + T08:00" max="2100-01-01T15:00"></td>
                              
                                    <td><label for="endTime">End Time:</label></td>
                                    <td><input type="datetime-local" id="startTime"
                                                name="endTime" value=""
                                                min="2020-01-14T15:00 + T08:00" max="2100-01-01T15:00"></td>
                                    
                                    <td><button type="submit" name="btnFilter">Filter Jobs</button></td>
                              </tr>
                     </table>
                              
                        <% for (int i = 0; i < jobs.size(); ++i) {%>
                              
                              <h4>Job<%= i+1 %>:</h4>
                              <div style="border:1px solid #ffcccc; margin: 15px;">  
                                    <%String clientName = clients.getClient(jobs.get(i).getClientId()).getName();%>
                                    <%String teamName = teams.getTeam(jobs.get(i).getTeam()).getTeamName();%>
                                    <% Double revenue = (jobs.get(i).getIsEmergencyCall()) ? jobs.get(i).getCost() * 4 : jobs.get(i).getCost() * 3;%>
                                    <% totalCost += jobs.get(i).getCost();  totalRevenue += revenue;%>

                                    <tags:JobDashboard job="<%= jobs.get(i) %>" 
                                                            clientName="<%=clientName %>" 
                                                            team="<%= teamName %>"
                                                            revenue="<%= revenue %>"/>
                              </div>

                        <% } %>
                       
                        <h4>Total Cost: $<%= totalCost %></h4>
                        <h4>Total Revenue: $<%= totalRevenue %></h4>
                  </form>
            
      </body>
</html>
