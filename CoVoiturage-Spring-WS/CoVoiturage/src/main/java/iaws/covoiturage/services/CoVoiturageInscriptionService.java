package iaws.covoiturage.services;

/**
 * @author Axel robert
 */
public interface CoVoiturageInscriptionService {
	public String inscrirePersonnel(
            String nom,
            String prenom,
            String mail,
            String adresse
    );
}
