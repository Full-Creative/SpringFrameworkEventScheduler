import static org.junit.Assert.*;
import org.junit.Test;

import com.eventschedule.datastore.DataBaseException;
import com.eventschedule.model.Event;
import com.eventschedule.model.ParticipantDetails;
import com.eventschedule.service.event.EventServiceImp;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class EventServiceTest {
	public EventServiceImp eventService = new EventServiceImp();
//test case name convention
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddEvent() throws DataBaseException {
		Event eventDetails = new Event();
		eventDetails.setEventTime(0);
		eventService.addEvent(eventDetails);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEventDuration() throws DataBaseException {
		Event eventDetails = new Event();
		eventDetails.setEventDuration(0);
		assertEquals(eventDetails, eventService.addEvent(eventDetails));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEventID() throws DataBaseException {
		Event eventDetails = new Event();
		eventDetails.setEventID(null);
		assertEquals(eventDetails, eventService.addEvent(eventDetails));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNullEvent() throws DataBaseException {
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
	public void testAddNullParticipant() throws EntityNotFoundException, DataBaseException {
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
