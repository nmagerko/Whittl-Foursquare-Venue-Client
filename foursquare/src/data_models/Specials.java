package data_models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains the "specials" for a particular
 * business, and how many specials they are offering
 * Note that this class returns arrays as opposed to
 * the actual List (a personal preference that should
 * not affect performance)
 * @author Nick Magerko
 *
 */
public class Specials{
	int count;
	List<String> items = new ArrayList<>();
	
	public Specials() {
		
	}
	
	public Specials(int count, String[] items) {
		this.count = count;
		for (String item : items){
			this.items.add(item);
		}
	}
	
	public int getCount() {
		return count;
	}
	
	public String[] getItems() {
		return Arrays.copyOf(items.toArray(), items.size(), String[].class);
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	public void setItems(String[] items) {
		for (String item : items){
			this.items.add(item);
		}
	}	
}
