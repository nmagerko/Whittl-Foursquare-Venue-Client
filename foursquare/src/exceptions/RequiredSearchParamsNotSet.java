package exceptions;

/**
 * A subclass of RuntimeException, alerting the entity utilizing
 * the search capabilities that the parameters required for a search is missing
 * @author Nick Magerko
 */
public class RequiredSearchParamsNotSet extends RuntimeException {
	// evidently, this is necessary if we ever decide
	// to serialize this class
	private static final long serialVersionUID = 3432998049562053870L;
	// the remainder simply calls the super class's
	// constructor to get the error info to the user
	public RequiredSearchParamsNotSet() { super(); }
	public RequiredSearchParamsNotSet(String description) { super(description); }
}
