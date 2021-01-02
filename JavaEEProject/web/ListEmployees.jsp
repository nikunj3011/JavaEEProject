<%-- 
    Document   : ListEmployees
    Created on : 9-Nov-2020, 8:59:59 PM
    Author     : Alena Selezneva
--%>

<%@page import="javaclasses.EmployeeUtility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.EmployeeModel"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%    
    ArrayList<EmployeeModel> employees = EmployeeUtility.retrieveEmployeeListFromSession(request.getSession());
    ArrayList<Boolean> showFullInfo = EmployeeUtility.retrieveIsFullInfoShownFromSession(request.getSession());
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List of Employees</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <h2>All Employees:</h2>
        
        <form action="EmployeeController" method="post">
            
        <table class="table">
            <tr>                    
                <td> Last Name:</td>
                <td><input type="text" name="searchLastName" value=''/></td>
            </tr>
            <tr>                    
                <td>SIN: </td>
                <td><input type="text" name="searchSin" value=''/></td>
            </tr>                        
        </table>
        <br>
        <button type="submit" name="btnSearchEmployee" > Search </button> 
                   
        <%  for (int i = 0; i < employees.size(); i++){  %>            
            <div style="border:1px solid #ffcccc; margin: 15px; clear: both;">
                <%  if (showFullInfo.get(i))  {%>
                    <tags:EmployeeFullInfo employee="<%= employees.get(i) %>"  />
                    <button type="submit" name="btnFlipShowFullInfo" value="<%= i %>"> Show Less</button>
                <% } else { %>
                    <tags:EmployeeEssential employee="<%= employees.get(i) %>" />
                    <button type="submit" name="btnFlipShowFullInfo" value="<%= i %>"> Show More</button>
                <% }  %> 
                
                <hr>
                <button type="submit" name="btnUpdateEmployee" value="<%= i %>"> Change </button>
                <button type="submit" name="btnDeleteEmployee" value="<%= i %>"> Delete </button> 
            </div> 
        <%  } %>    
        </form>
        
        
        <%@include file="WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
