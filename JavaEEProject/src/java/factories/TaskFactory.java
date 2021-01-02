/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import Interfaces.ITaskRepo;
import sqlAccess.SqlTask;

/**
 *
 * @author Nikunj
 */
public class TaskFactory {
    public static ITaskRepo makeTaskRepo() {
        return new SqlTask();
    }  
}
