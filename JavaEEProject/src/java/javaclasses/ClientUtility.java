/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclasses;

import Interfaces.IClientRepo;
import factories.ClientFactory;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.ClientModel;

/**
 *
 * @author Anastasiia Egorova
 */
public class ClientUtility {
    
    public static void validateClient(ClientModel client){
          
        ArrayList<String> errors = new ArrayList<>();
        
        if (client.getName() == null || client.getName().equals(""))
            errors.add("Name is required");

        client.setErrors(errors);
        client.setIsValid(errors.size() == 0);
    }
    
    public static ClientModel retrieveClientFromRequest(HttpServletRequest request) {
          
        ClientModel client = (ClientModel)request.getAttribute("ClientModel");
        
        if (client == null)
            client = new ClientModel();
        
        return client;
    }
    
    public static void saveClientToDB(ClientModel client){
          
            IClientRepo clientRepo = ClientFactory.makeClientRepo();
            
            if (client.getId() < 1)
                clientRepo.createClient(client);
            else
                clientRepo.updateClient(client);
    }
    
     public static ArrayList<ClientModel> retrieveClientListFromRequest(HttpSession session) {
              
            ArrayList<ClientModel> clients = (ArrayList<ClientModel>)session.getAttribute("ClientList");

            if (clients == null)
                clients = new ArrayList<ClientModel>();

            return clients;
    }
        
     public static ArrayList<Boolean> retrieveIsFullInfoShownFromRequest(HttpSession session) {
           
            ArrayList<Boolean> infoShown = (ArrayList<Boolean>)session.getAttribute("FullInfoShownList");

            if (infoShown == null)
                infoShown = new ArrayList<Boolean>();

            return infoShown;
    }
     
   public static void saveClientListToRequest(HttpSession session, ArrayList<ClientModel> clients){
            session.setAttribute("ClientList", clients);
    }

    public static void saveInfoShownListToRequest(HttpSession session, ArrayList<Boolean> infoShown){
            session.setAttribute("FullInfoShownList", infoShown);
    }

      public static ArrayList<ClientModel> selectClientsFromDB() {
                    
            IClientRepo clientRepo = ClientFactory.makeClientRepo();
            return clientRepo.getClients();
      }

      public static ArrayList<Boolean> buildShowFullInfoListOfSize(int size) {
            
            ArrayList<Boolean> list = new ArrayList<>();
            
            for (int i = 0; i < size; i++)
                list.add(false);

            return list;
      }

    public static void saveClientToRequest(HttpServletRequest request, ClientModel client){
        request.setAttribute("ClientModel", client);
    }

      public static void deleteClientFromDB(ClientModel client) {
            
            IClientRepo clientRepo = ClientFactory.makeClientRepo();
            clientRepo.deleteClient(client);
      }
     
}
