package pt.ulisboa.css.css2122_projeto1_033.client;

import pt.ulisboa.css.css2122_projeto1_033.business.activity.OccasionalActivity;
import pt.ulisboa.css.css2122_projeto1_033.business.instructor.Instructor;
import pt.ulisboa.css.css2122_projeto1_033.business.payment_details.PaymentDetails;
import pt.ulisboa.css.css2122_projeto1_033.business.session.Schedule;
import pt.ulisboa.css.css2122_projeto1_033.business.utils.DATE_TYPE;
import pt.ulisboa.css.css2122_projeto1_033.business.utils.DateMock;
import pt.ulisboa.css.css2122_projeto1_033.facade.dtos.InstructorDTO;
import pt.ulisboa.css.css2122_projeto1_033.facade.dtos.OccasionalActivityDTO;
import pt.ulisboa.css.css2122_projeto1_033.facade.dtos.RegularActivityDTO;
import pt.ulisboa.css.css2122_projeto1_033.facade.exceptions.ApplicationException;
import pt.ulisboa.css.css2122_projeto1_033.facade.exceptions.InvalidDateFormatException;
import pt.ulisboa.css.css2122_projeto1_033.facade.services.CreateActivityService;
import pt.ulisboa.css.css2122_projeto1_033.facade.services.ScheduleRegularActivityService;
import pt.ulisboa.css.css2122_projeto1_033.facade.services.BuyParticipationRegularActivityService;
import pt.ulisboa.css.css2122_projeto1_033.facade.services.ScheduleOccasionalActivityService;
import pt.ulisboa.css.css2122_projeto1_033.facade.startup.SaudeGes;

import java.util.ArrayList;
import java.util.List;

public class SimpleClient {

	private static final String delim = "====================================";

	public static void main(String[] args) {

		SaudeGes app = new SaudeGes();

		try {
			app.run();
			CreateActivityService createActivityService = app.getCreateActivityService();
			ScheduleRegularActivityService scheduleRegularActivityService = app.getSetScheduleActivityService();
			BuyParticipationRegularActivityService buyParticipationService = app.getBuyParticipationService();
			ScheduleOccasionalActivityService sOAS = app.getScheduleOccasionalActivityService();

			/*********************************************************************************************************/


			System.out.println(delim + " T1 " + delim);
			createOccasionalActivity(createActivityService, "Fisioterapia", "Fisioterapia 1",
					1, 45, 43);


			/*********************************************************************************************************/


			System.out.println(delim + " T2 " + delim);
			createOccasionalActivity(createActivityService, "Fisioterapia", "Fisioterapia 5",
					5, 45, 195);


			/*********************************************************************************************************/


			System.out.println(delim + " T3 " + delim);
			createOccasionalActivity(createActivityService, "Massagem Desportiva", "Massagem 3x60",
					3, 60, 200);


			/*********************************************************************************************************/


			try {
				System.out.println(delim + " T4 " + delim);
				createOccasionalActivity(createActivityService, "Massagem Desportiva", "Massagem 3x20",
						3, 20, 100);
			} catch (Exception e) {
				e.printStackTrace();
				// A duração da atividade não pode ser inferior à duração mínima definida para a
				//especialidade.
			}


			/*********************************************************************************************************/


			System.out.println(delim + " T5 " + delim);
			createRegularActivity(createActivityService, "Pilates Clínico", "Pilates 2x",
					2, 45, 60, 5);


			/*********************************************************************************************************/


			System.out.println(delim + " T6 " + delim);
			createRegularActivity(createActivityService, "Pilates Clínico", "Pilates 1x",
					1, 45, 40, 2);


			/*********************************************************************************************************/


			try {
				System.out.println(delim + " T7 " + delim);
				createRegularActivity(createActivityService, "Pós Parto", "Pós Parto 3x",
						3, 30, 40, 0);
			} catch (Exception e) {
				e.printStackTrace();
				//O número máximo de participantes numa atividade regular é obrigatório.
			}


			/*********************************************************************************************************/


			try {
				System.out.println(delim + " T8 " + delim);

				List<String> scheduleT8 = new ArrayList<>();
				scheduleT8.add("Terça 14:00");
				scheduleT8.add("Quinta 14:00");

				DateMock beginDateT8 = new DateMock(DATE_TYPE.YEAR_MONTH_DAY, "01/04/2022");

				setNewScheduleToRegularActivity(scheduleRegularActivityService, "Pilates 2x",
						scheduleT8, beginDateT8,
						6, 2, 2);

			} catch (Exception e) {
				e.printStackTrace();
				//O instrutor atribuído não tem certificação apropriada para a especialidade.			}
			}


			/*********************************************************************************************************/


			System.out.println(delim + " T9 " + delim);
			List<String> scheduleT9 = new ArrayList<>();
			scheduleT9.add("Terça 14:00");
			scheduleT9.add("Quinta 14:00");

			DateMock beginDateT9 = new DateMock(DATE_TYPE.YEAR_MONTH_DAY, "01/04/2022");

			setNewScheduleToRegularActivity(scheduleRegularActivityService, "Pilates 2x",
					scheduleT9, beginDateT9,
					6, 1, 2);


			/*********************************************************************************************************/


			try {
				System.out.println(delim + " T10 " + delim);
				List<String> scheduleT10 = new ArrayList<>();
				scheduleT10.add("Segunda 14:30");
				scheduleT10.add("Quinta 14:30");

				DateMock beginDateT10 = new DateMock(DATE_TYPE.YEAR_MONTH_DAY, "18/04/2022");

				setNewScheduleToRegularActivity(scheduleRegularActivityService, "Pilates 2x",
						scheduleT10, beginDateT10,
						6, 1, 1);

			} catch (Exception e) {
				e.printStackTrace();
				//O instrutor atribuído não está livre nos horários das sessões durante o tempo indicado.			}
			}


			/*********************************************************************************************************/


			System.out.println(delim + " T11 " + delim);
			List<String> scheduleT11 = new ArrayList<>();
			scheduleT11.add("Segunda 14:30");

			DateMock beginDateT11 = new DateMock(DATE_TYPE.YEAR_MONTH_DAY, "01/04/2022");

			setNewScheduleToRegularActivity(scheduleRegularActivityService, "Pilates 1x",
					scheduleT11, beginDateT11,
					3, 1, 3);


			/*********************************************************************************************************/


			System.out.println(delim + " T12 " + delim);
			List<String> scheduleT12 = new ArrayList<>();
			scheduleT12.add("Sexta 14:30");

			DateMock beginDateT12 = new DateMock(DATE_TYPE.YEAR_MONTH_DAY, "18/04/2022");

			setNewScheduleToRegularActivity(scheduleRegularActivityService, "Pilates 1x",
					scheduleT12, beginDateT12,
					3, 1, 3);


			/*********************************************************************************************************/


			System.out.println(delim + " T13 " + delim);
			buyParticipation(buyParticipationService, "Pilates 1x", "01/04/2022", 3, "u1@gmail.com");


			/*********************************************************************************************************/


			System.out.println(delim + " T14 " + delim);
			buyParticipation(buyParticipationService, "Pilates 1x", "11/04/2022", 1, "u2@gmail.com");

			/*********************************************************************************************************/

			try {
				System.out.println(delim + " T15 " + delim);
				buyParticipation(buyParticipationService, "Pilates 1x", "18/04/2022", 1, "u3@gmail.com");
			} catch (Exception e) {
				e.printStackTrace();
				//Não existem lugares disponíveis em todas as sessões do horário escolhido
			}


			/*********************************************************************************************************/


			System.out.println(delim + " T16 " + delim);
			buyParticipation(buyParticipationService, "Pilates 1x", "16/05/2022", 1, "u3@gmail.com");


			/*********************************************************************************************************/


			System.out.println(delim + " T17 " + delim);
			List<OccasionalActivityDTO> listOfOccasionalActivities;
			listOfOccasionalActivities = scheduleOccasionalActivity(sOAS, "Massagem Desportiva");
			System.out.println(listOfOccasionalActivities);
			OccasionalActivityDTO occAct = listOfOccasionalActivities.get(listOfOccasionalActivities.size() - 1);
			List<String> listOfDates = new ArrayList<>();
			listOfDates.add("28/03/2022-10:00");
			listOfDates.add("04/04/2022-14:00");
			listOfDates.add("11/04/2022-12:00");
			try {
				List<InstructorDTO> listOfAvailableInstructors = scheduleSessions(sOAS, occAct.getDescription(), listOfDates);
				System.out.println(listOfAvailableInstructors);
			} catch (InvalidDateFormatException e) {
				e.printStackTrace();
			}

			PaymentDetails paymentInfo = instructorSelect(sOAS, 2, "u4@gmail.com");
			System.out.println(paymentInfo.toString());


			/*********************************************************************************************************/

			app.stopRun();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}


	private static void buyParticipation(BuyParticipationRegularActivityService uc3s, String description, String beginDate, int nMonths, String email) throws ApplicationException {
		System.out.println("Sessions:");
		RegularActivityDTO temp = uc3s.joinRegularActivity(description);
		System.out.println(temp);
		System.out.println("Payment details:");
		List<Schedule> schedules = temp.getSessions().get(0).getSchedules();
		System.out.println(uc3s.selectOptions(schedules, new DateMock(DATE_TYPE.YEAR_MONTH_DAY, beginDate).convertToDate(), nMonths, email));
		System.out.println("Reservation sucess");
	}

	private static void createOccasionalActivity(CreateActivityService es, String specialtyName, String description, int nSessions, int duration, int price)
			throws ApplicationException {
		System.out.println("Specialty Types:");
		System.out.println(es.createActivity());
		System.out.println("Creating new activity named \"" + description + "\", of specialty " + specialtyName);

		es.createActivity(specialtyName, description, false, nSessions, duration, price, 0);

		System.out.println("Occasional Activity successfully added!");
	}

	private static void createRegularActivity(CreateActivityService es, String specialtyName, String description,
											  int nSessions, int duration, int price, int maxParticipants)
			throws ApplicationException {
		System.out.println("Specialty Types:");
		System.out.println(es.createActivity());
		System.out.println("Creating new activity named \"" + description + "\", of specialty " + specialtyName);

		es.createActivity(specialtyName, description, true, nSessions, duration, price, maxParticipants);

		System.out.println("Regular Activity successfully added!");
	}

	private static List<OccasionalActivityDTO> scheduleOccasionalActivity(ScheduleOccasionalActivityService sOAS, String specialty) throws ApplicationException {
		return sOAS.scheduleOccasionalActivity(specialty);
	}

	private static List<InstructorDTO> scheduleSessions(ScheduleOccasionalActivityService sOAS, String activityDescription, List<String> listOfDates) throws ApplicationException, InvalidDateFormatException {
		return sOAS.scheduleSessions(activityDescription, listOfDates);

	}

	private static PaymentDetails instructorSelect(ScheduleOccasionalActivityService sOAS, int instructorID, String clientEmail) throws ApplicationException {
		return sOAS.instructorSelect(instructorID, clientEmail);
	}

	private static void setNewScheduleToRegularActivity(ScheduleRegularActivityService es, String
			activityName, List<String> schedules, DateMock beginDate, int durationSchedule, int idInstructor, int remainingTimeInstructor)
			throws ApplicationException {
		System.out.println("Regular Activities:");
		System.out.println(es.setScheduleActivity());
		System.out.println("Set schedule to activity named \"" + activityName);

		es.setScheduleActivity(activityName, schedules, beginDate.convertToDate(), durationSchedule, idInstructor, remainingTimeInstructor);

		System.out.println("Schedule successfully added!");
	}

}
