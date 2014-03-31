package testing;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import client.FoursquareResult;

/**
 * Tests the functionality of the FoursquareResult
 * class.
 * @author Nick Magerko
 *
 */
public class ResultsTest {
	// the number of Businesses that should be stored from the "properQueryResponse"
	private static final int NUMBER_OF_BUSINESSSES_PROPER_QUERY = 1;
	// the number of Businesses that should be stored from either of the improper responses
	private static final int NUMBER_OF_BUSINESSES_IMPROPER_QUERY = 0;
	// response taken from the body of an actual HTTP GET with params
	// ll=40.7,-74&&limit=1
	private static String properQueryResponse;
	// a modification of the above query response, containing incorrect headers
	private static String improperHeadersQueryResponse;
	// a modification of the proper response, containing improper formatting
	private static String improperFormatQueryResponse;
	private FoursquareResult results;
	
	@BeforeClass
	public static void initialize(){
		properQueryResponse = "{\"meta\":{\"code\":200},"
							  + "\"notifications\":[{\"type\":\"notificationTray\",\"item\":{\"unreadCount\":0}}],"
							  + "\"response\":{\"venues\":[{\"id\":\"525e022711d2f114af4e7a49\",\"name\":\"pt. lingga indoteknik utama\",\"contact\":{\"phone\":\"(021) 88881155\",\"formattedPhone\":\"(021) 88881155\"},"
							  + "\"location\":{\"address\":\"ruko harapan indah blok EL No. 25 Pejuang Medan satria\",\"crossStreet\":\"btwn yokabento & alfamart\","
							  + "\"lat\":40.7,\"lng\":-74.0,\"distance\":0,\"postalCode\":\"17131\",\"cc\":\"US\",\"city\":\"Bekasi\",\"state\":\"Jawa Barat\",\"country\":\"United States\"},"
							  + "\"categories\":[{\"id\":\"4bf58dd8d48988d127941735\",\"name\":\"Conference Room\",\"pluralName\":\"Conference Rooms\",\"shortName\":\"Conference room\",\"icon\":{\"prefix\":\"https:\\/\\/ss1.4sqi.net\\/img\\/categories_v2\\/building\\/office_conferenceroom_\",\"suffix\":\".png\"},"
							  + "\"primary\":true}],\"verified\":false,\"stats\":{\"checkinsCount\":2,\"usersCount\":1,\"tipCount\":0},\"specials\":{\"count\":0,\"items\":[]},"
							  + "\"hereNow\":{\"count\":0,\"groups\":[]},\"referralId\":\"v-1396274937\"}],\"neighborhoods\":[],\"confident\":true}}";
		// to change the headers, simply use String.replace()
		improperHeadersQueryResponse = properQueryResponse.replace("meta", "fakeMeta").replace("notifications", "fakeNotifications").replace("response", "fakeResponse");
		// to cause formatting issues, remove all '}' characters
		improperFormatQueryResponse = properQueryResponse.replace('}', ' ');		
	}
	
	// A setup method is not utilized, since differing
	// constructors may be used in each test

	@Test
	public void emptyResponseConstructionTest() {
		results = new FoursquareResult("");
		assertNotNull("Result object initialized successfully", results);
		assertTrue("Result object does not contain any results for an empty response", results.getNumberOfResults() == NUMBER_OF_BUSINESSES_IMPROPER_QUERY);
		assertNull("Attempting to access non-existent result returns null, as expected", results.getResult(1));
	}
	
	@Test
	public void improperHeadersTest(){
		results = new FoursquareResult(improperHeadersQueryResponse);
		assertNotNull("Result object initialized successfully", results);
		assertTrue("Result object does not contain any results for a response with improper headers", results.getNumberOfResults() == NUMBER_OF_BUSINESSES_IMPROPER_QUERY);
		assertNull("Attempting to access non-existent result returns null, as expected", results.getResult(1));
	}
	
	@Test
	public void improperFormatTest(){
		results = new FoursquareResult(improperFormatQueryResponse);
		assertNotNull("Result object initialized successfully", results);
		assertTrue("Result object does not contain any results for a response with an improper format", results.getNumberOfResults() == NUMBER_OF_BUSINESSES_IMPROPER_QUERY);
		assertNull("Attempting to access non-existent result returns null, as expected", results.getResult(1));
	}
	
	@Test
	public void properResponseTest(){
		results = new FoursquareResult(properQueryResponse);
		assertNotNull("Result object initialized successfully", results);
		assertTrue("Result object contains only one result for a response with only one venue", results.getNumberOfResults() == NUMBER_OF_BUSINESSSES_PROPER_QUERY);
		assertNotNull("Attempting to access the only result in the response returns successfully", results.getResult(results.getNumberOfResults()));
		assertNull("Accessing a result beyond that which exists returns null", results.getResult(results.getNumberOfResults()+1));
	}

}
