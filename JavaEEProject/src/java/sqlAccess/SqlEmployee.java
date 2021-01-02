/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlAccess;

import Interfaces.IEmployeeRepo;
import Interfaces.ITaskRepo;
import factories.TaskFactory;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import models.EmployeeModel;
import models.TaskModel;

/**
 *
 * @author Alena Selezneva
 */
public class SqlEmployee implements IEmployeeRepo {
        
        public ArrayList<EmployeeModel> getEmployees(){
        try {
            Connection connection = DBConnection.setConnection();

            ArrayList<EmployeeModel> employees = new ArrayList<EmployeeModel>();

            String storedProcGetClients = "{CALL getEmployees()}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClients);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String sin = rs.getString("sin");
                double hourlyPayRate = Double.parseDouble(rs.getString("hourlyPayRate"));
                LocalDate creationDate = LocalDate.parse( rs.getString("creationDate"));
                LocalDate updateDate = LocalDate.parse( rs.getString("lastUpdateDate"));

                employees.add(new EmployeeModel(id, firstName, lastName, sin, hourlyPayRate, creationDate, updateDate));
            }
            return employees;
        } 
        catch (SQLException ex) {             
              System.out.println(ex.getMessage());
        } catch (Exception ex) {
              System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public EmployeeModel getEmployee(int id){
        try {
            Connection connection = DBConnection.setConnection();

            String storedProcGetClient = "{CALL getEmployee(?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClient);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            int index = Integer.parseInt(rs.getString("id"));
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String sin = rs.getString("sin");
            double hourlyPayRate = Double.parseDouble(rs.getString("hourlyPayRate"));
            LocalDate creationDate = LocalDate.parse( rs.getString("creationDate"));
            LocalDate updateDate = LocalDate.parse( rs.getString("lastUpdateDate"));

            return new EmployeeModel(index, firstName, lastName, sin, hourlyPayRate, creationDate, updateDate);
        } 
        catch (SQLException ex) {             
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public void createEmployee(EmployeeModel employee){
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcGetClient = "{CALL createEmployee(?, ?, ?, ?, ?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClient);

            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getSin());
            stmt.setDouble(4, employee.getHourlyPayRate());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String clientCreationDate = employee.getCreationDate().format(formatter);
            stmt.setString(5, clientCreationDate);

            ResultSet rs = stmt.executeQuery();
        } 
        catch (SQLException ex) {             
              System.out.println(ex.getMessage());
        } catch (Exception ex) {
              System.out.println(ex.getMessage());
        }
    }
    
    public void updateEmployee(EmployeeModel employee){
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcGetClient = "{CALL updateEmployee(?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClient);

            stmt.setInt(1, employee.getId());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getLastName());
            stmt.setString(4, employee.getSin());
            stmt.setDouble(5, employee.getHourlyPayRate());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String employeeCreationDate = employee.getCreationDate().format(formatter);
            stmt.setString(6, employeeCreationDate);
            
            String employeeUpdateDate = LocalDate.now().format(formatter);
            stmt.setString(7, employeeUpdateDate);

            ResultSet rs = stmt.executeQuery();
        } 
        catch (SQLException ex) {             
              System.out.println(ex.getMessage());
        } catch (Exception ex) {
              System.out.println(ex.getMessage());
        }
    }
    
    public void deleteEmployee(EmployeeModel employee){
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcGetClient = "{CALL deleteEmployee(?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClient);

            stmt.setInt(1, employee.getId());

            ResultSet rs = stmt.executeQuery();
        } 
        catch (SQLException ex) {             
              System.out.println(ex.getMessage());
        } catch (Exception ex) {
              System.out.println(ex.getMessage());
        }
    }
    
    public ArrayList<String> getTeamNamesOfEmployee(int id){
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcGetClients = "{CALL getTeamsForEmployee(?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClients);
            
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            ArrayList<String> teamNames = new ArrayList<>();
            
            while (rs.next()) {
                String teamName = rs.getString("teamname");
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
    
    public ArrayList<String> getSkillNamesOfEmployee(int id){
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcGetClients = "{CALL getTasksForEmployee(?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClients);
            
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            ArrayList<String> taskNames = new ArrayList<>();
            
            while (rs.next()) {
                String taskName = rs.getString("taskname");
                taskNames.add(taskName);
            }
            return taskNames;
        } 
        catch (SQLException ex) {             
              System.out.println(ex.getMessage());
        } catch (Exception ex) {
              System.out.println(ex.getMessage());
        }
        
        return new ArrayList<String>();
    }
    
    public ArrayList<TaskModel> getSkillsOfEmployee(int id){
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcGetClients = "{CALL getTasksForEmployee(?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClients);
            
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            ArrayList<TaskModel> skills = new ArrayList<>();
            ITaskRepo taskRepo = TaskFactory.makeTaskRepo();
            
            while (rs.next()) {              
                skills.add(taskRepo.getTask(rs.getInt("id")));
            }
            return skills;
        } 
        catch (SQLException ex) {             
              System.out.println(ex.getMessage());
        } catch (Exception ex) {
              System.out.println(ex.getMessage());
        }
        return new ArrayList<>();
    }
    
    public void removeSkillOfEmployee(int employeeID, int skillId){
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcGetClient = "{CALL removeSkillFromEmployee(?, ?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClient);

            stmt.setInt(1, employeeID);
            stmt.setInt(2, skillId);

            ResultSet rs = stmt.executeQuery();
        } 
        catch (SQLException ex) {             
              System.out.println(ex.getMessage());
        } catch (Exception ex) {
              System.out.println(ex.getMessage());
        }
    }
    
    public void addSkillForEmployee(int employeeID, int skillId){
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcGetClient = "{CALL addSkillForEmployee(?, ?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClient);

            stmt.setInt(1, employeeID);
            stmt.setInt(2, skillId);

            ResultSet rs = stmt.executeQuery();
        } 
        catch (SQLException ex) {             
              System.out.println(ex.getMessage());
        } catch (Exception ex) {
              System.out.println(ex.getMessage());
        }
    }
}
