/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static java.time.Clock.system;

/**
 *
 * @author Anastasiia Egorova
 */
public class DBConnection {
      
    private static String url = "jdbc:mysql://localhost:3306/JavaEEProject?autoReconnect=true&useSSL=false";
    private static String username = "root";
    private static String password = "n9033929669";

    public static Connection setConnection() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return (Connection) DriverManager.getConnection(url, username, password);
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}

