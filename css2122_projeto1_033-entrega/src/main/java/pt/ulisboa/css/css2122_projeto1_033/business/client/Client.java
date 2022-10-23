package pt.ulisboa.css.css2122_projeto1_033.business.client;

import pt.ulisboa.css.css2122_projeto1_033.business.activity.Activity;
import pt.ulisboa.css.css2122_projeto1_033.business.reservation.Reservation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Client
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = Client.FIND_BY_EMAIL, query = "SELECT a FROM Client a WHERE a.email = :" + Client.CLIENT_EMAIL),

})
public class Client implements Serializable {

	public static final String FIND_BY_EMAIL = "Client.findByDesignation";
	public static final String CLIENT_EMAIL = "email";
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;

	private String email;
	@OneToMany
	private final List<Reservation> clientReservations = new ArrayList<>();

	public Client() {
		super();
	}

	public Client(String email) {
		this.email = email;
	}

	/**
	 * @return the client's email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Get the reservation of a given id
	 * @param id the id of a reservation
	 * @return the reservation of the given id
	 */
	public Reservation getReservation(int id) {
		return this.clientReservations.get(id);
	}

	public void addReservation(Reservation reservation) {
		clientReservations.add(reservation);
	}
}
