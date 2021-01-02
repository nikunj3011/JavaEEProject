/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlAccess;

import Interfaces.ITaskRepo;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import models.TaskModel;

/**
 *
 * @author Nikunj
 */
public class SqlTask implements ITaskRepo {
        
        public ArrayList<TaskModel> getTasks(){
        try {
            Connection connection = DBConnection.setConnection();

            ArrayList<TaskModel> tasks = new ArrayList<TaskModel>();

            String storedProcGetTasks = "{CALL getTasks()}";
            CallableStatement stmt = connection.prepareCall(storedProcGetTasks);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                String taskname = rs.getString("taskname");
                String description = rs.getString("description");
                String duration = rs.getString("duration");
                LocalDate creationDate = LocalDate.parse( rs.getString("creationDate"));
                LocalDate updateDate = LocalDate.parse( rs.getString("lastUpdateDate"));
                tasks.add(new TaskModel(id, taskname, description, duration, creationDate, updateDate));
            }
            return tasks;
        } 
        catch (SQLException ex) {             
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public TaskModel getTask(int id){
        try {
            Connection connection = DBConnection.setConnection();

            String storedProcGetTask = "{CALL getTask(?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetTask);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            
            int taskID = Integer.parseInt(rs.getString("id"));
            String taskname = rs.getString("taskname");
            String description = rs.getString("description");
            String duration = rs.getString("duration");
            LocalDate creationDate = LocalDate.parse( rs.getString("creationDate"));
            LocalDate updateDate = LocalDate.parse( rs.getString("lastUpdateDate"));
            return new TaskModel(taskID, taskname, description, duration, creationDate, updateDate);
        } 
        catch (SQLException ex) {             
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public void createTask(TaskModel task){
        try {
            Connection connection = DBConnection.setConnection();

            String storedProcGetTask = "{CALL createTask(?, ?, ?, ?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetTask);

            stmt.setString(1, task.getTaskName());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getDuration());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String taskCreationDate = task.getCreationDate().format(formatter);
            stmt.setString(4, taskCreationDate);
            ResultSet rs = stmt.executeQuery();
        } 
        catch (SQLException ex) {             
              System.out.println(ex.getMessage());
        } catch (Exception ex) {
              System.out.println(ex.getMessage());
        }
    }
    
    public void updateTask(TaskModel task){
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcGetClient = "{CALL updateTask(?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClient);

            stmt.setInt(1, task.getId());
            stmt.setString(2, task.getTaskName());
            stmt.setString(3, task.getDescription());
            stmt.setString(4, task.getDuration());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String taskCreationDate = task.getCreationDate().format(formatter);
            stmt.setString(5, taskCreationDate); 
            String taskUpdateDate = LocalDate.now().format(formatter);
            stmt.setString(6, taskUpdateDate);

            ResultSet rs = stmt.executeQuery();
        } 
        catch (SQLException ex) {             
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
        
    public void deleteTask(TaskModel task){
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcGetClient = "{CALL deleteTask(?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClient);

            stmt.setInt(1, task.getId());

            ResultSet rs = stmt.executeQuery();
        } 
        catch (SQLException ex) {             
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
