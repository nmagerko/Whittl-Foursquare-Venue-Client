package data_models;

/**
 * Contains a business's contact information
 * (including some social media sites)
 * See https://developer.foursquare.com/docs/responses/venue
 * @author Nick Magerko
 *
 */
public class Contact {
	String phone;
	String formattedPhone;
	String twitter;
	String facebook;
	
	public Contact() {
		
	}
	
	public String getPhone() { return phone; }
	public String getFormattedPhone() {	return formattedPhone; }
	public String getTwitter() { return twitter; }
	public String getFacebook() { return facebook; }
	
	public void setFormattedPhone(String formattedPhone) { this.formattedPhone = formattedPhone; }
	public void setPhone(String phone) { this.phone = phone; }
	public void setTwitter(String twitter) { this.twitter = twitter; }
	public void setFacebook(String facebook) { this.facebook = facebook; }
}	
