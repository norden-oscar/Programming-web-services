package parsing;

import java.io.File;
import java.util.ArrayList;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class ApplicationXSLT {
	
	private final String xmlPath = "src/parsing/test3.xml";
	private final String xslPath ="src/parsing/userProfile.xsl";
	private final String outPath ="src/parsing/out.xsl";
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
