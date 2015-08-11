package ua.com.benlinus92.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class UsersListener implements ServletContextListener, HttpSessionListener {

	private static final String EMOJI_PATH = "/images/";
	public static Map<HttpSession, String> users = new ConcurrentHashMap<HttpSession, String>();
	
    public void contextDestroyed(ServletContextEvent arg0)  { 
         
    }

    public void contextInitialized(ServletContextEvent event)  { ;
         ServletContext sc = event.getServletContext();
         sc.setAttribute("users", users);
         try {
			sc.setAttribute("emojiList", readEmojiPictures(EMOJI_PATH, event));
		} catch(IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch(SecurityException e) {
			e.printStackTrace();
		}
			
    }

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		event.getSession().setAttribute("users_update", false);
	}
	
	public static void userLoggedIn() {
		for(Entry<HttpSession, String> entry : users.entrySet()) {
			HttpSession hs = entry.getKey();
			String nick = entry.getValue();
			users.remove(entry.getKey(), entry.getValue());
			hs.setAttribute("users_update", true);
			users.put(hs, nick);
			System.out.println(nick);
		}
	}

	public synchronized List<String> readEmojiPictures(String path, ServletContextEvent event) 
			throws NullPointerException, SecurityException, IOException {
		List<String> images = new ArrayList<String>();
		Iterator<String> it = event.getServletContext().getResourcePaths(path).iterator();
		while(it.hasNext()) {
			images.add(it.next().replace(path, ""));
		}
		Collections.sort(images);
		return images;
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		users.remove(event.getSession());
		event.getSession().removeAttribute("user");
		for(Entry<HttpSession, String> entry : users.entrySet()) {
			HttpSession hs = entry.getKey();
			String nick = entry.getValue();
			users.remove(entry.getKey(), entry.getValue());
			hs.setAttribute("users_update", true);
			users.put(hs, nick);
			System.out.println(nick);
		}
	}

}
