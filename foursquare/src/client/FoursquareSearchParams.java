package client;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Encapsulates the search parameters that will be used
 * to query Foursquare. The parameters should be stored 
 * as a Map not only to conform to the Foursquare API 
 * search method's parameters, but also in order to 
 * allow this class to hold parameters for any
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
	 * Creates a new set of search parameters, identified
	 * in a general way by the search type
	 * @param searchType	represents the purpose of the parameters (what is being queried)
	 */
	public FoursquareSearchParams(String searchType){
		searchParams = new HashMap<>();
		this.searchType = searchType;
	}
	
	/**
	 * Adds a parameter to the search parameters
	 * @param param	the name of the parameter to add, as specified by the Foursquare documentation
	 * @param value of the parameter to add
	 */
	public void addParam(String param, String value){
		if (!searchParams.containsKey(param)){
			searchParams.put(param, value);
		}
		else {
			throw new IllegalArgumentException("Parameter \"" + param + "\" already exists in search parameters.");
		}
	}
	
	/**
	 * Allows for the modification of a parameter, essentially following the
	 * same procedure as the addParam(param, value) method
	 * @param param	the name of the parameter to modify
	 * @param newValue	the new value of the parameter
	 */
	public void modifyParam(String param, String newValue){
		if (searchParams.containsKey(param)){
			searchParams.put(param, newValue);
		}
		else { 
			throw new IllegalArgumentException("Parameter \"" + param + "\" does not exist in search parameters.");
		}
	}
	
	/**
	 * Removes a parameter from the search parameters
	 * @param param	the name of the parameter to remove
	 */
	public void removeParam(String param){
		if (searchParams.containsKey(param)){
			searchParams.remove(param);
		}
		else {
			throw new IllegalArgumentException("Parameter \"" + param + "\" does not exist in search parameters.");
		}
	}
	
	/**
	 * Get the parameters that have been added thus far
	 * @return	the search parameters, as a Map
	 */
	public Map<String, String> getSearchParams(){
		return searchParams;
	}
	
	/**
	 * Prints the parameters contained in the search parameters.
	 * Intended for purposes of debugging
	 */
	public String toString(){
		String representation = searchType.toUpperCase() + " SEARCH with parameters " + "{\n";
		for (Entry<String, String> parameter : searchParams.entrySet()){
			representation += "\t\"" + parameter.getKey() + "\" : " + "\"" + parameter.getValue() + "\"\n";
		}
		representation += "}\n";
		return representation;
	}
}
