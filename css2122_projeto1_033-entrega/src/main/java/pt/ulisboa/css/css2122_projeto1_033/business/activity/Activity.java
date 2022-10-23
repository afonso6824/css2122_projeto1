package pt.ulisboa.css.css2122_projeto1_033.business.activity;

import pt.ulisboa.css.css2122_projeto1_033.business.reservation.Reservation;
import pt.ulisboa.css.css2122_projeto1_033.business.specialty.Specialty;

import javax.persistence.*;


import java.io.Serializable;
import java.util.List;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Inheritance(strategy = TABLE_PER_CLASS)
@NamedQueries({
		@NamedQuery(name = Activity.FIND_BY_DESIGNATION, query = "SELECT a FROM Activity a WHERE a.description = :" + Activity.ACTIVITY_DESIGNATION),
		@NamedQuery(name = Activity.GET_ALL_ACTIVITY, query = "SELECT a FROM Activity a"),
		@NamedQuery(name = Activity.EXISTS_OCCASIONAL_ACTIVITY, query = "SELECT COUNT(1) FROM OccasionalActivity a WHERE a.description = :" + Activity.ACTIVITY_DESIGNATION),
		@NamedQuery(name = Activity.EXISTS_REGULAR_ACTIVITY, query = "SELECT COUNT(1) FROM RegularActivity a WHERE a.description = :" + Activity.ACTIVITY_DESIGNATION),
		@NamedQuery(name = Activity.FIND_BY_SPECIALTY, query = "SELECT a FROM OccasionalActivity a, Specialty s WHERE a.specialty = s AND s.description =:" + Activity.SPECIALTY + " ORDER BY a.price"),
		@NamedQuery(name = Activity.GET_ALL_REGULAR_ACTIVITY, query = "SELECT a FROM RegularActivity a"),
		@NamedQuery(name = Activity.GET_ALL_REGULAR_ACTIVITY_NAMES, query = "SELECT a.description FROM RegularActivity a"),
		@NamedQuery(name = Activity.GET_ALL_OCCASIONAL_ACTIVITY, query = "SELECT a FROM OccasionalActivity a"),
})
public abstract class Activity implements Serializable {
	public static final String GET_ALL_ACTIVITY = "Activity.getAllActivity";
	public static final String GET_ALL_REGULAR_ACTIVITY_NAMES = "Activity.getAllActivityNames";
	public static final String GET_ALL_REGULAR_ACTIVITY = "Activity.getAllRegularActivity";
	public static final String GET_ALL_OCCASIONAL_ACTIVITY = "Activity.getAllOccasionalActivity";
	public static final String FIND_BY_DESIGNATION = "Activity.findByDesignation";
	public static final String EXISTS_OCCASIONAL_ACTIVITY = "OccasionalActivity.existsByDesignation";
	public static final String EXISTS_REGULAR_ACTIVITY = "RegularActivity.existsByDesignation";
	public static final String ACTIVITY_DESIGNATION = "designation";

	private static final long serialVersionUID = 1L;
	// Named query name constants
	public static final String FIND_BY_SPECIALTY = "findBySpecialty";
	public static final String SPECIALTY = "specialty";

	@Id
	private String description;

	@Column(nullable = false)
	private int nSessions;

	@Column(nullable = false)
	private int durationSessions;

	@Column(nullable = false)
	private double price;

	@OneToMany
	@JoinColumn
	private List<Reservation> reservation;


	@ManyToOne
	private Specialty specialty;

	protected Activity() {
		// Constructor needed by JPA.
	}


	public String getDescription() {
		return description;
	}

	public int getNSessions() {
		return nSessions;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public int getDurationSessions() {
		return durationSessions;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	protected Activity(String description, int nSessions, int durationSessions, int price) {
		this.description = description;
		this.nSessions = nSessions;
		this.durationSessions = durationSessions;
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return description;
	}
}
