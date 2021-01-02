/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javaclasses.JobCommentUtility;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.JobCommentModel;
import models.JobModel;

/**
 *
 * @author alena
 */
public class JobCommentsController extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (request.getParameter("btnChoosejob") != null){
            saveEverything(request);
            
        }else if (request.getParameter("btnRemoveComment") != null)
        {
            removeComment(request);
            saveEverything(request);
            buildAndSaveJobList(request);
            
        }else if(request.getParameter("btnAddComment") != null){
            addCommentToJob(request);
            saveEverything(request);
        }else if (request.getParameter("btnUpdateComment") != null){
            updateComment(request);
            saveEverything(request);
            
        }else{
            buildAndSaveJobList(request);
            resetCommentList(request);
        }  
        
        getServletContext().getRequestDispatcher("/ManageJobComments.jsp").forward(request,response); 
    }
    
    private void saveEverything(HttpServletRequest request){
            saveJobChosen(request);
            showComments(request);
    }
    
    private void saveJobChosen(HttpServletRequest request){  
        JobCommentUtility.saveJobToRequest(request, getJobChosen(request));
    }
    
    private JobModel getJobChosen(HttpServletRequest request){
        ArrayList<JobModel> jobs = JobCommentUtility.retrieveJobsListFromSession(request.getSession());
            
        int chosenIndex = JobCommentUtility.getChosenJobIndex(request.getParameter("Jobs"), jobs);
        
        if (chosenIndex < 0){
            chosenIndex = Integer.parseInt( request.getParameter("ChosenJob"));
        }
        
        JobModel job = jobs.get(chosenIndex);
       
        return job;
    }
    
        private void showComments(HttpServletRequest request){
        ArrayList<JobModel> jobs = JobCommentUtility.retrieveJobsListFromSession(request.getSession());
        
        JobModel job = JobCommentUtility.retrieveJobFromRequest(request);
        
        ArrayList<JobCommentModel> comments = JobCommentUtility.getCommentsForJob(job.getId());
        JobCommentUtility.saveCommentsListToSession(request.getSession(), comments);
    }
        
    private void buildAndSaveJobList(HttpServletRequest request){
        ArrayList<JobModel> jobs = JobCommentUtility.selectJobsFromDB();
        
        JobCommentUtility.saveJobsListToSession(request.getSession(), jobs);
    }
    
    private void resetCommentList(HttpServletRequest request){
        JobCommentUtility.saveCommentsListToSession(request.getSession(), new ArrayList<JobCommentModel>());
    }
    
    private void removeComment(HttpServletRequest request){
        ArrayList<JobCommentModel> comments = JobCommentUtility.retrieveCommentsListFromSession(request.getSession());
        
        JobCommentModel comment = comments.get(getChosedCommentIndex(request.getParameter("btnRemoveComment") , comments));
        JobCommentUtility.removeCommentFromJob(comment.getId()); 
    }
    
    private int getChosedCommentIndex(String chosenValue, ArrayList<JobCommentModel> comments){
        for (int i = 0; i < comments.size(); i++){
            if (Integer.toString(i).equals(chosenValue)){
                return i;
            }
        }
        return -1;
    }
    
    private void addCommentToJob(HttpServletRequest request){
        JobModel job = getJobChosen(request);
        
        JobCommentModel comment = retrieveNewComment(request);
        JobCommentUtility.addSCommentToJob(comment);
    }
    
    private JobCommentModel retrieveNewComment(HttpServletRequest request){
        JobCommentModel comment = new JobCommentModel();
        comment.setJobID(getJobChosen(request).getId());
        comment.setComment(request.getParameter("NewComment"));
        
        return comment;      
    }
    
    private void updateComment(HttpServletRequest request){
        JobModel job = getJobChosen(request);

        ArrayList<JobCommentModel> comments = JobCommentUtility.retrieveCommentsListFromSession(request.getSession());
        int chosenCommentIndex = getChosedCommentIndex(request.getParameter("btnUpdateComment") , comments);
        JobCommentModel comment = comments.get(chosenCommentIndex);
        comment.setComment(request.getParameter("ShowComment" + chosenCommentIndex));
        
        JobCommentUtility.updateJobComment(comment);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
