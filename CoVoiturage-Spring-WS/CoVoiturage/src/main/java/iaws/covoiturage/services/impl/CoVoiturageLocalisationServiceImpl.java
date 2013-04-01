package iaws.covoiturage.services.impl;

import java.util.ArrayList;

import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Document;
import com.fourspaces.couchdb.Session;
import com.fourspaces.couchdb.ViewResults;

import iaws.covoiturage.domain.Personnel;
import iaws.covoiturage.services.CoVoiturageLocalisationService;

/**
 * @author Axel Robert, Valentin Boutonné
 */
public class CoVoiturageLocalisationServiceImpl implements CoVoiturageLocalisationService{

	public ArrayList<Personnel> localiserVoisins(String userid, Double rayonKm) {
		ArrayList<Personnel> voisins = new ArrayList<Personnel>();
		Session s = new Session("localhost",5984);
		Database db = s.getDatabase("covoituragedb");
		
		Document main = db.getDocument(userid);
		Double latitude = (Double) main.get("latitude");
		Double longitude = (Double) main.get("longitude");
		
		ViewResults result = db.getAllDocuments();
		for (Document d: result.getResults()) {
				Object id = d.get("id");
				String tid = (String) id;
				if(!tid.equals(userid)) {
					Document full = db.getDocument(tid);
					Double lat = (Double) full.get("latitude");
					Double lon = (Double) full.get("longitude");
					if(distanceEntreDeuxVoisins(lat, latitude, lon, longitude) <= rayonKm) {
						Personnel voisin = new Personnel();
						voisin.setId(((String) id));
						voisin.setNom((String)full.get("nom"));
						voisin.setPrenom((String)full.get("prenom"));
						voisin.setMail((String)full.get("mail"));
						voisin.setAdresse((String)full.get("adresse"));
						voisins.add(voisin);
					} 
				}
		}
		
		return voisins;
	}
	
	Double distanceEntreDeuxVoisins(Double lat1, Double lat2, Double lon1, Double lon2) {
		Double distance = 2*6371*
						  Math.asin(
							  Math.sqrt(
								  Math.pow(Math.sin((Math.toRadians(lat1)-Math.toRadians(lat2))/2),2) +
								  (Math.cos(Math.toRadians(lat1)))*
								  (Math.cos(Math.toRadians(lat2)))*
								  (Math.pow(Math.sin((Math.toRadians(lon1)-Math.toRadians(lon2))/2),2))
							  )
						  );
		return distance;
	}
}
