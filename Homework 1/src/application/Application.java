package application;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import applicationJaxbRes.Application.Person;
import applicationJaxbRes.Application.Requirement.Companies;
import resultJaxRes.ApplicationType.Reference;
import resultJaxRes.Profile;
import companyJaxRes.Company;
import companyJaxRes.Office;
import employmentSaxRes.Person;
import java.util.logging.Level;
import java.util.logging.Logger;
import parsing.EmploymentOfficeParser;


public class Application {


	//Contain list of companies that will be created during parsing.
	private List<Company> companyCollection = new ArrayList<Company>();
	//private List<Person> employmentCollection = new ArrayList<Person>();

	//reference to root of jaxb 
	//reference to root of jaxb
	//reference to root of jaxb

	private final String xmlCompanyPath = "src/xml/company.xml";
	private final String xsdCompanyPath = "src/xml/company.xsd";
	private final String xmlApplicationPath ="src/xml/application.xml";
	private final String xmlResultPath = "src/out/jaxbResult.xml";
	
	//dom for xml file
	private Document doc;
	private String personId;
	private applicationJaxbRes.Application applicationRoot;
	private final Profile resultRoot = new Profile();
	Marshaller jaxbMarshaller;
	public static void main(String args []) {
		new Application();

	}

	public Application() {
		populateCompanyResource();
		populateEmploymentResource();
		initiateApplicationJAXB();
		//initiateDegreeJAXB()
		
		writeResultApplication();
		//writeResultDegree()
		//writeResultEmployment()
		//writeResultCompany()
		writeOutput();
	}

	private void writeOutput(){
		
		try{
			File file = new File(xmlResultPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(Profile.class);
			jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(resultRoot, file);
			
		} catch(JAXBException e){
			e.printStackTrace();
		}
	}

	private void initiateApplicationJAXB() {
		File file = new File(xmlApplicationPath);
		try{
			JAXBContext jaxbContext = 
					JAXBContext.newInstance("applicationJaxbRes");
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			//Source source = new StreamSource(inputStream);
			applicationRoot = (applicationJaxbRes.Application) jaxbUnmarshaller.unmarshal(file);
			

		} catch (JAXBException e){
			e.printStackTrace();
		}

	}

	private void writeResultApplication() {
		resultJaxRes.ApplicationType application = new resultJaxRes.ApplicationType();
		
		application.setCV(applicationRoot.getCV());
		application.setMotivationLetter(applicationRoot.getMotivationLetter());
		resultJaxRes.ApplicationType.Person p = new resultJaxRes.ApplicationType.Person();
		
		Person ap = applicationRoot.getPerson();
		p.setFirstName(ap.getFirstName());
		p.setId(ap.getId());
		p.setLastName(ap.getLastName());
		application.setPerson(p);
		
		resultJaxRes.ApplicationType.Requirement.Companies resultComp= 
				new resultJaxRes.ApplicationType.Requirement.Companies();
		
		applicationJaxbRes.Application.Requirement.Companies companies =
				applicationRoot.getRequirement().getCompanies();
		//Get companies
		for(applicationJaxbRes.Application.Requirement.Companies.Company c : companies.getCompany()){
			resultJaxRes.ApplicationType.Requirement.Companies.Company resultCompany = 
			new resultJaxRes.ApplicationType.Requirement.Companies.Company();
			resultCompany.setName(c.getName());
			resultComp.getCompany().add(resultCompany);
		}
		
		application.setRequirement(new resultJaxRes.ApplicationType.Requirement());
		application.getRequirement().setCompanies(resultComp);

		resultJaxRes.ApplicationType.Requirement resultReq = application.getRequirement();
		applicationJaxbRes.Application.Requirement req = applicationRoot.getRequirement();
		
		//get field
		for(applicationJaxbRes.Application.Requirement.Field f: req.getField()){
			resultJaxRes.ApplicationType.Requirement.Field resultField = 
					new resultJaxRes.ApplicationType.Requirement.Field();
			resultField.setName(f.getName());
			resultReq.getField().add(resultField);
		}
		//Get contract
		for(applicationJaxbRes.Application.Requirement.Contract c : req.getContract()){
			resultJaxRes.ApplicationType.Requirement.Contract resultContract = 
					new resultJaxRes.ApplicationType.Requirement.Contract();
			resultContract.setContractType(c.getContractType());
			resultReq.getContract().add(resultContract);
		}
		//get references
		
		for(applicationJaxbRes.Application.Reference r : applicationRoot.getReference()){
			resultJaxRes.ApplicationType.Reference resultRef = new 
					resultJaxRes.ApplicationType.Reference();
			resultRef.setFirstName(r.getFirstName());
			resultRef.setLastName(r.getLastName());
			resultRef.setPhone(r.getPhone());
			application.getReference().add(resultRef);
		}
		
		
		resultRoot.setApplication(application);
	}

	private void populateCompanyResource(){
		try{
			SchemaFactory schemaFactory =
					SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File(xsdCompanyPath));
			Validator valdiator = schema.newValidator();

			Source xmlSource = new StreamSource(new File(xmlCompanyPath));
			valdiator.validate(xmlSource);

			//initiate DocumentFactory for further use...
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

			//Create a documentBuilder from documentFactory
			//DocumentBuilder provides the api to get a dom from xml file
			DocumentBuilder docBuilder;
			docBuilder = documentFactory.newDocumentBuilder();

			//Document represents the entire xml (or html) document. Root of document tree
			//and provides access to its nodes
			doc = docBuilder.parse(xmlCompanyPath);

		} catch(ParserConfigurationException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		} catch(SAXException e) {
			e.printStackTrace();
		}

		String companyName;
		String city;
		String streetAddress;
		String streetNumber;
		String workerCount;

		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			XPathExpression expr;
			String expression = "//Company/@name";
			expr = xPath.compile(expression);
			NodeList n1= (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for(int i = 0; i< n1.getLength(); i++){
				companyName = n1.item(i).getNodeValue();
				expression = "//Company[@name = '" + companyName + "']/City/@name";
				city = xPath.compile(expression).evaluate(doc);

				expression = "//Company[@name = '" + companyName + "']/City/Office";
				NodeList n2 = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

				ArrayList <Office> officeList = new ArrayList<Office>();
				for(int j = 0; j<n2.getLength(); j++) {
					streetAddress = 
							n2.item(j).getAttributes().getNamedItem("streetAddress").getNodeValue();
					streetNumber = 
							n2.item(j).getAttributes().getNamedItem("streetNumber").getNodeValue();
					workerCount = 
							n2.item(j).getAttributes().getNamedItem("workers").getNodeValue();
					officeList.add(new Office(streetAddress, streetNumber, workerCount));
				}
				companyCollection.add(new Company(companyName, officeList));
			}


		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

    private void populateEmploymentResource() {
        
        File empOfficeXml = new File("src\\xml\\employmentOffice.xml");
        
        try {
            
            
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setValidating(true);
            saxParserFactory.setNamespaceAware(true);
          
            
              
            SAXParser saxParser = saxParserFactory.newSAXParser();
         
            saxParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", 
            "http://www.w3.org/2001/XMLSchema");
            
            EmploymentOfficeHandler handler = new EmploymentOfficeHandler();
            saxParser.parse(empOfficeXml, handler);
            
            ArrayList<Person> personList = handler.getPersonList();
   
            
        } catch (ParserConfigurationException | SAXException | IOException ex) {
         
            Logger.getLogger(EmploymentOfficeParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


} 

