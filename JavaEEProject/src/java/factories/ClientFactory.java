/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import Interfaces.IClientRepo;
import sqlAccess.SqlClient;

/**
 *
 * @author Anastasiia Egorova
 */
public class ClientFactory {
      
      public static IClientRepo makeClientRepo() {
            return new SqlClient();
      }      
}
