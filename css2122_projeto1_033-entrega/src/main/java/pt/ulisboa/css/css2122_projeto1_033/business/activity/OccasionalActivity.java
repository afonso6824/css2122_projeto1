package pt.ulisboa.css.css2122_projeto1_033.business.activity;

import pt.ulisboa.css.css2122_projeto1_033.business.session.OccasionalSession;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

import javax.persistence.OneToMany;
import java.util.List;


@Entity
@DiscriminatorValue(value = "Occasional")
@NamedQuery(name=OccasionalActivity.FIND_BY_DESCRIPTION, query="SELECT oA FROM OccasionalActivity oA WHERE oA.description =:" +
		OccasionalActivity.DESCRIPTION)
public class OccasionalActivity extends Activity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// Named query name constants
	public static final String FIND_BY_DESCRIPTION = "FindByDescription";
	public static final String DESCRIPTION = "description";

	@OneToMany
	private List<OccasionalSession> occasionalSessions;

	public OccasionalActivity() {
		// Constructor needed by JPA.
	}

	public OccasionalActivity(String description, int nSessions, int duration, int price) {
		super(description, nSessions, duration, price);
	}
	
	public void setOccasionalSessions(List<OccasionalSession> occasionalSessions) {
		this.occasionalSessions = occasionalSessions;
	}
	
	public List<OccasionalSession> getOccasionalSessions(){
		return occasionalSessions;
	}

}