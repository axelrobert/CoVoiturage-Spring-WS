package iaws.covoiturage.ws.contractfirst;

import junit.framework.TestCase;
import iaws.covoiturage.ws.contractfirst.XmlHelper;

import org.w3c.dom.Element;

public class TestXmlHelper extends TestCase {
	private static final String filePath = "CoVoiturage.xml";

    public void testGetRootElementFromFileInClasspath() {
        try {
            Element  element = XmlHelper.getRootElementFromFileInClasspath(filePath);
            assert element != null;
        } catch(Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
