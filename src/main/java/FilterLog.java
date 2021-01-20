import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(servletNames = { "ScheduleEvent", "Events" })
public class FilterLog implements Filter {
	private FilterConfig filterConfigObj = null;

	public void init(FilterConfig filterConfigObj) {
		this.filterConfigObj = filterConfigObj;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		String remoteAddress = req.getRemoteAddr();
		String uri = ((HttpServletRequest) req).getRequestURI();
		String protocol = req.getProtocol();
		System.out.println(
				"User Logged !  User IP: " + remoteAddress + " Resource File: " + uri + " Protocol: " + protocol);
		chain.doFilter(req, resp);

		filterConfigObj.getServletContext().log("Logging Filter Servlet called");
		filterConfigObj.getServletContext().log("**************************");
		filterConfigObj.getServletContext()
				.log("User Logged !  User IP: " + remoteAddress + " Resource File: " + uri + " Protocol: " + protocol);
	}

	public void destroy() {
	}

}
