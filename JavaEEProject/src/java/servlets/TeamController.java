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
import javaclasses.TeamUtility;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.EmployeeModel;
import models.TeamModel;

/**
 *
 * @author Nikunj
 */
public class TeamController extends HttpServlet {

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
            out.println("<title>Servlet TeamController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TeamController at " + request.getContextPath() + "</h1>");
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
  
        if (request.getParameter("btnCreateTeam") != null){
            saveTeamClicked(request);
            getServletContext().getRequestDispatcher("/createTeam.jsp").forward(request,response); 
            return;
        }else if (request.getParameter("btnFlipShowFullInfo") != null){
            flipShowFullInfo(request);
        }else if (request.getParameter("btnUpdateTeam") != null){
            updateTeam(request);
            getServletContext().getRequestDispatcher("/createTeam.jsp").forward(request,response); 
        }else if (request.getParameter("btnDeleteTeam") != null){
            deleteTeam(request);
        }else {
            buildAndSaveLists(request);
        }
         
        getServletContext().getRequestDispatcher("/ListTeams.jsp").forward(request,response); 
        
    }
    
    private void saveTeamClicked(HttpServletRequest request){
        
        TeamModel team = retrieveTeamModel(request);    
        TeamUtility.validateTeam(team);
        TeamUtility.saveTeamToRequest(request, team);

        if (team.isValid())
            TeamUtility.saveTeamToDB(team);
    }

    private TeamModel retrieveTeamModel(HttpServletRequest request){
        TeamModel team = new TeamModel();
        
        team.setId(Integer.parseInt( request.getParameter("id")));
        team.setTeamName(request.getParameter("teamname").toString()); 
        if (request.getParameter("isOnCall") != null) 
            team.setisOnCall(true);
        else
            team.setisOnCall(false);
        
        ArrayList<EmployeeModel> employeeFromDB = EmployeeUtility.selectEmployeesFromDB();
        ArrayList<Integer> employees = new ArrayList<Integer>();
        for (EmployeeModel employee : employeeFromDB) {
              int i = employee.getId();
              if (request.getParameter("employee"+i) != null) {
                    employees.add(Integer.parseInt(request.getParameter("employee"+i)));
              }
        }
            team.setTeamMembers(employees);
        
        return team;
    }
    
    private void saveListsToRequest(HttpServletRequest request, ArrayList<TeamModel> teams, ArrayList<Boolean> showFullInfo){
        TeamUtility.saveTeamListToRequest(request.getSession(), teams);
        TeamUtility.saveInfoShownListToRequest(request.getSession(), showFullInfo);
    }
    
    private void buildAndSaveLists(HttpServletRequest request){
        ArrayList<TeamModel> teams = TeamUtility.selectTeamsFromDB();
        ArrayList<Boolean> showFullInfo = TeamUtility.buildShowFullInfoListOfSize(teams.size());
        saveListsToRequest(request, teams, showFullInfo);
    }
    
    private int getChosedTeamIndex(String chosenValue, ArrayList<TeamModel> teams){
        for (int i = 0; i < teams.size(); i++){
            if (Integer.toString(i).equals(chosenValue)){
                return i;
            }
        }
        return -1;
    }
    
    private void flipShowFullInfo(HttpServletRequest request){
        ArrayList<TeamModel> teams = TeamUtility.retrieveTeamListFromRequest(request.getSession());
        ArrayList<Boolean> showFullInfo = TeamUtility.retrieveIsFullInfoShownFromRequest(request.getSession());
        
        int chosenIndex = getChosedTeamIndex(request.getParameter("btnFlipShowFullInfo"), teams);
        
        showFullInfo.set(chosenIndex, !showFullInfo.get(chosenIndex));
        saveListsToRequest(request, teams, showFullInfo);
    }
    
    private void deleteTeam(HttpServletRequest request){
        ArrayList<TeamModel> teams = TeamUtility.retrieveTeamListFromRequest(request.getSession());
        int chosenIndex = getChosedTeamIndex(request.getParameter("btnDeleteTeam"), teams);
        
        TeamUtility.deleteTeamFromDB(teams.get(chosenIndex));
        
        buildAndSaveLists(request);
    }
    
    private void updateTeam(HttpServletRequest request){
        ArrayList<TeamModel> teams = TeamUtility.retrieveTeamListFromRequest(request.getSession());
        int chosenIndex = getChosedTeamIndex(request.getParameter("btnUpdateTeam"), teams);
        
        TeamUtility.saveTeamToRequest(request, teams.get(chosenIndex));
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
