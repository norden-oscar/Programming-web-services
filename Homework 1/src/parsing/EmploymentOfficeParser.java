/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author norde_000
 */
public class EmploymentOfficeParser {
    public static void main(String[] args){
       
        
        try {
             SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setNamespaceAware(true);
            
          
            SAXParser saxParser = saxParserFactory.newSAXParser();
            
            
            EmploymentOfficeHandler handler = new EmploymentOfficeHandler();
            saxParser.parse("src\\parsing\\employmentOffice.xml", handler);
            ArrayList<Person> personList = handler.getPersonList();
            
                System.out.println(handler.getPersonList().get(0).getFirstName());
                System.out.println(handler.getPersonList().get(0).getWorkList().get(0).getCompany());
                System.out.println(handler.getPersonList().get(0).getWorkList().get(1).getCompany());
                System.out.println(handler.getPersonList().get(1).getLastName());
                System.out.println(handler.getPersonList().get(1).getWorkList().get(0).getCompany());
                System.out.println(handler.getPersonList().get(1).getWorkList().get(1).getCompany());
                
            
            
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(EmploymentOfficeParser.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
        
    }
}
