package testing;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
	 * Adds five parameters, named 0, 1, 2, 3, 4, and five value
	 * pairs, named 0, 1, 2, 3, 4, respectively, to the provided
	 * FoursquareSearchParams instance
	 * @param searchParams	the instance to add the parameters to
	 */
	private void addFiveParams(FoursquareSearchParams searchParams){
		for (int index = 0; index < 5; index++){
			params.addParam(index + "", index + "");
		}
	}
	
	/**
	 * Modifies the five parameters added by addFiveParams() by adding
	 * one to the value that was previously paired with its param
	 * @param searchParams	the instance to modify the parameters in
	 */
	private void modifyFiveParams(FoursquareSearchParams searchParams){
		for (int index = 0; index < 5; index++){
			params.modifyParam(index + "", (index + 1) + "");
		}
	}
	
	/**
	 * Removes the five parameters added by addFiveParams()
	 * @param searchParams	the instance to remove the parameters from
	 */
	private void removeFiveParams(FoursquareSearchParams searchParams){
		for (int index = 0; index < 5; index++){
			params.removeParam(index + "");
		}
	}

	/**
	 * Tests the addition of a parameter to the search
	 * parameters by checking to see if addParam (which
	 * is called before every test) inserted the proper
	 * values correctly. Then, check to be sure that inserting
	 * multiple values works as intended, and finally attempt
	 * to add a value that already exists
	 */
	@Test (expected = IllegalArgumentException.class)
	public void addParamTest() {
		assertTrue("Parameter added successfully", 
				   		params.toString().contains("\"" + initialParameter + "\" : " + "\"" + initialValue + "\""));
		addFiveParams(params);
		// check to make sure that the five parameters above and the initial parameter exist
		assertTrue("Multiple values added successfully", params.getSearchParams().size() == 6);
		// cause an error
		params.addParam(initialParameter, initialValue);
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
		addFiveParams(params);
		modifyFiveParams(params);
		// check to make sure that the parameters were modified correctly, by testing
		// one at random for correctness
		int randomIndexToTest = (int)(Math.random() * 6);
		assertTrue("Multiple parameters modified successfully", 
						params.getSearchParams().get(randomIndexToTest + "").equals((randomIndexToTest + 1) + ""));
		// cause an error
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
		// add and remove five parameters
		addFiveParams(params);
		removeFiveParams(params);
		// check to make sure that all parameters are gone
		assertTrue("Multiple parameters removed successfully", params.getSearchParams().size() == 0);
		// cause an error
		params.removeParam(initialParameter);
	}

}
