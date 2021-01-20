package service.event;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Event;

public class SortHelper {

	protected HashMap<Long, Event> sortByParticipantCountHelper(Map<Long, Event> events) {
		List<Map.Entry<Long, Event>> list = new LinkedList<Map.Entry<Long, Event>>(events.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Long, Event>>() {
			public int compare(Map.Entry<Long, Event> o1, Map.Entry<Long, Event> o2) {
				if(o1.getValue().getParticipantKey() !=null && o2.getValue().getParticipantKey()!=null)
				return (Integer.valueOf(o1.getValue().getParticipantKey().size()))
						.compareTo(Integer.valueOf(o2.getValue().getParticipantKey().size()));
				else return -1;
			}
		});

		HashMap<Long, Event> temp = new LinkedHashMap<Long, Event>();
		for (Entry<Long, Event> e : list) {
			temp.put(e.getKey(), e.getValue());
		}

		return temp;
	}

	protected HashMap<Long, Event> sortByEventDurationHelper(Map<Long, Event> events) {
		List<Map.Entry<Long, Event>> list = new LinkedList<Map.Entry<Long, Event>>(events.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Long, Event>>() {
			public int compare(Map.Entry<Long, Event> o1, Map.Entry<Long, Event> o2) {
				if (o1.getValue().getEventDuration() == (o2.getValue().getEventDuration()))
					return 0;
				else if (o1.getValue().getEventDuration() > (o2.getValue().getEventDuration()))
					return 1;
				else
					return -1;
			}
		});

		HashMap<Long, Event> temp = new LinkedHashMap<Long, Event>();
		for (Map.Entry<Long, Event> e : list) {
			temp.put(e.getKey(), e.getValue());
		}
		return temp;
	}

	protected HashMap<Long, Event> sortByEventCreatedHelper(Map<Long, Event> events) {
		List<Map.Entry<Long, Event>> list = new LinkedList<Map.Entry<Long, Event>>(events.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Long, Event>>() {
			public int compare(Map.Entry<Long, Event> o1, Map.Entry<Long, Event> o2) {
				if (o1.getValue().getEventCreatedTime() == (o2.getValue().getEventCreatedTime()))
					return 0;
				else if (o1.getValue().getEventCreatedTime() > (o2.getValue().getEventCreatedTime()))
					return 1;
				else
					return -1;
			}
		});

		HashMap<Long, Event> temp = new LinkedHashMap<Long, Event>();
		for (Map.Entry<Long, Event> e : list) {
			temp.put(e.getKey(), e.getValue());
		}
		return temp;
	}

	public HashMap<Long, Event> sortByEventTimeHelper(Map<Long, Event> events) {
		List<Map.Entry<Long, Event>> list = new LinkedList<Map.Entry<Long, Event>>(events.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Long, Event>>() {
			public int compare(Map.Entry<Long, Event> o1, Map.Entry<Long, Event> o2) {
				if (o1.getValue().getEventTime() == (o2.getValue().getEventTime()))
					return 0;
				else if (o1.getValue().getEventTime() > (o2.getValue().getEventTime()))
					return 1;
				else
					return -1;
			}
		});

		HashMap<Long, Event> temp = new LinkedHashMap<Long, Event>();
		for (Map.Entry<Long, Event> e : list) {
			temp.put(e.getKey(), e.getValue());
		}
		return temp;
	}

}
