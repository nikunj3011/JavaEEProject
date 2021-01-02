/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlAccess;

import Interfaces.IJobRepo;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import models.JobModel;

/**
 *
 * @author Anastasiia Egorova
 */
public class SqlJob implements IJobRepo{

    @Override
    public JobModel getJob(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

@Override
public ArrayList<JobModel> getJobs() {

    try {
        Connection connection = DBConnection.setConnection();

        //for the whole job except fot tasks
        ArrayList<JobModel> jobs = new ArrayList<JobModel>();

        String storedProcGetClients = "{CALL getJobs()}";
        CallableStatement stmt = connection.prepareCall(storedProcGetClients);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int index = Integer.parseInt(rs.getString("id"));
            String name = rs.getString("name");
            String description = rs.getString("description");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm");
            String startTimeString = rs.getString("startTime").substring(0,16);
            LocalDateTime startTime = LocalDateTime.parse(startTimeString, formatter);

            int duration = Integer.parseInt(rs.getString("duration"));
            int clientId = Integer.parseInt(rs.getString("clientID"));

            //TO DO: tasks
            ArrayList<Integer> tasksForJob = new ArrayList<Integer>();
            String storedProcGetTasksForJob = "{CALL getTasksForJob(?)}";
            CallableStatement stmtTasks = connection.prepareCall(storedProcGetTasksForJob);
            stmtTasks.setInt(1, index);
            ResultSet rsTasks = stmtTasks.executeQuery();
            while (rsTasks.next())
                  tasksForJob.add(Integer.parseInt(rsTasks.getString("TaskID")));

            int teamId = Integer.parseInt(rs.getString("teamID"));
            double cost = Double.parseDouble(rs.getString("cost"));
            boolean isOnSite = Boolean.parseBoolean(rs.getString("isOnSite"));
            boolean isEmergencyCall = Boolean.parseBoolean(rs.getString("isEmergencyCall"));

            jobs.add(new JobModel(index, name, description, startTime, duration, clientId, tasksForJob, teamId, cost, isOnSite, isEmergencyCall));
        }

        return jobs;
    } 
    catch (SQLException ex) {             
          System.out.println(ex.getMessage());
    } catch (ClassNotFoundException ex) {
          System.out.println(ex.getMessage());
    }
    return null;
    }

    @Override
    public void createJob(JobModel j) {

        try {

            Connection connection = DBConnection.setConnection();

            //adding to Job Table
            String storedProcCreateJob = "{CALL createJob(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = connection.prepareCall(storedProcCreateJob);

            stmt.setString(1, j.getName());
            stmt.setString(2, j.getDescription());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            String jobStartTime = j.getStartTime().format(formatter);
            stmt.setString(3, jobStartTime);

            String jobDuration = Integer.toString(j.getDuration());
            stmt.setString(4, jobDuration);

            Integer client = j.getClientId();
            stmt.setString(5, client.toString());

            Integer team = j.getTeam();
            stmt.setString(6, team.toString());

            String cost = String.valueOf(j.getCost());
            stmt.setString(7, cost);

            if (j.getIsOnSite())
                stmt.setString(8, "1");
            else
                stmt.setString(8, "0");

            if (j.getIsEmergencyCall())
                stmt.setString(9, "1");
            else
                stmt.setString(9, "0");

            ResultSet rs = stmt.executeQuery();

            // getting id of created job 
            storedProcCreateJob = "{CALL getLastJobId()}";
            stmt = connection.prepareCall(storedProcCreateJob);
            rs = stmt.executeQuery();
            rs.next();
            int id = Integer.parseInt(rs.getString("ID"));

            //adding to joining table (Job + Skill)
            storedProcCreateJob = "{CALL addJobTask(?, ?)}";
            stmt = connection.prepareCall(storedProcCreateJob);

            for (Integer t : j.getTasks()) {
                stmt.setInt(1, id);
                stmt.setInt(2, t);
                rs = stmt.executeQuery();
            }
        } 
        catch (SQLException ex) {             
              System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
              System.out.println(ex.getMessage());
        }
    }

      @Override
    public void updateJob(JobModel job) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void deleteJob(JobModel job) {

        try {
            Connection connection = DBConnection.setConnection();

            String storedProcGetClient = "{CALL deleteJob(?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClient);

            stmt.setInt(1, job.getId());

            ResultSet rs = stmt.executeQuery();
        } 
        catch (SQLException ex) {             
            System.out.println(ex.getMessage());
        } 
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

      @Override
    public ArrayList<JobModel> getJobsBetween(String date1, String date2) {

        try {
            Connection connection = DBConnection.setConnection();

            //for the whole job except fot tasks
            ArrayList<JobModel> jobs = new ArrayList<JobModel>();

            String storedProcGetClients = "{CALL getJobsBetween(?, ?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClients);
            stmt.setString(1, date1);
            stmt.setString(2, date2);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int index = Integer.parseInt(rs.getString("id"));
                String name = rs.getString("name");
                String description = rs.getString("description");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm");
                String startTimeString = rs.getString("startTime").substring(0,16);
                LocalDateTime startTime = LocalDateTime.parse(startTimeString, formatter);

                int duration = Integer.parseInt(rs.getString("duration"));
                int clientId = Integer.parseInt(rs.getString("clientID"));

                ArrayList<Integer> tasksForJob = new ArrayList<Integer>();
                String storedProcGetTasksForJob = "{CALL getTasksForJob(?)}";
                CallableStatement stmtTasks = connection.prepareCall(storedProcGetTasksForJob);
                stmtTasks.setInt(1, index);
                ResultSet rsTasks = stmtTasks.executeQuery();
                while (rsTasks.next())
                      tasksForJob.add(Integer.parseInt(rsTasks.getString("TaskID")));

                int teamId = Integer.parseInt(rs.getString("teamID"));
                double cost = Double.parseDouble(rs.getString("cost"));
                boolean isOnSite = Boolean.parseBoolean(rs.getString("isOnSite"));
                boolean isEmergencyCall = Boolean.parseBoolean(rs.getString("isEmergencyCall"));

                jobs.add(new JobModel(index, name, description, startTime, duration, clientId, tasksForJob, teamId, cost, isOnSite, isEmergencyCall));
            }

            return jobs;
        } 
        catch (SQLException ex) {             
              System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
              System.out.println(ex.getMessage());
        }
        return null;
    }

}
