package no.jpp.po.javamapping.template0;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/*import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;*/

import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;


public class TemplateMapping extends AbstractTransformation {



	// DB2
	//	private final String DBCONNECT = "jdbc:db2://159.216.221.38:5912/POT:currentSchema=SAPPOTDB;deferPrepares=0;";
	//	private final String DBUSER = "db2pst";
	//	private final String DBPASS = "dfopst2013"; // "dfopst2013";

	// MaxDB
	//	private final static String DBCONNECT = "jdbc:sapdb://dfo-po3.local.com/PO3";
	//	private final static String DBUSER = "SAPPO3DB";
	//	private final static String DBPASS = "Ides1234"; 

//	private String SID = null; // sap system id
//	private TransformationOutput output = null;

	@Override
	public void transform(TransformationInput input, TransformationOutput output) throws StreamTransformationException {

//		this.output = output;
//		SID = (String) System.getProperty("SAPSYSTEMNAME");
		execute(input.getInputPayload().getInputStream(), output.getOutputPayload().getOutputStream() );
	}

	private void execute(InputStream input, OutputStream output) throws StreamTransformationException {

		try 
		{
			//final Document sbDoc = convertInputStreamToDocument(in); 
			output.write( convertInputStreamToBytes(input) );
			
		} catch (Exception e) {
			throw new StreamTransformationException("Unable to Parse Input XML message: " + e.getMessage());
		} 
	}


/*	private Document convertInputStreamToDocument(InputStream is) throws StreamTransformationException {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		dbf.setIgnoringComments(false);
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setNamespaceAware(true);
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
			return db.parse(is);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new StreamTransformationException("Convert InputStream to XML Document\n" + e.getMessage());
		}
		finally{
			try { 
				if (is != null) is.close(); } 
			catch(IOException e) {
			}
		}
	}*/


	private byte[] convertInputStreamToBytes(InputStream inputStream) {

		StringBuilder stringBuilder = new StringBuilder();
		try (Reader reader = new BufferedReader(new InputStreamReader
				(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
			int c = 0;
			while ((c = reader.read()) != -1) {
				stringBuilder.append((char) c);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
	}



} // end class