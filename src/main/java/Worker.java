import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;
//import com.google.apphosting.api.ApiProxy.LogRecord.Level;

//  [START worker]
// The Worker servlet should be mapped to the "/worker" URL.
// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "TaskWorker", description = "TaskQueues: worker", urlPatterns = "/taskqueues/worker")
public class Worker extends HttpServlet {

	private static final Logger log = Logger.getLogger(Worker.class.getName());

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * String key = request.getParameter("key"); EventServiceImp eventService = new
		 * EventServiceImp();
		 * 
		 * // Do something with key. // [START_EXCLUDE] log.info("Worker is processing "
		 * + key); ConnectionHelper conHelper = new ConnectionHelper();
		 * HttpURLConnection connection = conHelper .createConnection(new
		 * URL("https://hooks.zapier.com/hooks/catch/9269338/opwwbyx/")); String
		 * collabmsg = "{\"msg\": \"Hello! From worker task\"}";
		 * conHelper.IOOperation(collabmsg, connection);
		 */
		try {
			sendMail("karthick.raman@anywhere.co", Stream.of("vaishnavi.rama@anywhere.co").collect(Collectors.toList()),
					"Test Subject", "Test Body", null, null, null);
		} catch (Exception e) {

			System.out.print(e.getMessage());
		}
	}

	public Boolean sendMail(String fromAddr, List<String> toAddressList, String subject, String body,
			byte[] attachmentBytes, String attachmentName, String mimeType) {
		MailService mailService = MailServiceFactory.getMailService();
		MailService.Message message = new MailService.Message();
		message.setSender(fromAddr);
		message.setSubject(subject);
		message.setTo(toAddressList);
		message.setHtmlBody("<HTML>" + body + "</HTML>");
//		if (attachmentName == null) {
//			attachmentName = "Report.txt";
//		}
//		MailService.Attachment attachment = new MailService.Attachment(attachmentName, attachmentBytes);
//		message.setAttachments(attachment);
		try {
			mailService.send(message);
		} catch (IOException e) {
			log.log(Level.SEVERE, "Could not send email with attachment", e);
			return false;
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return true;
	}

	private String sendEmail() throws IOException, AddressException {
		MailService mailService = MailServiceFactory.getMailService();
		MailService.Message message = new MailService.Message();
		message.setSender(new InternetAddress("vaishnavi.rama@anywhere.co").toString());

		String htmlContent = "Hello! From events";

		message.setTo("karthick.raman@anywhere.co");
		message.setReplyTo("vaishnavi.rama@anywhere.co");
		message.setSubject("Exception While Booking Appt");
		message.setHtmlBody(htmlContent);
		mailService.send(message);
		return "success";

	}

//	private void sendMail() throws ClassNotFoundException {
//		Properties props = new Properties();
//		Session session = Session.getDefaultInstance(props, null);
//
//		try {
//			Message msg = new MimeMessage(session);
//			msg.setFrom(new InternetAddress("vaishnavi.rama@anywhere.co", "Example.com Admin"));
//			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("karthick.raman@anywhere.co", "Mr. User"));
//			msg.setSubject("Your Example.com account has been activated");
//			msg.setText("This is a test");
//			Transport.send(msg);
//		} catch (AddressException e) {
//			System.out.print(e.getMessage());
//
//		} catch (MessagingException e) {
//			System.out.print(e.getMessage());
//
//		} catch (UnsupportedEncodingException e) {
//			System.out.print(e.getMessage());
//		}
//	}

}
