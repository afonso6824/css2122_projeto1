package pt.ulisboa.css.css2122_projeto1_033.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class RunSQLScript {

	public void runScript (EntityManagerFactory emf, String scriptFilename) throws IOException {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(scriptFilename))))) {
		    String command;
		    int i = 1;
		    EntityManager em = emf.createEntityManager();
		    while ((command = br.readLine()) != null) {
		        System.out.println(i + ": " + command);
		        i++;
			    em.getTransaction().begin();
		        Query q = em.createNativeQuery(command);
		        q.executeUpdate();
		        em.getTransaction().commit();
		    }
		    em.close();
		}
	}
	
}
