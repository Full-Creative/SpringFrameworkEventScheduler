
package com.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.MediaType;
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
import service.event.EventHelper;
import service.event.EventServiceImp;

/**
 * @author imssbora
 */
@Controller
public class EventController {
	EventServiceImp eventService = new EventServiceImp();
	EventHelper eventHelper = new EventHelper();

	@RequestMapping(value = "/event", method = RequestMethod.POST)
	@ResponseBody
	public String createEvent(@RequestBody Event event) {
		try {
			eventService.addEvent(event);
			return "Created event";
		} catch (DataBaseException | IllegalArgumentException e) {
			return "Failed to create event.";
		}
	}

	@GetMapping(value = "/event", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<Event> getEvents() {
		return eventService.sortEventCreatedTime();
	}

	@RequestMapping(value = "/event", method = RequestMethod.PUT)
	@ResponseBody
	public String updateEvent(@RequestBody Event event) {
		try {
			eventService.modifyEvent(event);
			return "Modified event";
		} catch (EntityNotFoundException | DataBaseException | IllegalArgumentException e) {
			return "Failed to update.Event not found";
		}

	}

	@RequestMapping(value = "/event", method = RequestMethod.DELETE)
	@ResponseBody
	public String DeleteEvent(@RequestParam("id") String id) {
		try {
			eventService.removeEvent(id);
			return "Deleted";

		} catch (IllegalArgumentException | EntityNotFoundException e) {
			return "Event not found. Failed to delete";
		}

	}

	@GetMapping(value = "/eventdate", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<Event> getEventByDate(@RequestParam("date") String date) {
		try {
			return eventHelper.retrieveByDate(date);
		} catch (ParseException | IllegalArgumentException e) {
			return null;
		}

	}

	@GetMapping(value = "/timerange", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<Event> getEventByTimeRange(@RequestParam("start") Long start, @RequestParam("end") Long end) {
		try {
			return eventHelper.retrieveByTimeRange(start, end);
		} catch (IllegalArgumentException e) {
			return null;
		}

	}

}
