package ua.com.benlinus92.server;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoggedChatFilter implements Filter {

	private FilterConfig config;
    public LoggedChatFilter() { }

	public void destroy() { }

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String attr = (String)session.getAttribute("user");
		if(attr != null) {
			ConcurrentHashMap<HttpSession, String> users = (ConcurrentHashMap<HttpSession, String>) config.getServletContext().getAttribute("users");
			if(users.get(session) != null)
				chain.doFilter(request, response);
			else
				resp.sendRedirect(req.getContextPath() + "/");
		} else
			resp.sendRedirect(req.getContextPath() + "/");
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}

}
