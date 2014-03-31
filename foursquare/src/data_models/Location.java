package data_models;

/**
 * Contains the location information about a business
 * See https://developer.foursquare.com/docs/responses/venue
 * @author Nick Magerko
 *
 */
public class Location{
	String address;
	String city;
	String state;
	String postalCode;
	String country;
	// abbreviated country
	String cc;	
	
	public Location() {
		
	}
	
	public String getAddress() { return address; }
	public String getCity() { return city; }
	public String getState() { return state; }
	public String getPostalCode() { return postalCode; }
	public String getCountry() { return country; }
	/**
	 * @return	the abbreviated form of the country name
	 */
	public String getCc() {
		return cc;
	}
	
	public void setAddress(String address) { this.address = address; }
	public void setCity(String city) { this.city = city; }
	public void setState(String state) { this.state = state; }
	public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
	public void setCountry(String country) { this.country = country; }
	/**
	 * @param cc	the abbreviated form of the country name
	 */
	public void setCc(String cc) { this.cc = cc; }	
}