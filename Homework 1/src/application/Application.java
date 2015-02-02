package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
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

import companyJaxRes.Company;
import companyJaxRes.Office;


public class Application {


	//Contain list of companies that will be created during parsing.
	private List<Company> companyCollection = new ArrayList<Company>();
	//private List<Person> employmentCollection = new ArrayList<Person>();

	//reference to root of jaxb 
	//reference to root of jaxb
	//reference to root of jaxb
	
	private final String xmlCompanyPath = "src/homework/pkg1/company.xml";
	private final String xsdCompanyPath = "src/homework/pkg1/company.xsd";
	//dom for xml file
	private Document doc;

	public static void main(String args []) {
		new Application();

	}
	
	public Application() {
		populateCompanyResource();
		//populateEmploymentResource();
		//initiateApplicationJAXB()
		//initiateDegreeJAXB()
		//initiateResultJAXB()
		//writeResultApplication()
		//writeResultDegree()
		//writeResultEmployment()
		//writeResultCompany()
		
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


} 

