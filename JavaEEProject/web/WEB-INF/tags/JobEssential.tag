<%-- 
    Document   : JobEssential
    Created on : Dec 8, 2020, 9:50:33 PM
    Author     : Anastasiia Egorova
--%>

<%@tag description="WaddlesRules" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="job" type="models.JobModel"%>

<div>
      <h2><%=job.getName() %></h2>
</div>