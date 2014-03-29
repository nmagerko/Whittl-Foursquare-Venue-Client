package exceptions;

/**
 * Indicates that the results returned by the 
 * Foursquare query were empty (no venues, for example)
 * @author Nick Magerko
 */
public class SearchResultsEmpty extends RuntimeException {
	// for purposes of serialization
	private static final long serialVersionUID = 5534076970541529744L;
	// initialize with the superclass
	public SearchResultsEmpty() { super(); }
	public SearchResultsEmpty(String description) { super(description); }	
}
