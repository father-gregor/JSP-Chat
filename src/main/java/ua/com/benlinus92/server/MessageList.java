package ua.com.benlinus92.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MessageList implements ServletContextListener {
	
	private static final int MESSAGE_LIMIT = 100;
	private static final MessageList msgList = new MessageList();

	private List<Message> list = new ArrayList<Message>();
	
	public static MessageList getInstance() {
		return msgList;
	}
	
	public synchronized void add(Message m) {
		if(list.size() >= MESSAGE_LIMIT)
			list.remove(0);
		list.add(m);
	}
	
	public synchronized JsonMessages getMessagesForNewUser(int count) {
		List<Message> onload_msg = new ArrayList<Message>();
		int begin_ind = 0;
		if(count < list.size())
			begin_ind = list.size() - count;
			
		try {
			for(int i = begin_ind; i < list.size(); i++)
				onload_msg.add(list.get(i));
		} catch(Exception e) {
			
		}
		JsonMessages result = new JsonMessages(onload_msg.toArray(new Message[onload_msg.size()]));
		return result;
	}
	
	public synchronized JsonMessages getNewMessages(String last_msg_time) throws NumberFormatException {
		Date lastMsgDate = new Date(Long.parseLong(last_msg_time));
		List<Message> updateList = new ArrayList<Message>();
		for(int i = list.size()-1; i > 0; i--) {
			Message m = list.get(i);
			Date listDate = new Date(Long.parseLong(m.getDate()));
			if(lastMsgDate.before(listDate))
				updateList.add(m);
			else
				i = 0; //чтобы не листать весь список
		}
		Collections.reverse(updateList);
		JsonMessages result = new JsonMessages(updateList.toArray(new Message[updateList.size()]));
		return result;
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {  }

	@Override
	public void contextInitialized(ServletContextEvent arg0) {  }
}
