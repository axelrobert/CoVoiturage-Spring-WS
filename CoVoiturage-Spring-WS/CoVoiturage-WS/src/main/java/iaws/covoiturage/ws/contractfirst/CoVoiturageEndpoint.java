package iaws.covoiturage.ws.contractfirst;

import java.util.List;

import iaws.covoiturage.services.CoVoiturageInscriptionService;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.XPathParam;
import org.w3c.dom.Element;

/**
 * @author Axel Robert
 */
@Endpoint
public class CoVoiturageEndpoint {
	private CoVoiturageInscriptionService coVoiturageInscriptionService;
	
	private static final String NAMESPACE_URI = "http://iaws/ws/contractfirst/exemple";
	
	public CoVoiturageEndpoint(CoVoiturageInscriptionService coVoiturageInscriptionService) {
        this.coVoiturageInscriptionService = coVoiturageInscriptionService;
    }
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "CoVoiturageRequest")
	@ResponsePayload
	public Element handleCoVoiturageRequest(@XPathParam("/CoVoiturageRequest/nom") String nomP,
            								@XPathParam("/CoVoiturageRequest/prenom") String prenomP,
    										@XPathParam("/CoVoiturageRequest/mail") String mailP,
    										@XPathParam("/CoVoiturageRequest/adresse") String adresseP) throws Exception {
		
		// parse le XML de CoVoiturageRequest pour extraire les informations du
        // nom, du prenom, du mail et de l'adresse  et creer les objets ad-hoc
        String nom = new String(nomP);
        String prenom = new String(prenomP);
        String mail = new String(mailP);
        String adresse = new String(adresseP);
        
     // invoque le service "CoVoiturageInscriptionService" pour inscrire un personnel
     String resultat = coVoiturageInscriptionService.inscrirePersonnel(nom, prenom, mail, adresse);
     
     // Transforme en element XML ad-hoc pour le retour
     Element elt = XmlHelper.getRootElementFromFileInClasspath("CoVoiturage.xml") ;
     return  elt;
	}
}
