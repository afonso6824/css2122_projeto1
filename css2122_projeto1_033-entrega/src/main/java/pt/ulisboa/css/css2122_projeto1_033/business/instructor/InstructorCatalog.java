package pt.ulisboa.css.css2122_projeto1_033.business.instructor;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import pt.ulisboa.css.css2122_projeto1_033.business.activity.OccasionalActivity;

/**
 * Entity implementation class for Entity: InstructorCatalog
 */
public final class InstructorCatalog {
	private final EntityManager em;


	public InstructorCatalog(EntityManager em) {
		this.em = em;
	}


	public Instructor getInstructorById(int instructorId)  {
		return em.find(Instructor.class, instructorId);
	}

	public List<Instructor> getAvailableInstructors(OccasionalActivity correntActivity) {
		TypedQuery<Instructor> query = em.createNamedQuery(Instructor.FIND_BY_CERTIFICATE, Instructor.class);
		query.setParameter(Instructor.CERTIFICATE, correntActivity.getSpecialty().getCertification());
		try {
			List<Instructor> instructors = query.getResultList();
			return instructors.stream()
					.filter(i -> i.isAvailable(correntActivity))
					.collect(Collectors.toList());
		} catch (PersistenceException e) {
			return Collections.emptyList();
		}
	}
}
