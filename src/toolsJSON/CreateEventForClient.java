package toolsJSON;

import java.util.List;

import com.google.gson.Gson;

import DBOperations.dbClient;
import DBOperations.dbEvent;
import DBOperations.dbLocationEvents;
import app.AppConstants;
import app.AppContext;
import entities.Client;
import entities.Event;
import entities.EventLocation;

public class CreateEventForClient {
	private static List<Event> listEvent;
	private static List<Event> listEventToSend;
	private static List<EventLocation> listEventLocation;
	private static Gson gson;
	private static Client clt;

	static public String getEvent_JSON(AppContext context, String nameOfClient) {
		String result = null;
		gson = new Gson();
		listEventToSend=dbEvent.listEvents(context, listEventToSend,1);
		listEventToSend.clear();
		listEvent = dbEvent.listEvents(context, listEvent, AppConstants.STATUS_EVENT_ON);
		listEventLocation = dbLocationEvents.listEventLocation(context, listEventLocation);
		clt = dbClient.getClientByName(context, nameOfClient);
		for (int i = 0; i < listEvent.size() - 1; i++) {
			for (int j = 0; j < listEventLocation.size() - 1; j++) {
				if (listEvent.get(i).getId() == listEventLocation.get(j).getIdEvent()
						&& clt.getLocation_id() == listEventLocation.get(j).getIdLocation()) {
					listEventToSend.add(listEvent.get(i));
				}
			}
		}
		Event e= new Event();
		e.setName("testings");
		e.setDescription("multa");
		e.setId((long)1);
		listEventToSend.add(e);
		if (listEventToSend!=null && !listEventToSend.isEmpty()) {
			String jsonString = gson.toJson(listEventToSend.get(0));
			System.out.println(jsonString);
			result = jsonString;
		}
		return result;
	}
}
