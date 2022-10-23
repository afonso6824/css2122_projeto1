package pt.ulisboa.css.css2122_projeto1_033.facade.exceptions;

public class RemainingTimeInstructorNotValidException extends ApplicationException {


	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -6775358948342044435L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public RemainingTimeInstructorNotValidException(String message) {
		super(message);
	}

	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public RemainingTimeInstructorNotValidException(String message, Exception e) {
		super(message, e);
	}

}
