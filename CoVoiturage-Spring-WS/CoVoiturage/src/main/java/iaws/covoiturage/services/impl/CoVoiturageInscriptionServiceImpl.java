package iaws.covoiturage.services.impl;

import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
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

	public String inscrirePersonnel(String nom, String prenom, String mail,
			String adresse) {
		return addPersonnel(nom, prenom, mail, adresse);
	}

	private static String addPersonnel(String nom, String prenom, String mail,
			String adresse) {
		String ok = "OK";
		String nok = "KO erreur ";
		Session s = new Session("localhost",5984);
		Database db = s.getDatabase("covoituragedb");
		Document newEntry = new Document();
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
	    return ok;
	}
	
	private static Coordonnee getLatitudeAndLongitude(String adresse) {
		String adr = adresse.replaceAll("\\s", "+").toLowerCase();
		adr = adr.replaceAll("[èéêë]","e");
		adr = adr.replaceAll("[àáâãäå]","a");
		adr = adr.replaceAll("[òóôõöø]","o");
		adr = adr.replaceAll("[ìíîï]","i");
		adr = adr.replaceAll("[ùúûü]","u");
		adr = adr.replaceAll("[ç]","c");
		String url = "http://nominatim.openstreetmap.org/search?q="
				+ adr + "&format=xml";
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
	    	  //lOGGER.error("Erreur lors de la creation du JDOM");
	    	  System.out.println("Erreur : " + (e.getMessage()));
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
	    	  System.out.println("Syntaxe de l'adresse postale incorrecte !");
	    	  System.out.println("La location doit être de la forme : " +
	    	  		"[numéro] rue[,] ville");
	    	  return null;
	      }
	      
	      return coord;
	}
	
	public static void main (String[] args){
	    System.out.println(addPersonnel("nom1","prenom1","mail1@univ-tlse3.fr","136 pilkington avenue, birmingham"));
	}

}
