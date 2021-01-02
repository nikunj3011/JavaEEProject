/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package sqlAccess;

import sqlAccess.DBConnection;
import Interfaces.ITeamRepo;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import models.EmployeeModel;
import models.TeamModel;

/**
 *
 * @author Nikunj
 */
public class SqlTeam implements ITeamRepo {
        
    public ArrayList<TeamModel> getTeams(){
        try {
            Connection connection = DBConnection.setConnection();

            ArrayList<TeamModel> teams = new ArrayList<TeamModel>();

            String storedProcGetTeams = "{CALL getTeams()}";
            CallableStatement stmt = connection.prepareCall(storedProcGetTeams);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                String teamname = rs.getString("teamName");
                boolean isOnCall = Integer.parseInt(rs.getString("isCurrentlyOnCall")) > 0;
                LocalDate creationDate = LocalDate.parse( rs.getString("creationDate"));
                ArrayList<Integer> employeeIDsForTeam = new ArrayList<Integer>();
                String storedProcgetTeamsForEmployee = "{CALL getTeamsForEmployee(?)}";
                CallableStatement stmtTeams = connection.prepareCall(storedProcgetTeamsForEmployee);
                stmtTeams.setInt(1, id);
                ResultSet rsTeams = stmtTeams.executeQuery();
                while (rsTeams.next())
                    employeeIDsForTeam.add(Integer.parseInt(rsTeams.getString("EmployeeID")));
                teams.add(new TeamModel(id, teamname, isOnCall, creationDate, employeeIDsForTeam));
                
            }
            return teams;
        } 
        catch (SQLException ex) {             
              System.out.println(ex.getMessage());
        } catch (Exception ex) {
              System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public ArrayList<String> getTeamNamesOfEmployee(int id){
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcGetClients = "{CALL getEmployees(?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClients);
            
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            ArrayList<String> teamNames = new ArrayList<>();
            
            while (rs.next()) {
                String teamName = rs.getString("firstName");
                teamNames.add(teamName);
            }
            return teamNames;
        } 
        catch (SQLException ex) {             
              System.out.println(ex.getMessage());
        } catch (Exception ex) {
              System.out.println(ex.getMessage());
        }
        
        return new ArrayList<String>();
    }
     
     
    public TeamModel getTeam(int id){
        try {
            Connection connection = DBConnection.setConnection();

            String storedProcGetTeam = "{CALL getTeam(?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetTeam);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            
            int teamID = Integer.parseInt(rs.getString("id"));
            String teamname = rs.getString("teamname");
            boolean isOnCall = Boolean.parseBoolean(rs.getString("isCurrentlyOnCall"));
            LocalDate creationDate = LocalDate.parse( rs.getString("creationDate"));
            return new TeamModel(teamID, teamname, isOnCall, creationDate);
        } 
        catch (SQLException ex) {             
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
     
    public TeamModel getTeamWithEmployees (int id) {
        try {
            Connection connection = DBConnection.setConnection();

            ArrayList<Integer> teamMembers = new ArrayList<Integer>();

            String storedProcGetTeam = "{CALL getTeamWithEmployees(?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetTeam);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            int teamID = Integer.parseInt(rs.getString("id"));
            String teamname = rs.getString("teamname");
            boolean isOnCall = Boolean.parseBoolean(rs.getString("isCurrentlyOnCall"));
            LocalDate creationDate = LocalDate.parse( rs.getString("creationDate"));
            teamMembers.add(Integer.parseInt(rs.getString("EmployeeID")));

            rs.next();
            teamMembers.add(Integer.parseInt(rs.getString("EmployeeID")));

            return new TeamModel(teamID, teamname, isOnCall, creationDate, teamMembers);
        } 
        catch (SQLException ex) {             
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public void createTeam(TeamModel team){
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcGetTeam = "{CALL createTeams(?, ?, ?, ?, ?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetTeam);

            stmt.setString(1, team.getTeamName());
            if (team.getisOnCall())
                stmt.setString(2, "1");
            else
                stmt.setString(2, "0");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String teamCreationDate = team.getCreationDate().format(formatter);
            stmt.setString(3, teamCreationDate);
            stmt.setString(4, "0");
            
            stmt.registerOutParameter(5, java.sql.Types.INTEGER);

            stmt.execute();
            
            int tId = stmt.getInt(5);
            
            for (Integer eID : team.getTeamMembers()) {
                
                String addEmployeeToTeamProc = "{CALL addEmployeeTeam(?, ?)}";
               
                stmt = connection.prepareCall(addEmployeeToTeamProc);

                stmt.setInt(1, eID);
                stmt.setInt(2, tId);
                ResultSet rs = stmt.executeQuery();
            }
        } 
        catch (SQLException ex) {             
              System.out.println(ex.getMessage());
        } catch (Exception ex) {
              System.out.println(ex.getMessage());
        }
    }
    
    public void updateTeam(TeamModel team){
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcUpdateTeam = "{CALL updateTeams(?, ?, ?, ?, ?)}";
            CallableStatement stmt = connection.prepareCall(storedProcUpdateTeam);

            stmt.setInt(1, team.getId());
            stmt.setString(2, team.getTeamName());
            stmt.setBoolean(3, team.getisOnCall());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String teamCreationDate = team.getCreationDate().format(formatter);
            stmt.setString(4, teamCreationDate);
            stmt.setString(5, "0");
            ResultSet rs = stmt.executeQuery();
            
            String storedProcRemoveEmployees = "{CALL removeEmployeesFromTeam(?)}";
            stmt = connection.prepareCall(storedProcRemoveEmployees);
            
            //removeEmployeesFromTeam
            stmt.setInt(1, team.getId());
            rs = stmt.executeQuery();
            
            for (Integer eID : team.getTeamMembers()) {
                
                String addEmployeeToTeamProc = "{CALL addEmployeeTeam(?, ?)}";
               
                stmt = connection.prepareCall(addEmployeeToTeamProc);

                stmt.setInt(1, eID);
                stmt.setInt(2, team.getId());
                rs = stmt.executeQuery();
            }
            
            
        } 
        catch (SQLException ex) {             
              System.out.println(ex.getMessage());
        } catch (Exception ex) {
              System.out.println(ex.getMessage());
        }
    }
    
    public ArrayList<Integer> getTeamIDsOfTeam(int id){
        return new ArrayList<Integer>();
    }
    
    public ArrayList<Integer> getSkillIDs(int teamId){
        
        try {
            Connection connection = DBConnection.setConnection();

            String storedProcGetClient = "{CALL getSkillsForTeam(?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClient);

            stmt.setInt(1, teamId);
            ResultSet rs = stmt.executeQuery();

            ArrayList<Integer> skills = new ArrayList<Integer>();

            while (rs.next()) {
                  Integer skill = rs.getInt("TaskID");
                  skills.add(skill);
            }

            return skills;
        } 
        catch (SQLException ex) {             
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }   
        return null;
    }

    @Override
    public void deleteTeam(TeamModel team) {
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcGetClient = "{CALL deleteTeams(?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClient);

            stmt.setInt(1, team.getId());

            ResultSet rs = stmt.executeQuery();
        } 
        catch (SQLException ex) {             
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
