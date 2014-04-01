package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.FoursquareClient;
import client.FoursquareResult;
import client.FoursquareSearchParams;

/**
 * It is my understanding that an integration
 * test combines one or more of the components
 * of the system, checking to make sure that all
 * dependent components work well together. I have
 * attempted to create such a test with this class, 
 * excluding the tests that have already been run in
 * the other three testing classes (for example, if I 
 * know that improper parameters are handled correctly,
 * I will not check this again in the integration test).
 * Note that this class requires user input - I cannot
 * mock System.in so as to provide the access token
 * @author Nick Magerko
 *
 */
public class IntegratedTest {
	private FoursquareSearchParams searchParams;
	private FoursquareClient client;
	private FoursquareResult results;
	
	/**
	 * Provides the tester with the reason as to why they
	 * will see prompts coming up on his/her console
	 */
	@BeforeClass
	public static void printExplanation(){
		System.out.println("===== User responses required =====");
		System.out.println("You may be required submit a request token to the program several times \n"
							+ "during testing, since a new client is created with each test. The token \n"
							+ "should stay valid throughout the tests that follow. Simply respond to \n"
							+ "the on-screen prompts.\n");
	}

	/**
	 * Initializes everything except for the results,
	 * which need to get their constructor parameters
	 * from the client
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		searchParams = new FoursquareSearchParams();
		client = new FoursquareClient();
		assertNotNull("Search parameters initialized", searchParams);
		assertNotNull("Client initialized", client);
	}

	@Test
	public void successWithLatLngTest() {
		// search the exact location around Whittl for... Whittl
		// note that "near" cannot be tested - Whittl is not within the
		// top 50 venue results around Chicago (at least through the venue search endpoint)
		searchParams.setQuery("Whittl");
		searchParams.setLatLong(41.895513, -87.636626);
		searchParams.setLimit(1);
		searchParams.setRadius(50);
		
		// initiate the search, and expect that the results are initialized
		results = client.venueSearch(searchParams);
		assertNotNull("Results received", results);
		
		checkWhittlInfo(results);
	}
	
	@Test
	public void noResultsTest() {
		// cause one of the only errors that can get through this program - a geocodable error
		// - by setting the location to somewhere that does not exist
		searchParams.setQuery("Whittl");
		searchParams.setQueryNear("Waffles, CN");
		searchParams.setLimit(1);
		searchParams.setRadius(50);
		
		// initiate the search, and expect that the results are initialized
		results = client.venueSearch(searchParams);
		assertNotNull("Results received", results);
		
		// check to be sure that no results were returned, if no error was thrown
		assertTrue("400 error handled approprately", results.getNumberOfResults() == 0);
		
	}
	
	/**
	 * Checks the first result in the results to see if it contains the
	 * correct information. Note that the intention here is not to test
	 * every single getter/setter in the models	
	 * @param result	the search results
	 */
	private void checkWhittlInfo(FoursquareResult result){
		// attempt to find Whittl in the results, and fail if it is not found
		for (int resultNumber = 1; resultNumber <= results.getNumberOfResults(); resultNumber++){
			if (results.getResult(1).getId().equals("52041709498e0e382ac7fb49")){
				assertTrue("Name correct", results.getResult(1).getName().toLowerCase().equals("whittl"));
				assertTrue("Address correct", results.getResult(1).getLocation().getAddress().toLowerCase().contains("superior st"));
				assertTrue("Phone number correct", results.getResult(1).getContact().getFormattedPhone().equals("(312) 857-4250"));
				// Whittl should not have a menu
				assertNull("No menu found, as expected", results.getResult(1).getMenu());
				return;
			}
		}
		fail("Whittl was not found in the results");		
	}

}
