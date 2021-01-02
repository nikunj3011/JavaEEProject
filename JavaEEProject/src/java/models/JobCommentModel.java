/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;

/**
 *
 * @author Alena Selezneva
 */
public class JobCommentModel {
    private int id;
    private int jobID;
    private String comment;
    
    private LocalDate creationDate;
    private LocalDate lastUpdateDate;
    
    public JobCommentModel(){
        creationDate = LocalDate.now();
    }
    
    public JobCommentModel(int id, int jobID, String comment, LocalDate crDate, LocalDate lastUpdateDate){
        this.id = id;
        this.jobID = jobID;
        this.comment = comment;
        this.creationDate = crDate;
        this.lastUpdateDate = lastUpdateDate;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public String getComment() {
        return comment != null ? comment : "";
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getCreationDate() {
        return creationDate != null ? creationDate : LocalDate.now();
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate != null ? lastUpdateDate : getCreationDate();
    }

    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
