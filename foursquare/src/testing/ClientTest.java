package testing;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import client.FoursquareClient;
import client.FoursquareSearchParams;
import exceptions.RequiredSearchParamsNotSet;
import exceptions.SearchResultError;
import exceptions.SearchResultsEmpty;
import fi.foyt.foursquare.api.FoursquareApiException;

/**
 * Tests the FoursquareClient class, only making
 * contact with Foursquare in order to produce exceptions
 * and check specific functionality
 * @author Nick Magerko
 *
 */
public class ClientTest {
	private static String clientID;
	private static String clientSecret;
	private static String redirectURL;
	private static String falseID;
	private static String falseSecret;
	// the two Maps that will represent the result of FoursqaureSearchParams.getSearchParams()
	private static Map<String, String> validSearchParams;
	private static Map<String, String> invalidSearchParams;
	private FoursquareSearchParams searchParams;
	private FoursquareClient client;

	/**
	 * Sets up unit tests by initializing data fields
	 * with the appropriate values
	 * @throws Exception
	 */
	@BeforeClass
	public static void initialize() throws Exception {
		// the proper credentials 
		clientID = "TH0X4LMHFD3LBHKCR1AOHO1TERVRRXYSO4A0VSJBCF2CK2TE";
		clientSecret = "0GG3QIRT343DAAT2I2DQRQBHJGGQEXKDLETV00F14KZZESBE";
		redirectURL = "http://example.com";
		// the improper credentials
		falseID = "FAUL7YID";
		falseSecret = "FAUL7YS3CR37";
		// initialize the parameters with proper/improper values
		validSearchParams = new HashMap<>();
		validSearchParams.put("near", "Chicago,IL");
		invalidSearchParams = new HashMap<>();
		invalidSearchParams.put("optionalParam1", "value1");
	}
	
	/**
	 * Creates a new mock of the search parameters
	 * required by the client
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		searchParams = Mockito.mock(FoursquareSearchParams.class);
	}

	/**
	 * Checks to be sure that the venue search throws an
	 * error when the near parameter or the ll (lat/lng) parameter
	 * is missing
	 * @throws FoursquareApiException 
	 */
	@Test (expected = RequiredSearchParamsNotSet.class)
	public void missingRequiredParamTest() throws FoursquareApiException {
		client = new FoursquareClient(clientID, clientSecret, redirectURL);
		assertNotNull("Client was initialized", client);
		// pass the parameters that lack those which are considered necessary
		Mockito.when(searchParams.getSearchParams()).thenReturn(invalidSearchParams);
		// cause an error
		client.venueSearch(searchParams);
	}
	
	/**
	 * Checks to be sure that the venue search does not
	 * work with an invalid clientID/clientSecret
	 * @throws FoursquareApiException 
	 */
	@Test (expected = SearchResultError.class)
	public void invalidCredentialsTest() throws FoursquareApiException {
		client = new FoursquareClient(falseID, falseSecret, redirectURL);
		assertNotNull("Client was initialized", client);
		// search for donuts
		validSearchParams.put("query", "donuts");
		// pass proper search parameters
		Mockito.when(searchParams.getSearchParams()).thenReturn(validSearchParams);
		// cause an error, due to a not-200 HTTP response
		client.venueSearch(searchParams);
	}
	
	/**
	 * Checks to make sure that the venue search does not
	 * produce results when nothing was returned to it
	 * @throws FoursquareApiException 
	 */
	@Test (expected = SearchResultsEmpty.class)
	public void impossibleSearchTest() throws FoursquareApiException {
		client = new FoursquareClient(clientID, clientSecret, redirectURL);
		assertNotNull("Client was initialized", client);
		// add the query for something that could not conceivably exist on Foursquare
		validSearchParams.put("query", "A B C D E F G H I J K l M N O P Q R S T U V W X Y Z0");
		// add the parameters
		Mockito.when(searchParams.getSearchParams()).thenReturn(validSearchParams);
		// check to make sure that no results were created, and that the error was thrown
		assertNull("Search failed properly", client.venueSearch(searchParams));
	}
	
	/**
	 * Checks to make sure that the venue search method
	 * produces results when given the proper input
	 * @throws FoursquareApiException 
	 */
	@Test
	public void validSearchTest() throws FoursquareApiException{
		client = new FoursquareClient(clientID, clientSecret, redirectURL);
		assertNotNull("Client was initialized", client);
		// search for donuts around Chicago
		validSearchParams.put("query", "donuts");
		// add the parameters
		Mockito.when(searchParams.getSearchParams()).thenReturn(validSearchParams);
		// check to make sure that results were returned
		assertNotNull("Search was successful", client.venueSearch(searchParams));
	}

}
