package pt.ulisboa.css.css2122_projeto1_033.facade.exceptions;

public class InvalidDateFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDateFormatException(String message) {
		super(message);
	}
	
	public InvalidDateFormatException(String message, Exception e) {
		super(message, e);
	}

}
