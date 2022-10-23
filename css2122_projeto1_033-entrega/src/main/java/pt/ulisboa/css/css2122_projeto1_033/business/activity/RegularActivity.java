package pt.ulisboa.css.css2122_projeto1_033.business.activity;

import pt.ulisboa.css.css2122_projeto1_033.business.session.RegularSession;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@DiscriminatorValue(value = "Regular")

public class RegularActivity extends Activity {

	private int maxParticipants;

	private int durationSchedule;

	@Column(nullable = false)
	private int remainingTimeInstructor;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<RegularSession> regularSessions;


	public RegularActivity() {
		// Constructor needed by JPA.
	}

	public RegularActivity(String description, int nSessions, int duration, int price, int maxParticipants) {
		super(description, nSessions, duration, price);
		this.maxParticipants = maxParticipants;
	}

	public int getDurationSchedule() {
		return durationSchedule;
	}

	public int getRemainingTimeInstructor() {
		return remainingTimeInstructor;
	}

	public void setRemainingTimeInstructor(int remainingTimeInstructor) {
		this.remainingTimeInstructor = remainingTimeInstructor;
	}

	public int getMaxParticipants() {
		return maxParticipants;
	}

	public void setDurationSchedule(int durationSchedule) {
		this.durationSchedule = durationSchedule;
	}

	public void addRegularSessions(List<RegularSession> regularSessions) {
		this.regularSessions = Stream.concat(this.regularSessions.stream(), regularSessions.stream())
				.collect(Collectors.toList());
	}

	public List<RegularSession> getRegularSessions() {
		return regularSessions;
	}
}