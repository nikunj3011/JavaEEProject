/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.util.ArrayList;
import models.JobModel;

/**
 *
 * @author Anastasiia Egorova
 */
public interface IJobRepo {
      
      public JobModel getJob(int id);
      
      public ArrayList<JobModel> getJobs();
      
      public ArrayList<JobModel> getJobsBetween(String date1, String date2);
      
      public void createJob(JobModel j);

      public void updateJob(JobModel job);

      public void deleteJob(JobModel job);
}
