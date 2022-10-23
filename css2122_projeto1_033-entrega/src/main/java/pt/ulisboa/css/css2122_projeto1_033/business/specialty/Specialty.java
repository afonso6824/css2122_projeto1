package pt.ulisboa.css.css2122_projeto1_033.business.specialty;

import pt.ulisboa.css.css2122_projeto1_033.business.activity.Activity;
import pt.ulisboa.css.css2122_projeto1_033.business.certification.Certification;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({
		@NamedQuery(name = Specialty.FIND_BY_DESIGNATION, query = "SELECT s FROM Specialty s WHERE s.description = :" + Specialty.SPECIALTY_DESIGNATION),
		@NamedQuery(name = Specialty.GET_ALL_SPECIALTY, query = "SELECT s FROM Specialty s"),
		@NamedQuery(name = Specialty.GET_ALL_SPECIALTY_NAMES, query = "SELECT s.description FROM Specialty s"),
})
public class Specialty implements Serializable {
	public static final String GET_ALL_SPECIALTY = "Specialty.getAllSpecialty";
	public static final String GET_ALL_SPECIALTY_NAMES = "Specialty.getAllSpecialtyNames";
	public static final String FIND_BY_DESIGNATION = "Specialty.findByDesignation";
	public static final String SPECIALTY_DESIGNATION = "designation";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false, unique = true)
	private String description;

	@Column(nullable = false)
	private int minimumDuration;

	@Enumerated(EnumType.STRING)
	private Certification certification;

	@OneToMany()
	private List<Activity> activities;

	public Specialty() {
		// Constructor needed by JPA.
	}

	public Certification getCertification() {
		return certification;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMinimumDuration() {
		return minimumDuration;
	}

	public void setMinimumDuration(int minimumDuration) {
		this.minimumDuration = minimumDuration;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void addActivity(Activity activity) {
		activities.add(activity);
	}
}