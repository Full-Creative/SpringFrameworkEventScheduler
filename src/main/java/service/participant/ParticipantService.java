package service.participant;

import com.google.appengine.api.datastore.EntityNotFoundException;

import datastore.DataBaseException;
import model.ParticipantDetails;

public interface ParticipantService {
	public ParticipantDetails createParticipant(ParticipantDetails participant);

	public ParticipantDetails modifyParticipant(ParticipantDetails participant) throws DataBaseException, EntityNotFoundException;

	public ParticipantDetails retrieveById(long id) throws EntityNotFoundException;
	
	public void removeParticipant(String id) throws EntityNotFoundException;


}
