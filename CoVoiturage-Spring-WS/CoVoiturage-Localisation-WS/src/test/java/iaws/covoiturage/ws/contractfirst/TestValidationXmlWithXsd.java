package iaws.covoiturage.ws.contractfirst;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TestValidationXmlWithXsd {

	@Test
	public void testValidate() throws SAXException, IOException {
		SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema") ;
		InputSource sourceentree = new InputSource(new FileInputStream(new File("src/main/resources/CoVoiturageLoc.xsd")));
		SAXSource sourceXSD = new SAXSource(sourceentree);
		Schema schema = factory.newSchema(sourceXSD);
		Validator validator = schema.newValidator() ;
		validator.validate(new StreamSource(new File("src/main/resources/CoVoiturageLoc.xml"))) ;
	}

}
