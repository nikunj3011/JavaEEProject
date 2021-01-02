/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import static javaclasses.JobCommentUtility.getChosenJobIndex;
import javaclasses.JobUtility;
import javaclasses.TaskUtility;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ClientModel;
import models.JobModel;
import models.TaskModel;

/**
 *
 * @author Anastasiia Egorova 
 */
public class JobController extends HttpServlet {

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
            
            if (request.getParameter("btnCalculateCost") != null) {
                  saveJobClicked(request);
                  getServletContext().getRequestDispatcher("/CreateJob.jsp").forward(request,response); 
            }
            else if (request.getParameter("btnCreateJob") != null) {

                  JobModel job = retrieveJobModel(request);
                  JobUtility.saveJobToDB(job);
                  
                  job.setIsSaved(true);
                  JobUtility.saveJobToRequest(request, job);
                  getServletContext().getRequestDispatcher("/CreateJob.jsp").forward(request,response); 
            }
            else if (request.getParameter("btnFlipShowFullInfo") != null) {
                  flipShowFullInfo(request);
            } 
            else if (request.getParameter("btnDeleteJob") != null) {
                  deleteJob(request);
            }
            else {
                  buildAndSaveLists(request);
            }
            
            getServletContext().getRequestDispatcher("/ListJobs.jsp").forward(request,response); 
      }

      private void saveJobClicked(HttpServletRequest request) {
            JobModel job = retrieveJobModel(request);
            JobUtility.validateJob(job);
            JobUtility.saveJobToRequest(request, job);
      }

      private JobModel retrieveJobModel(HttpServletRequest request) {
      
            JobModel job = new JobModel();
        
            job.setId(Integer.parseInt( request.getParameter("id")));
            job.setName(request.getParameter("name").toString());
            job.setDescription(request.getParameter("description").toString());
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            if (!(request.getParameter("startTime") == null || request.getParameter("startTime").equals(""))) {
                  LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("startTime"), formatter);
                  job.setStartTime(dateTime);
            }
            else 
                  job.setStartTime(null);
            
            int chosenClientInt = Integer.parseInt(request.getParameter("clients"));
            job.setClientID(chosenClientInt);
            
            int chosenTeam = Integer.parseInt(request.getParameter("teams"));
            job.setTeam(chosenTeam);
            
            ArrayList<TaskModel> tasksFromDB = TaskUtility.selectTasksFromDB();
            ArrayList<Integer> tasks = new ArrayList<Integer>();
            for (TaskModel task : tasksFromDB) {
                  int i = task.getId();
                  if (request.getParameter("task"+i) != null) {
                        tasks.add(Integer.parseInt(request.getParameter("task"+i)));
                  }
            }
            job.setTasks(tasks);
            
            if (JobUtility.doesTeamCoverAllTasks(job.getTasks(), job.getTeam()))
                  job.setDuration(JobUtility.calculateJobDuration(job.getTasks(), job.getTeam()));
            
            if (request.getParameter("onSite") != null) 
                  job.setIsOnSite(true);
            else
                  job.setIsOnSite(false);
            
            if (job.getStartTime() != null){
                int weekdayStart = 8;
                int weekdayFinish = 17;
                if (job.getStartTime().getHour() < weekdayStart ||  job.getStartTime().getHour() > weekdayFinish 
                        || job.getStartTime().getDayOfWeek().equals("SATURDAY")
                        || job.getStartTime().getDayOfWeek().equals("SUNDAY")) 
                      job.setIsEmergencyCall(true);
                else
                      job.setIsEmergencyCall(false);
            }
                
            job.setCost(JobUtility.calculateJobCost(job.getTeam(), job.getDuration(), job.getIsOnSite()));

            return job;
      }
      
      private int getChosenJobtIndex(String chosenValue, ArrayList<ClientModel> clients){
             for (int i = 0; i < clients.size(); i++){
                   if (Integer.toString(i).equals(chosenValue)){
                         return i;
                   }
             }
             return -1;
     }
     
      private void buildAndSaveLists(HttpServletRequest request) {
            
            ArrayList<JobModel> jobs = JobUtility.selectJobsFromDB();
            ArrayList<Boolean> showFullInfo = JobUtility.buildShowFullInfoListOfSize(jobs.size());

            saveListsToRequest(request, jobs, showFullInfo);
      }

      private void saveListsToRequest(HttpServletRequest request, ArrayList<JobModel> jobs, ArrayList<Boolean> showFullInfo) {
            
            JobUtility.saveJobListToRequest(request.getSession(), jobs);
            JobUtility.saveInfoShownListToRequest(request.getSession(), showFullInfo);
      }

      private void flipShowFullInfo(HttpServletRequest request) {
                     
            ArrayList<JobModel> jobs = JobUtility.retrieveJobListFromRequest(request.getSession());
            ArrayList<Boolean> showFullInfo = JobUtility.retrieveIsFullInfoShownFromRequest(request.getSession());

            String flippedValue = request.getParameter("btnFlipShowFullInfo");
            for (int i = 0; i < showFullInfo.size(); i++){
                  
                if (Integer.toString(i).equals(flippedValue)) {
                    showFullInfo.set(i, !showFullInfo.get(i));

                    saveListsToRequest(request, jobs, showFullInfo);
                    return;
                }
             }
      }

      private void deleteJob(HttpServletRequest request) {
            
            ArrayList<JobModel> jobs = JobUtility.retrieveJobListFromRequest(request.getSession());
            int chosenIndex = getChosenJobIndex(request.getParameter("btnDeleteJob"), jobs);
        
            JobUtility.deleteJobFromDB(jobs.get(chosenIndex));
        
            buildAndSaveLists(request);
      }
}
