package pt.ulisboa.css.css2122_projeto1_033.business.specialty;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class SpecialtyCatalog {
	private final EntityManager em;

	public SpecialtyCatalog(EntityManager em) {
		this.em = em;
	}

	public Specialty getSpecialty(String designation) {
		TypedQuery<Specialty> query = em.createNamedQuery(Specialty.FIND_BY_DESIGNATION, Specialty.class);
		query.setParameter(Specialty.SPECIALTY_DESIGNATION, designation);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

	public List<Specialty> getAllSpecialty() {
		try {
			TypedQuery<Specialty> query = em.createNamedQuery(Specialty.GET_ALL_SPECIALTY, Specialty.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public List<String> getAllSpecialtyNames() {
		try {
			TypedQuery<String> query = em.createNamedQuery(Specialty.GET_ALL_SPECIALTY_NAMES, String.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

}
