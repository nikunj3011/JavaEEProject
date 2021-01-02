
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="team" type="models.TeamModel"%>

<%-- any content can be specified here e.g.: --%>
<div>
    <table class="table">               
        <tr>                    
            <h2><%= team.getTeamName() %></h2>
        </tr> 
    </table> 
</div>