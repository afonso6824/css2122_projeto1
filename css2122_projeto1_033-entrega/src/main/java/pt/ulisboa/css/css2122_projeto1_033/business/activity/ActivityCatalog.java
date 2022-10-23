package pt.ulisboa.css.css2122_projeto1_033.business.activity;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import pt.ulisboa.css.css2122_projeto1_033.facade.exceptions.ApplicationException;

import java.util.ArrayList;
import java.util.List;

public class ActivityCatalog {
	private EntityManager em;

	public ActivityCatalog(EntityManager em) {
		this.em = em;
	}

	public Activity getActivity(String designation) {
		TypedQuery<Activity> query = em.createNamedQuery(Activity.FIND_BY_DESIGNATION, Activity.class);
		query.setParameter(Activity.ACTIVITY_DESIGNATION, designation);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

	public List<String> getAllRegularActivitiesName() {
		try {
			TypedQuery<String> query = em.createNamedQuery(Activity.GET_ALL_REGULAR_ACTIVITY_NAMES, String.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public boolean existsRegularActivity(String designation) {
		try {
			TypedQuery<Number> query = em.createNamedQuery(Activity.EXISTS_REGULAR_ACTIVITY, Number.class);
			query.setParameter(Activity.ACTIVITY_DESIGNATION, designation);

			Number result = query.getSingleResult();

			return result.intValue() >= 1;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean existsOccasionalActivity(String designation) {
		try {
			TypedQuery<Number> query = em.createNamedQuery(Activity.EXISTS_OCCASIONAL_ACTIVITY, Number.class);
			query.setParameter(Activity.ACTIVITY_DESIGNATION, designation);

			Number result = query.getSingleResult();

			return result.intValue() >= 1;
		} catch (Exception e) {
			return false;
		}
	}


	public List<OccasionalActivity> getOccasionalActivitiesBySpecialty(String spec) throws ApplicationException {
		TypedQuery<OccasionalActivity> query = em.createNamedQuery(Activity.FIND_BY_SPECIALTY, OccasionalActivity.class);
		query.setParameter(Activity.SPECIALTY, spec);
		try {
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new ApplicationException("List of Activities not found.", e);
		}
	}

	public OccasionalActivity getOccasionalActivityByDescription(String activityDescription) throws ApplicationException {
		TypedQuery<OccasionalActivity> query = em.createNamedQuery(OccasionalActivity.FIND_BY_DESCRIPTION, OccasionalActivity.class);
		query.setParameter(OccasionalActivity.DESCRIPTION, activityDescription);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException("Activity not found.", e);
		}
	}
}
