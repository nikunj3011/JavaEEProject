/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.util.ArrayList;
import models.JobCommentModel;

/**
 *
 * @author Alena Selezneva
 */
public interface IJobCommentRepo {
    
    public ArrayList<JobCommentModel> getJobComments(int jobID);
    
    public void createComment(JobCommentModel comment); 
    
    public void updateComment(JobCommentModel comment); 
    
    public void deleteComment(int commentID);
}
