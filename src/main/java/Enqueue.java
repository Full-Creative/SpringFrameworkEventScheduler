
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@WebServlet(name = "TaskEnqueue", description = "taskqueue: Enqueue a job with a key", urlPatterns = "/taskqueues/enqueue")
public class Enqueue extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static volatile int TASK_COUNTER = 0;
	private static final Logger log = Logger.getLogger(Enqueue.class.getName());

	// Executed by user menu click
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String key = req.getParameter("key");
		Queue q=QueueFactory.getQueue("my-queue");
	//	Queue queue = QueueFactory.getDefaultQueue();
		q.add(TaskOptions.Builder.withUrl("/taskqueues/worker").param("key", key));
		// [END addQueue]

		resp.sendRedirect("/");
		// Build a task using the TaskOptions Builder pattern from ** above
		/*
		 * Queue queue = QueueFactory.getDefaultQueue();
		 * queue.add(TaskOptions.Builder.withUrl("/taskqueues/enqueue").method(
		 * TaskOptions.Method.POST)); //
		 * queue.add(TaskOptions.Builder.withUrl("/worker").param("key", key));
		 * resp.getWriter().println("Task have been added to default queue...");
		 * 
		 * resp.getWriter().println("Refresh this page to add another count task");
		 */
	}

	// Executed by TaskQueue
	/*
	 * @Override protected void doPost(HttpServletRequest req, HttpServletResponse
	 * resp) throws ServletException, IOException {
	 * 
	 * // This is the body of the task for (int i = 0; i < 2; i++) {
	 * 
	 * log.info("Processing: " + req.getHeader("X-AppEngine-TaskName") + "-" +
	 * TASK_COUNTER++); } resp.getWriter().print("Sending simple email.");
	 * sendSimpleMail();
	 * 
	 * }
	 */

	private void sendSimpleMail() {
		// [START simple_example]
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("vaishnavi.rama@anywhere.co", "Example.com Admin"));
			msg.addRecipient(Message.RecipientType.TO,
					new InternetAddress("vaishnaviramabhadran@gmail.com", "Mr. User"));
			msg.setSubject("Your Example.com account has been activated");
			msg.setText("This is a test");
			Transport.send(msg);
		} catch (AddressException e) {
			// ...
		} catch (MessagingException e) {
			// ...
		} catch (UnsupportedEncodingException e) {
			// ...
		}
		// [END simple_example]
	}

}
