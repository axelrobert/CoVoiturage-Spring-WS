package iaws.covoiturage.services.impl;


import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
/*import org.jcouchdb.db.Database;
import org.jcouchdb.document.Document;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
*/
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Document;
import com.fourspaces.couchdb.Session;
import com.fourspaces.couchdb.ViewResults;
import iaws.covoiturage.domain.Coordonnee;
import iaws.covoiturage.services.CoVoiturageInscriptionService;

/**
 * @author Axel robert
 */
public class CoVoiturageInscriptionServiceImpl implements CoVoiturageInscriptionService{
	
	private static final Logger logger = Logger.getLogger(CoVoiturageInscriptionServiceImpl.class); 
	
	public String inscrirePersonnel(String nom, String prenom, String mail,
			String adresse) {
		return addPersonnel(nom, prenom, mail, adresse);
	}

	public String addPersonnel(String nom, String prenom, String mail,
			String adresse) {
		String ok = "OK";
		String nok = "KO erreur ";
		Session s = new Session("localhost",5984);
		Database db = s.getDatabase("covoituragedb");
		Document newEntry = new Document();
		//Database db = new Database("localhost", "covoituragedb");
		Coordonnee coord = getLatitudeAndLongitude(adresse);
		if(coord == null){
			return nok + "200 : Adresse postale non connue de Open Street Map";
		}
		ViewResults result = db.getAllDocuments();
		for (Document d: result.getResults()) {
				String id = (String) d.get("id");
				Document full = db.getDocument(id);
		        if(full.containsValue(mail)) {
		        	return nok + "100 : Adresse email déjà utilisée";
		        }
		}
		/*ViewResult<Map> result = db.listDocuments(null, null);
		List<ValueRow<Map>> res = result.getRows();
		System.out.println(res.size());*/
		if(!Pattern.matches("^[_A-Za-z0-9-.]+@univ-tlse3.fr$", mail)) {
			return nok + "110 : Adresse email invalide";
		}
		newEntry.put("nom", nom);
		newEntry.put("prenom", prenom);
		newEntry.put("mail", mail);
		newEntry.put("adresse", adresse);
		newEntry.put("latitude", coord.getLatitude());
		newEntry.put("longitude", coord.getLongitude());
		db.saveDocument(newEntry);
		// create a hash map document with two fields    
	    /*Map<String,String> doc = new LinkedHashMap<String, String>();
	    doc.put("nom", nom);
	    doc.put("mail", mail);
		doc.put("adresse", adresse);
		doc.put("latitude", Float.toString(coord.getLatitude()));
		doc.put("longitude", Float.toString(coord.getLongitude()));
	    db.createDocument(doc);*/
	    return ok;
	}
	
	public Coordonnee getLatitudeAndLongitude(String adresse) {
		String adr = adresse.replaceAll("\\s", "+").toLowerCase();
		adr = adr.replaceAll("[èéêë]","e");
		adr = adr.replaceAll("[àáâãäå]","a");
		adr = adr.replaceAll("[òóôõöø]","o");
		adr = adr.replaceAll("[ìíîï]","i");
		adr = adr.replaceAll("[ùúûü]","u");
		adr = adr.replaceAll("[ç]","c");
		String url = "http://nominatim.openstreetmap.org/search?q="+ adr + "&format=xml&limit=1";

		//On crée une instance de SAXBuilder
	      final SAXBuilder sxb = new SAXBuilder();
		  org.jdom2.Document document = null;
	      try {
	    	  HttpClient hc = new HttpClient();
	    	  GetMethod get = new GetMethod(url);
	    	  //BufferedWriter out;
	    	  if(hc.executeMethod(get)!=200) {
	    		  System.out.println("FAILED");
	    	  } else {
		    	  document = sxb.build(url);
	    	  }
	    	  
	      } catch (Exception e) {
	    	  logger.error("Erreur lors de la creation du JDOM");
	    	  logger.error("Erreur : " + (e.getMessage()));
	      }

	      Element racine = document.getRootElement();
	      Element place = racine.getChild("place");
	      Coordonnee coord = new Coordonnee();
	      try {
	    	  Float latitude = new Float(place.getAttributeValue("lat"));
	    	  Float longitude = new Float(place.getAttributeValue("lon"));
		      coord.setLatitude(latitude);
		      coord.setLongitude(longitude);
	      } catch (NullPointerException e) {
	    	  logger.error("Syntaxe de l'adresse postale incorrecte ou adresse inconnue !");
	    	  logger.info("La location doit être de la forme : " +
	    	  		"[numéro] rue[,] ville");
	    	  return null;
	      }
	      
	      return coord;
	}
}
