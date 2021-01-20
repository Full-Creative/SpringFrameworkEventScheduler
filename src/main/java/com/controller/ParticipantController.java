package com.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.datastore.EntityNotFoundException;

import datastore.DataBaseException;
import helper.SerializerHelper;
import model.Event;
import model.ParticipantDetails;
import service.event.EventServiceImp;
import service.participant.ParticipantServiceImp;

@Controller
@RequestMapping("participant")
public class ParticipantController {
	ParticipantServiceImp participantService = new ParticipantServiceImp();

	@GetMapping(value = "/event", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<Event> getEventByMail(@RequestParam("email") String email) {
		try {
			return participantService.retrieveEvents(email);
		} catch (EntityNotFoundException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/event", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> addParticipant(@RequestBody ParticipantDetails participant) {

		EventServiceImp eventService = new EventServiceImp();
		try {
			eventService.addParticipant(participant);
			return new ResponseEntity<>("Added Participant!", HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			// response.getWriter().print("Failed participant");
			System.out.println("Event not available");
		}
		// doGet(request, response);
		catch (DataBaseException e) {
			return new ResponseEntity<>("Participant does not exist please create!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("Failed!", HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
