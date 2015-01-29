package homework.pkg1;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class Application {

	public static void main(String args []) {
		new Application();
	}
	public Application() {
		parseCompany();

	}
	private void parseCompany(){



		//Relative path
		final String xmlPath = "./src/homework/pkg1/company.xml";
		final String xsdPath = "./src/homework/pkg1/company.xsd";
		
		  //Get a factory object for DocumentBuilder objects
	      DocumentBuilderFactory factory =
	            DocumentBuilderFactory.newInstance();

	      
	      // to make the parser a validating parse
	      factory.setValidating(true);
	      //To parse a XML document with a namespace,
	      factory.setNamespaceAware(true);
	      
	      // to ignore cosmetic whitespace between elements.
	      factory.setIgnoringElementContentWhitespace(true);
	      factory.setAttribute(
	    		    "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
	    		    "http://www.w3.org/2001/XMLSchema");
	      //specifies the XML schema document to be used for validation.
	      factory.setAttribute(
	    		    "http://java.sun.com/xml/jaxp/properties/schemaSource",
	    		    "company.xsd");
	    	
	      //Get a DocumentBuilder (parser) object
	      	      
	      DocumentBuilder builder=null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	      //Parse the XML input file to create a
	      // Document object that represents the
	      // input XML file.
	      ///
		Document document= null;
	      try {
			document = builder.parse(
			                          new File(xmlPath));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      processNode(document);
	     


	}
	 void processCompanies(Node root)
	 {
		 try
		 {			 
			 if(root.getNodeName().equals("companies"))
			 {				 
				 							
				 String student_name = getNodeValue(root.getChildNodes(), "");							 							 
				 String univ_name= getNodeValue(root.getChildNodes(), "university");
				 String degree = getNodeValue(root.getChildNodes(), "degree");							 							 
				 String year= getNodeValue(root.getChildNodes(), "year");
				 NodeList courseList = root.getChildNodes();
				 String[] courses = new String[courseList.getLength()];
				 
				 for(int i=0; i<courseList.getLength(); i++)
				 {
					 courses[i] = courseList.item(0).getNodeValue();
				 }
				 System.out.print(student_name + ", " + univ_name + " , " + degree + ", " + year + " ,");
			 }						 
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace(System.err);
	     }
	 }
	 
	 Node findNode(NodeList nodes, String nodeName)
	 {
		 for(int i=0; i<nodes.getLength(); i++)
		 {
			 if(nodes.item(i).getNodeName().equals(nodeName))
			 {
				 return nodes.item(i);
			 }
		 }
		 return null;
	 }
	 
	 String getNodeValue(NodeList nodes, String nodeName)
	 {
		 Node result = findNode(nodes, nodeName);
		 if(result != null)
		 {
			 return result.getFirstChild().getNodeValue();
		 }
		 return null;
	 }
	 
	 void processNode(Node root)
	 {

	    try{
	      if (root == null){
	        System.err.println("Node is null !!!");
	        return;
	      }

	      int type = root.getNodeType();
	      
	      switch (type){
	        case Node.TEXT_NODE:{
	            System.out.print("Text Node: <" +  root.getNodeValue() +  ">\n");
	          
	          break;
	        }//end case Node.TEXT_NODE

	        case Node.ATTRIBUTE_NODE:{
	        	System.out.print("Attribute Node: <" + root.getNodeName() + " = " +  root.getNodeValue() + " >\n");
	        	break;
	        }

	        case Node.ELEMENT_NODE:{
	        	NodeList children = root.getChildNodes();
	        	System.out.print("Element Node: <" + root.getNodeName() +"> has "+ children.getLength()+" child elements.\n");	        		          
	            for (Node child = root.getFirstChild();   child != null;   child = child.getNextSibling())
	            	processNode(child);
	          break;
	        }//end case ELEMENT_NODE

	        case Node.DOCUMENT_NODE:{
	        	NodeList children = root.getChildNodes();
	        	System.out.print("Document Node: <" + root.getNodeName() + "> has "+children.getLength()+" child elements.\n");	        		 
	            
	            for (Node child = root.getFirstChild();   child != null;   child = child.getNextSibling())
	            	processNode(child);
                        
	          break;
	        }//end case DOCUMENT_NODE

	        // there are even more cases to check
	        default:{

	        }//end default

	      }//end switch

	    }catch(Exception e){
	      e.printStackTrace(System.err);
	    }//end catch
  }

}
