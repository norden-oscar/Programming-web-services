package parsing;

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

import oracle.jrockit.jfr.tools.ConCatRepository;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class Application {
	//private Skola s;
	//private Person lastPerson;
	
	//Contain list of companies that will be created during parsing.
	private List<Company> companyCollection = new ArrayList<Company>();
	private final String xmlFile = "src/homework/pkg1/company.xml";
	private final String xsdFile = "src/homework/pkg1/company.xsd";
	//dom for xml file
	private Document doc;
	
	public static void main(String args []) {
		new Application();




	}

	public Application() {
		//DOM company.xml
		initiateDOM(xmlFile);
		parseDOM();
		//SAX
		initiateSAX(xmlFile);
		//
	}
	private void initiateDOM(String path){
		

		try{
			SchemaFactory schemaFactory =
					SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File(xsdFile));
			Validator valdiator = schema.newValidator();
			
			Source xmlSource = new StreamSource(new File(xmlFile));
			valdiator.validate(xmlSource);
			
			//initiate DocumentFactory for further use...
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

			//Create a documentBuilder from documentFactory
			//DocumentBuilder provides the api to get a dom from xml file
			DocumentBuilder docBuilder;
			docBuilder = documentFactory.newDocumentBuilder();

			//Document represents the entire xml (or html) document. Root of document tree
			//and provides access to its nodes
			doc = docBuilder.parse(path);
			
		} catch(ParserConfigurationException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		} catch(SAXException e) {
			e.printStackTrace();
		}
	}
	
	private void initiateSAX(String path){
		//Initiate SAXParserFactory for further use...
		//jaxp == java api for processing xml
		//Vi har två alternativ verkar det som... Köra på XMLReaderFactory eller SAXParserFactory?
	
		try{
			
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		SAXParser saxParser = factory.newSAXParser();
		
		
		DefaultHandler handler = new DefaultHandler() {
			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes attributes) throws SAXException {
					//if not root elem
					
					if(qName.equalsIgnoreCase("Person")){
						//lastPerson = new Person();
						//s.addPerson(lastPerson);
					}
					if(qName.equalsIgnoreCase("Betyg")){
						
					}
					
			}
			
			@Override
			public void startDocument() throws SAXException {
			//	s = new Skola();
				//super.startDocument();
			}
		};
		
		
		saxParser.parse(path, handler);
		} catch(SAXException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
		
			e.printStackTrace();
			
		}
		
	}
	
	public void parseDOM(){

		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			XPathExpression expr;
			String expression = "//Company/@name";
			expr = xPath.compile(expression);
			NodeList n1= (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for(int i = 0; i< n1.getLength(); i++){
				String companyName = n1.item(i).getNodeValue();
				expression = "//Company[@name = '" + companyName + "']/City/@name";
				String city = xPath.compile(expression).evaluate(doc);
				System.out.println(city);
				expression = "//Company[@name = '" + companyName + "']/City/Office";
				NodeList n2 = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
				
				for(int j = 0; j<n2.getLength(); j++) {
					String streetAddress = 
							n2.item(j).getAttributes().getNamedItem("streetAddress").getNodeValue();
					String streetNumber = 
							n2.item(j).getAttributes().getNamedItem("streetNumber").getNodeValue();
					String workerCount = 
							n2.item(j).getAttributes().getNamedItem("workers").getNodeValue();
					System.out.println(streetAddress);
					System.out.println(streetNumber);
					System.out.println(workerCount);
					
				}
			}
			
			
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} 
}
