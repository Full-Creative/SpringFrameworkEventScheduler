package com.eventschedule.controller;

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

import com.eventschedule.datastore.DataBaseException;
import com.eventschedule.model.Event;
import com.eventschedule.model.ParticipantDetails;
import com.eventschedule.service.event.EventServiceImp;
import com.eventschedule.service.participant.ParticipantServiceImp;
import com.google.appengine.api.datastore.EntityNotFoundException;

@Controller
@RequestMapping("participant")
public class ParticipantController {

	@GetMapping(value = "/event", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<Event> getEventByMail(@RequestParam("email") String email) {
		// email null check
		try {
			if (email == null)
				return null;
			return new ParticipantServiceImp().retrieveEvents(email);

		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	@RequestMapping(value = "/event", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> addParticipant(@RequestBody ParticipantDetails participant) {

		try {
			if(participant==null)
				return ResponseEntity.status(400).body("Participant not added. please add again!");
			new EventServiceImp().addParticipant(participant);
			return ResponseEntity.status(200).body("Added Participant!");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(400).body("Event unavailable");
		} catch (DataBaseException e) {
			return ResponseEntity.status(400).body("Participant not added. please add again!");
		}
	}
}
