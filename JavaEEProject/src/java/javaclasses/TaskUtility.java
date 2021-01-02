/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclasses;

import Interfaces.ITaskRepo;
import factories.TaskFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.TaskModel;

/**
 *
 * @author Nikunj
 */

public class TaskUtility {
    public static final double MINIMUM_DURATION = 30;

    public static void validateTask(TaskModel task){
        ArrayList<String> errors = new ArrayList<>();
        
        if (task.getTaskName() == null || task.getTaskName().equals(""))
            errors.add("Task Name is required");

        if ( getDoubleValue(task.getDuration()) < MINIMUM_DURATION )
            errors.add("Duration should be greater than " + MINIMUM_DURATION );

        if (task.getDescription() == null || task.getDescription().equals(""))
            errors.add("Description is required");

        task.setErrors(errors);
        task.setIsValid_(errors.size() == 0);
            
    
    }

    private static double getDoubleValue(String value){
        
        try{
            return Double.parseDouble(value);
        }
        catch(Exception e){
            return 0.0;
        }
    }
    
    public static boolean isNumeric(String value){
        boolean result = true;
        
        try{
            double d = Double.parseDouble(value);
        }
        catch(NumberFormatException nfe){
            result = false;
        }
        if(!result){
            return true;
        }
        return result;
    }
        
    public static TaskModel retrieveTaskFromRequest(HttpServletRequest request){
        TaskModel task = (TaskModel)request.getAttribute("TaskModel");
        if (task == null)
            task = new TaskModel();
        return task;
    }
    
    public static void saveTaskToRequest(HttpServletRequest request, TaskModel task){
        request.setAttribute("TaskModel", task);
    }
    
    public static void saveTaskToDB(TaskModel task){
        ITaskRepo taskRepo = TaskFactory.makeTaskRepo();
        if (task.getId() < 1){
            taskRepo.createTask(task);
        }else{
            taskRepo.updateTask(task);
        }
    }
    
    public static ArrayList<TaskModel> retrieveTaskListFromRequest(HttpSession session){
        ArrayList<TaskModel> tasks = (ArrayList<TaskModel>)session.getAttribute("TaskList");
        if (tasks == null)
            tasks = new ArrayList<TaskModel>();
        
        return tasks;
    }
    
    public static ArrayList<Boolean> retrieveIsFullInfoShownFromRequest(HttpSession session){
        ArrayList<Boolean> infoShown = (ArrayList<Boolean>)session.getAttribute("FullInfoShownList");
        if (infoShown == null)
            infoShown = new ArrayList<Boolean>();
        
        return infoShown;
    }
    
    public static void saveTaskListToRequest(HttpSession session, ArrayList<TaskModel> tasks){
        session.setAttribute("TaskList", tasks);
    }
    
    public static void saveInfoShownListToRequest(HttpSession session, ArrayList<Boolean> infoShown){
        session.setAttribute("FullInfoShownList", infoShown);
    }
    
    public static ArrayList<TaskModel> selectTasksFromDB(){
        ITaskRepo taskRepo = TaskFactory.makeTaskRepo();
        
        return taskRepo.getTasks();
    }
    
    public static ArrayList<Boolean> buildShowFullInfoListOfSize(int n){
        ArrayList<Boolean> list = new ArrayList<>();
        for (int i = 0; i < n; i++)
            list.add(false);
        
        return list;
    }
    
     public static void deleteTaskFromDB(TaskModel task){
        ITaskRepo taskRepo = TaskFactory.makeTaskRepo();
        
        taskRepo.deleteTask(task);
    }
}
