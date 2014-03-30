package data_models;

/**
 * Contains one of the categories that Foursquare has
 * classified a business under
 * @author Nick Magerko
 *
 */
public class Category{
	private String id;
	private String name;
	private String shortName;
	Icon icon;
	private boolean primary;
	
	public Category() {
		
	}
	
	public String getId() {	return id; }
	public String getName() { return name; }
	public String getShortName() { return shortName; }
	public Icon getIcon() {	return icon; }
	public boolean isPrimary() { return primary; }
	
	public void setId(String id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setShortName(String shortName) { this.shortName = shortName; }
	public void setIcon(Icon icon) { this.icon = icon; }
	public void setPrimary(boolean primary) { this.primary = primary; }
}