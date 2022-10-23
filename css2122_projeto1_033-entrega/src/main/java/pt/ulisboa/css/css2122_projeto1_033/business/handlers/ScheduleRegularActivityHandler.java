package pt.ulisboa.css.css2122_projeto1_033.business.handlers;

import pt.ulisboa.css.css2122_projeto1_033.business.activity.ActivityCatalog;
import pt.ulisboa.css.css2122_projeto1_033.business.activity.RegularActivity;
import pt.ulisboa.css.css2122_projeto1_033.business.instructor.Instructor;
import pt.ulisboa.css.css2122_projeto1_033.business.instructor.InstructorCatalog;
import pt.ulisboa.css.css2122_projeto1_033.business.session.RegularSession;
import pt.ulisboa.css.css2122_projeto1_033.business.session.Schedule;
import pt.ulisboa.css.css2122_projeto1_033.business.utils.DATE_TYPE;
import pt.ulisboa.css.css2122_projeto1_033.business.utils.DateMock;
import pt.ulisboa.css.css2122_projeto1_033.facade.exceptions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleRegularActivityHandler {

	private static final int NUMBER_WEEKS_FOR_MONTH = 4;

	private final EntityManagerFactory emf;

	public ScheduleRegularActivityHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public List<String> getAllRegularActivities() throws ApplicationException {
		return getAllRegularActivitiesName();
	}

	public void setScheduleRegularActivity(String activityName, List<String> schedulesString, Date beginDate, int durationSchedule,
										   int idInstructor, int remainingTimeInstructor) throws ApplicationException {

		EntityManager em = emf.createEntityManager();
		ActivityCatalog activityCatalog = new ActivityCatalog(em);
		InstructorCatalog instructorCatalog = new InstructorCatalog(em);

		List<Schedule> schedules = convertFromStringToSchedules(schedulesString, beginDate);

		try {
			em.getTransaction().begin();

			RegularActivity activity = (RegularActivity) activityCatalog.getActivity(activityName);

			if (activity == null)
				throw new ActivityNotExistsException("Don't exists an activity with the designation " + activityName);

			if (!isSchedulesValid(schedules, activity))
				throw new SchedulesNotValidException("The given schedules are not valid for this regular activity");

			if (!isBeginDateValid(beginDate))
				throw new BeginDateNotValidException("The given begin date is not valid");

			if (!isDurationValid(durationSchedule))
				throw new DurationNotValidException("The duration given is not valid");

			if (!isInstructorValid(idInstructor, activity))
				throw new InstructorNotValidException("The instructor given is not valid");

			if (!isRemainingTimeInstructorValid(remainingTimeInstructor, durationSchedule))
				throw new RemainingTimeInstructorNotValidException("The remaining time instructor given is not valid");


			activity.setDurationSchedule(durationSchedule);
			activity.setRemainingTimeInstructor(remainingTimeInstructor);

			List<RegularSession> regularSessions = new ArrayList<>();
			List<RegularSession> regularSessionsToInstructor = new ArrayList<>();

			Instructor instructor = instructorCatalog.getInstructorById(idInstructor);


			int remainingTimeInstructorWeeks = remainingTimeInstructor * NUMBER_WEEKS_FOR_MONTH;
			for (int i = 0; i < durationSchedule * NUMBER_WEEKS_FOR_MONTH; i++) {
				for (int j = 0; j < schedules.size(); j++) {
					RegularSession regularSession;
					if (remainingTimeInstructorWeeks > 0) {
						regularSession = new RegularSession(0, activity, schedules,
								pickDate(i, schedules.get(j), beginDate));

						regularSessions.add(regularSession);
						regularSessionsToInstructor.add(regularSession);
					} else {
						regularSession = new RegularSession(0, activity, schedules,
								pickDate(i, schedules.get(j), beginDate));

						regularSessions.add(regularSession);
					}
				}
				remainingTimeInstructorWeeks -= 1;
			}

			if (!isInstructorAvailableToSessions(instructor, regularSessions))
				throw new InstructorAvailableToSessionsNotValidException("Instructor is not available");


			regularSessions.forEach(em::persist);
			activity.addRegularSessions(regularSessions);
			instructor.addRegularSessions(regularSessionsToInstructor);

			em.merge(instructor);
			em.merge(activity);
			em.getTransaction().commit();

		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error create activity", e);
		} finally {
			em.close();
		}
	}

	public Date pickDate(int nWeek, Schedule schedule, Date beginDate) {

		//Get begin date
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);

		// Get correct hour and minute
		Calendar hourCalendar = Calendar.getInstance();
		hourCalendar.setTime(schedule.getBeginHour());

		// Get next day of the week
		LocalDate localDate = LocalDateTime.ofInstant(date.toInstant(), date.getTimeZone().toZoneId()).toLocalDate();
		localDate = localDate.with(TemporalAdjusters.next(schedule.getDayOfTheWeek()));

		// Convert LocalDate into Calendar
		Date dateTemp = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Calendar result = Calendar.getInstance();
		result.setTime(dateTemp);

		//increment which week
		result.add(Calendar.WEEK_OF_YEAR, nWeek);

		//Set correct hour and minutes in date
		result.set(Calendar.MINUTE, hourCalendar.get(Calendar.MINUTE));
		result.set(Calendar.HOUR, hourCalendar.get(Calendar.HOUR));

		return result.getTime();
	}

	public boolean isInstructorAvailableToSessions(Instructor instructor, List<RegularSession> givenSessions) {
		return givenSessions.stream().noneMatch(instructor.getRegularSessions()::contains) &&
				givenSessions.stream().noneMatch(instructor.getOccasionalSessions()::contains);
	}

	public boolean isRemainingTimeInstructorValid(int remainingTimeInstructor, int durationSchedule) {
		return remainingTimeInstructor <= durationSchedule;
	}

	public boolean isSchedulesValid(List<Schedule> schedules, RegularActivity activity) {
		if (activity.getNSessions() != schedules.size()) {
			return false;
		}
		return schedules.stream().distinct().count() == schedules.size();
	}

	public boolean isBeginDateValid(Date beginDate) {
		return beginDate.compareTo(DateMock.getCurrentTime()) > 0;
	}


	public boolean isDurationValid(int durationSchedule) {
		return durationSchedule >= 1;
	}

	public boolean isInstructorValid(int instructorId, RegularActivity activity) throws ApplicationException {

		Instructor instructor = getInstructorById(instructorId);
		if (instructor == null) {
			return false;
		}
		return checkInstructorHasCertifications(instructor, activity);
	}

	private Instructor getInstructorById(int instructorId) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		InstructorCatalog instructorCatalog = new InstructorCatalog(em);
		try {
			em.getTransaction().begin();
			Instructor instructor = instructorCatalog.getInstructorById(instructorId);
			em.getTransaction().commit();
			return instructor;
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error fetching activity", e);
		} finally {
			em.close();
		}
	}

	private boolean checkInstructorHasCertifications(Instructor instructor, RegularActivity activity) {
		return instructor.getCertificates().stream()
				.anyMatch(certification -> certification.equals(activity.getSpecialty().getCertification()));
	}

	public List<String> getAllRegularActivitiesName() throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		ActivityCatalog activityCatalog = new ActivityCatalog(em);
		try {
			em.getTransaction().begin();
			List<String> result = activityCatalog.getAllRegularActivitiesName();
			em.getTransaction().commit();
			return result;
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error fetching specialty", e);
		} finally {
			em.close();
		}
	}


	public List<Schedule> convertFromStringToSchedules(List<String> schedulesString, Date beginDate) {

		List<Schedule> schedules = new ArrayList<>();

		schedulesString.forEach(s -> {
			try {
				DateMock dateMock = new DateMock(DATE_TYPE.DAY_OF_WEEK_HOUR_MINUTE, s);
				schedules.add(new Schedule(dateMock, beginDate));
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		});

		return schedules;
	}

}




