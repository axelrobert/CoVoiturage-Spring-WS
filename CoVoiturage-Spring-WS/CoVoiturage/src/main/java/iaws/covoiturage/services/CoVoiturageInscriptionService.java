package iaws.covoiturage.services;

import java.io.IOException;

/**
 * @author Axel Robert, Valentin Boutonn�
 */
public interface CoVoiturageInscriptionService {
	public String inscrirePersonnel(
            String nom,
            String prenom,
            String mail,
            String adresse
    ); 
}
