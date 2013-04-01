package iaws.covoiturage.ws.contractfirst;

import iaws.covoiturage.domain.Personnel;
import iaws.covoiturage.services.CoVoiturageLocalisationService;

import java.util.ArrayList;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.Namespace;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.XPathParam;

/**
 * @author Axel Robert, Valentin Boutonné
 */
@Endpoint
public class CoVoiturageLocalisationEndPoint {
	private CoVoiturageLocalisationService coVoiturageLocalisationService;
	
	private static final String NAMESPACE_URI = "http://iaws/ws/contractfirst/localisation";
	
	public CoVoiturageLocalisationEndPoint(CoVoiturageLocalisationService coVoiturageLocalisationService) {
        this.coVoiturageLocalisationService = coVoiturageLocalisationService;
    }
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "CoVoiturageRequestLoc")
	@Namespace(prefix = "cv", uri = NAMESPACE_URI)
	@ResponsePayload
	public Element handleCoVoiturageRequest(@XPathParam("/cv:CoVoiturageRequestLoc/cv:userid") String id,
            								@XPathParam("/cv:CoVoiturageRequestLoc/cv:rayonvoisinage") Double rayon
    										) throws JDOMException {
		
		// parse le XML de CoVoiturageRequest pour extraire les informations du
        // nom, du prenom, du mail et de l'adresse  et creer les objets ad-hoc
        String iduser = new String(id);
        Double rayonKm = new Double(rayon);
        
	     // invoque le service "CoVoiturageInscriptionService" pour inscrire un personnel
	     ArrayList<Personnel> resultat = coVoiturageLocalisationService.localiserVoisins(iduser, rayonKm);
	     
	     // construit le xml résultat
	     Element elt = XmlHelper.getResultsInXml(resultat);   
	     
	     return  elt;
	}
}
