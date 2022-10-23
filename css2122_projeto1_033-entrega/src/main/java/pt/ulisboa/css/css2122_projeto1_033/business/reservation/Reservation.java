package pt.ulisboa.css.css2122_projeto1_033.business.reservation;

import pt.ulisboa.css.css2122_projeto1_033.business.client.Client;
import pt.ulisboa.css.css2122_projeto1_033.business.activity.Activity;
import pt.ulisboa.css.css2122_projeto1_033.business.payment_details.PaymentDetails;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Reservation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	private int nMonths;

	@Column(nullable = false)
	private double totalPrice;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ReservationStatus status;

	@Embedded
	private PaymentDetails paymentDetails;

	@ManyToOne
	@JoinColumn
	private Activity activity;

	@ManyToOne
	@JoinColumn
	private Client client;

	public Reservation() {
		// Constructor needed by JPA.
	}

	public Reservation(int months, Client client, Activity activity) {
		this.nMonths = months;
		this.status = ReservationStatus.UNPAID;
		this.totalPrice = nMonths * activity.getPrice();
		this.paymentDetails = new PaymentDetails(totalPrice);
		this.client = client;
		this.activity = activity;
	}

	public Reservation(Client client, Activity activity) {
		this.client = client;
		this.status = ReservationStatus.UNPAID;
		this.paymentDetails = new PaymentDetails(activity.getPrice());
		this.activity = activity;
	}

	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}
}