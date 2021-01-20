
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
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		return "Failed to create";
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
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (DataBaseException e) {
			e.printStackTrace();
		} catch (EntityNotFoundException e) {
			System.out.println("Event not found");
		}
		return "Failed to update";
	}

	@RequestMapping(value = "/event", method = RequestMethod.DELETE)
	@ResponseBody
	public String DeleteEvent(@RequestParam("id") String id) {
		try {
			eventService.removeEvent(id);
			return "Deleted";

		} catch (IllegalArgumentException | EntityNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return "Failed to delete";
	}

	@GetMapping(value = "/eventdate", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<Event> getEventByDate(@RequestParam("date") String date) {
		try {
			return eventHelper.retrieveByDate(date);
		} catch (ParseException e) {
		}

		return eventService.sortEventCreatedTime();
	}

	@GetMapping(value = "/timerange", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<Event> getEventByTimeRange(@RequestParam("start") Long start, @RequestParam("end") Long end) {
		return eventHelper.retrieveByTimeRange(start, end);

	}

}
