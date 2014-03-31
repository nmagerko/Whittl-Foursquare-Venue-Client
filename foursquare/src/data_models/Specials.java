package data_models;

/**
 * Contains the "specials" count for a particular
 * business. Note that the specific specials have been removed,
 * since they are part of another endpoint
 * See https://developer.foursquare.com/docs/responses/venue
 * @author Nick Magerko
 *
 */
public class Specials{
	int count;
	
	public Specials() {
		
	}
	public int getCount() { return count; }
	
	public void setCount(int count) { this.count = count; }
}
