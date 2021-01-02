<%-- 
    Document   : ListJobs
    Created on : Dec 8, 2020, 12:51:26 PM
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
<%@taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
          ArrayList<JobModel> jobs = JobUtility.retrieveJobListFromRequest(request.getSession());
          ArrayList<Boolean> showFullInfo = JobUtility.retrieveIsFullInfoShownFromRequest(request.getSession());
          
          IClientRepo clients = ClientFactory.makeClientRepo();
          ITeamRepo teams = TeamFactory.makeTeamRepo();
          ITaskRepo tasks = TaskFactory.makeTaskRepo();  
%>

<html>
      <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>List of Jobs:</title>
      </head>
      
      <body>
            <%@include file="WEB-INF/jspf/header.jspf" %>
            <h2>All Jobs:</h2>
            
            <form action="JobController" method="post">
                  
                  <% for (int i = 0; i < jobs.size(); ++i) {%>
                        <div style="border:1px solid #ffcccc; margin: 15px;">  
                              <% if (showFullInfo.get(i)) {%>
                                    <%String clientName = clients.getClient(jobs.get(i).getClientId()).getName();%>
                                    <%String teamName = teams.getTeam(jobs.get(i).getTeam()).getTeamName();%>
                                    <% Double revenue = (jobs.get(i).getIsEmergencyCall()) ? jobs.get(i).getCost() * 4 : jobs.get(i).getCost() * 3;%>
                                    <% ArrayList<String> jobsTasks = new ArrayList<String>();%>
                                    <% for (int j : jobs.get(i).getTasks()) { %>
                                          <%jobsTasks.add(tasks.getTask(j).getTaskName());%>
                                    <% } %>
                                    
                                    <tags:JobFullInfo job="<%= jobs.get(i) %>" 
                                                                  clientName="<%=clientName %>" 
                                                                  team="<%= teamName %>"
                                                                  revenue="<%= revenue %>"
                                                                  tasks="<%= jobsTasks %>"/>
                                    
                                    <button type="submit" name="btnFlipShowFullInfo" value="<%= i %>">Show Less</button>
                              <% } else { %>
                                    <tags:JobEssential job="<%= jobs.get(i) %>"/>
                                    <button type="submit" name="btnFlipShowFullInfo" value="<%= i %>">Show More</button>
                              <% } %>
                              
                              <hr/>
                              <button type="submit" name="btnDeleteJob" value="<%= i %>"> Delete </button> 
                        </div>
                  <% } %>
                  
            </form>
      </body>
</html>
