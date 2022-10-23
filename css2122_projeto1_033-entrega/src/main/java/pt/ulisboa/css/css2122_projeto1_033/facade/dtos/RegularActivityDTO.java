package pt.ulisboa.css.css2122_projeto1_033.facade.dtos;

import java.util.List;

import pt.ulisboa.css.css2122_projeto1_033.business.session.RegularSession;
import pt.ulisboa.css.css2122_projeto1_033.business.session.Schedule;

import java.io.Serializable;

public class RegularActivityDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private double price;
	// dia e hora
	private List<RegularSessionDTO> sessions;
	
	
	
	public RegularActivityDTO(double price, List<RegularSessionDTO> scheList) {
		this.price = price;
		this.sessions = scheList;
	}

	public double getPrice() {
		return price;
	}
	

	
	public List<RegularSessionDTO> getSessions() {
		return sessions;
	}

	@Override
	public String toString() {
		return	"price:" + price +
				", horarios:\n" + sessions.toString();
	}
}

