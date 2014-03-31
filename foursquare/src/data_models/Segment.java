package data_models;

/**
 * Describes the named segments of the days
 * in a timeframe in which a business is open.
 * See https://developer.foursquare.com/docs/responses/hours
 * @author Nick Magerko
 *
 */
public class Segment {
	String label;
	String start;
	String end;
	
	public Segment() {
		
	}
	
	public String getLabel(){ return this.label; }
	public String getStart(){ return this.start; }
	public String getEnd(){ return this.end; }
	
	public void setLabel(String label){ this.label = label; }
	public void setStart(String start){ this.start = start; }
	public void setEnd(String end){ this.end = end; }
}
