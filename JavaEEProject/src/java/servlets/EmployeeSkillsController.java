/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javaclasses.EmployeeUtility;
import javaclasses.TaskUtility;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.EmployeeModel;
import models.TaskModel;

/**
 *
 * @author alena
 */
public class EmployeeSkillsController extends HttpServlet {
    private final String employeeSkillsListName = "EmployeeSkillslist";
    private final String unusedSkillsList = "UnusedSkillsList";
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EmployeeSkillsController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployeeSkillsController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        
        if (request.getParameter("btnChooseEmployee") != null){
            saveEverything(request);
            
        }else if (request.getParameter("btnRemoveSkill") != null)
        {
            removeSkillFromEmployee(request);
            saveEverything(request);
            
        }else if(request.getParameter("btnAddSkill") != null){
            addSkillForEmployee(request);
            saveEverything(request);
        }else{
            buildAndSaveEmployeeList(request);
            resetSkillLists(request);
        }        

        getServletContext().getRequestDispatcher("/ManageEmployeeSkills.jsp").forward(request,response);     
    }
    
    private void saveEverything(HttpServletRequest request){
            saveEmployeeChosen(request);
            showSkills(request);
            selectAndSaveUnusedSkills(request);
    }
    
    private void buildAndSaveEmployeeList(HttpServletRequest request){
        ArrayList<EmployeeModel> employees = EmployeeUtility.selectEmployeesFromDB();

        EmployeeUtility.saveEmployeeListToSession(request.getSession(), employees);
    }
    
    private void resetSkillLists(HttpServletRequest request){
        EmployeeUtility.saveSkillsListToSession(request.getSession(), new ArrayList<TaskModel>(), unusedSkillsList);
        EmployeeUtility.saveSkillsListToSession(request.getSession(), new ArrayList<TaskModel>(), employeeSkillsListName);
    }
    
    private void saveEmployeeChosen(HttpServletRequest request){   
        EmployeeUtility.saveEmployeeToRequest(request, getEmployeeChosen(request));
    }
    
    private EmployeeModel getEmployeeChosen(HttpServletRequest request){
        ArrayList<EmployeeModel> employees = EmployeeUtility.retrieveEmployeeListFromSession(request.getSession());
            
        int chosenIndex = EmployeeUtility.getChosenEmployeeIndex(request.getParameter("Employees"), employees);
        
        if (chosenIndex < 0){
            chosenIndex = Integer.parseInt( request.getParameter("ChosenEmployee"));
        }
        
        EmployeeModel employee = employees.get(chosenIndex);  

        return employee;
    }
    
    private void showSkills(HttpServletRequest request){
        ArrayList<EmployeeModel> employees = EmployeeUtility.retrieveEmployeeListFromSession(request.getSession());
        
        EmployeeModel employee = getEmployeeChosen(request);

        ArrayList<TaskModel> skills = EmployeeUtility.getSkillsForEmployee(employee.getId());
        EmployeeUtility.saveSkillsListToSession(request.getSession(), skills, employeeSkillsListName);
    }
    
    private void removeSkillFromEmployee(HttpServletRequest request){
        EmployeeModel employee = getEmployeeChosen(request);
        ArrayList<TaskModel> employeeSkills = EmployeeUtility.retrieveSkillsListFromSession(request.getSession(), employeeSkillsListName);
    
        
        TaskModel skill = employeeSkills.get(getChosedSkillIndex(request.getParameter("btnRemoveSkill") , employeeSkills));
        EmployeeUtility.removeSkillForEmployee(employee.getId(), skill.getId());
    }
    
    private int getChosedSkillIndex(String chosenValue, ArrayList<TaskModel> skills){
        for (int i = 0; i < skills.size(); i++){
            if (Integer.toString(i).equals(chosenValue)){
                return i;
            }
        }
        return -1;
    }
    
    private void selectAndSaveUnusedSkills(HttpServletRequest request){
        ArrayList<TaskModel> allSkills = TaskUtility.selectTasksFromDB();
        ArrayList<TaskModel> employeeSkills = EmployeeUtility.retrieveSkillsListFromSession(request.getSession(), employeeSkillsListName);
        ArrayList<TaskModel> unusedSkills = EmployeeUtility.getNotChosenSkills(allSkills, employeeSkills);
        
        EmployeeUtility.saveSkillsListToSession(request.getSession(), unusedSkills, unusedSkillsList);
    }

    private void addSkillForEmployee(HttpServletRequest request){
        EmployeeModel employee = getEmployeeChosen(request);
        ArrayList<TaskModel> unusedSkills = EmployeeUtility.retrieveSkillsListFromSession(request.getSession(), unusedSkillsList);
    
        
        TaskModel skill = unusedSkills.get(getChosedSkillIndex(request.getParameter("UnusedSkills") , unusedSkills));
        EmployeeUtility.addSkillForEmployee(employee.getId(), skill.getId());
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
