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
	 * Allows for the querying of Foursquare's venue database,
	 * given the proper search parameters
	 * @param params	the parameters to search with
	 * @return	a FoursquareResult instance, representing the businesses that were returned
	 */
	public FoursquareResult venueSearch(FoursquareSearchParams params){
		// how many time the client has failed to validate (this helps to make the output prettier)
		int failureCount = 0;
		// if the access token is null (this is very unlikely), ask the user to revalidate
		// there is really no way around asking for validation again
		if (accessToken == null){
			while (accessToken == null){
				if (failureCount == 0){	
					// if this is the first failure, print the validation requirement, and increment failureCount (this only needs to be done once)
					System.err.println("The client must be validated before it will search for venues. Please revalidate. \n");
					failureCount++;
				}
				validateClient();
			}
		}
		// if the search parameters are not valid, tell the user why
		// do not automatically set these parameters, or the user may believe that all is well, even if an error is printed
		if (!params.searchParamsValid()){
			System.err.println(
					"Warning: Neither the \"ll\" parameter nor the \"near\" parameter was found in conjunction with the \"query\" parameter.\n"
					+ "Consult https://developer.foursquare.com/docs/venues/search for more information. Note that the FoursquareResult \n" 
					+ "will contain no entries");
			return new FoursquareResult("");
		}
		// otherwise, create a new GET request for the venues
		OAuthRequest request = new OAuthRequest(Verb.GET, requestURLBuilder(params.getSearchParams()));
		// sign it
		service.signRequest(accessToken, request);
		// and send it
		Response response = request.send();
		// if there was an issue with the request, alert the user
		// return a FoursquareResult initialized with an empty String (the class will take care of this case)
		if (response.getCode() != HTTP_OK){
			System.err.println("Warning: The request returned with HTTP code " + response.getCode() + ".\n"
								+ "The response was: " + response.getBody() + "\n"
								+ "Note that the FoursquareResult will contain no entries.");
			return new FoursquareResult("");
		}
		// otherwise, return the result with the full body
		return new FoursquareResult(response.getBody());
	}
	
	/**
	 * Allows the client access to the user's personal information
	 * (not necessary for venue searches, yet could be helpful in the future)
	 */
	private void validateClient(){
		// get Foursquare's authorization url
		String authorizationURL = service.getAuthorizationUrl(EMPTY_TOKEN);
		// prompt the user to get his/her request token, and put it into a "verifier" instance
		System.out.print("Please copy and paste the following URL into your browser:\n"
						 + authorizationURL + "\n\n"
						 + "After you accept, you will be redirected to http://example.com/?code=[XXXX]2#_=_ where [XXXX] is called the request token.\n"
						 + "Paste the request token here: ");
		try {
			Verifier verifier = new Verifier(scanner.nextLine());
			System.out.println();
			// Scribe will throw an org.scribe.exceptions.OAuthException if the inputed request token is faulty
			accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
			// if no exception is thrown, the program will continue and the access token has been received
		}
		catch (Exception exception){
			// otherwise, tell the user that they will have to sign in when they search
			System.err.println("Warning: An error occurred during validation (check your request token?). Any searches requested will require a successful validation, \n"
								+ "which may be performed at a later time. \n");
		}
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
	
	/* For quick testing only */
	public static void main(String[] args){
		
		FoursquareSearchParams params = new FoursquareSearchParams();
		params.setQuery("Whittl");
		params.setLatLong(41.895513, -87.636626);
		params.setLimit(10);
		
		FoursquareClient client = new FoursquareClient();
		FoursquareResult results = client.venueSearch(params);
		
		if (results.getNumberOfResults() > 0){
			for (int i = 1; i <= results.getNumberOfResults(); i++){
				// check to make sure the result is not null!!
				System.out.println(i + " " + results.getResult(i).getId() + " - " + results.getResult(i).getName());
				
			}
		}
		else { System.out.println("No results returned"); }
	}
}
