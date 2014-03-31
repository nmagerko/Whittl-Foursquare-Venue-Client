package data_models;

/**
 * Represents the range of times in a day in which 
 * a business is open (see https://developer.foursquare.com/docs/responses/hours
 * for more details), where start is the opening 24 HR
 * time, and end is the 24 HR closing time
 * @author Nick Magerko
 *
 */
public class Open {
	String start;
	String end;
	
	public Open() {
		
	}
	
	public String getStart(){ return this.start; }
	public String getEnd(){ return this.end; }
	
	public void setStart(String start){ this.start = start; }
	public void setEnd(String end){ this.end = end; }

}
