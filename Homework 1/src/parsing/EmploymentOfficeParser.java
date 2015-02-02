/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

import application.EmploymentOfficeHandler;
import employmentSaxRes.Person;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
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
       
        File empOfficeXml = new File("src\\xml\\employmentOffice.xml");
        File empOfficeXsl = new File("src\\xml\\employmentOfficeSchema.xsd");
        Schema schema = null;
        try {
            
            // Måste fixa så parsern är validating
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setValidating(true);
            saxParserFactory.setNamespaceAware(true);
          
            //String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            //SchemaFactory factory = SchemaFactory.newInstance(language);
            //schema = factory.newSchema(empOfficeXsl);
            //saxParserFactory.setSchema(schema);
              
            SAXParser saxParser = saxParserFactory.newSAXParser();
         
            saxParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", 
            "http://www.w3.org/2001/XMLSchema");
            
            EmploymentOfficeHandler handler = new EmploymentOfficeHandler();
            saxParser.parse(empOfficeXml, handler);
            
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
