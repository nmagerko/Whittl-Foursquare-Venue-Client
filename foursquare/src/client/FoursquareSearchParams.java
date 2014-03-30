package client;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Encapsulates the search parameters that will be used
 * to query Foursquare. The parameters allowed are only
 * those that are allowed by the Foursquare venue search
 * @author Nick Magerko
 */
public class FoursquareSearchParams {
	// the parameters that are available for a venue search
	// (the parameter list was taken from https://developer.foursquare.com/docs/venues/search)
	private static final String[] AVAILABLE_SEARCH_PARAMS =
			{ 
			  "ll", "near", "query", "limit", "intent", "radius",
			  "sw", "ne", "categoryId", "url"
			};
	// if any one of these parameters are used, they will require at least one of the other two
	private static final String[] REQUIRED_SEARCH_PARAMS = {"ll", "near", "query"};
	// the search parameters
	private Map<String, String> searchParams;
	
	/**
	 * Creates a new set of search parameters that will 
	 * be used to search Foursquare's venues
	 */
	public FoursquareSearchParams(){
		// add a default value of "" to each of the available values
		searchParams = new HashMap<>();
		for (String param : AVAILABLE_SEARCH_PARAMS){
			searchParams.put(param, "");
		}
	}
	
	/**
	 * Sets the latitude and longitude around which to search.
	 * Resets the "near" param if set
	 * @param lat	latitude
	 * @param lng	longitude
	 */
	public void setLatLong(double lat, double lng){
		if (searchParams.get("near") != ""){
			searchParams.put("near", "");
		}
		searchParams.put("ll", lat + "," + lng);
	}
	
	/**
	 * Sets the geocodable location around which to search.
	 * Resets the "ll" param if set
	 * @param location	geocodable location around which to search
	 */
	public void setQueryNear(String location){
		if (searchParams.get("ll") != ""){
			searchParams.put("ll", "");
		}
		searchParams.put("near", strip(location));
	}
	
	/**
	 * Sets the query for the search
	 * @param query	the string to search for
	 */
	public void setQuery(String query){
		searchParams.put("query", query);
	}
	
	/**
	 * Sets the number of results to return (up to 50)
	 * @param limit the number of results to return up to 50
	 */
	public void setLimit(int limit){
		if (limit > 50){
			throw new InvalidParameterException("The limit may not be above 50 results");
		}
		searchParams.put("limit", limit + "");
	}
	
	/**
	 * Sets the intent of the search
	 * @param intent	either checkin, browse, global or match
	 */
	public void setIntent(String intent){
		intent = strip(intent);
		// check to be sure that the entered intent is available for use 
		if (!(intent.equals("checking") || intent.equals("browse") || intent.equals("global") || intent.equals("match"))){
			throw new InvalidParameterException("The value \"" + intent + "\" is not valid for the parameter \"intent\"");
		}
		searchParams.put("intent", strip(intent));
	}
	
	/**
	 * Sets the radius of the query (in meters)
	 * @param radius	the radius, in meters, to query around
	 */
	public void setRadius(int radius){
		if (radius <= 0 || radius > 100000){
			throw new InvalidParameterException("The radius may not be under zero meters, or above 100,000 meters");
		}
		searchParams.put("radius", radius + "");
	}
	
	/**
	 * Sets the south-west corner of a rectangle around which to bound the results
	 * @param lat	the latitude of this point
	 * @param lng	the longitude of this point
	 */
	public void setSW(double lat, double lng){
		searchParams.put("sw", lat + "," + lng);
	}
	
	/**
	 * Sets the north-east corner of a rectangle around which to bound the results
	 * @param lat	the latitude of this point
	 * @param lng	the longitude of this point
	 */
	public void setNE(double lat, double lng){
		searchParams.put("ne", lat + "," + lng);
	}
	
	/**
	 * Sets a list of categories to limit results to
	 * @param categoryIds	categories to limit results to
	 */
	public void setCategoryIds(String[] categoryIds){
		String paramValue = "";
		// format the array into a string of comma separated values
		for (int index = 0; index < categoryIds.length; index++){
			paramValue += categoryIds[index];
			if (index != categoryIds.length - 1){ paramValue += ","; }
		}
		searchParams.put("categoryId", paramValue);
	}
	
	/**
	 * Provides a URL that Foursquare will attempt to 
	 * match locations to
	 * @param URL	a URL to match locations to, beginning with http://
	 */
	public void setURL(String URL){
		if (!URL.contains("http")){
			throw new InvalidParameterException("URL must contain http:// or https://");
		}
		searchParams.put("url", URL);
	}
	
	/**
	 * Provides the parameters that may be set to the client
	 * @return	the parameters that may be set
	 */
	public String[] getAvailableParams() {
		return AVAILABLE_SEARCH_PARAMS;
	}
	
	/**
	 * Provides the parameters that must be set to the client
	 * @return	the parameters that must be set
	 */
	public String[] getRequiredParams() {
		return REQUIRED_SEARCH_PARAMS;
	}
	
	/**
	 * Get the parameters that have been added thus far
	 * @return	the search parameters, as a Map
	 */
	public Map<String, String> getSearchParams(){
		// only return the parameters that have been set, checking
		// to see if the value is ""
		Map<String, String> setSearchParams = new HashMap<>();
		for (Entry<String, String> entry : searchParams.entrySet()){
			if (entry.getValue() != ""){
				setSearchParams.put(entry.getKey(), entry.getValue());
			}
		}
		return setSearchParams;
	}
	
	/**
	 * Decides whether or not the parameters that have been
	 * set satisfy the required parameter list
	 * @return	Whether or not the required parameters are in the search parameters together
	 */
	public boolean searchParamsValid(){
		// if any of the required search params have been set, perform this check (if none are set, Foursquare will still accept the query)
		if (searchParams.get("query") != null || searchParams.get("ll") != null || searchParams.get("near") != null){
			// whether or not the required parameters have been found
			boolean queryFound = false;
			boolean llOrLocationFound = false;
			// check for the required parameters
			for (String param : getSearchParams().keySet()){
				for (String requiredParam : REQUIRED_SEARCH_PARAMS){
					if (param.equals(requiredParam));{
						if (param.equals("query")){ queryFound = true; }
						else { llOrLocationFound = true; }
					}
					if (queryFound && llOrLocationFound){ return true; }
				}
			}
			// return false if the query and the (lng/lat or "near") parameters were not set
			return false;
		}
		return true;
	}
	
	/**
	 * Strips a string of all whitespace, and then
	 * returns the result
	 * @param string	the string to strip
	 * @return	the stripped string
	 */
	private String strip (String string){
		return string.replaceAll("\\s+", "");
	}
}

/**
 * Alerts the client that an invalid value for a parameter
 * has been passed
 * @author Nick Magerko
 *
 */
class InvalidParameterException extends RuntimeException {
	private static final long serialVersionUID = 6877230524494459380L;
	public InvalidParameterException() { super(); }
	public InvalidParameterException(String description) { super(description); }
	
}
