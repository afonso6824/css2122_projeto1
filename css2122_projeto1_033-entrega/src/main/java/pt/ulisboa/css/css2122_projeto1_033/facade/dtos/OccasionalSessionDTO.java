package pt.ulisboa.css.css2122_projeto1_033.facade.dtos;

import java.util.Date;

public class OccasionalSessionDTO {

	private Date dateWithHour;

	public OccasionalSessionDTO(Date dateWithHour) {
		this.dateWithHour = dateWithHour;
	}


	public Date getDateWithHour() {
		return dateWithHour;
	}

	public void setDateWithHour(Date dateWithHour) {
		this.dateWithHour = dateWithHour;
	}
}
