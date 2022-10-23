package pt.ulisboa.css.css2122_projeto1_033.business.handlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


import pt.ulisboa.css.css2122_projeto1_033.business.activity.ActivityCatalog;
import pt.ulisboa.css.css2122_projeto1_033.business.activity.OccasionalActivity;
import pt.ulisboa.css.css2122_projeto1_033.business.client.Client;
import pt.ulisboa.css.css2122_projeto1_033.business.client.ClientCatalog;
import pt.ulisboa.css.css2122_projeto1_033.business.instructor.Instructor;
import pt.ulisboa.css.css2122_projeto1_033.business.instructor.InstructorCatalog;
import pt.ulisboa.css.css2122_projeto1_033.business.payment_details.PaymentDetails;
import pt.ulisboa.css.css2122_projeto1_033.business.reservation.Reservation;
import pt.ulisboa.css.css2122_projeto1_033.business.session.OccasionalSession;
import pt.ulisboa.css.css2122_projeto1_033.facade.dtos.InstructorDTO;
import pt.ulisboa.css.css2122_projeto1_033.facade.dtos.OccasionalActivityDTO;
import pt.ulisboa.css.css2122_projeto1_033.facade.dtos.OccasionalSessionDTO;
import pt.ulisboa.css.css2122_projeto1_033.facade.exceptions.ApplicationException;

public class ScheduleOccasionalActivityHandler {

	private final EntityManagerFactory emf;
	private OccasionalActivity correntActivity = null;


	public ScheduleOccasionalActivityHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}


	public List<OccasionalActivityDTO> scheduleOccasionalActivity(String spec) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		ActivityCatalog actCat = new ActivityCatalog(em);
		try {
			em.getTransaction().begin();
			List<OccasionalActivity> listOccAct = actCat.getOccasionalActivitiesBySpecialty(spec);
			em.getTransaction().commit();

			return listOccAct
					.stream()
					.map(occasionalActivity -> new OccasionalActivityDTO(occasionalActivity.getDescription(),
							occasionalActivity.getOccasionalSessions()
									.stream()
									.map(occasionalSession -> new OccasionalSessionDTO(occasionalSession.getDateWithHour()))
									.collect(Collectors.toList())))
					.collect(Collectors.toList());

		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Error fetching list of occasional activities.", e);
		} finally {
			em.close();
		}
	}


	public List<InstructorDTO> scheduleSessions(String activityDescription, List<Date> sessionsDate) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		ActivityCatalog actCat = new ActivityCatalog(em);
		InstructorCatalog instructorCat = new InstructorCatalog(em);
		try {
			em.getTransaction().begin();
			this.correntActivity = actCat.getOccasionalActivityByDescription(activityDescription);
			List<OccasionalSession> sessions = new ArrayList<>();
			if (sessionsValid(sessionsDate)) {
				for (Date date : sessionsDate) {
					sessions.add(new OccasionalSession(date, correntActivity));
				}
				correntActivity.setOccasionalSessions(sessions);
			}
			em.merge(correntActivity);
			em.getTransaction().commit();
			em.getTransaction().begin();
			List<Instructor> availableInstructors;
			availableInstructors = instructorCat.getAvailableInstructors(correntActivity);
			if (availableInstructors.isEmpty()) {
				throw new ApplicationException("Instructors are not available");
			}
			em.getTransaction().commit();

			return availableInstructors.stream()
					.map(instructor -> new InstructorDTO(instructor.getId(), instructor.getName()))
					.collect(Collectors.toList());

		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("The given list of dates are not valid", e);
		}
	}


	public PaymentDetails instructorSelect(int numInstructor, String clientEmail) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		ClientCatalog clientCatalog = new ClientCatalog(em);

		try {
			em.getTransaction().begin();
			Instructor correntInstructor = em.find(Instructor.class, numInstructor);
			for (OccasionalSession s : correntActivity.getOccasionalSessions()) {
				correntInstructor.addOccasionalSession(s);
			}

			Client client = clientCatalog.getClientByEmail(clientEmail);
			if (client == null) {
				client = new Client(clientEmail);
				em.persist(client);
			}


			Reservation newReservation = new Reservation(client, correntActivity);
			client.addReservation(newReservation);
			em.merge(correntInstructor);
			em.persist(newReservation);
			em.merge(client);
			em.getTransaction().commit();
			return newReservation.getPaymentDetails();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new ApplicationException("Error it's not possible to associate sessions to this instructor", e);
		}
	}

	/* Auxiliary method to validate a list of dates
	 * @Param list with sessions date
	 * @return if the list of dates is valid
	 */
	private boolean sessionsValid(List<Date> sessionsDate) {

		if (correntActivity.getNSessions() != sessionsDate.size()) {
			return false;
		}
		return sessionsDate.stream().distinct().count() == sessionsDate.size();

	}
}
