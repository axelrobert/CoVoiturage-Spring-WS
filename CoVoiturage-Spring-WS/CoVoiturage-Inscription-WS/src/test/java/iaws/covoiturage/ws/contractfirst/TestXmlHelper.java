package iaws.covoiturage.ws.contractfirst;

import static org.junit.Assert.fail;

import org.jdom2.Element;
import org.junit.Test;

import iaws.covoiturage.domain.Coordonnee;
import iaws.covoiturage.ws.contractfirst.XmlHelper;

/**
 * @author Axel Robert, Valentin Boutonné
 */
public class TestXmlHelper {
	private static final String nom = "nom";
	private static final String prenom = "prenom";
	private static final String mail = "mail";
	private static final String adresse = "adresse";
	private Coordonnee coord = new Coordonnee(); 

	@Test
    public void testGetResultsInXml() {
    	coord.setLatitude(3.45f);
    	coord.setLongitude(87.5f);
        try {
            Element  element = XmlHelper.getResultsInXml(coord, nom, prenom, mail, adresse);
            assert element != null;
        } catch(Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}

