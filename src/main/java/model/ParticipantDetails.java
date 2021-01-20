package model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class ParticipantDetails {
@Id	private String email;
	private String name;
	private String eventID;
	private String timeZone;
	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timezone) {
		this.timeZone = timezone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof ParticipantDetails) {
			ParticipantDetails temp = (ParticipantDetails) obj;
			if (this.name.equals(temp.name) && this.email.equals( temp.email))
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (this.name.hashCode() + this.email.hashCode());
	}

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventId) {
		this.eventID = eventId;
	}
}
