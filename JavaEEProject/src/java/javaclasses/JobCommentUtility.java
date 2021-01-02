/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclasses;

import Interfaces.IJobCommentRepo;
import Interfaces.IJobRepo;
import factories.JobCommentFactory;
import factories.JobFactory;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.EmployeeModel;
import models.JobCommentModel;
import models.JobModel;

/**
 *
 * @author Alena Selezneva
 */
public class JobCommentUtility {
    
    
    public static ArrayList<JobModel> retrieveJobsListFromSession(HttpSession session){
        ArrayList<JobModel> jobs = (ArrayList<JobModel>)session.getAttribute("JobList");
        if (jobs == null)
            jobs = new ArrayList<JobModel>();
        
        return jobs;
    }
    
    public static ArrayList<JobCommentModel> retrieveCommentsListFromSession(HttpSession session){
        ArrayList<JobCommentModel> comments = (ArrayList<JobCommentModel>)session.getAttribute("CommentList");
        if (comments == null)
            comments = new ArrayList<JobCommentModel>();
        
        return comments;
    }
    
    public static JobModel retrieveJobFromRequest(HttpServletRequest request){
        JobModel job = (JobModel)request.getAttribute("jobModel");
        if (job == null)
            job = new JobModel();
        return job;
    }
    
    public static int findIndexOfJobInList(ArrayList<JobModel> jobs, JobModel job){
        for (int i = 0; i < jobs.size(); i++){
            if (jobs.get(i).getId() == job.getId())
                return i;
        }
        return -1;
    }
    
    public static void saveJobToRequest(HttpServletRequest request, JobModel job){
        request.setAttribute("jobModel", job);
    }
    
    public static void saveJobsListToSession(HttpSession session, ArrayList<JobModel> jobs){
        session.setAttribute("JobList", jobs);
    }
    
    public static void saveCommentsListToSession(HttpSession session, ArrayList<JobCommentModel> comments){
        session.setAttribute("CommentList", comments);
    }
    
    public static int getChosenJobIndex(String chosenValue, ArrayList<JobModel> jobs){
        for (int i = 0; i < jobs.size(); i++){
            if (Integer.toString(i).equals(chosenValue)){
                return i;
            }
        }
        return -1;
    }
    
    public static ArrayList<JobCommentModel> getCommentsForJob(int id){
        IJobCommentRepo jobCommentRepo = JobCommentFactory.makeJobCommentRepo();
        
        return jobCommentRepo.getJobComments(id);
    }
    
    public static ArrayList<JobModel> selectJobsFromDB(){
        IJobRepo jobRepo = JobFactory.makeJobRepo();
        
        return jobRepo.getJobs();
    }
    
    public static void removeCommentFromJob(int comID){
        IJobCommentRepo jobCommentRepo = JobCommentFactory.makeJobCommentRepo();
        
        jobCommentRepo.deleteComment(comID);
    }
    
    public static void addSCommentToJob(JobCommentModel comment){
        IJobCommentRepo jobCommentRepo = JobCommentFactory.makeJobCommentRepo();
        
        jobCommentRepo.createComment(comment);
    }
    
    public static void updateJobComment(JobCommentModel comment){
        IJobCommentRepo jobCommentRepo = JobCommentFactory.makeJobCommentRepo();
        
        jobCommentRepo.updateComment(comment);
    }
}
