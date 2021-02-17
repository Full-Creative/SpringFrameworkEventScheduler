
package com.eventschedule.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventschedule.datastore.DataBaseException;
import com.eventschedule.model.Event;
import com.eventschedule.service.event.EventHelper;
import com.eventschedule.service.event.EventServiceImp;
import com.eventschedule.taskqueue.ConfirmationTask;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

@Controller
@RequestMapping("/event")
public class EventController {
	EventServiceImp eventService = new EventServiceImp();
	EventHelper eventHelper = new EventHelper();
	
	@PostMapping
	@ResponseBody
	public Event createEvent(@RequestBody Event event) {
		try {
			if (event == null)
				return new Event();
			Event obj = eventService.addEvent(event);
			
			return obj;

		} catch (IllegalArgumentException e) {
			return new Event();
		} catch (Exception e) {
			return new Event();
		}
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<Event> getEvents() {
		return eventService.sortEventCreatedTime();
	}

	@PutMapping
	@ResponseBody
	public Event updateEvent(@RequestBody Event event) {
		try {
			if (event == null)
				return new Event();
			return eventService.modifyEvent(event);
		} catch (EntityNotFoundException | IllegalArgumentException e) {
			return new Event();
		} catch (Exception e) {
			return new Event();
		}

	}

	@DeleteMapping("/{eventID}")
	@ResponseBody
	public String DeleteEvent(@PathVariable(value = "eventID") String id) {
		try {
			if (id == null)
				return "Event ID unavailable";
			eventService.removeEvent(id);
			return "Deleted";

		} catch (IllegalArgumentException | EntityNotFoundException e) {
			return "Event not found. Failed to delete";
		} catch (Exception e) {
			return "Failed to delete";
		}

	}

	@GetMapping(value = "/date", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<Event> getEventByDate(@RequestParam("date") String date) {
		try {
			if (date == null)
				return null;
			return eventHelper.retrieveByDate(date);
		} catch (ParseException | IllegalArgumentException e) {
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	@GetMapping(value = "/timerange", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<Event> getEventByTimeRange(@RequestParam("start") Long start, @RequestParam("end") Long end) {
		try {
			if (start == null || end == null)
				return null;
			return eventHelper.retrieveByTimeRange(start, end);
		} catch (IllegalArgumentException e) {
			return null;
		} catch (Exception e) {
			return null;
		}

	}

}
