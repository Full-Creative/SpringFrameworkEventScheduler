package service.event;

import java.text.ParseException;
import java.util.List;

import datastore.EventsDB;
import model.Event;

public class EventHelper {

	public EventsDB eventDB;

	public EventHelper() {
		eventDB = new EventsDB();
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

	public List<Event> retrieveByDate(String date) throws ParseException {
		if (date == null)
			throw new IllegalArgumentException("Invalid input");
		return eventDB.retrieveByDate(date);
	}

}
