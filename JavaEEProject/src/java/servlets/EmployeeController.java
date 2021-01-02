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
import javaclasses.EmployeeUtility;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.EmployeeModel;

/**
 *
 * @author Alena Selezneva
 */
public class EmployeeController extends HttpServlet {

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
            out.println("<title>Servlet EmployeeController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployeeController at " + request.getContextPath() + "</h1>");
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
  
        if (request.getParameter("btnSaveEmployee") != null){
            saveEmployeeClicked(request);
            getServletContext().getRequestDispatcher("/CreateEmployee.jsp").forward(request,response); 
            return;
        }else if (request.getParameter("btnFlipShowFullInfo") != null){
            flipShowFullInfo(request);
        }else if (request.getParameter("btnUpdateEmployee") != null){
            updateEmployee(request);
            getServletContext().getRequestDispatcher("/CreateEmployee.jsp").forward(request,response); 
        }else if (request.getParameter("btnDeleteEmployee") != null){
            deleteEmployee(request);
        } else if (request.getParameter("btnSearchEmployee") != null){
            searchEmployees(request);
        } else {
            buildAndSaveLists(request);
        }
        
        
        getServletContext().getRequestDispatcher("/ListEmployees.jsp").forward(request,response); 
        
    }
    
    private void saveEmployeeClicked(HttpServletRequest request){
        
        EmployeeModel employee = retrieveEmployeeModel(request);    
        EmployeeUtility.validateEmployee(employee);
        EmployeeUtility.saveEmployeeToRequest(request, employee);

        if (employee.isValid())
            EmployeeUtility.saveEmployeeToDB(employee);
    }

    private EmployeeModel retrieveEmployeeModel(HttpServletRequest request){
        EmployeeModel employee = new EmployeeModel();
        
        employee.setId(Integer.parseInt( request.getParameter("id")));
        employee.setCreationDate(LocalDate.parse( request.getParameter("creationDate")));
        employee.setFirstName(request.getParameter("firstName").toString());
        employee.setLastName(request.getParameter("lastName").toString());
        employee.setSin(request.getParameter("sin").toString());
        employee.setHourlyPayRate(Double.parseDouble(request.getParameter("hourlyPayRate").toString()));
        
        return employee;
    }
    
    private void saveListsToSession(HttpServletRequest request, ArrayList<EmployeeModel> employees, ArrayList<Boolean> showFullInfo){
        EmployeeUtility.saveEmployeeListToSession(request.getSession(), employees);
        EmployeeUtility.saveInfoShownListToSession(request.getSession(), showFullInfo);
    }
    
    private void buildAndSaveLists(HttpServletRequest request){
        ArrayList<EmployeeModel> employees = EmployeeUtility.selectEmployeesFromDB();
        ArrayList<Boolean> showFullInfo = EmployeeUtility.buildShowFullInfoListOfSize(employees.size());
        
        for (int i = 0; i < employees.size(); i++){
            employees.set(i, EmployeeUtility.retrieveTasksAndTeamsFromDB(employees.get(i)));
        }
        
        saveListsToSession(request, employees, showFullInfo);
    }
    
    private void flipShowFullInfo(HttpServletRequest request){
        ArrayList<EmployeeModel> employees = EmployeeUtility.retrieveEmployeeListFromSession(request.getSession());
        ArrayList<Boolean> showFullInfo = EmployeeUtility.retrieveIsFullInfoShownFromSession(request.getSession());
        
        int chosenIndex = EmployeeUtility.getChosenEmployeeIndex(request.getParameter("btnFlipShowFullInfo"), employees);
        
        showFullInfo.set(chosenIndex, !showFullInfo.get(chosenIndex));
        saveListsToSession(request, employees, showFullInfo);
    }
    
    private void deleteEmployee(HttpServletRequest request){
        ArrayList<EmployeeModel> employees = EmployeeUtility.retrieveEmployeeListFromSession(request.getSession());
        int chosenIndex = EmployeeUtility.getChosenEmployeeIndex(request.getParameter("btnDeleteEmployee"), employees);
        
        EmployeeUtility.deleteEmployeeFromDB(employees.get(chosenIndex));
        
        buildAndSaveLists(request);
    }
    
    private void updateEmployee(HttpServletRequest request){
        ArrayList<EmployeeModel> employees = EmployeeUtility.retrieveEmployeeListFromSession(request.getSession());
        int chosenIndex = EmployeeUtility.getChosenEmployeeIndex(request.getParameter("btnUpdateEmployee"), employees);
        
        EmployeeUtility.saveEmployeeToRequest(request, employees.get(chosenIndex));
    }
    
    private void searchEmployees(HttpServletRequest request){
        String lastName = request.getParameter("searchLastName");
        String sin = request.getParameter("searchSin");
        
        ArrayList<EmployeeModel> employees = EmployeeUtility.selectEmployeesFromDB();
        if (!lastName.isEmpty()){
            employees.removeIf(emp -> (!emp.getLastName().toLowerCase().contains(lastName.toLowerCase())));
        }
        if (! sin.isEmpty()){
            employees.removeIf(emp -> (!emp.getSin().contains(sin)));
        }
        
        saveListsToSession(request, employees, EmployeeUtility.buildShowFullInfoListOfSize(employees.size()));  
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
