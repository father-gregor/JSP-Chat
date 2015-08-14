package ua.com.benlinus92.server;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOGIN_PATTERN = "[a-zA-Z_0-9\\-]+";
	private static final String PASSWORD_PATTERN = "[a-zA-Z_0-9\\-]+";
	private static final String LOGIN = "/login";
	private static final String LOGOUT = "/logout";
	private static final String ERR_01 = "Поля должны быть заполнены!";
	private static final String ERR_02 = "Неверный логин или пароль!";
	private static final String ERR_03 = "Такой пользователь уже существует!";
       
    public LoginServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	if(req.getServletPath().equals(LOGOUT)) {
			resp.setHeader("Cache-Control", "no-cache, no-store");
			resp.setHeader("Pragma", "no-cache");
			System.out.println(req.getServletPath());
			req.getSession().invalidate();
			resp.sendRedirect(req.getContextPath());
    	}
    }
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(req.getServletPath().equals(LOGIN)) {
			String login = req.getParameter("login");
			String password = req.getParameter("password"); 
			DatabaseSQL dsql = (DatabaseSQL)getServletContext().getAttribute("dsql");
			
			if(!login.equals("") && !password.equals("")) {
				boolean regResult = false;
				boolean loginMatch = login.matches(LOGIN_PATTERN);
				boolean passwordMatch = password.matches(PASSWORD_PATTERN);
				
				if (!loginMatch | !passwordMatch) {
					req.setAttribute("error", ERR_02);
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/");
			        rd.forward(req, resp);
					//resp.sendRedirect(req.getHeader("Referer"));
					return;
				}
				if(req.getParameter("send").equals("register")) {
					regResult = LoginProcess.registerNewUser(login, password, dsql);
					if(regResult == false) {
						req.setAttribute("error", ERR_03);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/");
				        rd.forward(req, resp);
					}
				}
				if(req.getParameter("send").equals("log") || regResult == true) {
					boolean checkResult = LoginProcess.checkUser(login, password, dsql);
					if(checkResult == true) {
						HttpSession session = req.getSession(false);
						session.setAttribute("user", login);
						session.setAttribute("users_update", true);
						ConcurrentHashMap<HttpSession, String> users = (ConcurrentHashMap<HttpSession, String>) getServletContext().getAttribute("users");
						UsersListener.userLoggedIn();
						users.put(session, login);
						getServletContext().setAttribute("users", users);
						resp.sendRedirect("chat");
					} else {
						req.setAttribute("error", ERR_02);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/");
				        rd.forward(req, resp);
					}
				}
			} else {
				req.setAttribute("error", ERR_01);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/");
		        rd.forward(req, resp);;
			}
		}
	}

}
