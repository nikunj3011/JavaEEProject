/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.util.ArrayList;
import models.ClientModel;

/**
 *
 * @author Anastasiia Egorova
 */
public interface IClientRepo {
      public ClientModel getClient(int id);
      
      public ArrayList<ClientModel> getClients();
      
      public void createClient(ClientModel c);
      
      public void updateClient(ClientModel c); 

      public void deleteClient(ClientModel client);
}
