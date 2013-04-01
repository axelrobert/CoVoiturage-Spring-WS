package iaws.covoiturage.ws.contractfirst;

import iaws.covoiturage.config.loglvl.ResultLevel;
import iaws.covoiturage.domain.Coordonnee;
import iaws.covoiturage.services.CoVoiturageInscriptionService;
import iaws.covoiturage.services.impl.CoVoiturageInscriptionServiceImpl;

import org.apache.log4j.Logger;
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
public class CoVoiturageInscriptionEndpoint {
	private static final Logger logger = Logger.getLogger(CoVoiturageInscriptionEndpoint.class); 
	private CoVoiturageInscriptionService coVoiturageInscriptionService;
	
	private static final String NAMESPACE_URI = "http://iaws/ws/contractfirst/inscription";
	
	public CoVoiturageInscriptionEndpoint(CoVoiturageInscriptionService coVoiturageInscriptionService) {
        this.coVoiturageInscriptionService = coVoiturageInscriptionService;
    }
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "CoVoiturageRequestIns")
	@Namespace(prefix = "cv", uri = NAMESPACE_URI)
	@ResponsePayload
	public Element handleCoVoiturageRequest(@XPathParam("/cv:CoVoiturageRequestIns/cv:nom") String nomP,
            								@XPathParam("/cv:CoVoiturageRequestIns/cv:prenom") String prenomP,
    										@XPathParam("/cv:CoVoiturageRequestIns/cv:mail") String mailP,
    										@XPathParam("/cv:CoVoiturageRequestIns/cv:adresse") String adresseP) throws JDOMException {
		
		// parse le XML de CoVoiturageRequest pour extraire les informations du
        // nom, du prenom, du mail et de l'adresse  et creer les objets ad-hoc
        String nom = new String(nomP);
        String prenom = new String(prenomP);
        String mail = new String(mailP);
        String adresse = new String(adresseP);
	        
	     // invoque le service "CoVoiturageInscriptionService" pour inscrire un personnel
	     String resultat = coVoiturageInscriptionService.inscrirePersonnel(nom, prenom, mail, adresse);
	     
	     // construit le xml résultat
	     CoVoiturageInscriptionServiceImpl impl = new CoVoiturageInscriptionServiceImpl();
	     Coordonnee coord = impl.getLatitudeAndLongitude(adresse);
	     Element elt = null;
	     if(resultat.equals("OK")) {
		     if(coord != null)
		     	elt = XmlHelper.getResultsInXml(coord, nom, prenom, mail, adresse);
	     } else {
	    	 logger.log(ResultLevel.RESULT, resultat);
	     }
	     
	     return  elt;
	}
}
