package iaws.covoiturage.ws.contractfirst;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import iaws.covoiturage.domain.Personnel;

import org.jdom2.Element;
import org.junit.Test;

public class TestXmlHelper {
	private Personnel p1 = new Personnel();
	private ArrayList<Personnel> liste = new ArrayList<Personnel>();

	@Test
    public void testGetResultsInXml() {
		p1.setNom("nom");
		p1.setPrenom("prenom");
		p1.setMail("mail");
		p1.setAdresse("adresse");
		liste.add(p1);
        try {
            Element  element = XmlHelper.getResultsInXml(liste);
            assert element != null;
        } catch(Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}

