package client;

import java.util.Map;

import fi.foyt.foursquare.api.*;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

/**
 * TODO: General comment
 * @author Nick Magerko
 */
public class FoursquareClient {
	private final int HTTP_OK = 200;
	private FoursquareApi API;
	
	/**
	 * TODO: General comment
	 * @param clientID
	 * @param clientSecret
	 * @param redirectURL
	 * @param searchParams
	 */
	public FoursquareClient(String clientID, String clientSecret, String redirectURL){
		API = new FoursquareApi(clientID, clientSecret, redirectURL);
	}
	
	/**
	 * TODO: General comment
	 * @param searchParams
	 * @return	an instance of FoursquareResult
	 * @throws FoursquareApiException	the ambiguously-defined exception in the API
	 */
	public FoursquareResult venueSearch(FoursquareSearchParams searchParams) throws FoursquareApiException{
		Map<String, String> params = searchParams.getSearchParams();
		if (!venueSearchParamsValidation(params)){
			throw new RequiredSearchParamsNotSet(
					"Neither the \"ll\" parameter nor the \"near\" parameter was found.\n" +
					"Consult https://developer.foursquare.com/docs/venues/search for more information."
			);
		}
		// note: must allow API to throw its exception
		Result<VenuesSearchResult> searchResults = API.venuesSearch(params);
		if (searchResults.getMeta().getCode() != HTTP_OK){
			throw new SearchResultError(
					"Search failed with HTTP response code " + searchResults.getMeta().getCode() + ".\n" +
					"Error defined as \"" + searchResults.getMeta().getErrorType() + "\" by API." + 
					"Details: \n" + searchResults.getMeta().getErrorDetail()
					);
		}
		return new FoursquareResult(searchResults);
	}
	
	/**
	 * TODO: General comment
	 * @param params
	 * @return
	 */
	private boolean venueSearchParamsValidation(Map<String, String> params){
		// required parameters, as listed at https://developer.foursquare.com/docs/venues/search
		if(params.containsKey("ll") || params.containsKey("near")){
			return true;
		}
		return false;
	}
}

/**
 * TODO: General comment
 * @author Nick Magerko
 */
class RequiredSearchParamsNotSet extends RuntimeException {
	// evidently, this is necessary if we ever decide
	// to serialize our data
	private static final long serialVersionUID = 3432998049562053870L;
	// the remainder simply calls the super class's
	// constructor to get the error info to the user
	public RequiredSearchParamsNotSet() { super(); }
	public RequiredSearchParamsNotSet(String description) { super(description); }
}

/**
 * TODO: General comment
 * @author Nick Magerko
 */
class SearchResultError extends RuntimeException {
	// once again, for purposes of serialization
	private static final long serialVersionUID = 8278881970410698844L;
	// and then make calls to super class
	public SearchResultError() { super(); }
	public SearchResultError(String description) { super(description); }
}