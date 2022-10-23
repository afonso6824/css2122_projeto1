package pt.ulisboa.css.css2122_projeto1_033.facade.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pt.ulisboa.css.css2122_projeto1_033.business.activity.OccasionalActivity;
import pt.ulisboa.css.css2122_projeto1_033.business.handlers.ScheduleOccasionalActivityHandler;
import pt.ulisboa.css.css2122_projeto1_033.business.instructor.Instructor;
import pt.ulisboa.css.css2122_projeto1_033.business.payment_details.PaymentDetails;
import pt.ulisboa.css.css2122_projeto1_033.business.utils.DATE_TYPE;
import pt.ulisboa.css.css2122_projeto1_033.business.utils.DateMock;
import pt.ulisboa.css.css2122_projeto1_033.facade.dtos.InstructorDTO;
import pt.ulisboa.css.css2122_projeto1_033.facade.dtos.OccasionalActivityDTO;
import pt.ulisboa.css.css2122_projeto1_033.facade.exceptions.ApplicationException;
import pt.ulisboa.css.css2122_projeto1_033.facade.exceptions.InvalidDateFormatException;
public class ScheduleOccasionalActivityService {
	
	private ScheduleOccasionalActivityHandler schedOccActHandler;

	public ScheduleOccasionalActivityService(ScheduleOccasionalActivityHandler schedOccActHandler) {
		this.schedOccActHandler = schedOccActHandler;
	}

	public List<OccasionalActivityDTO> scheduleOccasionalActivity(String specialty) throws ApplicationException {
		return schedOccActHandler.scheduleOccasionalActivity(specialty);
		
	}

	public List<InstructorDTO> scheduleSessions(String activityDescription, List<String> listOfDates) throws ApplicationException, InvalidDateFormatException {
		List<Date> datesList = new ArrayList<>();
		
		try {
			for (String d : listOfDates) {
				datesList.add(new DateMock(DATE_TYPE.DAY_MONTH_YEAR_TRACE_HOUR_MINUTE, d).convertToDate());
			}
		} catch(Exception e) {
			throw new InvalidDateFormatException("Input com formatação não reconhecida.");
		}

		return schedOccActHandler.scheduleSessions(activityDescription, datesList);
	}

	public PaymentDetails instructorSelect(int instructorID, String clientEmail) throws ApplicationException {
		return schedOccActHandler.instructorSelect(instructorID, clientEmail);
	}
	
	
}
