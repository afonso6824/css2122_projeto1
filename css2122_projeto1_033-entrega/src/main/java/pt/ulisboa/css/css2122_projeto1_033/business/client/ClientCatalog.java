package pt.ulisboa.css.css2122_projeto1_033.business.client;

import javax.persistence.*;


public class ClientCatalog {

	private EntityManager em;


	public ClientCatalog(EntityManager em) {
		this.em = em;
	}


	public Client getClientByEmail(String email) {
		TypedQuery<Client> query = em.createNamedQuery(Client.FIND_BY_EMAIL, Client.class);
		query.setParameter(Client.CLIENT_EMAIL, email);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
}
