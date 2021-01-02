<%-- 
    Document     :     ListClients
    Created on    :     Nov 16, 2020, 8:57:34 PM
    Author            :    Anastasiia Egorova
--%>

<%@page import="javaclasses.ClientUtility"%>
<%@page import="models.ClientModel"%>
<%@page import="java.util.ArrayList"%>
<%@page import="factories.ClientFactory"%>
<%@page import="Interfaces.IClientRepo"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
          ArrayList<ClientModel> clients = ClientUtility.retrieveClientListFromRequest(request.getSession());
          ArrayList<Boolean> showFullInfo = ClientUtility.retrieveIsFullInfoShownFromRequest(request.getSession());
%>

<html>
      <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>List of Clients</title>
      </head>
       
      <body>
            <%@include file="WEB-INF/jspf/header.jspf" %>
            <h2>All Clients:</h2>
            
            <form action="ClientController" method="post">
                
                <table class="table">
                    <tr>                    
                        <td> Name:</td>
                        <td><input type="text" name="searchName" value=''/></td>
                    </tr>                       
                </table>
                <br>
                <button type="submit" name="btnSearchClient" > Search </button> 
                  
                  <% for (int i = 0; i < clients.size(); ++i) {%>
                        <div style="border:1px solid #ffcccc; margin: 15px;">  
                              <% if (showFullInfo.get(i)) {%>
                                    <tags:ClientFullInfo client="<%= clients.get(i) %>"/>
                                    <button type="submit" name="btnFlipShowFullInfo" value="<%= i %>">Show Less</button>
                              <% } else { %>
                                    <tags:ClientEssential client="<%= clients.get(i) %>"/>
                                    <button type="submit" name="btnFlipShowFullInfo" value="<%= i %>">Show More</button>
                              <% } %>
                              
                              <hr/>
                              <button type="submit" name="btnUpdateClient" value="<%= i %>"> Update </button> 
                              <button type="submit" name="btnDeleteClient" value="<%= i %>"> Delete </button> 
                        </div>
                  <% } %>
                  
            </form>
                  
            <%@include file="WEB-INF/jspf/footer.jspf" %>
      </body>
</html>
