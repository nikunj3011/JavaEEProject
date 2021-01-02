/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javaclasses.TaskUtility;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.TaskModel;

/**
 *
 * @author Nikunj
 */
public class TaskController extends HttpServlet {

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
  
        if (request.getParameter("btnCreateTask") != null){
            saveTaskClicked(request);
            getServletContext().getRequestDispatcher("/createTask.jsp").forward(request,response); 
            return;
        }else if (request.getParameter("btnFlipShowFullInfo") != null){
            flipShowFullInfo(request);
        }else if (request.getParameter("btnUpdateTask") != null){
            updateTask(request);
            getServletContext().getRequestDispatcher("/createTask.jsp").forward(request,response); 
        }else if (request.getParameter("btnDeleteTask") != null){
            deleteTask(request);
        }else {
            buildAndSaveLists(request);
        }
        
        
        getServletContext().getRequestDispatcher("/ListTasks.jsp").forward(request,response); 
        
    }
    
    private void saveTaskClicked(HttpServletRequest request){
        
        TaskModel task = retrieveTaskModel(request);    
        TaskUtility.validateTask(task);
        TaskUtility.saveTaskToRequest(request, task);

        if (task.isValid())
            TaskUtility.saveTaskToDB(task);
    }

    private TaskModel retrieveTaskModel(HttpServletRequest request){
        TaskModel task = new TaskModel();
        
        task.setId(Integer.parseInt( request.getParameter("id")));
        task.setTaskName(request.getParameter("taskname").toString());
        task.setDescription(request.getParameter("description").toString());
        task.setDuration(request.getParameter("duration").toString());
        
        return task;
    }
    
    private void saveListsToRequest(HttpServletRequest request, ArrayList<TaskModel> tasks, ArrayList<Boolean> showFullInfo){
        TaskUtility.saveTaskListToRequest(request.getSession(), tasks);
        TaskUtility.saveInfoShownListToRequest(request.getSession(), showFullInfo);
    }
    
    private void buildAndSaveLists(HttpServletRequest request){
        ArrayList<TaskModel> tasks = TaskUtility.selectTasksFromDB();
        ArrayList<Boolean> showFullInfo = TaskUtility.buildShowFullInfoListOfSize(tasks.size());
        saveListsToRequest(request, tasks, showFullInfo);
    }
    
    private int getChosedTaskIndex(String chosenValue, ArrayList<TaskModel> tasks){
        for (int i = 0; i < tasks.size(); i++){
            if (Integer.toString(i).equals(chosenValue)){
                return i;
            }
        }
        return -1;
    }
    
    private void flipShowFullInfo(HttpServletRequest request){
        ArrayList<TaskModel> tasks = TaskUtility.retrieveTaskListFromRequest(request.getSession());
        ArrayList<Boolean> showFullInfo = TaskUtility.retrieveIsFullInfoShownFromRequest(request.getSession());
        
        int chosenIndex = getChosedTaskIndex(request.getParameter("btnFlipShowFullInfo"), tasks);
        
        showFullInfo.set(chosenIndex, !showFullInfo.get(chosenIndex));
        saveListsToRequest(request, tasks, showFullInfo);
    }
    
    private void deleteTask(HttpServletRequest request){
        ArrayList<TaskModel> tasks = TaskUtility.retrieveTaskListFromRequest(request.getSession());
        int chosenIndex = getChosedTaskIndex(request.getParameter("btnDeleteTask"), tasks);
        
        TaskUtility.deleteTaskFromDB(tasks.get(chosenIndex));
        
        buildAndSaveLists(request);
    }
    
    private void updateTask(HttpServletRequest request){
        ArrayList<TaskModel> tasks = TaskUtility.retrieveTaskListFromRequest(request.getSession());
        int chosenIndex = getChosedTaskIndex(request.getParameter("btnUpdateTask"), tasks);
        
        TaskUtility.saveTaskToRequest(request, tasks.get(chosenIndex));
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
