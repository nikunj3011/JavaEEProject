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
import java.time.LocalDate;
import java.util.ArrayList;
public class TaskModel {
    private int id;
    private String taskName;
    private String description;
    private String duration;
    private LocalDate creationDate;
    private ArrayList<String> errors;
    private boolean isValid_;
    private LocalDate lastUpdateDate;
    public TaskModel() {  
        creationDate = LocalDate.now();
        isValid_ = false;
        errors = new ArrayList<String>();
    }
    
    public TaskModel(int id, String taskName, String description
        , String duration, LocalDate creationDate, LocalDate lastUpdateDate)
    {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.duration = duration;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        errors = new ArrayList<String>();
    }
    
    public TaskModel(int id, String taskName, String description
        , String duration, LocalDate creationDate, LocalDate lastUpdateDate, boolean isValid_, ArrayList<String> errors)
    {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.duration = duration;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
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
    public String getTaskName() {
        return taskName != null ? taskName : "";
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDuration() {
        return duration != null ? duration : "";
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    public LocalDate getCreationDate() {
        return creationDate != null ? creationDate : LocalDate.now();
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    
    public void setIsValid_(boolean isValid_) {
        this.isValid_ = isValid_;
    }
    
    public LocalDate getLastUpdateDate() {
        return lastUpdateDate != null ? lastUpdateDate : getCreationDate();
    }

    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
