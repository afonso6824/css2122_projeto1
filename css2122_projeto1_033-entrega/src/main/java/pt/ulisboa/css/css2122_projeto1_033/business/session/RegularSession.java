package pt.ulisboa.css.css2122_projeto1_033.business.session;

import pt.ulisboa.css.css2122_projeto1_033.business.activity.RegularActivity;
import pt.ulisboa.css.css2122_projeto1_033.business.utils.DateMock;

import javax.persistence.*;
import java.util.Date;

import java.util.List;


/**
 * Entity implementation class for Entity: Session
 */
@Entity(name = "REGULARSESSION")
@DiscriminatorValue(value = "RegularSession")
public class RegularSession extends Session {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private int sessionsParticipants;

	@ManyToOne
	private RegularActivity regularActivity;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<Schedule> schedule;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date date;


	public RegularSession() {
		// Constructor needed by JPA.
	}

	public RegularActivity getRegularActivity() {
		return regularActivity;
	}

	public List<Schedule> getSchedules() {
		return schedule;
	}

	public Date getDate() {
		return date;
	}

	public int getParticipants() {
		return sessionsParticipants;
	}


	public void increaseParticipants() {
		this.sessionsParticipants = this.sessionsParticipants + 1;

	}


	public RegularSession(int sessionsParticipants, RegularActivity regularActivity, List<Schedule> schedule, Date date) {
		this.sessionsParticipants = sessionsParticipants;
		this.regularActivity = regularActivity;
		this.schedule = schedule;
		this.date = date;
	}

	@Override
	public boolean equals(Object o) {

		if (o instanceof RegularSession) {

			RegularSession that = (RegularSession) o;

			int increment = that.regularActivity.getDurationSessions();

			for (Schedule value : schedule) {
				for (Schedule value2 : that.getSchedules()) {
					if (value.equalsSchedule(value2, increment, date)) {
						return true;
					}
				}

			}
			return false;

		} else if (o instanceof OccasionalSession) {

			OccasionalSession tho = (OccasionalSession) o;

			int increment = tho.getOccasionalActivity().getDurationSessions();

			Date endDate = DateMock.increaseMinToData(increment, this.date);

			return tho.getDateWithHour().compareTo(this.date) >= 0 && tho.getDateWithHour().compareTo(endDate) <= 0;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = sessionsParticipants;
		result = 31 * result + (regularActivity != null ? regularActivity.hashCode() : 0);
		result = 31 * result + (schedule != null ? schedule.hashCode() : 0);
		result = 31 * result + (date != null ? date.hashCode() : 0);
		return result;
	}

}
