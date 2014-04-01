package testing;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import client.FoursquareClient;
import client.FoursquareResult;
import client.FoursquareSearchParams;

/**
 * Tests the functionality of the FoursquareClient
 * class (with the Scribe framework - I was unable to
 * come up with a mocking technique). A successful search
 * test is excluded, since I believe tests that 
 * contact a database should be part of the integration
 * tests. FoursquareResult is not mocked, since it is my
 * belief that keeping it in the mix ensures that the assertion
 * checking is correct
 *
 */
public class ClientTest {
	private FoursquareSearchParams mockedSearchParams;
	private Map<String, String> fakeSearchParams;
	private FoursquareClient client;
	
	/**
	 *  Uses a library called "System Rules"
	 *  (http://www.stefan-birkner.de/system-rules/#TextFromStandardInputStream)
	 *  to aid in automating input (where possible)
	 */
	@Rule
	public final TextFromStandardInputStream fakeSystemIn = TextFromStandardInputStream.emptyStandardInputStream();
	
	/**
	 * Creates a new instance of the client before each test.
	 * The neither the "searchParams" nor the "results" are
	 * reinitialized, since they differently in some methods.
	 * Additionally, recreate the fake input system
	 * 
	 */
	@Before
	public void setUp() throws Exception {
		client = new FoursquareClient();
	}
	
	@Test
	public void scribeInitializationTest() {
		// check that the FoursquareClient (reinitialized before every test) is not null
		assertNotNull("API ID, secret and callback URL passed to Scribe successfully", client);
	}
	
	@Test
	public void improperParamsTest(){
		mockedSearchParams = mock(FoursquareSearchParams.class);
		// create a Map to imitate the parameters that would be returned by an actual 
		// parameters object, without the necessary values
		fakeSearchParams = new HashMap<>();
		when(mockedSearchParams.getSearchParams()).thenReturn(fakeSearchParams);
		when(mockedSearchParams.searchParamsValid()).thenReturn(false);
		FoursquareResult venues;
		venues = client.venueSearch(mockedSearchParams);
		assertNull("The client correctly handled only the query parameter", venues.getResult(1));
	}
	
	@Test
	public void improperClientValidationTest() {
		mockedSearchParams = mock(FoursquareSearchParams.class);
		// create a Map to imitate the parameters that would be returned by an actual 
		// parameters object, with the necessary values
		fakeSearchParams = new HashMap<>();
		when(mockedSearchParams.getSearchParams()).thenReturn(fakeSearchParams);
		when(mockedSearchParams.searchParamsValid()).thenReturn(true);
		FoursquareResult venues;
		// provide a letter for each of the five authentication trials
		fakeSystemIn.provideText("T\nO\nK\nE\nN");
		// reset
		venues = client.venueSearch(mockedSearchParams);
		// get the first (zeroth) result, and check to make sure that it does not exist
		assertNull("The client correctly handled a lack of validation", venues.getResult(1));
		System.out.println();
	}
}
