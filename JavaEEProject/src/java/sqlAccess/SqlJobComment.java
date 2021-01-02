/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlAccess;

import Interfaces.IJobCommentRepo;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import models.JobCommentModel;

/**
 *
 * @author Alena Selezneva
 */
public class SqlJobComment implements IJobCommentRepo{

    @Override
    public ArrayList<JobCommentModel> getJobComments(int jobID) {
        try {
            Connection connection = DBConnection.setConnection();

            ArrayList<JobCommentModel> comments = new ArrayList<>();
            
            String storedProcGetClients = "{CALL getCommentsForJob(?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClients);
            stmt.setInt(1, jobID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                int jID = Integer.parseInt(rs.getString("JobID"));
                String commentText = rs.getString("Comment");
                LocalDate creationDate = LocalDate.parse( rs.getString("creationDate"));
                LocalDate updateDate = LocalDate.parse( rs.getString("lastUpdateDate"));

                comments.add(new JobCommentModel(id, jID, commentText, creationDate, updateDate));
                
            }
            return comments;
        } 
        catch (SQLException ex) {             
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public void createComment(JobCommentModel comment) {
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcGetClient = "{CALL createJobComment(?, ?, ?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClient);

            stmt.setInt(1, comment.getJobID());
            stmt.setString(2, comment.getComment());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String clientCreationDate = comment.getCreationDate().format(formatter);
            stmt.setString(3, clientCreationDate);

            ResultSet rs = stmt.executeQuery();
        } 
        catch (SQLException ex) {             
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void updateComment(JobCommentModel comment) {
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcGetClient = "{CALL updateJobComment(?, ?, ?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClient);

            stmt.setInt(1, comment.getId());
            stmt.setString(2, comment.getComment());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String clientCreationDate = comment.getLastUpdateDate().format(formatter);
            stmt.setString(3, clientCreationDate);

            ResultSet rs = stmt.executeQuery();
        } 
        catch (SQLException ex) {             
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deleteComment(int commentID) {
        try {

            Connection connection = DBConnection.setConnection();

            String storedProcGetClient = "{CALL deleteJobComment(?)}";
            CallableStatement stmt = connection.prepareCall(storedProcGetClient);

            stmt.setInt(1, commentID);

            ResultSet rs = stmt.executeQuery();
        } 
        catch (SQLException ex) {             
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
}
