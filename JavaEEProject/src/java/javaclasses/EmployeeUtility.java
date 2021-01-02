/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclasses;

import Interfaces.IEmployeeRepo;
import factories.EmployeeFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.EmployeeModel;
import models.TaskModel;

/**
 *
 * @author Alena Selezneva
 */
public class EmployeeUtility {
    public static final double MINIMUM_PAY_RATE = 11.50;
    
    public static void validateEmployee(EmployeeModel employee){
        ArrayList<String> errors = new ArrayList<>();
        
        if (employee.getFirstName() == null || employee.getFirstName().equals(""))
            errors.add("First Name is required");
        if (employee.getLastName() == null || employee.getLastName().equals(""))
            errors.add("Last Name is required");
        
        if ( employee.getHourlyPayRate() < MINIMUM_PAY_RATE)
            errors.add("Hourly Pay Rate should be greate than " + MINIMUM_PAY_RATE);

        if (employee.getSin() == null || employee.getSin().equals(""))
            errors.add("SIN is required");
        else{
            if ( employee.getSin().length() != 9)
                errors.add("SIN should consist of 9 symbols");

            try {
                Integer.parseInt(employee.getSin());
            }catch(Exception e){
                errors.add("SIN should only consist of digits");
            }
        }

        employee.setErrors(errors);
        employee.setIsValid_(errors.size() == 0);
    }
    
    public static EmployeeModel retrieveEmployeeFromRequest(HttpServletRequest request){
        EmployeeModel employee = (EmployeeModel)request.getAttribute("EmployeeModel");
        if (employee == null)
            employee = new EmployeeModel();
        return employee;
    }
    
    public static void saveEmployeeToRequest(HttpServletRequest request, EmployeeModel employee){
        request.setAttribute("EmployeeModel", employee);
    }
    
    public static void saveEmployeeToDB(EmployeeModel employee){
        IEmployeeRepo employeeRepo = EmployeeFactory.makeEmployeeRepo();
        if (employee.getId() < 1){
            employeeRepo.createEmployee(employee);
        }else{
            employeeRepo.updateEmployee(employee);
        }
    }
    
    public static ArrayList<EmployeeModel> retrieveEmployeeListFromSession(HttpSession session){
        ArrayList<EmployeeModel> employees = (ArrayList<EmployeeModel>)session.getAttribute("EmployeeList");
        if (employees == null)
            employees = new ArrayList<EmployeeModel>();
        
        return employees;
    }
    
    public static ArrayList<Boolean> retrieveIsFullInfoShownFromSession(HttpSession session){
        ArrayList<Boolean> infoShown = (ArrayList<Boolean>)session.getAttribute("FullInfoShownList");
        if (infoShown == null)
            infoShown = new ArrayList<Boolean>();
        
        return infoShown;
    }
    
    public static ArrayList<TaskModel> retrieveSkillsListFromSession(HttpSession session, String name){
        ArrayList<TaskModel> skills = (ArrayList<TaskModel>)session.getAttribute(name);
        if (skills == null)
            skills = new ArrayList<TaskModel>();
        
        return skills;
    }
    
    public static void saveEmployeeListToSession(HttpSession session, ArrayList<EmployeeModel> employees){
        session.setAttribute("EmployeeList", employees);
    }
    
    public static void saveSkillsListToSession(HttpSession session, ArrayList<TaskModel> skills, String name){
        session.setAttribute(name, skills);
    }
    
    public static void saveInfoShownListToSession(HttpSession session, ArrayList<Boolean> infoShown){
        session.setAttribute("FullInfoShownList", infoShown);
    }
    
    public static ArrayList<EmployeeModel> selectEmployeesFromDB(){
        IEmployeeRepo employeeRepo = EmployeeFactory.makeEmployeeRepo();
        
        return employeeRepo.getEmployees();
    }
    
    public static ArrayList<Boolean> buildShowFullInfoListOfSize(int n){
        ArrayList<Boolean> list = new ArrayList<>();
        for (int i = 0; i < n; i++)
            list.add(false);
        
        return list;
    }

    public static void deleteEmployeeFromDB(EmployeeModel employee){
        IEmployeeRepo employeeRepo = EmployeeFactory.makeEmployeeRepo();
        
        employeeRepo.deleteEmployee(employee);
    }
    
    public static EmployeeModel retrieveTasksAndTeamsFromDB(EmployeeModel employee){
        IEmployeeRepo employeeRepo = EmployeeFactory.makeEmployeeRepo();
        
        employee.setTeamNames(employeeRepo.getTeamNamesOfEmployee(employee.getId()));
        employee.setSkillNames(employeeRepo.getSkillNamesOfEmployee(employee.getId()));
        
        return employee;
    }
    
    public static int getChosenEmployeeIndex(String chosenValue, ArrayList<EmployeeModel> employees){
        for (int i = 0; i < employees.size(); i++){
            if (Integer.toString(i).equals(chosenValue)){
                return i;
            }
        }
        return -1;
    }
    
    public static int findIndexOfEmployeeInList(ArrayList<EmployeeModel> employees, EmployeeModel employee){
        for (int i = 0; i < employees.size(); i++){
            if (employees.get(i).getId() == employee.getId())
                return i;
        }
        return -1;
    }
    
    public static ArrayList<TaskModel> getSkillsForEmployee(int id){
        IEmployeeRepo employeeRepo = EmployeeFactory.makeEmployeeRepo();
        
        return employeeRepo.getSkillsOfEmployee(id);
    }
    
    public static void removeSkillForEmployee(int employeeID, int skillID){
        IEmployeeRepo employeeRepo = EmployeeFactory.makeEmployeeRepo();
                
        employeeRepo.removeSkillOfEmployee(employeeID, skillID);
    }
    
    public static void addSkillForEmployee(int employeeID, int skillID){
        IEmployeeRepo employeeRepo = EmployeeFactory.makeEmployeeRepo();
                
        employeeRepo.addSkillForEmployee(employeeID, skillID);
    }
    
    public static ArrayList<TaskModel> getNotChosenSkills(ArrayList<TaskModel> allTasks, ArrayList<TaskModel> employeeTasks){
        ArrayList<TaskModel> notChosenTasks = new ArrayList<>();
        
        for (int i = 0; i < allTasks.size(); i++){
            boolean contains = false;
            for (int j = 0; j < employeeTasks.size(); j++){
                if (allTasks.get(i).getId() == employeeTasks.get(j).getId()){
                    contains = true;
                    break;
                }
            }
            
            if (!contains)
                notChosenTasks.add(allTasks.get(i));
        }
        
        return notChosenTasks;
    }
    
}
