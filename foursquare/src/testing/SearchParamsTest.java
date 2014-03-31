package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import client.FoursquareSearchParams;

/**
 * Tests the functionality of the FoursquareSearchParams
 * class.
 * Note: this unit test will not test simple getters (such
 * as the function that gets the available search params)
 * but will test those that work with getting/setting
 * the search params
 * @author Nick Magerko
 *
 */
public class SearchParamsTest {
	private FoursquareSearchParams searchParams;

	/**
	 * Creates a new instance of the search parameters
	 * for use in each test
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		searchParams = new FoursquareSearchParams();
		assertNotNull("Search parameters object re-initialized", searchParams);
	}

	@Test
	public void latLngTest() {
		assertTrue("Latitude and longitude parameter begins without value", searchParams.getLatLongParam().equals(""));
		searchParams.setLatLong(91.0, 181.0);
		assertTrue("Maximum correction works", searchParams.getLatLongParam().equals("90.0,180.0"));
		searchParams.setLatLong(-91.0, -181.0);
		assertTrue("Minimum correction works", searchParams.getLatLongParam().equals("-90.0,-180.0"));
		searchParams.setLatLong(41.895513, -87.636626);
		assertTrue("Normal lat/lng setting works", searchParams.getLatLongParam().equals("41.895513,-87.636626"));
	}
	
	@Test
	public void queryNearTest() {
		assertTrue("Query-near parameter begins without value", searchParams.getNearParam().equals(""));
		searchParams.setQueryNear("Chicago, IL");
		assertTrue("Query-near parameter setting works", searchParams.getNearParam().equals("Chicago, IL"));
	}
	
	@Test
	public void queryTest() {
		assertTrue("Query parameter begins without a value", searchParams.getQuery().equals(""));
		searchParams.setQuery("Whittl");
		assertTrue("Query parameter setting works", searchParams.getQuery().equals("Whittl"));
	}
	
	@Test
	public void limitTest() {
		assertTrue("Limit parameter begins without a value", searchParams.getLimit() == 0);
		searchParams.setLimit(51);
		assertTrue("Maximum correction works", searchParams.getLimit() == 50);
		searchParams.setLimit(0);
		assertTrue("Minimum correction works", searchParams.getLimit() == 1);
		searchParams.setLimit(10);
		assertTrue("Normal limit setting works", searchParams.getLimit() == 10);
	}
	
	@Test
	public void intentTest() {
		assertTrue("Intent parameter begins without a value", searchParams.getIntent().equals(""));
		searchParams.setIntent("invalid");
		assertTrue("Invalid search intent correction works", searchParams.getIntent().equals("checkin"));
		searchParams.setIntent("checkin");
		assertTrue("Checkin search intent set successfully", searchParams.getIntent().equals("checkin"));
		searchParams.setIntent("browse");
		assertTrue("Browse search intent set successfully", searchParams.getIntent().equals("browse"));
		searchParams.setIntent("global");
		assertTrue("Global search intent set successfully", searchParams.getIntent().equals("global"));
		searchParams.setIntent("match");
		assertTrue("Match search intent set successfully", searchParams.getIntent().equals("match"));
	}
	
	@Test
	public void radiusTest() {
		assertTrue("Radius parameter begins without a value", searchParams.getRadius() == 0);
		searchParams.setRadius(500000);
		assertTrue("Radius maximum correction works corrrectly", searchParams.getRadius() == 100000);
		searchParams.setRadius(-1);
		assertTrue("Radius minimum correction works correctly", searchParams.getRadius() == 1);
		searchParams.setRadius(800);
		assertTrue("Normal radius setting works", searchParams.getRadius() == 800);
	}
	
	@Test
	public void swTest() {
		assertTrue("SW parameter begins without value", searchParams.getSW().equals(""));
		searchParams.setSW(91.0, 181.0);
		assertTrue("Maximum correction works", searchParams.getSW().equals("90.0,180.0"));
		searchParams.setSW(-91.0, -181.0);
		assertTrue("Minimum correction works", searchParams.getSW().equals("-90.0,-180.0"));
		searchParams.setSW(41.895513, -87.636626);
		assertTrue("Normal SW setting works", searchParams.getSW().equals("41.895513,-87.636626"));
	}
	
	@Test
	public void neTest(){
		assertTrue("NE parameter begins without value", searchParams.getNE().equals(""));
		searchParams.setNE(91.0, 181.0);
		assertTrue("Maximum correction works", searchParams.getNE().equals("90.0,180.0"));
		searchParams.setNE(-91.0, -181.0);
		assertTrue("Minimum correction works", searchParams.getNE().equals("-90.0,-180.0"));
		searchParams.setNE(41.895513, -87.636626);
		assertTrue("Normal lat/lng setting works", searchParams.getNE().equals("41.895513,-87.636626"));
	}
	
	@Test
	public void categoryIdTest(){
		assertTrue("categoryId parameter begins without value", searchParams.getCategoryIds().equals(""));
		searchParams.setCategoryIds(new String[]{"asad13242l", "btbe24353m"});
		assertTrue("categoryId set successfully", searchParams.getCategoryIds().equals("asad13242l,btbe24353m"));
	}
	
	@Test
	public void  URLTest(){
		assertTrue("URL parameter begins without a value", searchParams.getURL().equals(""));
		searchParams.setURL("whittl.com");
		assertTrue("URL parameter without http:// or https:// fixed", searchParams.getURL().equals("http://whittl.com"));
		searchParams.setURL("http://whittl.com");
		assertTrue("Normal URL parameter setting works for http:// prefix", searchParams.getURL().equals("http://whittl.com"));
		searchParams.setURL("https://whittl.com");
		assertTrue("Normal URL parameter setting works for https:// prefix", searchParams.getURL().equals("https://whittl.com"));
	}
}
