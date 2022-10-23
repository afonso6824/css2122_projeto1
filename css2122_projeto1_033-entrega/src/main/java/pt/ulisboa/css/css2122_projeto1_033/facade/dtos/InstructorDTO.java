package pt.ulisboa.css.css2122_projeto1_033.facade.dtos;


public class InstructorDTO {

	private int id;

	private String name;


	public InstructorDTO(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "id: " + id + "name: " + name;
	}
}
