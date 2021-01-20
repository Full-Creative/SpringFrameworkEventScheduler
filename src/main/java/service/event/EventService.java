package service.event;

import java.util.*;

import com.google.appengine.api.datastore.EntityNotFoundException;

import datastore.DataBaseException;
import model.Event;
import model.ParticipantDetails;

public interface EventService {
	public Event addEvent(Event eventDetails);

	public Event modifyEvent(Event event) throws DataBaseException, EntityNotFoundException;

	public Event retrieveById(String id) throws EntityNotFoundException;

	public void removeEvent(String id) throws EntityNotFoundException;

}
