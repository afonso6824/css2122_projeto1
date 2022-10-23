package pt.ulisboa.css.css2122_projeto1_033.business.handlers;

import pt.ulisboa.css.css2122_projeto1_033.business.activity.Activity;
import pt.ulisboa.css.css2122_projeto1_033.business.activity.ActivityCatalog;
import pt.ulisboa.css.css2122_projeto1_033.business.activity.OccasionalActivity;
import pt.ulisboa.css.css2122_projeto1_033.business.activity.RegularActivity;
import pt.ulisboa.css.css2122_projeto1_033.business.specialty.Specialty;
import pt.ulisboa.css.css2122_projeto1_033.business.specialty.SpecialtyCatalog;
import pt.ulisboa.css.css2122_projeto1_033.facade.exceptions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class CreateActivityHandler {

	private final EntityManagerFactory emf;

	public CreateActivityHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public List<String> createActivity() throws ApplicationException {
		return getAllSpecialtiesNames();
	}

	public void createActivity(String specialtyName, String description, boolean isRegular, int nSessions,
							   int duration, int price, int maxParticipants) throws ApplicationException {

		EntityManager em = emf.createEntityManager();
		SpecialtyCatalog specialtyCatalog = new SpecialtyCatalog(em);


		try {
			em.getTransaction().begin();

			Specialty specialty = specialtyCatalog.getSpecialty(specialtyName);


			if (specialty == null)
				throw new SpecialtyNotExistsException("Don't exists an specialty with the designation " + specialtyName);

			if (!isDescriptionValid(description))
				throw new DescriptionNotValidException("Description given is not valid " + description);

			if (!isNSessionsValid(nSessions, isRegular))
				throw new NumberSessionsNotValidException("The number of sessions is not valid for this type of activity");

			if (!isDurationValid(duration, specialty))
				throw new DurationNotValidException("The duration given is not valid");

			if (!isPriceValid(price))
				throw new PriceNotValidException("The price given is not valid");

			if (!isMaxParticipantsValid(maxParticipants, isRegular))
				throw new MaxParticipantsNotValidException("The max participants given is not valid");

			Activity activity;

			if (isRegular) {
				activity = new RegularActivity(description, nSessions, duration, price, maxParticipants);
			} else {
				activity = new OccasionalActivity(description, nSessions, duration, price);
			}


			specialty.addActivity(activity);
			activity.setSpecialty(specialty);

			em.persist(activity);
			em.merge(specialty);
			em.getTransaction().commit();

		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error create activity", e);
		} finally {
			em.close();
		}
	}

	public boolean isDescriptionValid(String description) throws ApplicationException {
		if (description == null || description.length() == 0) {
			return false;
		}
		return description.matches("[a-zA-Zà-úÀ-Ú0-9\\-\\s]+$") && !existsDescriptionWithThisName(description);
	}

	private boolean existsDescriptionWithThisName(String description) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		ActivityCatalog activityCatalog = new ActivityCatalog(em);
		try {
			em.getTransaction().begin();
			boolean alreadyExists = activityCatalog.existsRegularActivity(description) || activityCatalog.existsOccasionalActivity(description) ;
			em.getTransaction().commit();
			return alreadyExists;
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Error fetching activity", e);
		} finally {
			em.close();
		}
	}

	public boolean isNSessionsValid(int nSessions, boolean isRegular) {
		if (isRegular) {
			return nSessions >= 1 && nSessions <= 5;
		} else {
			return nSessions >= 1 && nSessions <= 20;
		}
	}

	public boolean isDurationValid(int duration, Specialty specialty) {
		return duration > 0 && duration >= specialty.getMinimumDuration();
	}

	public boolean isPriceValid(int price) {
		return price > 0;
	}

	public boolean isMaxParticipantsValid(int maxParticipants, boolean isRegular) {
		return !isRegular || maxParticipants > 0;
	}


	public List<String> getAllSpecialtiesNames() throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		SpecialtyCatalog specialtyCatalog = new SpecialtyCatalog(em);
		try {
			em.getTransaction().begin();
			List<String> result = specialtyCatalog.getAllSpecialtyNames();
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

}




