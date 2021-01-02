/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package javaclasses;

import Interfaces.ITeamRepo;
import factories.TeamFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.TeamModel;

/**
 *
 * @author Nikunj
 */

public class TeamUtility {
    public static void validateTeam(TeamModel team){
        ArrayList<String> errors = new ArrayList<>();
        
        if (team.getTeamName() == null || team.getTeamName().equals(""))
            errors.add("Team Name is required"); 
        if (team.getTeamMembers().size()<2 || team.getTeamMembers().size()>2)
            errors.add("Please choose only two team members");
        team.setErrors(errors);
        team.setIsValid_(errors.size() == 0);
    }
        
    public static TeamModel retrieveTeamFromRequest(HttpServletRequest request){
        TeamModel team = (TeamModel)request.getAttribute("TeamModel");
        if (team == null)
            team = new TeamModel();
        return team;
    }
    
    public static void saveTeamToRequest(HttpServletRequest request, TeamModel employee){
        request.setAttribute("TeamModel", employee);
    }
    
    public static void saveTeamToDB(TeamModel team){
        ITeamRepo teamRepo = TeamFactory.makeTeamRepo();
        if (team.getId() < 1){
            teamRepo.createTeam(team);
        }else{
            teamRepo.updateTeam(team);
        }
    }
    
    public static ArrayList<TeamModel> retrieveTeamListFromRequest(HttpSession session){
        ArrayList<TeamModel> teams = (ArrayList<TeamModel>)session.getAttribute("TeamList");
        if (teams == null)
            teams = new ArrayList<TeamModel>();
        
        return teams;
    }
    
    public static ArrayList<Boolean> retrieveIsFullInfoShownFromRequest(HttpSession session){
        ArrayList<Boolean> infoShown = (ArrayList<Boolean>)session.getAttribute("FullInfoShownList");
        if (infoShown == null)
            infoShown = new ArrayList<Boolean>();
        
        return infoShown;
    }
    
    public static void saveTeamListToRequest(HttpSession session, ArrayList<TeamModel> teams){
        session.setAttribute("TeamList", teams);
    }
    
    public static void saveInfoShownListToRequest(HttpSession session, ArrayList<Boolean> infoShown){
        session.setAttribute("FullInfoShownList", infoShown);
    }
    
    public static ArrayList<TeamModel> selectTeamsFromDB(){
        ITeamRepo employeeRepo = TeamFactory.makeTeamRepo();
        
        return employeeRepo.getTeams();
    }
    
    public static ArrayList<Boolean> buildShowFullInfoListOfSize(int n){
        ArrayList<Boolean> list = new ArrayList<>();
        for (int i = 0; i < n; i++)
            list.add(false);
        
        return list;
    }
    public static void deleteTeamFromDB(TeamModel team){
        ITeamRepo teamRepo = TeamFactory.makeTeamRepo();
        
        teamRepo.deleteTeam(team);
    }
    
}
