package teste;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("unitPSC");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();


	}

}