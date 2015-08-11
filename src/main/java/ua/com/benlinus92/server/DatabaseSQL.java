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
			emf = Persistence.createEntityManagerFactory(DB_NAME);
		}
		return dsql;
	}
	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
	
	public void closeDB() {
		emf.close();
	}
}
