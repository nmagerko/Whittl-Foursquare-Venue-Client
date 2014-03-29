package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.FoursquareSearchParams;

/**
 * Tests the functionality of the FoursquareSearchParams
 * class
 * @author Nick Magerko
 */
public class SearchParamsTest {
	// the type of search to provide to params
	private static String searchType;
	// the initial parameter type to provide to params
	private static String initialParameter;
	// a non-existent parameter to cause errors
	private static String nonexistentParameter;
	// a value to be paired with the initial parameter
	private static String initialValue;
	// a value that will replace the initalValue
	private static String modifiedValue;
	// an instance of the class to test
	private FoursquareSearchParams params;
	
	/**
	 * Sets up the unit tests by assigning values to the
	 * class fields
	 * @throws Exception
	 */
	@BeforeClass
	public static void initialize() throws Exception {
		// assign values to fields
		searchType = "test";
		initialParameter = "parameter";
		nonexistentParameter = "falseParameter";
		initialValue = "value";
		modifiedValue = "value2";
	}
	
	/**
	 * Sets up each unit test by recreating the conditions
	 * of a FoursqaureSearchParams instance with one parameter
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		// initialize the instance
		params = new FoursquareSearchParams(searchType);
		// check that it was initialized properly
		assertNotNull("FoursquareSearchParams object initialized", params);
		// add the initial parameters
		params.addParam(initialParameter, initialValue);
	}

	/**
	 * Tests the addition of a parameter to the search
	 * parameters by checking to see if addParam (which
	 * is called before every test) inserted the proper
	 * values correctly
	 */
	@Test
	public void addParamTest() {
		assertTrue("Parameter added successfully", 
				   		params.toString().contains("\"" + initialParameter + "\" : " + "\"" + initialValue + "\""));
	}
	
	/**
	 * Tests the modification of a parameter by modifying
	 * an existing parameter and checking for the new value, 
	 * and then causes an error by attempting to change a
	 * non-existent parameter
	 */
	@Test (expected = IllegalArgumentException.class)
	public void modifyParamTest() {
		params.modifyParam(initialParameter, modifiedValue);
		assertTrue("Parameter modified successfully",
						params.toString().contains("\"" + initialParameter + "\" : " + "\"" + modifiedValue + "\""));
		params.modifyParam(nonexistentParameter, modifiedValue);
	}
	
	/**
	 * Tests the removal of a parameter by removing an
	 * existing parameter and checking to see that it is
	 * absent, and then causes an error by attempting to
	 * remove a non-existent parameter
	 */
	@Test (expected = IllegalArgumentException.class)
	public void removeParamTest() {
		params.removeParam(initialParameter);
		assertTrue("Parameter removed successfully", 
		   		!params.toString().contains("\"" + initialParameter + "\" : " + "\"" + initialValue + "\""));
		System.out.println(params);
		params.removeParam(initialParameter);
	}

}
