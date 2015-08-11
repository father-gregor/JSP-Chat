package ua.com.benlinus92.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DatabaseContextListener implements ServletContextListener {

	DatabaseSQL dsql;
    public DatabaseContextListener() {
        
    }
    
    public void contextDestroyed(ServletContextEvent event)  { 
         if(dsql != null)
        	 dsql.closeDB();
    }

    public void contextInitialized(ServletContextEvent event)  { 
        ServletContext sc = event.getServletContext();
        dsql = DatabaseSQL.getInstance();
        sc.setAttribute("dsql", dsql);
    }
	
}
