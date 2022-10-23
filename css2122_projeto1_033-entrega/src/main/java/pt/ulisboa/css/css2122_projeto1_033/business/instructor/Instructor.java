package pt.ulisboa.css.css2122_projeto1_033.business.instructor;

import pt.ulisboa.css.css2122_projeto1_033.business.activity.OccasionalActivity;
import pt.ulisboa.css.css2122_projeto1_033.business.certification.Certification;
import pt.ulisboa.css.css2122_projeto1_033.business.session.OccasionalSession;
import pt.ulisboa.css.css2122_projeto1_033.business.session.RegularSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Instructor
 */
@Entity(name = "INSTRUCTOR")
@NamedQuery(name = Instructor.FIND_BY_CERTIFICATE, query = "SELECT i FROM INSTRUCTOR i JOIN i.certificates c WHERE c =:" + Instructor.CERTIFICATE)
public class Instructor implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String FIND_BY_CERTIFICATE = "findByCerticate";
	public static final String CERTIFICATE = "certificate";

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String name;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<Certification> certificates = new ArrayList<>();

	@OneToMany
	private List<RegularSession> regularSessions = new ArrayList<>();

	@OneToMany
	private List<OccasionalSession> occasionalSessions = new ArrayList<>();

	public Instructor() {
		super();
	}


	public List<Certification> getCertificates() {
		return certificates;
	}

	public List<RegularSession> getRegularSessions() {
		return regularSessions;
	}

	public List<OccasionalSession> getOccasionalSessions() {
		return occasionalSessions;
	}

	public void addRegularSessions(List<RegularSession> regularSessions) {
		this.regularSessions = Stream.concat(this.regularSessions.stream(), regularSessions.stream())
				.collect(Collectors.toList());
	}

	public void addOccasionalSession(OccasionalSession session) {
		this.occasionalSessions.add(session);
	}


	public boolean isAvailable(OccasionalActivity correntActivity) {
		return correntActivity.getOccasionalSessions().stream().noneMatch(this.getOccasionalSessions()::contains)
				&& correntActivity.getOccasionalSessions().stream().noneMatch(this.getRegularSessions()::contains);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "id: " + id + "name: " + name;
	}
}



