package servlets;

import java.sql.*;

public class Jdbc{
    public static void main(String[] args) {
        Connection con=null;
        try{
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaEEProject?autoReconnect=true&useSSL=false","root","n9033929669");
            if(con!=null){
             System.out.print("yes");   
            }
        }catch(Exception e){
            System.out.print("no");
        }
    }
}
