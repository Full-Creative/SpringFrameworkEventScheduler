package service.participant;

import model.Event;

import java.text.ParseException;
import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;

import datastore.DataBaseException;
import datastore.EventsDB;
import model.ParticipantDetails;

public class ParticipantServiceImp implements ParticipantService {
	public EventsDB eventDB;

	public ParticipantServiceImp() {
		eventDB = new EventsDB();
	}

	@Override
	public ParticipantDetails createParticipant(ParticipantDetails participant) {
		if (participant == null || participant.getEmail() == null || participant.getName() == null)
			throw new IllegalArgumentException("Participant Details not available");
		eventDB.createParticipant(participant);
		return participant;
	}

	@Override
	public ParticipantDetails modifyParticipant(ParticipantDetails participant)
			throws DataBaseException, EntityNotFoundException {
		if (participant.getEmail() == null)
			throw new IllegalArgumentException("Participant not available");
		boolean modified = false;
		modified = eventDB.updateParticipant(participant);
		if (!modified)
			throw new DataBaseException("Not modified");
		return participant;
	}

	@Override
	public ParticipantDetails retrieveById(long id) throws EntityNotFoundException {
		return null;
	}

	@Override
	public void removeParticipant(String id) throws EntityNotFoundException {
		eventDB.removeParticipant(id);
	}

	public List<Event> retrieveEvents(String id) throws EntityNotFoundException, ParseException {
		return eventDB.retrieveEventByEmail(id);
	}

}
