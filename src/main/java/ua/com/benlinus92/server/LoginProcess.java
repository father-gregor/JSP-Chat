package ua.com.benlinus92.server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class LoginProcess {
	 
	@SuppressWarnings("unchecked")
	public static boolean checkUser(String login, String password, DatabaseSQL dsql) {
		boolean res = false;
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!+++++++++++++++++++++++++++" + System.getenv("OPENSHIFT_MYSQL_DB_PORT"));
		System.out.println("Sunshine");
		if(dsql == null)
			System.out.println("Some words");
		else
			System.out.println("Little less");
		EntityManager em = null;
		dsql.getEntityManagerFactory();//.createEntityManager();
		try {
			Query q = em.createQuery("SELECT r from Login r WHERE r.login = :login", Login.class);
			q.setParameter("login", login);
			List<Login> results = (List<Login>) q.getResultList();
			for(Login log: results) {
				String pass_hash = log.getPassword();
				if(BCrypt.checkpw(password, pass_hash))
					res = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean registerNewUser(String login, String password, DatabaseSQL dsql) {
		boolean res = false;
		EntityManager em = dsql.getEntityManagerFactory().createEntityManager();
		try{
			Query q = em.createQuery("SELECT r from Login r WHERE r.login = :login", Login.class);
			q.setParameter("login", login);
			List<Login> results = (List<Login>) q.getResultList();
			if(results.isEmpty()) {
				try {
					String pass_hash = BCrypt.hashpw(password, BCrypt.gensalt());
					Login l = new Login(login, pass_hash);
					em.getTransaction().begin();
					em.persist(l);
					em.getTransaction().commit();
					res = true;
				} catch(Exception e) {
					em.getTransaction().rollback();
				}
			}
		} catch(Exception e) {
			res = false;
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return res;
	}
}
