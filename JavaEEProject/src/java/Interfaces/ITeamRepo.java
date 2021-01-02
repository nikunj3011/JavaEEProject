/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package Interfaces;

import models.TeamModel;
import java.util.ArrayList;
import models.EmployeeModel;
/**
 *
 * @author Nikunj
 */
public interface ITeamRepo {
    public ArrayList<TeamModel> getTeams();
    
    public TeamModel getTeam(int id);
    
    public TeamModel getTeamWithEmployees(int id);
    
    public void createTeam(TeamModel team); 
    
    public void updateTeam(TeamModel team); 
    
    public ArrayList<Integer> getSkillIDs(int teamId);
    public void deleteTeam(TeamModel team); 
}

