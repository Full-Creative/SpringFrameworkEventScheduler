import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebFilter("/*")
public class HeaderFilter implements Filter {
	private FilterConfig filterConfigObj = null;

	public void init(FilterConfig filterConfigObj) {
		this.filterConfigObj = filterConfigObj;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) req;
		String val = httpRequest.getHeader("user");
		
		//if( val!=null && val.equals("user"))
		{
			System.out.println("In header filter");
			chain.doFilter(req, resp);
		}
	//	else
		{
		//	HttpServletResponse response = (HttpServletResponse) resp;
		//	response.sendError(404);
		}
	}

	public void destroy() {
	}

}
