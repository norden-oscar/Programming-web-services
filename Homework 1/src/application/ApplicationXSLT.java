package application;

import java.io.File;
import java.util.ArrayList;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class ApplicationXSLT {
	
	private final String xmlPath = "src/xml/application.xml";
	private final String xslPath ="src/xml/userProfile.xsl";
	private final String outPath ="src/out/result.xml";
	public static void main(String args []) {
		new ApplicationXSLT();
	}
	public ApplicationXSLT() {
		initiate();
	}
	private void initiate(){
		try{
			
			TransformerFactory factory = TransformerFactory.newInstance();
			Source xslt = new StreamSource(new File(xslPath));
			Transformer transformer = factory.newTransformer(xslt);

			Source text = new StreamSource(new File(xmlPath));
			transformer.transform(text, new StreamResult(new File(outPath)));
			
	
		} catch(TransformerException e){
			e.printStackTrace();
		}
	}
}
