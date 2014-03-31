package data_models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a business, or an entity that is
 * returned by the Foursquare API after a venue 
 * search. Note that some fields are returned as
 * arrays - this is a personal preference that should
 * not affect performance. Additionally, some fields
 * (including objects) may be null, due to a lack of information
 * See: https://developer.foursquare.com/docs/responses/venue
 * @author Nick Magerko
 *
 */
public class Business{
	String id;
	String name;
	Contact contact;
	Location location;
	// created as a List, since it is not known how many (if any) categories will be returned
	List<Category> categories = new ArrayList<Category>();
	boolean verified;
	Stats stats;
	String url;
	Hours hours;
	Menu menu;
	//Specials specials;
	HereNow hereNow;
	String storeId;
	String referralId;
	
	public Business() {	}
	
	public String getId() {	return id; }
	public String getName() { return name; }
	public Contact getContact() { return contact; }
	public Location getLocation() {	return location; }
	public Category[] getCategories() {	return (Category[]) Arrays.copyOf(categories.toArray(), categories.size(), Category[].class); }
	public boolean isVerified() { return verified; }
	public Stats getStats() { return stats; }
	public String getUrl() { return url; }
	public Hours getHours() {return hours; }
	public Menu getMenu() { return menu; }
	//public Specials getSpecials() {	return specials; }
	public HereNow getHereNow() { return hereNow; }
	public String getStoreId() { return storeId; }
	public String getReferralId() { return referralId; }
	
	public void setId(String id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setContact(Contact contact) { this.contact = contact; }
	public void setLocation(Location location) { this.location = location; }
	public void setCategories(Category[] categories) { 
		this.categories.clear();
		for (Category category : categories){
			this.categories.add(category);
		}
	}
	public void setVerified(boolean verified) { this.verified = verified; }
	public void setStats(Stats stats) { this.stats = stats; }
	public void setUrl(String url) { this.url = url; }
	public void setHours(Hours hours){ this.hours = hours; }
	public void setMenu(Menu menu) { this.menu = menu; }
	//public void setSpecials(Specials specials) { this.specials = specials; }
	public void setHereNow(HereNow hereNow) { this.hereNow = hereNow; }
	public void setStoreId(String storeId) { this.storeId = storeId; }
	public void setReferralId(String referralId) { this.referralId = referralId; }
}
