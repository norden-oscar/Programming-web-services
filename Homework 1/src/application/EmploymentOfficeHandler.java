/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import employmentSaxRes.Person;
import employmentSaxRes.Work;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author norde_000
 */
public class EmploymentOfficeHandler extends DefaultHandler {
    //Lists to hold Person and objects
    private ArrayList<Person> personList = new ArrayList<Person>();
    private Person person = null;
    private Work work=null;
    private ArrayList<Work> workList = new ArrayList<Work>();
    
   
    /**
     * @return the personList
     */
    public ArrayList<Person> getPersonList() {
        return personList;
    }

    
    
    @Override
    public void startElement(String uri,String localName,String qName, Attributes attributes){
        if(qName.equalsIgnoreCase("Person")){
            //save attributes for person object
            String firstName = attributes.getValue("firstName");
            String lastName = attributes.getValue("lastName");
            String id = attributes.getValue("id");
            //create person with attributes, but dont put him in list until endelement
            person = new Person(firstName,lastName,id);
            
            
            
        }
        else if (qName.equalsIgnoreCase("Work")){
            //save Work attributes
            String start = attributes.getValue("start");
            String end = attributes.getValue("end");
            String company = attributes.getValue("company");
            //create work, but dont put in list yet 
            work = new Work(start,end,company);
        }
        
    }
    @Override
    public void endElement(String uri,String localName,String qName){
        // antingen är det en person eller work, om person lägg till den i person listan
        // annars är det work, lägg då till denna i den senast tillagda person.workList
        if(qName.equalsIgnoreCase("Person")){
            for(int i=0;i<=workList.size()-1;i++){
            person.getWorkList().add(workList.get(i));
            }
            personList.add(person);
            workList.clear();
        }
          else if(qName.equalsIgnoreCase("Work")){
            workList.add(work);  
            
        }
        

                
            
        }
    @Override
     public void warning(SAXParseException e) throws SAXException {
    System.out.println(e.getMessage());
    throw (e);
  }

    @Override
  public void error(SAXParseException e) throws SAXException {
    System.out.println(e.getMessage());
    throw (e);
  }

    @Override
  public void fatalError(SAXParseException e) throws SAXException {
    
      System.out.println(e.getMessage());
    throw (e);
  }

  
}
    
   /** @Override
    public void characters(char ch[],int start,int lenght){
       
    }
      **/      

