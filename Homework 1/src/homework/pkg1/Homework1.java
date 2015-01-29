/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework.pkg1;

import com.sun.xml.internal.bind.api.JAXBRIContext;
import com.w3schools.Degree;
import com.w3schools.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


/**
 *
 * @author norde_000
 */
public class Homework1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JAXBException {
        // TODO code application logic here
    
        // Creates JAXContext instance
        JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
       //Use the JAXBContext intsance to create unmarshaller
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        //unmarshall Degree.xml to get an instance of the JAXBElement
        JAXBElement<Degree> unmarshalledObject = (JAXBElement<Degree>)unmarshaller.unmarshal(
        ClassLoader.getSystemResourceAsStream("DegreeMall.xml"));
        //get instance of required JAXB Root Class from JAXBElement
        Degree degreeobj = unmarshalledObject.getValue();
        System.out.print("Testing university" + degreeobj.getUniversity());
    
    
    
    
    
    }   
        
}
