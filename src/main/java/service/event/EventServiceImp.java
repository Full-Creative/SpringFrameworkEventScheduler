package service.event;

import java.util.Map.Entry;

import com.google.appengine.api.datastore.EntityNotFoundException;

import datastore.DataBaseException;
import datastore.EventsDB;
import model.Event;
import model.ParticipantDetails;

import java.text.ParseException;
import java.util.*;

public class EventServiceImp implements EventService {
	public EventsDB eventDB;

	public EventServiceImp() {
		eventDB = new EventsDB();
	}

	@Override
	public Event addEvent(Event eventDetails) {
		if (eventDetails == null || eventDetails.getEventTime() == 0 || eventDetails.getEventDuration() == 0)
			throw new IllegalArgumentException("Event Details not available");
		boolean isInserted = false;
		try {
			isInserted = eventDB.setEvent(eventDetails);
			if (!isInserted)
				throw new DataBaseException("Not inserted");
		} catch (DataBaseException e) {
			System.out.println(e.getMessage());
		}
		return eventDetails;
	}

	@Override
	public Event modifyEvent(Event event) throws DataBaseException, EntityNotFoundException {
		if (retrieveById(event.getEventID()) == null)
			throw new IllegalArgumentException("Event not available");
		boolean modified = false;
		modified = eventDB.updateEvent(event);
		if (!modified)
			throw new DataBaseException("Not modified");
		return event;
	}

	@Override
	public void removeEvent(String id) throws EntityNotFoundException {
		if (retrieveById(id) == null || id == null)
			throw new IllegalArgumentException("Event not available");
		eventDB.deleteEvent(id);
	}

	public ParticipantDetails addParticipant(ParticipantDetails participant) throws EntityNotFoundException, DataBaseException {
		if (participant == null || participant.getEmail() == null)
			throw new IllegalArgumentException("Participant Details not available");
		if (retrieveById(participant.getEventID()) == null || participant.getEventID() == null)
			throw new IllegalArgumentException("No such event");
		boolean isInserted = false;
			isInserted = eventDB.addParticipant(participant);
			if (!isInserted)
				throw new DataBaseException("Not inserted");
		
		return participant;

	}

	@Override
	public Event retrieveById(String id) throws EntityNotFoundException {
		if (id == null)
			throw new IllegalArgumentException("Invalid ID");
		Event event = eventDB.getEvent(id);
		if (event == null)
			throw new IllegalArgumentException("No such event");
		return event;
	}

	public List<Event> retrieveByDatesortByParticipantCount(String date) throws ParseException {
		List<Event> events = eventDB.retrieveByDatesortByParticipantCount(date);
		return events;
	}

	public List<Event> sortDuration() {
		return eventDB.sortByDuration();
	}

	public List<Event> sortEventCreatedTime() {
		return eventDB.sortByCreatedTime();
	}

	public List<Event> sortEventStartTime() {
		return eventDB.sortByStartTime();
	}

	public List<Event> retrieveByTimeRange(long start, long end) {
		if (start < 0 || end < 0)
			throw new IllegalArgumentException("Invalid input");
		return eventDB.retrieveByTimeRange(start, end);
	}

}
