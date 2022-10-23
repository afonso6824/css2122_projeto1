package pt.ulisboa.css.css2122_projeto1_033.business.session;

import pt.ulisboa.css.css2122_projeto1_033.business.activity.OccasionalActivity;
import pt.ulisboa.css.css2122_projeto1_033.business.utils.DateMock;

import javax.persistence.*;


import java.util.Date;


/**
 * Entity implementation class for Entity: Session
 */
@Entity
@DiscriminatorValue(value = "OccasionalSession")
public class OccasionalSession extends Session {

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	private Date dateWithHour;

	@ManyToOne
	private OccasionalActivity occasionalActivity;

	public OccasionalSession() {
		// Constructor needed by JPA.
	}

	public OccasionalSession(Date dateWithHour, OccasionalActivity activity) {
		this.dateWithHour = dateWithHour;
		this.occasionalActivity = activity;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof RegularSession) {

			RegularSession that = (RegularSession) o;

			int increment = that.getRegularActivity().getDurationSessions();

			for (Schedule value : that.getSchedules()) {
				for (Schedule value2 : that.getSchedules()) {
					if (value.equalsSchedule(value2, increment, that.getDate())) {
						return true;
					}
				}

			}
			return false;

		} else if (o instanceof OccasionalSession) {

			OccasionalSession tho = (OccasionalSession) o;

			int increment = tho.getOccasionalActivity().getDurationSessions();

			Date endDate = DateMock.increaseMinToData(increment, this.dateWithHour);

			return tho.getDateWithHour().compareTo(this.dateWithHour) >= 0 && tho.getDateWithHour().compareTo(endDate) <= 0;
		}
		return false;
	}


	@Override
	public int hashCode() {
		int result = 31 * (occasionalActivity != null ? occasionalActivity.hashCode() : 0);
		result = 31 * result + (dateWithHour != null ? dateWithHour.hashCode() : 0);
		return result;
	}


	/**
	 * @return the activity
	 */
	public OccasionalActivity getOccasionalActivity() {
		return occasionalActivity;
	}
	
	public Date getDateWithHour() {
		return dateWithHour;
	}

}
