<%-- 
    Document   : EmployeeEssential
    Created on : 9-Nov-2020, 8:25:20 PM
    Author     : Alena Selezneva
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="employee" type="models.EmployeeModel"%>

<%-- any content can be specified here e.g.: --%>
<div>
    <h2><%= employee.getFirstName() + " " + employee.getLastName() %></h2> 
</div>