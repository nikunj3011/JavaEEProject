/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import Interfaces.IJobRepo;
import sqlAccess.SqlJob;

/**
 *
 * @author Anastasiia Egorova
 */
public class JobFactory {
      
      public static IJobRepo makeJobRepo() {
            return new SqlJob();
      }      
}
