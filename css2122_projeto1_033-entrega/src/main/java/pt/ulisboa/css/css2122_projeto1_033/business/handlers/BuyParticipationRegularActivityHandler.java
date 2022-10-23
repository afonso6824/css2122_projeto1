package pt.ulisboa.css.css2122_projeto1_033.business.handlers;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import pt.ulisboa.css.css2122_projeto1_033.business.activity.ActivityCatalog;
import pt.ulisboa.css.css2122_projeto1_033.business.activity.RegularActivity;
import pt.ulisboa.css.css2122_projeto1_033.business.client.Client;
import pt.ulisboa.css.css2122_projeto1_033.business.client.ClientCatalog;
import pt.ulisboa.css.css2122_projeto1_033.business.payment_details.PaymentDetails;
import pt.ulisboa.css.css2122_projeto1_033.business.reservation.Reservation;
import pt.ulisboa.css.css2122_projeto1_033.business.session.RegularSession;
import pt.ulisboa.css.css2122_projeto1_033.business.utils.DateMock;
import pt.ulisboa.css.css2122_projeto1_033.facade.dtos.RegularActivityDTO;
import pt.ulisboa.css.css2122_projeto1_033.facade.dtos.RegularSessionDTO;
import pt.ulisboa.css.css2122_projeto1_033.facade.exceptions.*;
import pt.ulisboa.css.css2122_projeto1_033.business.session.Schedule;



import java.util.List;
import java.util.stream.Collectors;

public class BuyParticipationRegularActivityHandler {
	
	/**
	 * Entity manager factory for accessing the persistence service 
	 */
	private final EntityManagerFactory emf;
	private String regularActivityDescription;
	

	/**
	 * Creates a handler for the event creation use case given
	 * the application's entity manager factory
	 * @param emf The entity manager factory of the application
	 */
	public BuyParticipationRegularActivityHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	public RegularActivityDTO joinRegularActivity(String description) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		ActivityCatalog activityCatalog = new ActivityCatalog(em);
		
		
		try {
			em.getTransaction().begin();
			RegularActivity regularActivity = (RegularActivity) activityCatalog.getActivity(description);
			
			if (regularActivity == null) 
				throw new RegularActivityNotExistsException("Regular Activity " + description +" does not exists");
			
			regularActivityDescription = description;
			
			List<RegularSession> sessions = regularActivity.getRegularSessions();
			sessions = sessions.stream()
					.filter( rs -> rs.getDate().compareTo(DateMock.getCurrentTime()) >= 0)
					.collect(Collectors.toList());
			List<RegularSessionDTO> sessionDTOs = new ArrayList<>();
			
			for (RegularSession session : sessions) {
				sessionDTOs.add(new RegularSessionDTO(session.getSchedules(), session.getDate()));
			}
			em.getTransaction().commit();
			return new RegularActivityDTO( regularActivity.getPrice() , sessionDTOs);
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error activity fetching ", e);
		}finally {
			em.close();
		}
	}
	
	
	public PaymentDetails selectOptions( List<Schedule> schedules, Date beginDate, int nMonths, String email) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		ClientCatalog clientCatalog = new ClientCatalog(em);
		ActivityCatalog activityCatalog = new ActivityCatalog(em);

		try {
			em.getTransaction().begin();
			RegularActivity regularActivity = (RegularActivity) activityCatalog.getActivity(regularActivityDescription);
			
			if (regularActivity == null) 
				throw new RegularActivityNotExistsException("Regular Activity " + regularActivityDescription +" does not exists");

			Client client = clientCatalog.getClientByEmail(email);
			if (client == null) {
				client = new Client(email);
				em.persist(client);
			}
			em.getTransaction().commit();
			if (!checkAndReserveAvailableSpaces(schedules,regularActivity, beginDate,nMonths, em))
				throw new NotAvailableSpacesException("Not possible to reserve activity sessions");

			em.getTransaction().begin();
			Reservation reservation = new Reservation(nMonths, client, regularActivity);
			client.addReservation(reservation);
			em.persist(reservation);
			em.merge(client);

			em.getTransaction().commit();
			return reservation.getPaymentDetails();

		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error reservation creation", e);
		}finally {
			em.close();
		}
		
		
	}
	
	
	private boolean checkAndReserveAvailableSpaces(List<Schedule> schedules,RegularActivity activity, Date beginDateClient, int nMonths, EntityManager em) {
		
		em.getTransaction().begin();

		Date endDate = DateMock.increaseMinToData(nMonths * 30 *24 *60, beginDateClient);

		List<RegularSession> sessions = getAllSessionsBefore(schedules,beginDateClient,endDate, activity);
		if (sessions.isEmpty()) {
			return false;
		}
		
		for (RegularSession regularSession : sessions) {
			if (regularSession.getParticipants() + 1 > activity.getMaxParticipants()) {
				return false;
			}
		}
		
		for (RegularSession regularSession : sessions) {
			regularSession.increaseParticipants();
			em.merge(regularSession);
		}

		em.getTransaction().commit();
		return true;

	
		
	}


	private List<RegularSession> getAllSessionsBefore(List<Schedule> schedules,Date begin, Date end, RegularActivity activity){
		List<RegularSession> sessions = new ArrayList<>();
		for (RegularSession regularSession : activity.getRegularSessions()) {
			if (regularSession.getSchedules().equals(schedules)){
				Date date = regularSession.getDate();
				if ((date.before(end) || date.equals(end)) && (date.after(begin) || date.equals(begin)))  {
					sessions.add(regularSession);
				}
			}

		}
		return sessions;
		
	}
}
