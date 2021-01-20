package com.controller;

import java.util.List;

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
		} catch (EntityNotFoundException | NullPointerException e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	@RequestMapping(value = "/event", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> addParticipant(@RequestBody ParticipantDetails participant) {

		EventServiceImp eventService = new EventServiceImp();
		try {
			eventService.addParticipant(participant);
			return ResponseEntity.status(200).body("Added Participant!");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(501).body("Event unavailable");
		} catch (DataBaseException e) {
			return ResponseEntity.status(501).body("Participant does not exist please create!");
		}
	}
}
