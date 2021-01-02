/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.util.ArrayList;
import models.EmployeeModel;
import models.TaskModel;

/**
 *
 * @author Alena Selezneva
 */
public interface IEmployeeRepo {
    
    public ArrayList<EmployeeModel> getEmployees();
    
    public EmployeeModel getEmployee(int id);
    
    public void createEmployee(EmployeeModel employee); 
    
    public void updateEmployee(EmployeeModel employee); 
    
    public void deleteEmployee(EmployeeModel employee);
    
    public ArrayList<String> getTeamNamesOfEmployee(int id);
    
    public ArrayList<String> getSkillNamesOfEmployee(int id);
    
    public ArrayList<TaskModel> getSkillsOfEmployee(int id);
    
    public void removeSkillOfEmployee(int employeeID, int skillId);
    
    public void addSkillForEmployee(int employeeID, int skillId);
}
