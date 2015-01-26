package mypack;

import java.io.*;


import javax.xml.transform.*;
import javax.xml.transform.stream.*;

public class SampleXSLTTransformer {

	
	public static void main(String[] args) {
		try {
			TransformerFactory  tFactory =  TransformerFactory.newInstance();
   	
			Source xslSource = new StreamSource( "src/myXSD/MyCV.xsl" );
			Transformer transformer = tFactory.newTransformer( xslSource );
			transformer.transform( new StreamSource( "Transcript.xml" ),new StreamResult( new FileOutputStream( "test_output.xml" )));

		}catch(Exception ex) {
			 ex.printStackTrace();
		}

	}

}
