package toolsJSON;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import DBOperations.dbEvent;
import DBOperations.dbLocation;
import DBOperations.dbLocationEvents;
import app.AppContext;
import entities.Event;
import entities.EventLocation;
import entities.Location;

public class CreateEventFromJSON {

	static public boolean ConvertToEvent(AppContext context) {
		boolean result = false;
		Event ev = null;

		EventLocation el = null;
		org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
		try {
			Object obj = parser.parse(new FileReader("JsonFiles\\eventAdd.json"));
			JSONObject jsonObj = (JSONObject) obj;
			ev = new Event();
			ev.setName((String) jsonObj.get("Name"));
			ev.setDescription((String) jsonObj.get("Description"));
			ev.setStatus((Long) jsonObj.get("Status"));
			if (ev != null) {
				dbEvent.addEvent(context, ev);
			}
			// add eventLocation
			String Location = (String) jsonObj.get("County");
			String[] LocationArray = Location.split(",");
			List<Location> listLocation = null;
			listLocation = dbLocation.listLocation(context, listLocation);
			el = new EventLocation();
			for (String string : LocationArray) {
				System.out.println(string);

				for (int i = 0; i < listLocation.size() - 1; i++) {
					if (string.contains(listLocation.get(i).getName())) {
						el.setIdLocation(listLocation.get(i).getId());
						ev = dbEvent.getEventByName(context, ev.getName());
						el.setIdEvent(ev.getId());
						dbLocationEvents.addEventLocation(context, el);

					}
				}

			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		result = true;
		return result;
	}

}
