package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @date    November 5, 2020
 * @description Client class (Model)
 * @author Anastasiia Egorova
 */
public class ClientModel  {
      
      private int id;
      private String name;
      private String description;
      private LocalDate creationDate;
      private boolean isDeleted;
      
      private boolean isValid_;
      private ArrayList<String> errors;
      
      // default C-TOR
      public ClientModel() {
            
            this.creationDate = LocalDate.now();
            this.isDeleted = false;
            this.isValid_ = false;
            this.errors = new ArrayList<String>();
      }
      
      // C-TOR without id (to create new client in DB, which already has AUTO_INCREMENT for id)
      public ClientModel(String name, String description) {
            this.id = 0;            // not using later on
            this.name = name;
            this.description = description;
            this.creationDate = LocalDate.now();
            this.isDeleted = false;
            
            errors = new ArrayList<String>();
      }
      
       // complete C-TOR (with creation date)
      public ClientModel(int id, String name, String description, String date) {
            this.id = id;
            this.name = name;
            this.description = description;
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.creationDate = LocalDate.parse(date, dtf);
            this.isDeleted = false;
            
            errors = new ArrayList<String>();
      }
      
      // complete C-TOR (without creation date)
      public ClientModel(int id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.creationDate = LocalDate.now();
            this.isDeleted = false;
            
            errors = new ArrayList<String>();
      }
      
      public int getId() {
            return this.id;
      }
      
      public void setId(int id) {
            this.id = id;
      }
      
      public String getName() {
            return this.name != null ? name : "";
      }
      
      public void setName(String name) {
            this.name = name;
      }
      
      public String getDescription() {
            return this.description != null ? description : "";
      }
      
      public void setDescription(String description) {
            this.description = description;
      }
      
      public LocalDate getCreationDate() {
            return this.creationDate;
      }
      
      public void setCreationDate(LocalDate date) {
            this.creationDate = date;
      }
      
      public boolean getIsDeleted() {
            return this.isDeleted;
      }
      
      public void setIsDeleted(boolean d) {
            this.isDeleted = d;
      } 
      
      public boolean getIsValid() {
            return this.isValid_;
      }
      
      public void setIsValid(boolean isValid) {
            this.isValid_ = isValid;
      }
      
      public ArrayList<String> getErrors(){
            return errors;
      }
    
      public void setErrors(ArrayList<String> err){
            this.errors = err;
      }
}
