package com.eventschedule.service.participant;

import com.eventschedule.datastore.DataBaseException;
import com.eventschedule.model.ParticipantDetails;
import com.google.appengine.api.datastore.EntityNotFoundException;

public interface ParticipantService {
	public ParticipantDetails createParticipant(ParticipantDetails participant);

	public ParticipantDetails modifyParticipant(ParticipantDetails participant) throws DataBaseException, EntityNotFoundException;

	public ParticipantDetails retrieveById(long id) throws EntityNotFoundException;
	
	public void removeParticipant(String id) throws EntityNotFoundException;


}
