package pt.ulisboa.css.css2122_projeto1_033.utils;

import javax.persistence.Persistence;
import java.util.HashMap;

public class CreateDatabase {

	/**
	 * An utility class should not have public constructors
	 */
	private CreateDatabase () {
	}
	
	public static void main(String[] args) {
		HashMap<String, String> properties = new HashMap<>();
		properties.put("javax.persistence.schema-generation.database.action", "drop-and-create");
		properties.put("javax.persistence.schema-generation.create-source", "metadata");
		properties.put("javax.persistence.schema-generation.drop-source", "metadata");
		properties.put("javax.persistence.sql-load-script-source", "META-INF/load-script.sql");

   	 	Persistence.generateSchema("css2122_projeto1_033_JPA", properties);
	}
}
