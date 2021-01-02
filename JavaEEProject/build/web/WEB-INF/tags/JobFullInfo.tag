<%-- 
    Document   : JobFullInfo
    Created on : Dec 8, 2020, 9:52:12 PM
    Author     : Anastasiia Egorova
--%>

<%@tag description="Waggles rules" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="job" type="models.JobModel"%>
<%@attribute name="clientName" type="String"%>
<%@attribute name="team" type="String"%>
<%@attribute name="revenue" type="Double"%>
<%@attribute name="tasks" type="java.util.Collection"%>

<%-- any content can be specified here e.g.: --%>
<div>
    <table class="table">               
        <tr>                    
            <td>Name: </td>
            <td><%= job.getName() %></td>
        </tr>
        <tr>                    
            <td>Description: </td>
            <td><%= job.getDescription()%></td>
        </tr>
        <tr>                    
            <td>Start Time: </td>
            <td><%= job.getStartTime()%></td>
        </tr>
                <tr>                    
            <td>Duration: </td>
            <td><%= job.getDuration()%></td>
        </tr>
        <tr>                    
            <td>Client: </td>
            <td><%= clientName%></td>
        </tr>
         <tr>                    
            <td>Tasks: </td>
            <td><%= tasks%></td>
        </tr>
        <tr>                    
            <td>Team: </td>
            <td><%= team%></td>
        </tr>
        <tr>                    
            <td>Cost: </td>
            <td>$<%= job.getCost()%></td>
        </tr>
        <tr>                    
            <td>Revenue: </td>
            <td>$<%= revenue %></td>
        </tr>
        <tr>                    
            <td>Is On Site: </td>
            <td><%= job.getIsOnSite()%></td>
        </tr>
        <tr>                    
            <td>Is Emergency Call: </td>
            <td><%= job.getIsEmergencyCall()%></td>
        </tr>
    </table>
</div>