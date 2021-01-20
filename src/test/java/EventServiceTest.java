import static org.junit.Assert.*;
import org.junit.Test;
import com.google.appengine.api.datastore.EntityNotFoundException;

import datastore.DataBaseException;
import model.Event;
import model.ParticipantDetails;
import service.event.EventServiceImp;

public class EventServiceTest {
	public EventServiceImp eventService = new EventServiceImp();

	@Test(expected = IllegalArgumentException.class)
	public void testAddEvent() {
		Event eventDetails = new Event();
		eventDetails.setEventTime(0);
		eventService.addEvent(eventDetails);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEvent1() {
		Event eventDetails = new Event();
		eventDetails.setEventDuration(0);
		assertEquals(eventDetails, eventService.addEvent(eventDetails));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEvent2() {
		Event eventDetails = new Event();
		eventDetails.setEventID(null);
		assertEquals(eventDetails, eventService.addEvent(eventDetails));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEvent3() {
		assertEquals(null, eventService.addEvent(null));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testModifyEvent() throws DataBaseException, EntityNotFoundException {
		Event eventDetails = new Event();
		eventDetails.setEventID(null);
		assertEquals(eventDetails, eventService.modifyEvent(eventDetails));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveEvent() throws EntityNotFoundException {
		eventService.removeEvent(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRetrieveById() throws EntityNotFoundException {
			assertEquals(new Event(), eventService.retrieveById(null));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddParticipant() throws EntityNotFoundException, DataBaseException {
		ParticipantDetails participant = new ParticipantDetails();
		participant.setEmail(null);
		assertEquals(participant, eventService.addParticipant(participant));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddParticipant2() throws EntityNotFoundException, DataBaseException {
		assertEquals(null, eventService.addParticipant(null));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRetrieveByTimeRange() {
		assertEquals(null, eventService.retrieveByTimeRange(-1, 0));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testRetrieveByTimeRange1() {
		assertEquals(null, eventService.retrieveByTimeRange(0, -1));
	}

}
