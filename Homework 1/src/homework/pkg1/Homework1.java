/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework.pkg1;

import com.w3schools.Degree;
import com.w3schools.Degree.Program;
import com.w3schools.Degree.University;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;

/**
 * import com.w3schools.Degree; import com.w3schools.ObjectFactory import
 * javax.xml.bind.JAXBContext; import javax.xml.bind.JAXBElement; import
 * javax.xml.bind.JAXBException; import javax.xml.bind.Unmarshaller;
*
 */
/**
 *
 * @author norde_000
 */
public class Homework1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /**
         * // Creates JAXContext instance JAXBContext jaxbContext =
         * JAXBContext.newInstance(ObjectFactory.class); //Use the JAXBContext
         * intsance to create unmarshaller Unmarshaller unmarshaller =
         * jaxbContext.createUnmarshaller(); //unmarshall Degree.xml to get an
         * instance of the JAXBElement JAXBElement<Degree> unmarshalledObject =
         * (JAXBElement<Degree>)unmarshaller.unmarshal(
         * ClassLoader.getSystemResourceAsStream("DegreeMall.xml")); //get
         * instance of required JAXB Root Class from JAXBElement Degree
         * degreeobj = unmarshalledObject.getValue(); System.out.print("Testing
         * university" + degreeobj.getUniversity());
         *
         *
         */
        Homework1 test = new Homework1();
        try {
            test.readDegree();
        } catch (JAXBException | XMLStreamException ex) {
            Logger.getLogger(Homework1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public ArrayList<JAXBElement> readDegree()    throws JAXBException, XMLStreamException{
        //Opens the file to read from
        File inFile = new File("src\\homework\\pkg1\\DegreeMall.xml");
        // makes an instance of the whole XML documentclass
        JAXBContext jc = JAXBContext.newInstance(Degree.class);

        //create unmarshaller and unmarshall DegreeMall
        Unmarshaller unMarshall = jc.createUnmarshaller();
        Degree degree = (Degree) unMarshall.unmarshal(inFile);
        
        // get instances of program and degree so we can make new elements of these
        Program program = degree.getProgram();
        University university = degree.getUniversity();
        // making the new elements for program and university, this is what we will return.
        QName qNameProgram = new QName("com.w3schools.Degree", "program");
        JAXBElement<Program> programElement = new JAXBElement<>(qNameProgram, Program.class, program);
        
        QName qNameUniversity = new QName("com.w3schools.Degree", "university");
        JAXBElement<University> universityElement = new JAXBElement<>(qNameUniversity, University.class, university);
        ArrayList<JAXBElement> returnList = new ArrayList<JAXBElement>();
        returnList.add(universityElement);
        returnList.add(programElement);        
                
        return returnList;
        /**
        // opens/creates the output file and creates the marshaller
        File outFile = new File("src\\homework\\pkg1\\DegreeOutput.xml");
        Marshaller marshall = jc.createMarshaller();
        marshall.setProperty(marshall.JAXB_FORMATTED_OUTPUT, true);

           // Fixing "RootElementAnnotation missing" aka make new Elements containing program and university
            // because that is what we need from this file
        

        marshall.marshal(universityElement, outFile);
        marshall.marshal(programElement, outFile);
        **/
    }

}
