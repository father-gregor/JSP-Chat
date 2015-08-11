package ua.com.benlinus92.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
public class OpenChatServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String SEND_MSG = "/send";
	private static final String LOAD_PAGE = "/loadmsg";
	private static final String GET_USERS = "/getusers";
	private static final String UPDATE_CHAT_MSG = "/update";
	private static final int MSG_LOAD_COUNT = 50;
	
	private MessageList msgList = MessageList.getInstance();
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setCharacterEncoding("UTF-8");
		String servletPath = req.getServletPath();
		JsonMessages updateList;
		if(servletPath.equals(LOAD_PAGE)) {
			updateList = msgList.getMessagesForNewUser(MSG_LOAD_COUNT);
			resp.setContentType("application/json");
			if(!msgList.isEmpty() || updateList.getArraySize() > 0) {
				resp.getWriter().print(updateList.createJsonObject());
			} else {
				resp.getWriter().print(new JsonObject());
			}
		}
		if(servletPath.equals(SEND_MSG)) { 
			String msg = req.getParameter("message");
			String user = req.getParameter("user");
			String receiver = req.getParameter("receiver");
			String date = req.getParameter("date");
			Message m = new Message(user, receiver, msg, date);
			msgList.add(m);
		}
		if(servletPath.equals(GET_USERS)) {
			String state = "";
			if(req.getParameter("state") != null)
				state = req.getParameter("state");
			resp.setContentType("application/json");
			//CHANGE (boolean)req.getSession().getAttribute("users_update") to that because of error casting(java 6?)
			if( ((Boolean)req.getSession().getAttribute("users_update")).booleanValue() || state.equals("onload")) {
				Users usersList = new Users();
				Map<HttpSession, String> map = new HashMap<HttpSession, String>((ConcurrentHashMap<HttpSession, String>)
						getServletContext().getAttribute("users"));
				for(Entry<HttpSession, String> entry : map.entrySet()) {
					String user = entry.getValue();
					String id = createUserId(entry.getKey().getId(), user);
					usersList.addUser(id, user);
				}
				String json = new GsonBuilder().create().toJson(usersList);
				System.out.println(json);
				req.getSession(false).setAttribute("users_update", false);
				resp.getWriter().print(new JsonParser().parse(json).getAsJsonObject());
			} else
				resp.getWriter().print(new JsonObject());
		}
		if(servletPath.equals(UPDATE_CHAT_MSG)) {
			String lastMsgTime = req.getParameter("last_msg_time");
			updateList = msgList.getNewMessages(lastMsgTime);
			resp.setContentType("application/json");
			if(!msgList.isEmpty() || updateList.getArraySize() > 0) {
				resp.getWriter().print(updateList.createJsonObject());
			} else {
				resp.getWriter().print(new JsonObject());
			}
		}
	}
	
	public synchronized String createUserId(String id, String user) {
		return user + id.substring(0, 4);
	}
}
