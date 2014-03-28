package client;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Encapsulates the search parameters that will be used
 * to query Foursquare for a specific venue. The parameters
 * should be stored as a Map not only to conform to the 
 * Foursquare API search method's parameters, but also in
 * order to allow this class to hold parameters for any
 * Foursquare query
 * @author Nick Magerko
 */
public class FoursquareSearchParams {
	// the search parameters
	private Map<String, String> searchParams;
	// a String representing the purpose of the parameters
	// (that is, to query a venue, event, etc)
	private String searchType;
	
	/**
	 * TODO: General comment
	 * (Note purpose of not allowing user to simply pass a Map)
	 * @param searchType
	 */
	public FoursquareSearchParams(String searchType){
		searchParams = new HashMap<>();
		this.searchType = searchType;
	}
	
	/**
	 * TODO: General comment
	 * @param param
	 * @param value
	 */
	public void addParam(String param, String value){
		searchParams.put(param, value);
	}
	
	/**
	 * TODO: General comment
	 * (Note purpose of having two methods that do the same thing)
	 * @param param
	 * @param newValue
	 */
	public void modifyParam(String param, String newValue){
		searchParams.put(param, newValue);
	}
	
	/**
	 * TODO: General comment
	 * @param param
	 */
	public void removeParam(String param){
		searchParams.remove(param);
	}
	
	/**
	 * TODO: General comment
	 * @return	the search parameters, as a Map
	 */
	public Map<String, String> getSearchParams(){
		return searchParams;
	}
	
	/**
	 * TODO: General comment
	 */
	public String toString(){
		String representation = searchType.toUpperCase() + " SEARCH with parameters:\n" + "{\n";
		for (Entry<String, String> parameter : searchParams.entrySet()){
			representation += "\t\"" + parameter.getKey() + "\" : " + "\"" + parameter.getValue() + "\"\n";
		}
		representation += "}\n";
		return representation;
	}
}
