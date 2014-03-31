package client;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Encapsulates the search parameters that will be used
 * to query Foursquare. The parameters allowed are only
 * those that are allowed by the Foursquare venue search.
 * See https://developer.foursquare.com/docs/venues/search
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
	// note that these are not necessarily required for a venue search, but since this assignment
	// requires the use of them, I have declared them to be "required"
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
		// reset the near parameter, if it set
		if (searchParams.get("near") != ""){
			searchParams.put("near", "");
		}
		
		searchParams.put("ll", checkLat(lat) + "," + checkLng(lng));
	}
	
	/**
	 * Provides the latitude and longitude around which to search.
	 * @return the latitude and longitude (as a String)
	 */
	public String getLatLongParam(){ return searchParams.get("ll"); }
	
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
	 * Provides the geocodable location around which to search
	 * @return the location around which to search
	 */
	public String getNearParam(){ return searchParams.get("near"); }
	
	/**
	 * Sets the query for the search
	 * @param query	the string to search for
	 */
	public void setQuery(String query){
		searchParams.put("query", query);
	}
	
	/**
	 * Provides the query for the search
	 * @return	the query string to search for
	 */
	public String getQuery(){ return searchParams.get("query"); }
	
	/**
	 * Sets the number of results to return (up to 50)
	 * @param limit the number of results to return up to 50
	 */
	public void setLimit(int limit){
		// check to make sure that the limit is reasonable
		if (limit > 50){
			System.err.println("Warning: limit may not exceed 50 results. Limit reduced.");
			limit = 50;
		}
		if (limit < 1){
			System.err.println("Warning: limit may not be less than 1 result. Limit increased.");
			limit = 1;
		}
		searchParams.put("limit", limit + "");
	}
	
	/**
	 * Provides the number of results to return
	 * @return	the number of results to return
	 */
	public Integer getLimit() { return Integer.parseInt(searchParams.get("limit")); }
	
	/**
	 * Sets the intent of the search
	 * @param intent	either checkin, browse, global or match
	 */
	public void setIntent(String intent){
		intent = strip(intent);
		// check to be sure that the entered intent is available for use 
		if (!(intent.toLowerCase().equals("checkin") || intent.toLowerCase().equals("browse") || intent.toLowerCase().equals("global") || intent.toLowerCase().equals("match"))){
			System.err.println("Warning: the value \"" + intent + "\" is not valid for the parameter \"intent\". Using \"checkin\" intent instead.");
			intent = "checkin";
		}
		searchParams.put("intent", strip(intent));
	}
	
	/**
	 * Provides the intent of the search
	 * @return	the intent of the search
	 */
	public String getIntent(){ return searchParams.get("intent"); }
	
	/**
	 * Sets the radius of the query (in meters)
	 * @param radius	the radius, in meters, to query around
	 */
	public void setRadius(int radius){
		// check to be sure that the radius will not cause an error
		if (radius <= 0) {
			System.err.println("Warning: the radius may not be at or under zero meters. Radius increased to 1 meter.");
			radius = 1;
		}			
		else if(radius > 100000){
			System.err.println("Warning: the radius may not be above 100,000 meters. Radius decreased.");
			radius = 100000;
		}
		searchParams.put("radius", radius + "");
	}
	
	/**
	 * Provides the radius of the query
	 * @return	the radius of the query (in meters)
	 */
	public Integer getRadius(){ return Integer.parseInt(searchParams.get("radius")); }
	
	/**
	 * Sets the south-west corner of a rectangle around which to bound the results
	 * @param lat	the latitude of this point
	 * @param lng	the longitude of this point
	 */
	public void setSW(double lat, double lng){
		searchParams.put("sw", checkLat(lat) + "," + checkLng(lng));
	}
	
	/**
	 * Provides the south-west corner of a rectangle around which to bound the results
	 * @return	the south-west corner of the search bounding box (as a String of "latitude, longitude")
	 */
	public String getSW(){ return searchParams.get("sw"); }
	
	/**
	 * Sets the north-east corner of a rectangle around which to bound the results
	 * @param lat	the latitude of this point
	 * @param lng	the longitude of this point
	 */
	public void setNE(double lat, double lng){
		searchParams.put("ne", checkLat(lat) + "," + checkLng(lng));
	}
	
	/**
	 * Provides the north-east corner of a rectangle around which to bound the results
	 * @return	the north-east corner of the search bounding box (as a String of "latitude, longitude")
	 */
	public String getNE(){ return searchParams.get("ne"); }
	
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
	 * Provides the list of categories to limit results to
	 * @return	categories to limit results, as a concatenated String
	 */
	public String getCategoryIds() { return searchParams.get("categoryId"); }
	
	/**
	 * Provides a URL that Foursquare will attempt to 
	 * match locations to
	 * @param URL	a URL to match locations to, beginning with http://
	 */
	public void setURL(String URL){
		// check to see if the URL is properly formatted
		if (!URL.contains("http://") || !URL.contains("https://")){
			URL = new String("http://" + URL);
		}
		searchParams.put("url", URL);
	}
	
	/**
	 * Provides the URL that Foursquare will attempt to 
	 * match locations to
	 * @return	the URL to match locations to
	 */
	public String getURL(){ return searchParams.get("url"); }
	
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
	
	/**
	 * Strips a string of all whitespace, and then
	 * returns the result
	 * @param string	the string to strip
	 * @return	the stripped string
	 */
	private String strip (String string){
		return string.replaceAll("\\s+", "");
	}
	
	/**
	 * Checks to be sure that the latitude is in a reasonable range
	 * @param lat	the latitude
	 * @return	the corrected latitude
	 */
	private double checkLat(double lat){
		if (lat < -90.0){
			System.err.println("Warning: latitude was less than minimum (-90.0). Latitude increased.");
			lat = -90.0;
		}
		else if (lat > 90.0){
			System.err.println("Warning: latitude was more than maximum (90.0). Latitude decreased.");
			lat = 90.0;
		}
		return lat;
	}
	
	/**
	 * Checks to be sure that the longitude is in a reasonable range
	 * @param lng	the longitude
	 * @return	the corrected longitude
	 */
	private double checkLng(double lng){
		if (lng < -180.0){
			System.err.println("Warning: longitude was less than minimum (-180.0). Longitude increased.");
			lng = -180.0;
		}
		else if (lng > 180.0){
			System.err.println("Warning: longitude was more than maximum (180.0). Longitude decreased.");
			lng = 180.0;
		}
		return lng;
	}
}
