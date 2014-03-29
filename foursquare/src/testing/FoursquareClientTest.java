package testing;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import client.FoursquareClient;
import client.FoursquareResult;
import client.FoursquareSearchParams;
import fi.foyt.foursquare.api.FoursquareApiException;

public class FoursquareClientTest {
	private String clientID;
	private String clientSecret;
	private String redirectURL;
	private FoursquareSearchParams searchParams;

	@Before
	public void setUp() throws Exception {
		clientID = "TH0X4LMHFD3LBHKCR1AOHO1TERVRRXYSO4A0VSJBCF2CK2TE";
		clientSecret = "0GG3QIRT343DAAT2I2DQRQBHJGGQEXKDLETV00F14KZZESBE";
		redirectURL = "http://example.com";
		
		searchParams = new FoursquareSearchParams("venue");
		//searchParams.addParam("near", "Chicago, IL");
		searchParams.addParam("ll", "41.895513,-87.636626");
		searchParams.addParam("query", "Starbucks");
		searchParams.addParam("intent", "browse");
		searchParams.addParam("limit", "15");
		
	}

	@Test
	public void test() throws FoursquareApiException {
		FoursquareClient client = new FoursquareClient(clientID, clientSecret, redirectURL);
		FoursquareResult results = client.venueSearch(searchParams);
		for (String result : results.getResults()){
			System.out.println(result);
		}
		
		fail("Not yet implemented");
	}

}
