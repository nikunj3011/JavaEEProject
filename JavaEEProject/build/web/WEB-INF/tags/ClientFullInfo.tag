<%-- 
    Document   : ClientFullInfo
    Created on : Nov 17, 2020, 7:20:24 PM
    Author     : Anastasiia Egorova
--%>

<%@tag description="WaddlesRules" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="client" type="models.ClientModel"%>

<%-- any content can be specified here e.g.: --%>
<div>
    <table class="table">               
        <tr>                    
            <td>Name: </td>
            <td><%= client.getName() %></td>
        </tr>
        <tr>                    
            <td>Description: </td>
            <td><%= client.getDescription()%></td>
        </tr>
        <tr>
            <td>Was Added On: </td>
            <td><%= client.getCreationDate()%></td>
        </tr>   
    </table>
</div>