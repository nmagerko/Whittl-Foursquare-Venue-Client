package data_models;

import java.util.Arrays;
import java.util.List;

/**
 * Details what Foursquare users are at this location currently
 * Once again, some fields are returned as arrays
 * @author Nick Magerko
 *
 */
public class HereNow {
	int count;
	List<String> groups;
	
	public HereNow() {
		
	}
	
	public int getCount() { return count; }
	public String[] getGroups() { return Arrays.copyOf(groups.toArray(), groups.size(), String[].class); }
	
	public void setCount(int count) { this.count = count; }
	public void setGroups(String[] groups) {
		for (String group : groups){
			this.groups.add(group);
		}
	}
	
}
