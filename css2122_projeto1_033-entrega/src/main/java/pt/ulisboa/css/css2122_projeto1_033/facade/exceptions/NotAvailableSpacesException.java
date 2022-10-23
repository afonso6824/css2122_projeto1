package pt.ulisboa.css.css2122_projeto1_033.facade.exceptions;

public class NotAvailableSpacesException extends Exception {
	
	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public NotAvailableSpacesException(String message, Exception e) {
		super (message, e);
	}
	
	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 */
	public NotAvailableSpacesException(String message) {
		super (message);
	}
}
