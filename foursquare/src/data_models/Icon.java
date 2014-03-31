package data_models;

/**
 * Contains a business's category icon, with the
 * main URL of the image in "prefix" and the file
 * extension in "suffix"
 * See https://developer.foursquare.com/docs/responses/venue
 * @author Nick Magerko
 *
 */
public class Icon{
	private String prefix;
	private String suffix;
	
	public Icon() {
		
	}
	
	public String getPrefix() { return prefix; }
	public String getSuffix() { return suffix; }
	
	public void setPrefix(String prefix) { this.prefix = prefix; }
	public void setSuffix(String suffix) { this.suffix = suffix; }			
}
