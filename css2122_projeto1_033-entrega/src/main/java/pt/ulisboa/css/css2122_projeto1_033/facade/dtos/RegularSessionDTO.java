package pt.ulisboa.css.css2122_projeto1_033.facade.dtos;

import java.util.Date;
import java.util.List;

import pt.ulisboa.css.css2122_projeto1_033.business.session.Schedule;
import pt.ulisboa.css.css2122_projeto1_033.business.utils.DateMock;

public class RegularSessionDTO {

	private final List<Schedule> schedule;
	private final Date date;

	public RegularSessionDTO(List<Schedule> schedule, Date date) {
		this.schedule = schedule;
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public List<Schedule> getSchedules() {
		return this.schedule;
	}

	@Override
	public String toString() {
		return "SessionDate:" + DateMock.printDate(date) +
				" Schedules=" + schedule.toString() +
				"\n";
	}
}
