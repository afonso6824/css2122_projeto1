package pt.ulisboa.css.css2122_projeto1_033.facade.services;

import pt.ulisboa.css.css2122_projeto1_033.business.handlers.CreateActivityHandler;
import pt.ulisboa.css.css2122_projeto1_033.facade.exceptions.ApplicationException;

import javax.persistence.EntityManager;
import java.util.List;

public class CreateActivityService {
	private final CreateActivityHandler createActivityHandler;

	public CreateActivityService(CreateActivityHandler createActivityHandler) {
		this.createActivityHandler = createActivityHandler;
	}

	public void createActivity(String specialtyName, String description, boolean isRegular, int nSessions, int duration,
							   int price, int maxParticipants) throws ApplicationException {

		createActivityHandler
				.createActivity(specialtyName, description, isRegular, nSessions, duration, price, maxParticipants);
	}

	public List<String> createActivity() throws ApplicationException {
		return createActivityHandler.createActivity();
	}

}
