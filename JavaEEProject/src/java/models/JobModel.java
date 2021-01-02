/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Anastasiia Egorova
 */
public class JobModel {
      
      private int id;
      private String name;
      private String description;
      private LocalDateTime startTime;
      private int duration;        //duration is in minutes => int
      private int clientID;
      private ArrayList<Integer> tasks;
      private int teamID;
      private double cost;
      private boolean isOnSite;
      private boolean isEmergencyCall;
      
      private boolean isValid_;
      private boolean isSaved_;
      private ArrayList<String> errors;
      
      // default C-TOR
      public JobModel() {
            this.isValid_ = false;
            this.isSaved_ = false;
            this.errors = new ArrayList<String>();
      }
      
     // C-TOR without id (to create new client in DB, which already has AUTO_INCREMENT for id)
      public JobModel(String name, String description, LocalDateTime startTime, int duration, 
              int clientID, ArrayList<Integer> tasks, int teamID, double cost, boolean isOnSite, boolean isEmergencyCall) {
            
            this.id = 0;            // not using later on
            this.name = name;
            this.description = description;
            this.startTime = startTime;
            this.duration = duration;
            this.clientID = clientID;
            this.teamID = teamID;
            this.cost = cost;
            this.isOnSite = isOnSite;
            this.isEmergencyCall = isEmergencyCall;
            this.isSaved_ = false;
            
            errors = new ArrayList<String>();
      }
      
      //C-TOR with ID
      public JobModel(int id, String name, String description, LocalDateTime startTime, int duration, 
              int clientID, ArrayList<Integer> tasks, int teamID, double cost, boolean isOnSite, boolean isEmergencyCall) {
            
            this.id = id;         
            this.name = name;
            this.description = description;
            this.startTime = startTime;
            this.duration = duration;
            this.clientID = clientID;
            this.tasks = tasks;
            this.teamID = teamID;
            this.cost = cost;
            this.isOnSite = isOnSite;
            this.isEmergencyCall = isEmergencyCall;
            this.isSaved_ = false;
            
            errors = new ArrayList<String>();
      }
      
      public int getId() {
            return this.id;
      }
      
      public void setId(int id) {
            this.id = id;
      } 
      
      public String getName() {
            return this.name != null ? this.name : "";
      }
      
      public void setName(String name) {
            this.name = name;
      } 
      
      public String getDescription() {
            return this.description != null ? this.description : "";
      }
      
      public void setDescription(String description) {
            this.description = description;
      } 
      
      public LocalDateTime getStartTime() {
            return this.startTime;
      }
      
      public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
      } 
      
      public int getDuration() {
            return this.duration;
      }
      
      public void setDuration(int duration) {
            this.duration = duration;
      } 
      
      public int getClientId() {
            return this.clientID;
      }
      
      public void setClientID(int clientID) {
            this.clientID = clientID;
      } 
      
      public ArrayList<Integer> getTasks() {
            return this.tasks;
      }
      
      public void setTasks(ArrayList<Integer> tasks) {
            this.tasks = tasks;
      }
      
      public int getTeam() {
            return this.teamID;
      }
      
      public void setTeam(int teamID) {
            this.teamID = teamID;
      } 
      
      public double getCost() {
            return this.cost;
      }
      
      public void setCost(double cost) {
            this.cost = cost;
      } 
      
      public boolean getIsOnSite() {
            return this.isOnSite;
      }
      
      public void setIsOnSite(boolean onSite) {
            this.isOnSite = onSite;
      } 
      
      public boolean getIsEmergencyCall() {
            return this.isEmergencyCall;
      }
      
      public void setIsEmergencyCall(boolean eCall) {
            this.isEmergencyCall = eCall;
      } 
      
      public boolean getIsValid() {
            return this.isValid_;
      }
      
      public void setIsValid(boolean isValid) {
            this.isValid_ = isValid;
      }
      
      public boolean getIsSaved() {
            return this.isSaved_;
      }
      
      public void setIsSaved(boolean isSaved) {
            this.isSaved_ = isSaved;
      }
      
      public ArrayList<String> getErrors(){
            return errors;
      }
    
      public void setErrors(ArrayList<String> err){
            this.errors = err;
      }
}
