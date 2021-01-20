package generator;

import java.util.*;

import model.Event;
import model.ParticipantDetails;

public class DataGenerator {
	static int email;
	static int eventID = 1;

	public List<Event> generateEvents(int numEvents) {
		List<Event> events = new ArrayList<Event>();
		long hour = 0;
		for (int i = 1; i <= numEvents; i++) {
			Event eventDetails = new Event();
			eventDetails.setEventID(String.valueOf(eventID++));
			eventDetails.setEventTitle("Event" + i);
			eventDetails.setEventCreatedTime(1414590045000L);
			eventDetails.setEventDuration(3600000 * (numEvents - i + 1));
			long millis = 1414590045000L;
			eventDetails.setEventTime(millis - hour);
			hour += 86400000;
			events.add(eventDetails);
		}

		return events;
	}

	public Event generateEvent() {
		Event event = new Event();
		event.setEventID("2");
		event.setEventTime(System.currentTimeMillis());
		event.setEventTitle("Updated Event");
		event.setEventDuration(3600000 * 8);

		return event;
	}

	public Set<ParticipantDetails> generateParticipants(int number) {
		Set<ParticipantDetails> participants = new HashSet<ParticipantDetails>();
		for (int j = 0; j < number; j++) {
			ParticipantDetails p = new ParticipantDetails();
			p.setName("name");
			p.setEmail("email" + email++ + "@gmail.com");
			participants.add(p);
		}
		return participants;

	}
}
