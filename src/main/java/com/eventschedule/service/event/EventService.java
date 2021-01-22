package com.eventschedule.service.event;

import java.util.*;

import com.eventschedule.datastore.DataBaseException;
import com.eventschedule.model.Event;
import com.eventschedule.model.ParticipantDetails;
import com.google.appengine.api.datastore.EntityNotFoundException;

public interface EventService {
	public Event addEvent(Event eventDetails) throws DataBaseException;

	public Event modifyEvent(Event event) throws DataBaseException, EntityNotFoundException;

	public Event retrieveById(String id) throws EntityNotFoundException;

	public void removeEvent(String id) throws EntityNotFoundException;

}
