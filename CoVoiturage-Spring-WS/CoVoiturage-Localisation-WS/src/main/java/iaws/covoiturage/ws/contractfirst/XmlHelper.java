package iaws.covoiturage.ws.contractfirst;

import iaws.covoiturage.domain.Personnel;

import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

/**
 * @author Axel Robert, Valentin Boutonné
 */
public class XmlHelper {
	
	/**
     * Return the root Element which contains all parameters 
     * @param coord Coordonnees de l'adresse
     * @param nom Nom du personnel
     * @param prenom Prenom du personnel
     * @param mail Email du personnel
     * @return  the root Element
     */
	public static Element getResultsInXml(ArrayList<Personnel> resultat) {
		//Nous allons commencer notre arborescence en créant la racine XML
   		//qui sera ici "personnes".
   		Element racine = new Element("CoVoiturage");
   		racine.setNamespace(Namespace.getNamespace("http://iaws/ws/contractfirst/localisation"));
   		Namespace XSI = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
   		racine.addNamespaceDeclaration(XSI);
   		racine.setAttribute("schemaLocation", "http://iaws/ws/contractfirst/localisation CoVoiturageLoc.xsd", XSI);
   		Document doc = new Document(racine);
   		
   		for(Personnel p : resultat) {
	   		Element personnel = new Element("personnel","http://iaws/ws/contractfirst/localisation");
	        racine.addContent(personnel);
	        Element familyname = new Element("nom","http://iaws/ws/contractfirst/localisation");
	        familyname.setText(p.getNom());
	        Element firstname = new Element("prenom","http://iaws/ws/contractfirst/localisation");
	        firstname.setText(p.getPrenom());
	        Element email = new Element("mail","http://iaws/ws/contractfirst/localisation");
	        email.setText(p.getMail());
	        Element adr = new Element("adresse","http://iaws/ws/contractfirst/localisation");
	        adr.setText(p.getAdresse());
	        personnel.addContent(familyname);
	        personnel.addContent(firstname);
	        personnel.addContent(email);
	        personnel.addContent(adr);
   		}
        
        return doc.getRootElement();
	}
	
}
