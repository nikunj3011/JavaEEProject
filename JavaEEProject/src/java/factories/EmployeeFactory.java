/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import Interfaces.IEmployeeRepo;
import sqlAccess.SqlEmployee;

/**
 *
 * @author Alena Selezneva
 */
public class EmployeeFactory {
    public static IEmployeeRepo makeEmployeeRepo() {
        return new SqlEmployee();
    }  
}
