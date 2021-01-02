/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaclasses;

import Interfaces.IEmployeeRepo;
import Interfaces.IJobRepo;
import Interfaces.ITaskRepo;
import Interfaces.ITeamRepo;
import factories.EmployeeFactory;
import factories.JobFactory;
import factories.TaskFactory;
import factories.TeamFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.EmployeeModel;
import models.JobModel;
import models.TaskModel;
import models.TeamModel;

/**
 *
 * @author Anastasiia Egorova
 */
public class JobUtility {

      public static void validateJob(JobModel job) {
            
               ArrayList<String> errors = new ArrayList<>();

               if (job.getName() == null || job.getName().equals(""))
                   errors.add("Name is required");
               
               if (job.getDescription() == null || job.getDescription().equals(""))
                   errors.add("Description is required");

               if (job.getStartTime() == null)
                   errors.add("Start Date/Time is required");

               if (job.getTasks().isEmpty())
                     errors.add("Please choose at least one task");
               
               if (!JobUtility.doesTeamCoverAllTasks(job.getTasks(), job.getTeam()))
                     errors.add("Team's skills don't cover all tasks. Please choose another team");
               
               job.setErrors(errors);
               job.setIsValid(errors.size() == 0);
      }

      public static void saveJobToRequest(HttpServletRequest request, JobModel job) {
            request.setAttribute("JobModel", job);
      }
      
      public static JobModel retrieveJobFromRequest(HttpServletRequest request){
            JobModel job = (JobModel)request.getAttribute("JobModel");
            if (job == null)
                job = new JobModel();
            return job;
    }

      public static void saveJobToDB(JobModel job) {
                  IJobRepo jobRepo = JobFactory.makeJobRepo();

                  if (job.getId() < 1)
                        jobRepo.createJob(job);
                  else
                      jobRepo.updateJob(job);
      }

      public static boolean doesTeamCoverAllTasks(ArrayList<Integer> tasks, int team) {
            
            ITeamRepo teams = TeamFactory.makeTeamRepo();
            ArrayList<Integer> tasksTeamCanDo = teams.getSkillIDs(team);
            
            for (Integer task : tasks) {
                  if (!tasksTeamCanDo.contains(task))
                        return false;
            }
            
            return true;
      }

      public static int calculateJobDuration(ArrayList<Integer> tasks, int team) {
            IEmployeeRepo employeeRepo = EmployeeFactory.makeEmployeeRepo();
            
            // to get real array of tasks, not taskIDs
            ITaskRepo tasksRepo = TaskFactory.makeTaskRepo();
            ArrayList<TaskModel> tasksArray = new ArrayList<TaskModel>();
            for (Integer i : tasks)
                  tasksArray.add(tasksRepo.getTask(i));
            
            //sort tasks by duration
            Collections.sort(tasksArray, new Comparator<TaskModel>() {
                  public int compare(TaskModel t1, TaskModel t2) {
                        return Integer.parseInt(t2.getDuration()) - Integer.parseInt(t1.getDuration());
                  }
            });
            
            //temp variable to switch between team members
            ITeamRepo teamRepo = TeamFactory.makeTeamRepo();
            TeamModel currentTeam = teamRepo.getTeamWithEmployees(team);
            ArrayList<Integer> duration = new ArrayList<Integer>(Arrays.asList(0, 0));
            int currentMember = 0;
            
            //assign tasks for members switching between them
            for (TaskModel t : tasksArray) {
                  int tempMember = currentTeam.getTeamMembers().get(currentMember);
                  if (employeeRepo.getSkillNamesOfEmployee(tempMember).contains(t.getTaskName())) {
                        duration.set(currentMember, duration.get(currentMember) + Integer.parseInt(t.getDuration()));
                        currentMember = 1 - currentMember;
                  }
                  else {
                        currentMember = 1 - currentMember;
                        duration.set(currentMember, duration.get(currentMember) + Integer.parseInt(t.getDuration()));
                        currentMember = 1 - currentMember;
                  }
            }
            
            if (duration.get(0) > duration.get(1))
                  return duration.get(0);
            else
                  return duration.get(1);
      }
      
      public static double calculateJobCost(int team, int duration,  boolean isOnSite) {
            
            ITeamRepo teamRepo = TeamFactory.makeTeamRepo();
            IEmployeeRepo employeeRepo = EmployeeFactory.makeEmployeeRepo();
            
            ArrayList<Integer> teamMemberIDs = teamRepo.getTeamWithEmployees(team).getTeamMembers();
            
            int employeeID = teamMemberIDs.get(0);
            EmployeeModel employee = employeeRepo.getEmployee(employeeID);
            
            double salaryFirstEmployee = employee.getHourlyPayRate();
            double salarySecondEmployee =  employeeRepo.getEmployee(teamMemberIDs.get(1)).getHourlyPayRate();
            
            double totalCost = salaryFirstEmployee * duration / 60;
            totalCost += salarySecondEmployee * duration / 60;
            
            // an hour extra to get to/from place
            if (isOnSite)
                  totalCost += salaryFirstEmployee + salarySecondEmployee;
            
            return totalCost;
      }

      public static ArrayList<JobModel> selectJobsFromDB() {
           
            IJobRepo jobRepo = JobFactory.makeJobRepo();
            return jobRepo.getJobs();
      }

      public static ArrayList<Boolean> buildShowFullInfoListOfSize(int size) {
            
            ArrayList<Boolean> list = new ArrayList<>();
            
            for (int i = 0; i < size; i++)
                list.add(false);

            return list;
      }

      public static void saveJobListToRequest(HttpSession session, ArrayList<JobModel> jobs) {
            session.setAttribute("JobList", jobs);
      }

      public static void saveInfoShownListToRequest(HttpSession session, ArrayList<Boolean> infoShown) {
            session.setAttribute("FullInfoShownList", infoShown);
      }
      
      public static ArrayList<JobModel> retrieveJobListFromRequest(HttpSession session) {
              
            ArrayList<JobModel> jobs = (ArrayList<JobModel>)session.getAttribute("JobList");

            if (jobs == null)
                jobs = new ArrayList<JobModel>();

            return jobs;
      }
      
      public static ArrayList<Boolean> retrieveIsFullInfoShownFromRequest(HttpSession session) {
           
            ArrayList<Boolean> infoShown = (ArrayList<Boolean>)session.getAttribute("FullInfoShownList");

            if (infoShown == null)
                infoShown = new ArrayList<Boolean>();

            return infoShown;
      }

      public static void deleteJobFromDB(JobModel job) {
            IJobRepo jobRepo = JobFactory.makeJobRepo();
            jobRepo.deleteJob(job);
      }
}
