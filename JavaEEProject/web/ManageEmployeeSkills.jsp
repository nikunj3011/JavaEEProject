<%-- 
    Document   : ManageEmployeeTasks
    Created on : 6-Dec-2020, 7:27:10 PM
    Author     : Alena Selezneva
--%>

<%@page import="javaclasses.TaskUtility"%>
<%@page import="models.TaskModel"%>
<%@page import="javaclasses.EmployeeUtility"%>
<%@page import="models.EmployeeModel"%>
<%@page import="java.util.ArrayList"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    ArrayList<EmployeeModel> employees = EmployeeUtility.retrieveEmployeeListFromSession(request.getSession());
    ArrayList<TaskModel> employeeSkills = EmployeeUtility.retrieveSkillsListFromSession(request.getSession(), "EmployeeSkillslist");
    ArrayList<TaskModel> unusedSkills = EmployeeUtility.retrieveSkillsListFromSession(request.getSession(), "UnusedSkillsList");
    
    EmployeeModel chosenEmployee = EmployeeUtility.retrieveEmployeeFromRequest(request);
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        
        <div style="width:90%; margin: 100px auto;">
        
            <form action="EmployeeSkillsController" method="post">

                <% if (chosenEmployee == null || chosenEmployee.getId() < 1) { %>
                    <select name="Employees" id="Employees" >

                    <%  for (int i = 0; i < employees.size(); i++){  %>
                            <option value="<%= i %>" > <%= employees.get(i).getFullName() %> </option>
                    <%  } %>
                    </select>
                    <button type="submit" name="btnChooseEmployee" > Show Skills </button>
                
                <% } else { %>
                    <h2> Skills of <%= chosenEmployee.getFullName() %> </h2>
                    <input type="hidden" id="ChosenEmployee" name="ChosenEmployee" value="<%=EmployeeUtility.findIndexOfEmployeeInList(employees, chosenEmployee)%>">
                    
                    <select name="UnusedSkills" id="UnusedSkills" >

                        <%  for (int i = 0; i < unusedSkills.size(); i++){  %>
                                <option value="<%= i %>" > <%= unusedSkills.get(i).getTaskName() %> </option>
                        <%  } %>
                    </select>
                    <button type="submit" name="btnAddSkill" > Add Skill </button>
  
                    <a href="EmployeeSkillsController" class="w3-bar-item w3-button w3-padding-large">Choose Another Employee</a>
                <% } %>
                
                <hr>
                
                <% for (int i = 0; i < employeeSkills.size(); i++) { %>
                    <div style="border:1px solid #ffcccc; margin: 15px; clear: both;">
                        <tags:taskinfo task="<%= employeeSkills.get(i) %>"  />

                        <button type="submit" name="btnRemoveSkill" value="<%= i %>"> Remove Skill </button> 
                    </div> 
                <% } %>
                
                <% if (employeeSkills.size() == 0 && !(chosenEmployee == null || chosenEmployee.getId() < 0)) { %>
                    <p> No skills to show </p>
                <% } %>              
            </form>              
        </div>
        
        <%@include file="WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
