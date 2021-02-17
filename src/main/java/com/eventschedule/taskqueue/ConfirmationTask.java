package com.eventschedule.taskqueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.common.net.MediaType;
import com.google.gson.Gson;

@WebServlet(name = "TaskConfirmation", description = "TaskQueues: confirm", urlPatterns = "/taskqueues/confirmation")
public class ConfirmationTask extends HttpServlet {
	public void enQueue(String email, String msg) {
		Queue q = QueueFactory.getQueue("my-queue");

		HashMap<String, String> taskMessage = new HashMap();
		taskMessage.put("email", email);
		taskMessage.put("message", msg);

		String jsonString = new Gson().toJson(taskMessage);
		q.add(TaskOptions.Builder.withUrl("/taskqueues/confirmation").payload(jsonString.getBytes(),
				MediaType.JSON_UTF_8.toString()));
	}
	// max payload

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
			buffer.append(System.lineSeparator());
		}
		String data = buffer.toString();
		Map<String, String> h = new Gson().fromJson(data, HashMap.class);
		sendMail("vaishnavi.rama@anywhere.co", h.get("email"), "Confirmation", h.get("message"));
	}

	public Boolean sendMail(String fromAddr, String toAddressList, String subject, String body) {
		MailService mailService = MailServiceFactory.getMailService();
		MailService.Message message = new MailService.Message();
		message.setSender(fromAddr);
		message.setSubject(subject);
		message.setTo(toAddressList);
		message.setHtmlBody(body);
		try {
			mailService.send(message);
		} catch (IOException e) {
			System.out.print(e.getMessage());
			return false;
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return true;
	}

}