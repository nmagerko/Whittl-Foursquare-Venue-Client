package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data_models.Business;
import flexjson.JSONDeserializer;
import flexjson.JSONException;
import flexjson.JSONSerializer;

/**
 * Encapsulates the data that were provided to the
 * client after the venue search, converting it from
 * JSON to individual Business objects
 * @author Nick Magerko
 *
 */
public class FoursquareResult {
	// the search results, as Business objects
	List<Business> businesses;
	
	/**
	 * Creates a new FoursquareResult instance, given the
	 * JSON response string
	 * @param queryResponse	the JSON response from Foursquare
	 */
	public FoursquareResult(String queryResponse) {
		// the list of businesses in the query response
		businesses = new ArrayList<>();
		// if the query failed, do not add anything to the array
		if (!queryResponse.equals("")){
			// deserialize the response so that the venues can be accessed, if possible
			Map<String, Map<String, ArrayList<Map<String, Object>>>> deserializedResponse = null;
			boolean allJSONParsed = true;
			try {
				deserializedResponse = new JSONDeserializer<Map<String, Map<String, ArrayList<Map<String, Object>>>>>().deserialize(queryResponse);
			}
			catch (JSONException exception){
				System.err.println("A portion of the JSON could not be parsed. The parser claims:\n"
						+ exception.getMessage() + "\n"
						+ "All results up to this point (if any) have been stored. \n");
						allJSONParsed = false;
			}
			if (allJSONParsed){
				// check to be sure that the format of the response is not different than was expected
				// if it was, don't continue, as before
				boolean formatCorrect = true;
				try {
					deserializedResponse.get("response").get("venues");
				}
				catch (Exception exception){
					System.err.println("The format of the query response was different than expected. The response's header objects were assumed to be [\"meta\", \"notifications\", \"response\"]\n"
										+ "but the header objects " + deserializedResponse.keySet() + " were found instead. Note that this FoursquareResult will contain no entries. \n");
					formatCorrect = false;
				}
				if (formatCorrect) {
					// for each venue, reserialize the String, and then deserialize it into a Business object
					// add the new object to the list of businesses, if possible
					for (Map<String, Object> venue : deserializedResponse.get("response").get("venues")){
						Business newBusiness = null;
						boolean businessJSONParsed = true;
						try {
							newBusiness = new JSONDeserializer<Business>().deserialize(new JSONSerializer().deepSerialize(venue), Business.class);
						}
						catch (JSONException exception){
							System.err.println("A portion of the JSON could not be parsed. The parser claims:\n"
												+ exception.getMessage() + "\n"
												+ "All results up to this point (if any) have been stored. \n");
							businessJSONParsed = false;
						}
						if (businessJSONParsed){
							businesses.add(newBusiness);
						}
					}
				}
			}
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
	 * Allows the user to access each result individually, when
	 * he or she provides the result number that he or she would like to access.
	 * Note that results are numbered from 1 to N
	 * @param resultNumber	the result to access
	 * @return	the Business object at that number
	 */
	public Business getResult(int resultNumber){
		// if the user tries to access a result that doesn't exist, warn them of the result
		if (resultNumber > getNumberOfResults() || resultNumber <= 0){
			System.err.println("Warning: the resultNumber must be between one and the integer returned by getNumberOfResults(), inclusive.\n"
								+ "Be aware that the result will be null. \n");
			Business nullBusiness = null;
			return nullBusiness;
		}
		return businesses.get(resultNumber-1);
	}
}
