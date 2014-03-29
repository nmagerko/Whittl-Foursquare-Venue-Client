package client;

import java.util.Map;

import exceptions.RequiredSearchParamsNotSet;
import exceptions.SearchResultError;
import exceptions.SearchResultsEmpty;
import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

/**
 * Serves as the client through which an entity could provide
 * credentials and a redirect URL (as is required by Foursquare's
 * API) to obtain information about businesses around a location
 * @author Nick Magerko
 */
public class FoursquareClient {
	// the response that should be obtained by a Foursquare (or really any) successful query
	private final int HTTP_OK = 200;
	// the API instance that will control the information flow in all queries/responses
	private FoursquareApi API;
	
	/**
	 * Creates a new instance of the client, requiring that the
	 * entity creating the instance provides the necessary credentials
	 * @param clientID	the client's Foursquare ID (provided upon app creation)
	 * @param clientSecret	the client's Foursquare Secret (provided upon app creation)
	 * @param redirectURL	the URL to redirect a user to after login (not necessary for venue queries)
	 */
	public FoursquareClient(String clientID, String clientSecret, String redirectURL){
		API = new FoursquareApi(clientID, clientSecret, redirectURL);
	}
	
	/**
	 * Searches for Foursquare venues that correspond to the inputed
	 * search parameters. See https://developer.foursquare.com/docs/venues/search
	 * for a listing of the required/optional parameters
	 * @param searchParams	the search parameters to be used in the query
	 * @return	an instance of a FoursquareResult
	 * @throws FoursquareApiException	the ambiguously-defined exception in the API, indicating that there was an initialization error
	 */
	public FoursquareResult venueSearch(FoursquareSearchParams searchParams) throws FoursquareApiException{
		Map<String, String> params = searchParams.getSearchParams();
		// check to be sure that at least the required parameters (a latitude/longitude or a location) is
		// present in the parameters, or throw an error
		if (!venueSearchParamsValidation(params)){
			throw new RequiredSearchParamsNotSet(
					"Neither the \"ll\" parameter nor the \"near\" parameter was found.\n" +
					"Consult https://developer.foursquare.com/docs/venues/search for more information."
			);
		}
		// search Foursquare, allowing the API to throw its exception as necessary
		Result<VenuesSearchResult> searchResults = API.venuesSearch(params);
		// check to be sure that the results were received. Otherwise, throw an error, alerting
		// the entity utilizing the class of what went wrong
		if (searchResults.getMeta().getCode() != HTTP_OK){
			throw new SearchResultError(
					"Search failed with HTTP response code " + searchResults.getMeta().getCode() + ".\n" +
					"Error defined as \"" + searchResults.getMeta().getErrorType() + "\" by API.\n" + 
					 searchResults.getMeta().getErrorDetail()
					);
		}
		// check to be sure that there are results to pass, or throw an exception
		if (searchResults.getResult().getVenues().length == 0){
			throw new SearchResultsEmpty(
					"The venues search did not return any results. A FoursquareResult was not created."
					);
		}
		// otherwise, return a new instance of a FoursquareResult
		return new FoursquareResult(searchResults.getResult().getVenues());
	}
	
	/**
	 * Checks to be sure that the search parameters passed to the 
	 * venue search will have at least the required parameters
	 * @param params	the search parameters
	 * @return	true if either a location or a latitude/longitude exists in the params, false otherwise
	 */
	private boolean venueSearchParamsValidation(Map<String, String> params){
		// required parameters, as listed at https://developer.foursquare.com/docs/venues/search
		if(params.containsKey("ll") || params.containsKey("near")){
			return true;
		}
		return false;
	}
}