package data_models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the times in which the business
 * may be open. 
 * See https://developer.foursquare.com/docs/responses/hours
 * @author Nick Magerko
 *
 */
public class TimeFrame {
	List<Integer> days = new ArrayList<>();
	List<Open> open = new ArrayList<>();
	boolean includesToday;
	List<Segment> segments = new ArrayList<>();
	
	public TimeFrame() {
		
	}
	
	public Integer[] getDays(){ return (Integer[]) Arrays.copyOf(days.toArray(), days.size(), Integer[].class);}
	public Open[] getOpen(){ return (Open[]) Arrays.copyOf(open.toArray(), open.size(), Open[].class); }
	public boolean getIncludesToday(){ return this.includesToday; }
	public Segment[] getSegments(){ return (Segment[]) Arrays.copyOf(segments.toArray(), segments.size(), Segment[].class); }
	
	public void setDays(Integer[] days){ 
		this.days.clear();
		for (Integer day : days){
			this.days.add(day);
		}
	}
	public void setOpen(Open[] open){
		this.open.clear();
		for (Open openObject : open){
			this.open.add(openObject);
		}
	}
	public void setIncludesToday(boolean includesToday){ this.includesToday = includesToday; }
	public void setSegments(Segment[] segments){
		this.segments.clear();
		for (Segment segment : segments){
			this.segments.add(segment);
		}
	}
}
