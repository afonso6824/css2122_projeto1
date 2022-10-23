package pt.ulisboa.css.css2122_projeto1_033.business.payment_details;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import pt.ulisboa.css.css2122_projeto1_033.business.utils.DateMock;
import pt.ulisboa.css.css2122_projeto1_033.business.utils.PaymentMock;

/**
 * Entity implementation class for Entity: PaymentDetails
 * A class representing payment details of a single reservation.
 */
@Embeddable
public class PaymentDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String entity;

	@Column(nullable = false)
	private String reference;

	@Column(nullable = false)
	private double value;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date expirationDate;


	/**
	 * Constructor needed by JPA.
	 */
	public PaymentDetails() {
	}

	public PaymentDetails(double value) {
		this.value = value;
		this.reference = PaymentMock.generateMockReference();
		this.entity = PaymentMock.generateMockEntity();
		this.expirationDate = DateMock.increaseMinToData(24 * 60, DateMock.getCurrentTime());
	}

	@Override
	public String toString() {
		return "Entidade ='" + entity + '\n' +
				"Referencia='" + reference + '\n' +
				"Valor=" + value + '\n' +
				"O pagamento deve ser recebido até " + DateMock.printDate(expirationDate);
	}
}
