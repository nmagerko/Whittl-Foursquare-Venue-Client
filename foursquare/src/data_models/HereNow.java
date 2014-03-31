package data_models;

/**
 * Details how many Foursquare users are at this location currently
 * Note that which users are at these locations have been removed,
 * since they are part of another endpoint
 * See https://developer.foursquare.com/docs/responses/venue
 * @author Nick Magerko
 *
 */
public class HereNow {
	int count;
	
	public HereNow() {
		
	}
	
	public int getCount() { return count; }
	
	public void setCount(int count) { this.count = count; }
	
}
