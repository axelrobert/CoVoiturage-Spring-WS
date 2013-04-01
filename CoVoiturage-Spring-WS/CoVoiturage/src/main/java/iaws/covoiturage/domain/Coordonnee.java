package iaws.covoiturage.domain;

/**
 * @author Axel Robert, Valentin Boutonné
 */
public class Coordonnee {
	private int id;
	private Personnel personnel;
	private float latitude;
	private float longitude;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Personnel getPersonnel() {
		return personnel;
	}
	
	public float getLatitude() {
		return latitude;
	}
	
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	
}
