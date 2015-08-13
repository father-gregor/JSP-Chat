package ua.com.benlinus92.server;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseSQL {
	private static final DatabaseSQL dsql = new DatabaseSQL();
	private static EntityManagerFactory emf;
	private static final String DB_NAME = "JSPchat";
	
	private DatabaseSQL() { }
	
	public static DatabaseSQL getInstance() {
		if(emf == null) {
			emf = Persistence.createEntityManagerFactory(System.getenv("OPENSHIFT_APP_NAME"));
			System.out.println("All fine");
			if(emf == null)
				System.out.println("NOT OK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		return dsql;
	}
	public EntityManagerFactory getEntityManagerFactory() {
		System.out.println("NOT OK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return emf;
	}
	
	public void closeDB() {
		emf.close();
	}
}
