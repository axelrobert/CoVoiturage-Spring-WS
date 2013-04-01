package iaws.covoiturage.services;

import iaws.covoiturage.domain.Personnel;

import java.util.ArrayList;

/**
 * @author Axel Robert, Valentin Boutonné
 */
public interface CoVoiturageLocalisationService {
	public ArrayList<Personnel> localiserVoisins(String id, Double rayonKm);
}
