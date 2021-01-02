<%-- 
    Document   :    ClientEssential
    Created on  :    Nov 16, 2020, 8:45:47 PM
    Author          :    Anastasiia Egorova
--%>

<%@tag description="WaddlesRules" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="client" type="models.ClientModel"%>

<div>
      <h2><%=client.getName() %></h2>
</div>