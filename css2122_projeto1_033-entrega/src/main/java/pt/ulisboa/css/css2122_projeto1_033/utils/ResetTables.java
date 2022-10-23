package pt.ulisboa.css.css2122_projeto1_033.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.SQLException;

public class ResetTables {

	public void resetDB() throws IOException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("css2122_projeto1_033_JPA");
		new RunSQLScript().runScript(emf, "META-INF/reset-tables.sql");
		emf.close();
	}

	public static void main(String[] args) throws IOException {
		new ResetTables().resetDB();
	}

}
