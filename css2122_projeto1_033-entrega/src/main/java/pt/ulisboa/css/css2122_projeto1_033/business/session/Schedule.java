
package pt.ulisboa.css.css2122_projeto1_033.business.session;


import pt.ulisboa.css.css2122_projeto1_033.business.utils.DateMock;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Date;

/**
 * Entity implementation class for Entity: DayPeriod.
 * A class representing a period of time in a single day.
 */
@Embeddable
public class Schedule implements Serializable {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private DayOfWeek dayOfTheWeek;

	@Temporal(TemporalType.DATE)
	private Date beginDate;

	@Temporal(TemporalType.TIME)
	private Date beginHour;


	public Schedule() {
		//Constructor needed by JPA.
	}

	public Schedule(DateMock date, Date beginDate) {
		this.dayOfTheWeek = date.getDayOfWeek();
		this.beginHour = date.convertToDate();
		this.beginDate = beginDate;
	}

	public Date getBeginDate() {
		return beginDate;
	}
	public DayOfWeek getDayOfTheWeek() {
		return dayOfTheWeek;
	}

	public Date getBeginHour() {
		return beginHour;
	}

	@Override
	public boolean equals(Object o) {

		Schedule schedule = (Schedule) o;

		if(schedule == null) return false;

		if (this.getDayOfTheWeek() != schedule.getDayOfTheWeek())
			return false;

		return schedule.beginHour == this.beginHour;
	}

	public boolean equalsSchedule(Object o, int increment, Date date){
		Schedule schedule = (Schedule) o;
		Date endDate = DateMock.increaseMinToData(increment, date);

		if (this.getDayOfTheWeek() != schedule.getDayOfTheWeek())
			return false;

		return date.compareTo(schedule.getBeginHour()) >= 0 && date.compareTo(endDate) <= 0;
	}

	@Override
	public int hashCode() {
		int result = dayOfTheWeek != null ? dayOfTheWeek.hashCode() : 0;
		result = 31 * result + (beginHour != null ? beginHour.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "DayOfTheWeek=" + dayOfTheWeek +
				", beginDate=" + DateMock.printDate(beginDate) +
				", beginHour=" + DateMock.printHour(beginHour) ;
	}
}