package pt.ulisboa.css.css2122_projeto1_033.business.session;

import java.io.Serializable;
import javax.persistence.*;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;


/**
 * Entity implementation class for Entity: Session
 */
@Entity
@Inheritance(strategy = TABLE_PER_CLASS)
public abstract class Session implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;


	public Session() {
		// Constructor needed by JPA.
	}





}
