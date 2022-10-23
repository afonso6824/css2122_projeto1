package pt.ulisboa.css.css2122_projeto1_033.facade.services;

import pt.ulisboa.css.css2122_projeto1_033.business.handlers.ScheduleRegularActivityHandler;
import pt.ulisboa.css.css2122_projeto1_033.facade.exceptions.ApplicationException;

import java.util.Date;
import java.util.List;

public class ScheduleRegularActivityService {
	private final ScheduleRegularActivityHandler scheduleRegularActivityHandler;

	public ScheduleRegularActivityService(ScheduleRegularActivityHandler scheduleRegularActivityHandler) {
		this.scheduleRegularActivityHandler = scheduleRegularActivityHandler;
	}

	public void setScheduleActivity(String activityName, List<String> schedules, Date beginDate, int durationSchedule,
									int idInstructor, int remainingTimeInstructor) throws ApplicationException {

		scheduleRegularActivityHandler
				.setScheduleRegularActivity(activityName, schedules, beginDate, durationSchedule, idInstructor, remainingTimeInstructor);
	}

	public List<String> setScheduleActivity() throws ApplicationException {
		return scheduleRegularActivityHandler.getAllRegularActivities();
	}

}
