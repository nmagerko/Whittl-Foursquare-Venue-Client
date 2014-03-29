package exceptions;

/**
 * A subclass of RuntimeException, alerting the entity utilizing
 * the search capabilities that the search results were empty
 * @author Nick Magerko
 */
public class SearchResultError extends RuntimeException {
	// once again, for purposes of serialization
	private static final long serialVersionUID = 8278881970410698844L;
	// and then make calls to super class
	public SearchResultError() { super(); }
	public SearchResultError(String description) { super(description); }
}
