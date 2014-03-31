package data_models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains the information regarding whether
 * or not a business is open, when it is open,
 * and so on. Note that this value never appears
 * to be in the search results, but was included
 * due to its perceived usefulness
 * See https://developer.foursquare.com/docs/responses/hoursformatted.html
 * @author Nick Magerko
 *
 */
public class Hours {
	String status;
	boolean isOpen;
	List<TimeFrame> timeframes = new ArrayList<>();
	boolean includesToday;
	
	public String getStatus(){ return this.getStatus(); }
	public boolean getIsOpen(){ return this.isOpen; }
	public TimeFrame[] getTimeFrames(){ return (TimeFrame[]) Arrays.copyOf(this.timeframes.toArray(), this.timeframes.size(), TimeFrame[].class); }
	public boolean includesToday() { return includesToday(); }
	
	public void setStatus(String status){ this.status = status; }
	public void setIsOpen(boolean isOpen){ this.isOpen = isOpen; }
	public void setTimeFrames(TimeFrame[] timeframes){
		this.timeframes.clear();
		for (TimeFrame timeframe : timeframes){
			this.timeframes.add(timeframe);
		}
	}
	public void setIncludesToday(boolean includesToday){ this.includesToday = includesToday; }
	

}
