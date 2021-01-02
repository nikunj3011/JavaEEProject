/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Alena Selezneva
 */
public class EmployeeModel {
    private int id;  
    private String firstName;
    private String lastName;
    private String sin;
    private double hourlyPayRate;
    private LocalDate creationDate;
    private LocalDate lastUpdateDate;
    
    private ArrayList<String> teamNames;
    private ArrayList<String> skillNames;
    
    private ArrayList<String> errors;
    private boolean isValid_;

    public EmployeeModel(){
        creationDate = LocalDate.now();
        isValid_ = false;
        
        initializeArrays();
    }
    
    public EmployeeModel(int id, String firstName, String lastName
        , String sin, double hourlyPayRate, LocalDate creationDate, LocalDate lastUpdateDate)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sin = sin;
        this.hourlyPayRate = hourlyPayRate;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        
        initializeArrays();
    }
    
    public EmployeeModel(int id, String firstName, String lastName, String sin,
            double hourlyPayRate, LocalDate creationDate, LocalDate lastUpdateDate, boolean isValid_, ArrayList<String> errors)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sin = sin;
        this.hourlyPayRate = hourlyPayRate;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.isValid_ = isValid_;
        
        initializeArrays();
        this.errors = errors;
    }
    
    private void initializeArrays(){
        errors = new ArrayList<String>();
        skillNames = new ArrayList<>();
        teamNames = new ArrayList<>();
    }
    
    public boolean isValid(){
        return isValid_;
    }
    
    public ArrayList<String> getErrors(){
        return errors;
    }

    public ArrayList<String> getTeamNames() {
        return teamNames;
    }

    public ArrayList<String> getSkillNames() {
        return skillNames;
    }
    
    public void setTeamNames(ArrayList<String> teamNames) {
        this.teamNames = teamNames;
    }

    public void setSkillNames(ArrayList<String> skillNames) {
        this.skillNames = skillNames;
    }
     
    public void setErrors(ArrayList<String> err){
        this.errors = err;
    }
    
    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
    
    public String getFirstName() {
        return firstName != null ? firstName : "";
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }
    
    public void setIsValid_(boolean isValid_) {
        this.isValid_ = isValid_;
    }
    
    public String getLastName() {
        return lastName != null ? lastName : "";
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getSin() {
        return sin != null ? sin : "";
    }

    public void setSin(String sin) {
        this.sin = sin;
    }

    public double getHourlyPayRate() {
        return hourlyPayRate;
    }

    public void setHourlyPayRate(double hourlyPayRate) {
        this.hourlyPayRate = hourlyPayRate;
    }

    public LocalDate getCreationDate() {
        return creationDate != null ? creationDate : LocalDate.now();
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate != null ? lastUpdateDate : getCreationDate();
    }

    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
    public boolean isPartOfTeam(){
        return teamNames != null && teamNames.size() > 0;
    }
    
    public boolean hasSkills(){
        return skillNames != null && skillNames.size() > 0;
    }
    
    public String getTeam(int i){
        if ( i < 0 || i >= teamNames.size())
            return "";
        return teamNames.get(i);
    }
    
    public String getSkill(int i ){
        if ( i < 0 || i >= skillNames.size())
            return "";
        return skillNames.get(i);
    }
}
