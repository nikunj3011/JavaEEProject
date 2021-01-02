/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlAccess;

import Interfaces.IClientRepo;
//import com.mysql.jdbc.Connection;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import models.ClientModel;

/**
 *
 * @author Anastasiia Egorova
 */
public class SqlClient implements IClientRepo {
      
      public SqlClient() {
      }
      
      @Override
      public ClientModel getClient(int id) {
            
            try {

                  Connection connection = DBConnection.setConnection();

                  String storedProcGetClient = "{CALL getClient(?)}";
                  CallableStatement stmt = connection.prepareCall(storedProcGetClient);
                  stmt.setInt(1, id);

                  ResultSet rs = stmt.executeQuery();
                  rs.next();

                  int index = Integer.parseInt(rs.getString("id"));
                  String name = rs.getString("name");
                  String description = rs.getString("description");
                  String creationDate = rs.getString("creationDate");

                  return new ClientModel(index, name, description, creationDate);

            } 
            catch (SQLException ex) {             
                  System.out.println(ex.getMessage());
            } catch (ClassNotFoundException ex) {
                  System.out.println(ex.getMessage());
            }
            return null;
      }
      
      @Override
      public ArrayList<ClientModel> getClients() {
                       
            try {

                  Connection connection = DBConnection.setConnection();
                  
                  ArrayList<ClientModel> clients = new ArrayList<ClientModel>();
                  
                  String storedProcGetClients = "{CALL getClients()}";
                  CallableStatement stmt = connection.prepareCall(storedProcGetClients);

                  ResultSet rs = stmt.executeQuery();
                  
                  while (rs.next()) {
                        int index = Integer.parseInt(rs.getString("id"));
                        String name = rs.getString("name");
                        String description = rs.getString("description");
                        String creationDate = rs.getString("creationDate");
                        clients.add(new ClientModel(index, name, description, creationDate));
                  }
                  return clients;
            } 
            catch (SQLException ex) {             
                  System.out.println(ex.getMessage());
            } catch (ClassNotFoundException ex) {
                  System.out.println(ex.getMessage());
            }
            return null;
      }

      @Override
      public void createClient(ClientModel c) {
            
            try {

                  Connection connection = DBConnection.setConnection();
                  
                  String storedProcCreateClient = "{CALL createClient(?, ?, ?)}";
                  CallableStatement stmt = connection.prepareCall(storedProcCreateClient);
                  
                  stmt.setString(1, c.getName());
                  stmt.setString(2, c.getDescription());
                  
                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                  String clientCreationDate = c.getCreationDate().format(formatter);
                  stmt.setString(3, clientCreationDate);
                  
                  ResultSet rs = stmt.executeQuery();
            } 
            catch (SQLException ex) {             
                  System.out.println(ex.getMessage());
            } catch (ClassNotFoundException ex) {
                  System.out.println(ex.getMessage());
            }
      }
        
      @Override
      public void updateClient(ClientModel c) {
           try {

                  Connection connection = DBConnection.setConnection();
                  
                  String storedProcCreateClient = "{CALL updateClient(?, ?, ?, ?)}";
                  CallableStatement stmt = connection.prepareCall(storedProcCreateClient);
                  
                  stmt.setString(1, Integer.toString(c.getId()));
                  stmt.setString(2, c.getName());
                  stmt.setString(3, c.getDescription());
                  
                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                  String clientCreationDate = c.getCreationDate().format(formatter);
                  stmt.setString(4, clientCreationDate);
                  
                  //stmt.setString(5, String.valueOf(c.getIsDeleted()));
                  
                  ResultSet rs = stmt.executeQuery();
            } 
            catch (SQLException ex) {             
                  System.out.println(ex.getMessage());
            } catch (ClassNotFoundException ex) {
                  System.out.println(ex.getMessage());
            }
      }

      @Override
      public void deleteClient(ClientModel client) {
            
            try {
                  Connection connection = DBConnection.setConnection();

                  String storedProcGetClient = "{CALL deleteClient(?)}";
                  CallableStatement stmt = connection.prepareCall(storedProcGetClient);

                  stmt.setInt(1, client.getId());

                  ResultSet rs = stmt.executeQuery();
            } 
            catch (SQLException ex) {             
                  System.out.println(ex.getMessage());
            } 
            catch (Exception ex) {
                  System.out.println(ex.getMessage());
            }
      }
}
