/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employmentSaxRes;

import java.util.ArrayList;

/**
 *
 * @author norde_000
 */
public class Person  {
    private String firstName;
    private String lastName;
    private String id;
    private ArrayList<Work> workList = new ArrayList<Work>();
    public Person(String firstName,String lastName,String id){
        this.firstName=firstName;
        this.lastName=lastName;
        this.id = id;
      
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    
    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    
    /**
     * @return the workList
     */
    public ArrayList<Work> getWorkList() {
        return workList;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param workList the workList to set
     */
    public void setWorkList(ArrayList<Work> workList) {
        this.workList = workList;
    }

   
}
