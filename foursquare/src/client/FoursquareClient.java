package client;

import java.util.Map;
import java.util.Scanner;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Foursquare2Api;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * Allows for a query to be made to Foursquare's venues search,
 * returning the result as a FoursquareResult instance
 * @author Nick Magerko
 *
 */
public class FoursquareClient {
	// the URL which will be accessed to query Foursquare
	private static final String SEARCH_URL = "https://api.foursquare.com/v2/venues/search?v=20140329&oauth_token=";
	// the Foursquare credentials for this client
	private static final String CLIENT_ID = "TH0X4LMHFD3LBHKCR1AOHO1TERVRRXYSO4A0VSJBCF2CK2TE";
	private static final String CLIENT_SECRET = "0GG3QIRT343DAAT2I2DQRQBHJGGQEXKDLETV00F14KZZESBE";
	private static final String REDIRECT_URL = "http://example.com";
	// the code of a successful HTTP response
	private static final int HTTP_OK = 200;
	// an empty token, suggested in place using simply "null" by Scribe
	private static final Token EMPTY_TOKEN = null;
	// the Scribe OAuth service
	private OAuthService service;
	// the token to be provided with the query
	private Token accessToken;
	// a scanner instance for getting the access token
	private Scanner scanner;
	
	/**
	 * Creates a new Foursquare client instance, which requires the 
	 * user to validate the client after initialization
	 */
	public FoursquareClient(){
		// initialize all fields, and ask the user to validate the client
		accessToken = null;
		scanner = new Scanner(System.in);
		// creates an OAuthService instance for working with Foursquare's API
		service = new ServiceBuilder().provider(Foursquare2Api.class)
									  .apiKey(CLIENT_ID)
									  .apiSecret(CLIENT_SECRET)
									  .callback(REDIRECT_URL)
									  .build();
		validateClient();
	}
	
	/**
	 * Allows the client access to the user's personal information
	 * (not necessary for venue searches, yet could be helpful in the future)
	 * Note that this is a public method, since the user may need to 
	 * revalidate if something went wrong
	 */
	public void validateClient(){
		// get Foursquare's authorization url
		String authorizationURL = service.getAuthorizationUrl(EMPTY_TOKEN);
		// prompt the user to get his/her request token, and put it into a "verifier" instance
		System.out.print("Please copy and paste the following URL into your browser:\n"
						 + authorizationURL + "\n\n"
						 + "After you accept, you will be redirected to http://example.com/?code=[XXXX]2#_=_ where [XXXX] is called the request token.\n"
						 + "Paste the request token here: ");
		Verifier verifier = new Verifier(scanner.nextLine());
		System.out.println();
		// Scribe will throw an org.scribe.exceptions.OAuthException if the inputed request token is faulty
		accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
		// if no exception is thrown, the program will continue and the access token has been recieved
	}
	
	/**
	 * Allows for the querying of Foursquare's venue database,
	 * given the proper search parameters
	 * @param params	the parameters to search with
	 * @return	a FoursquareResult instance, representing the businesses that were returned
	 */
	public FoursquareResult venueSearch(FoursquareSearchParams params){
		// if the access token is null (this is very unlikely), ask the user to revalidate
		if (accessToken == null){
			throw new ValidationException("The client must be validated before it will search for venues");
		}
		// if the search parameters are not valid, tell the user why
		if (!params.searchParamsValid()){
			throw new RequiredSearchParamsNotSet(
					"Neither the \"ll\" parameter nor the \"near\" parameter was found in conjunction with the \"query\" parameter.\n" +
					"Consult https://developer.foursquare.com/docs/venues/search for more information.");
		}
		// otherwise, create a new GET request for the venues
		OAuthRequest request = new OAuthRequest(Verb.GET, requestURLBuilder(params.getSearchParams()));
		// sign it
		service.signRequest(accessToken, request);
		// and send it
		Response response = request.send();
		// if there was an issue with the request, alert the user
		if (response.getCode() != HTTP_OK){
			throw new ResponseException("The request returned with HTTP code " + response.getCode() + ".\n"
										+ "The response was: " + response.getBody());
		}
		// otheerwise, return the result
		return new FoursquareResult(response.getBody());
	}
	
	/**
	 * Creates the URL which will be used to make the GET request
	 * @param searchParams	the parameters to add to the URL
	 * @return	the URL to make the request
	 */
	private String requestURLBuilder(Map<String, String> searchParams){
		// add the access token, then the parameters
		String requestURL = SEARCH_URL + accessToken.getToken();
		for (String param : searchParams.keySet()){
			requestURL += "&" + param + "=" + searchParams.get(param);
		}
		return requestURL;
	}
	
	/**
	 * Alerts the user that the client had an issue with validating itself
	 * @author Nick Magerko
	 *
	 */
	class ValidationException extends RuntimeException {
		private static final long serialVersionUID = 6307904175088389249L;
		public ValidationException() { super(); }
		public ValidationException(String description) { super(description); }
	}
	
	/**
	 * Alerts the user that the client found an error in the query response
	 * @author Nick Magerko
	 *
	 */
	class ResponseException extends RuntimeException {
		private static final long serialVersionUID = -3427122580503963151L;
		public ResponseException() { super(); }
		public ResponseException(String description) { super(description); }
	}
	
	/**
	 * Alerts the user that the client found one or more missing parameters
	 * @author Nick Magerko
	 *
	 */
	class RequiredSearchParamsNotSet extends RuntimeException {
		private static final long serialVersionUID = -756232409751936198L;
		public RequiredSearchParamsNotSet() { super(); }
		public RequiredSearchParamsNotSet(String description) { super(description); }
		
	}
	
	/* For quick and dirty testing only */
	public static void main(String[] args){
		FoursquareSearchParams params = new FoursquareSearchParams();
		params.setQuery("Donuts");
		params.setQueryNear("Chicago,IL");
		params.setLimit(10);
		
		FoursquareClient client = new FoursquareClient();
		FoursquareResult results = client.venueSearch(params);
		
		for (int i = 1; i < results.getNumberOfResults(); i++){
			System.out.println(results.getResult(i).getId());
			
		}
	}
}
