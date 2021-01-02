/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import javaclasses.ClientUtility;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.ClientModel;

/**
 *
 * @author Anastasiia Egorova
 */
public class ClientController extends HttpServlet {

      private String mainPageUrl = "/index.jsp";
      

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

        if (request.getParameter("btnCreateClient") != null) {
              saveClientClicked(request);
              getServletContext().getRequestDispatcher("/CreateClient.jsp").forward(request,response); 
              return;
        } 
        else if (request.getParameter("btnUpdateClient") != null) {
              updateClient(request);
              getServletContext().getRequestDispatcher("/CreateClient.jsp").forward(request,response); 
              return;
        }
        else if (request.getParameter("btnFlipShowFullInfo") != null) {
              flipShowFullInfo(request);
        } 
        else if (request.getParameter("btnDeleteClient") != null) {
              deleteClient(request);
        } else if (request.getParameter("btnSearchClient") != null){
            searchClients(request);
        }
        else {
              buildAndSaveLists(request);
        }
        getServletContext().getRequestDispatcher("/ListClients.jsp").forward(request,response); 
      }
      
    private ClientModel retrieveClientModel(HttpServletRequest request) {
        ClientModel client = new ClientModel();
        
        client.setId(Integer.parseInt( request.getParameter("id")));
        client.setName(request.getParameter("name").toString());
        client.setDescription(request.getParameter("description").toString());
        client.setCreationDate(LocalDate.parse( request.getParameter("creationDate")));
        client.setIsDeleted(false);
        
        return client;
    }
    
    private void saveListsToSession(HttpServletRequest request, ArrayList<ClientModel> clients, ArrayList<Boolean> showFullInfo) {
          
            ClientUtility.saveClientListToRequest(request.getSession(), clients);
            ClientUtility.saveInfoShownListToRequest(request.getSession(), showFullInfo);
    }
    
    private void buildAndSaveLists(HttpServletRequest request) {
          
            ArrayList<ClientModel> clients = ClientUtility.selectClientsFromDB();
            ArrayList<Boolean> showFullInfo = ClientUtility.buildShowFullInfoListOfSize(clients.size());

            saveListsToSession(request, clients, showFullInfo);
    }
      
   private void flipShowFullInfo(HttpServletRequest request) {
         
        ArrayList<ClientModel> clients = ClientUtility.retrieveClientListFromRequest(request.getSession());
        ArrayList<Boolean> showFullInfo = ClientUtility.retrieveIsFullInfoShownFromRequest(request.getSession());

        String flippedValue = request.getParameter("btnFlipShowFullInfo");
        for (int i = 0; i < showFullInfo.size(); i++){

            if (Integer.toString(i).equals(flippedValue)) {
                showFullInfo.set(i, !showFullInfo.get(i));

                saveListsToSession(request, clients, showFullInfo);
                return;
            }
        }
    }
   
    private void updateClient(HttpServletRequest request) {
            
        ArrayList<ClientModel> clients = ClientUtility.retrieveClientListFromRequest(request.getSession());            
        int chosenIndex = getChosenClientIndex(request.getParameter("btnUpdateClient"), clients);

        ClientUtility.saveClientToRequest(request, clients.get(chosenIndex));
    }
    
    private int getChosenClientIndex(String chosenValue, ArrayList<ClientModel> clients) {
        for (int i = 0; i < clients.size(); i++){
              if (Integer.toString(i).equals(chosenValue)){
                  return i;
              }
        }
        return -1;
     }
    
    private void saveClientClicked(HttpServletRequest request) {
        ClientModel client = retrieveClientModel(request);
        ClientUtility.validateClient(client);
        ClientUtility.saveClientToRequest(request, client);

        if (client.getIsValid())
            ClientUtility.saveClientToDB(client);
      }
    
    private void deleteClient(HttpServletRequest request) {
        ArrayList<ClientModel> clients = ClientUtility.retrieveClientListFromRequest(request.getSession());
        int chosenIndex = getChosenClientIndex(request.getParameter("btnDeleteClient"), clients);

        ClientUtility.deleteClientFromDB(clients.get(chosenIndex));

        buildAndSaveLists(request);
    }
      
    private void searchClients(HttpServletRequest request){
        String name = request.getParameter("searchName");
        
        ArrayList<ClientModel> clients = ClientUtility.selectClientsFromDB();
        if (! name.isEmpty()){
            clients.removeIf(c -> (! c.getName().toLowerCase().contains(name.toLowerCase())));
        }
        
        saveListsToSession(request, clients, ClientUtility.buildShowFullInfoListOfSize(clients.size()));  
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
}
