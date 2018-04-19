import javax.persistence.*;
import java.util.*;

public class Main {

	public static void main(String[] args) {

		Player s;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("player_info.odb");		
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		
		s = new Player("Aayush", 5,67);
		em.persist(s);
		s = new Player("Tapan",6,75);
		em.persist(s);
		s = new Player("Askhay",3, 20000);
		em.persist(s);
		s = new Player("Ajinath",6, 200);
		em.persist(s);
		s = new Player("Askhay",5, 15000);
		em.persist(s);
		s = new Player("Askhay",8, 10000);
		em.persist(s);
		
		em.getTransaction().commit();
		em.getTransaction().begin();
	//	int q1=em.createQuery("delete from Player").executeUpdate();
		
		Query q1=em.createQuery("select p from Player p");
		Query q2=em.createQuery("select name from Player where run>5000");
		Query q3=em.createQuery("select from Player order by totalmatch");
		Query q4=em.createQuery("select name from Player where run>3000 && run<13000");
		
		
		
		//System.out.println("All Players \n"+ q1.getResultList());
		
		System.out.println("All Players \n"+ q1.getResultList());
		System.out.println("Query 1 \n"+ q2.getResultList());
		System.out.println("Query 2 \n"+ q3.getResultList());
		System.out.println("Query 3 \n"+ q4.getResultList());
		
		em.getTransaction().commit();
		
	}
	
}
