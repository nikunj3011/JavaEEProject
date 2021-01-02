<%-- 
    Document   :   CreateClient
    Created on :    Nov 8, 2020, 9:34:01 AM
    Author        :     Anastasiia Egorova
--%>

<%@page import="javaclasses.ClientUtility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.ClientModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
   ClientModel client = ClientUtility.retrieveClientFromRequest(request);
%>

<html>
      <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Create Client</title>
      </head>
      <body>
            <%@include file="WEB-INF/jspf/header.jspf" %>
            
            <h2>Create a Client</h2>
            
            <% if (client != null && client.getIsValid()) { %>
            
                  <h3>Client <%= client.getName() %> is saved</h3>

            <%  } else {  %>
            
                  <form action="ClientController" method="POST">
                        
                  <input type="hidden" name ="id" value ='<%= client.getId()  %>' />   
                  <input type="hidden" name ="creationDate" value ='<%= client.getCreationDate()%>' />  
                  
                        <table>
                              <tr>
                                    <td><label>Client Name:</label></td>
                                    <td><input type="text" name="name" value='<%= client.getName()%>'/></td>
                              </tr>

                              <tr>
                                    <td><label>Client Description:</label></td>
                                   <td><input type="text" name="description" value='<%= client.getDescription()%>'/></td>
                              </tr>
                        </table>

                        <div class="p-4 errors">
                            <%if (client.getErrors().size() > 0) {%>
                            <ul class="alert alert-danger" role="alert">
                                <% for (String err : client.getErrors()) {%>
                                <li><%= err%></li>
                                    <%}%>
                            </ul>
                            <%}%>
                        </div>
                        
                        <% if (client.getName() == null) { %>
                              <button name="btnCreateClient"  class="btn btn-primary">Save</button>
                        <% } else { %>
                              <button name="btnCreateClient" class="btn btn-primary">Save</button>
                              <button name="btnCancel" class="btn btn-primary">Cancel</button>
                        <%}%>
                  </form>
            
            <%}%>
            
            <%@include file="WEB-INF/jspf/footer.jspf"%>
      </body>
</html>
