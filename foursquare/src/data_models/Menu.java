package data_models;

/**
 * Holds the menu information for a business
 * (This may or may not be useful to Whittl, but
 * I included as many models as I could)
 * @author Nick Magerko
 *
 */
public class Menu {
	String type;
	String label;
	String anchor;
	String url;
	String mobileURL;
	
	public Menu() {
		
	}
	
	public String getType() { return type; }
	public String getLabel() { return label; }
	public String getAnchor() {	return anchor; }
	public String getUrl() { return url; }
	public String getMobileURL() { return mobileURL; }
	
	public void setType(String type) { this.type = type; }
	public void setLabel(String label) { this.label = label; }
	public void setAnchor(String anchor) { this.anchor = anchor; }
	public void setUrl(String url) { this.url = url; }
	public void setMobileURL(String mobileURL) { this.mobileURL = mobileURL; }
}
