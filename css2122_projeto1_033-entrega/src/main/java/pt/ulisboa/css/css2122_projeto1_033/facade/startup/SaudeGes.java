package pt.ulisboa.css.css2122_projeto1_033.facade.startup;

import pt.ulisboa.css.css2122_projeto1_033.business.handlers.CreateActivityHandler;
import pt.ulisboa.css.css2122_projeto1_033.business.handlers.ScheduleOccasionalActivityHandler;
import pt.ulisboa.css.css2122_projeto1_033.business.handlers.BuyParticipationRegularActivityHandler;
import pt.ulisboa.css.css2122_projeto1_033.business.handlers.ScheduleRegularActivityHandler;
import pt.ulisboa.css.css2122_projeto1_033.facade.exceptions.ApplicationException;
import pt.ulisboa.css.css2122_projeto1_033.facade.services.CreateActivityService;
import pt.ulisboa.css.css2122_projeto1_033.facade.services.BuyParticipationRegularActivityService;
import pt.ulisboa.css.css2122_projeto1_033.facade.services.ScheduleRegularActivityService;
import pt.ulisboa.css.css2122_projeto1_033.facade.services.ScheduleOccasionalActivityService;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SaudeGes {
	private EntityManagerFactory emf;
	private CreateActivityService createActivityService;
	private ScheduleOccasionalActivityService scheduleOccasionalActivityService;
	private BuyParticipationRegularActivityService buyParticipationRegularActivityService;
	private ScheduleRegularActivityService scheduleRegularActivityService;

	public void run() throws ApplicationException {
		try {
			emf = Persistence.createEntityManagerFactory("css2122_projeto1_033_JPA");
			createActivityService = new CreateActivityService(new CreateActivityHandler(emf));
			scheduleRegularActivityService = new ScheduleRegularActivityService(new ScheduleRegularActivityHandler(emf));
			buyParticipationRegularActivityService = new BuyParticipationRegularActivityService(new BuyParticipationRegularActivityHandler(emf));
			scheduleOccasionalActivityService = new ScheduleOccasionalActivityService(new ScheduleOccasionalActivityHandler(emf));

		} catch (Exception e) {
			throw new ApplicationException("Error connecting to database!", e);
		}
	}

	/**
	 * Closes the database connection
	 */
	public void stopRun() {
		emf.close();
	}

	public CreateActivityService getCreateActivityService() {
		return this.createActivityService;
	}

	public BuyParticipationRegularActivityService getBuyParticipationService() {
		return this.buyParticipationRegularActivityService;
	}

	public ScheduleRegularActivityService getSetScheduleActivityService() {
		return scheduleRegularActivityService;
	}

	public ScheduleOccasionalActivityService getScheduleOccasionalActivityService() {
		return this.scheduleOccasionalActivityService;
	}
}
