import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import helper.ConnectionHelper;

@SuppressWarnings("serial")
public class CronServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) req;
			ConnectionHelper conHelper = new ConnectionHelper();
			
			if (httpRequest.getHeader("X-Appengine-Cron").equals("true")) {
				HttpURLConnection connection = conHelper
						.createConnection(new URL("https://hooks.zapier.com/hooks/catch/9269338/o09n1hp/"));
				String msg = "{\"msg\": \"Hello from cron job\"}";
				conHelper.IOOperation(msg, connection);
			}
		} catch (IOException ex) {
			System.out.print(ex.getMessage());
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}