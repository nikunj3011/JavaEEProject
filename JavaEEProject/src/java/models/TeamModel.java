/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nikunj
 */
package models; 
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import sqlAccess.DBConnection;

public class TeamModel {
    private int id;
    private String teamName;
    private boolean isOnCall;
    private LocalDate creationDate;
    private ArrayList<Integer> teamMembers;
    
    private ArrayList<String> errors;
    private boolean isValid_; 
    private LocalDate lastUpdateDate;
    
    public TeamModel() {  
        creationDate = LocalDate.now();
        isValid_ = false;
        errors = new ArrayList<String>(); 
    }
    
    public TeamModel(int id, String teamName, boolean isOnCall, LocalDate creationDate)
    {
        this.id = id;
        this.teamName = teamName;
        this.isOnCall = isOnCall;
        this.creationDate = creationDate;
        
        errors = new ArrayList<String>();
    }
    
        public TeamModel(int id, String teamName, boolean isOnCall, LocalDate creationDate, ArrayList<Integer> members)
    {
        this.id = id;
        this.teamName = teamName;
        this.isOnCall = isOnCall;
        this.creationDate = creationDate;
        this.teamMembers = members;
        
        errors = new ArrayList<String>();
    }
     
    public TeamModel(int id, String teamName, boolean isOnCall, LocalDate creationDate, boolean isValid_, ArrayList<String> errors)
    {
        this.id = id;
        this.teamName = teamName;
        this.isOnCall = isOnCall;
        this.creationDate = creationDate;
        
        this.isValid_ = isValid_;
        this.errors = this.errors;
    } 

    public boolean isValid(){
        return isValid_;
    }
    
    public ArrayList<String> getErrors(){
        return errors;
    }
    
    public void setErrors(ArrayList<String> err){
        this.errors = err;
    }
    
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTeamName() {
        return teamName != null ? teamName : "";
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public boolean getisOnCall() {
        return isOnCall;
    }
    public void setisOnCall(boolean isOnCall) {
        this.isOnCall = isOnCall;
    }
    
    public ArrayList<Integer> getTeamMembers() {
          return this.teamMembers;
    }
    
    public void setTeamMembers(ArrayList<Integer> teamMembers) {
          this.teamMembers = teamMembers;
    }
    
    public void setIsValid_(boolean isValid_) {
        this.isValid_ = isValid_;
    }
     
    public LocalDate getCreationDate() {
        return creationDate != null ? creationDate : LocalDate.now();
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
    