package pt.ulisboa.css.css2122_projeto1_033.facade.services;

import java.util.Date;
import java.util.List;

import pt.ulisboa.css.css2122_projeto1_033.business.handlers.BuyParticipationRegularActivityHandler;
import pt.ulisboa.css.css2122_projeto1_033.business.payment_details.PaymentDetails;
import pt.ulisboa.css.css2122_projeto1_033.business.session.Schedule;
import pt.ulisboa.css.css2122_projeto1_033.facade.dtos.RegularActivityDTO;
import pt.ulisboa.css.css2122_projeto1_033.facade.exceptions.ApplicationException;

public class BuyParticipationRegularActivityService {
	private final BuyParticipationRegularActivityHandler buyParticipationRegularActivityHandler;
	
	public BuyParticipationRegularActivityService(BuyParticipationRegularActivityHandler handler) {
		this.buyParticipationRegularActivityHandler = handler;
	}
	
	public RegularActivityDTO joinRegularActivity(String description) throws ApplicationException{
		return buyParticipationRegularActivityHandler.joinRegularActivity(description);
	}
	
	public PaymentDetails selectOptions(List<Schedule> schedules, Date beginDate, int nMonths, String email) throws ApplicationException {
		return buyParticipationRegularActivityHandler.selectOptions(schedules ,beginDate, nMonths, email);
	}
}
