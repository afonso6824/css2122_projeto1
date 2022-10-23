package pt.ulisboa.css.css2122_projeto1_033.facade.exceptions;

public class InstructorNotValidException extends ApplicationException {


	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -6775358948342044435L;

	/**
	 * Creates an exception given an error message
	 * @param message The error message
	 */
	public InstructorNotValidException(String message) {
		super(message);
	}

	/**
	 * Creates an exception wrapping a lower level exception
	 * @param message The error message
	 * @param e		  The wrapped exception
	 */
	public InstructorNotValidException(String message, Exception e) {
		super(message, e);
	}

}
