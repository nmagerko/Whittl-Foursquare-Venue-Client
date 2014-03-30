package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data_models.Business;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

/**
 * Encapsulates the data that were provided to the
 * client after the venue search, converting it from
 * JSON to individual Business objects
 * @author Nick Magerko
 *
 */
public class FoursquareResult {
	// the JSON serializer and deserializer
	private static JSONDeserializer<?> deserializer = new JSONDeserializer<>();
	private static JSONSerializer serializer = new JSONSerializer();
	// the search results, as Business objects
	List<Business> businesses;
	
	/**
	 * Creates a new FoursquareResult instance, given the
	 * JSON response string
	 * @param queryResponse	the JSON response from Foursquare
	 */
	@SuppressWarnings("unchecked")
	public FoursquareResult(String queryResponse) {
		// get the headers from the query's response
		Map<String, Map<String, ArrayList<Object>>> headers = (Map<String, Map<String, ArrayList<Object>>>) deserializer.deserialize(queryResponse);
		// check to make sure that the structure has not changed from what was expected
		if (headers.get("response").get("venues") == null){
			throw new UnexpectedFormatException("Expected \"response\" header object containing list of \"venues,\" but found different structure:\n"
												+ serializer.deepSerialize(headers));
		}
		// if there are no venues, just create an empty ArrayList
		if (headers.get("response").get("venues").size() == 0){
			businesses = new ArrayList<>();
		}		
		// get the response header and the venues that compose it, and convert each venue into a Business
		// assign the result to the businesses 
		try {
			businesses = (ArrayList<Business>) deserializer.use("values", Business.class).deserialize(serializer.deepSerialize(headers.get("response").get("venues")));	
		}
		catch (IllegalArgumentException exception){
			// need to do some testing to figure out why this would happen
			System.out.println("Error!");
		}
		finally {
			System.out.println(serializer.deepSerialize(headers.get("response").get("venues")));
		}
	}
	
	/**
	 * Provides the number of results that were returned
	 * by the venue search
	 * @return	the number of results returned by the venue search
	 */
	public int getNumberOfResults() {
		return businesses.size();
	}
	
	/**
	 * Allows the client to access each result individually, when
	 * he or she provides the result number that they would like to access
	 * @param resultNumber	the result to access
	 * @return	the Business object at that number
	 */
	public Business getResult(int resultNumber){
		if (resultNumber > getNumberOfResults() || resultNumber <= 0){
			throw new IllegalArgumentException("The resultNumber must be between one and the integer returned by getNumberOfResults(), inclusive.");
		}
		return businesses.get(resultNumber-1);
	}
}

/**
 * Alerts the user that the format of the JSON
 * string is different that what was expected
 * @author Nick Magerko
 *
 */
class UnexpectedFormatException extends RuntimeException {
	private static final long serialVersionUID = -4589374284099677509L;
	public UnexpectedFormatException() { super(); }
	public UnexpectedFormatException(String description) { super(description); }
}
