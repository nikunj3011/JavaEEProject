<%-- 
    Document   : ManageJobComments
    Created on : 8-Dec-2020, 9:04:08 PM
    Author     : Alena Selezneva
--%>


<%@page import="models.JobCommentModel"%>
<%@page import="javaclasses.JobCommentUtility"%>
<%@page import="models.JobModel"%>
<%@page import="java.util.ArrayList"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    ArrayList<JobModel> jobs = JobCommentUtility.retrieveJobsListFromSession(session);
    ArrayList<JobCommentModel> comments = JobCommentUtility.retrieveCommentsListFromSession(session);

    JobModel chosenJob = JobCommentUtility.retrieveJobFromRequest(request);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        
        <div style="width:90%; margin: 100px auto;">
            <form action="JobCommentsController" method="post">
                
                <% if (chosenJob == null || chosenJob.getId() < 1) { %>
                    <select name="Jobs" id="Jobs" >

                    <%  for (int i = 0; i < jobs.size(); i++){  %>
                            <option value="<%= i %>" > <%= jobs.get(i).getName()%> </option>
                    <%  } %>
                    </select>
                    <button type="submit" name="btnChoosejob" > Show Comments </button>
                
                <% } else { %>
                    <h2> Comments for <%= chosenJob.getName() %> </h2>
                    <input type="hidden" id="ChosenJob" name="ChosenJob" value="<%=JobCommentUtility.findIndexOfJobInList(jobs, chosenJob)%>">                 
                    
                    <input type="text" cols="50" rows="10" style="width:250px; height:100px;" name="NewComment"  />
                     
                    <br>
                    <button type="submit" name="btnAddComment" > Add Comment </button>
  
                    <a href="JobCommentsController" class="w3-bar-item w3-button w3-padding-large">Choose Another Job</a>
                <% } %>
                
                <hr>
                
                <% for (int i = 0; i < comments.size(); i++) { %>
                    <div style="border-bottom: 1px solid #ffcccc; margin: 15px; clear: both;">

                        <input type="text" cols="50" rows="10" style="width:250px; height:100px;" name="ShowComment<%=i%>" value="<%= comments.get(i).getComment() %>"  />
                        <br>
                        
                        <button type="submit" name="btnUpdateComment" value="<%= i %>"> Update Comment </button> 
                        <button type="submit" name="btnRemoveComment" value="<%= i %>"> Remove Comment </button> 
                    </div> 
                <% } %>
                
                <% if (comments.size() == 0 && !(chosenJob == null || chosenJob.getId() < 0)) { %>
                    <p> No comments to show </p>
                <% } %> 
                        
            </form>
        </div>
        
        <%@include file="WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
