package pt.ulisboa.css.css2122_projeto1_033.facade.dtos;

import pt.ulisboa.css.css2122_projeto1_033.business.session.OccasionalSession;

import java.util.List;

public class OccasionalActivityDTO {

	private String description;

	private List<OccasionalSessionDTO> occasionalSessionDTOS;

	public OccasionalActivityDTO(String description, List<OccasionalSessionDTO> occasionalSessionDTOS) {
		this.description = description;
		this.occasionalSessionDTOS = occasionalSessionDTOS;
	}

	public List<OccasionalSessionDTO> getOccasionalSessionDTOS() {
		return occasionalSessionDTOS;
	}

	public String getDescription() {
		return description;
	}

	public void setOccasionalSessionDTOS(List<OccasionalSessionDTO> occasionalSessionDTOS) {
		this.occasionalSessionDTOS = occasionalSessionDTOS;
	}
}
