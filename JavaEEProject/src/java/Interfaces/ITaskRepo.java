/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import models.TaskModel;
import java.util.ArrayList;
/**
 *
 * @author Nikunj
 */
public interface ITaskRepo {
    public ArrayList<TaskModel> getTasks();
    
    public TaskModel getTask(int id);
    
    public void createTask(TaskModel employee); 
    
    public void updateTask(TaskModel task); 
    
    public void deleteTask(TaskModel task);
}
