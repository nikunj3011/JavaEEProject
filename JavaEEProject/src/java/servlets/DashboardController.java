/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Interfaces.IJobRepo;
import factories.JobFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javaclasses.JobUtility;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.JobModel;

/**
 *
 * @author Anastasiia Egorova
 */
public class DashboardController extends HttpServlet {


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
            
            //doPost(request, response);
            buildAndSaveLists(request);
            
            getServletContext().getRequestDispatcher("/Dashboard.jsp").forward(request,response); 
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
            
            if (request.getParameter("btnFilter") != null) {
                  
                String startTime = readDateTime(request, "startTime").substring(0, 10);
                String endTime = readDateTime(request, "endTime").substring(0, 10);

                IJobRepo jobs = JobFactory.makeJobRepo();
                ArrayList<JobModel> fliteredJobs = jobs.getJobsBetween(startTime, endTime);

                request.setAttribute("JobList", fliteredJobs);
            }
            else
                buildAndSaveLists(request);
            
            getServletContext().getRequestDispatcher("/Dashboard.jsp").forward(request,response); 
      }

      /**
       * Returns a short description of the servlet.
       *
       * @return a String containing servlet description
       */
      @Override
      public String getServletInfo() {
            return "Short description";
      }

    private void buildAndSaveLists(HttpServletRequest request) {
            
        ArrayList<JobModel> jobs = JobUtility.selectJobsFromDB();
        request.setAttribute("JobList", jobs);   
    }

      private String readDateTime(HttpServletRequest request, String param) {
            
            String dateTime = null;
            
            if (request.getParameter(param) != null) {
                  dateTime = request.getParameter(param);
            }
            
            return dateTime;
      }

}
