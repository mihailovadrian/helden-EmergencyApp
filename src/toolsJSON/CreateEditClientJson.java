package toolsJSON;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import DBOperations.dbClient;
import DBOperations.dbEvent;
import DBOperations.dbLocation;
import DBOperations.dbLocationEvents;
import app.AppConstants;
import app.AppContext;
import entities.*;

public class CreateEditClientJson {
	static public boolean ConvertToClientFromJSON(AppContext context) {
		boolean result = false;
		Client clt = null;
		Client cltToCompare = null;
		List<EventLocation> eventLoc = null;
		List<Event> events = null;
		org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
		try {
			Object obj = parser.parse(new FileReader("JsonFiles\\name_client.json"));
			JSONObject jsonObj = (JSONObject) obj;

			clt = new Client();
			clt.setName((String) jsonObj.get("userName"));
			clt.setLatitude((String) jsonObj.get("latitude"));
			clt.setLongitude((String) jsonObj.get("longitude"));
			String Location = (String) jsonObj.get("location");
			System.out.println(Location);
			int id_LocationByName = dbLocation.getLocationByName(context, Location).getId();

			cltToCompare = dbClient.getClientByName(context, clt.getName());
			if (cltToCompare != null) {
//update
				System.out.println("in update");
				cltToCompare.setLatitude(clt.getLatitude());
				cltToCompare.setLongitude(clt.getLongitude());
				cltToCompare.setLocation_id(id_LocationByName);
				if (cltToCompare.getStatus() == AppConstants.STATUS_TYPE_NONE) {

					events = dbEvent.listEvents(context, events, AppConstants.STATUS_EVENT_ON);
					eventLoc = dbLocationEvents.listEventLocation(context, eventLoc);

					for (EventLocation el : eventLoc) {
						if (el.getIdLocation() == cltToCompare.getLocation_id()) {
							for (int i = 0; i < events.size() - 1; i++) {
								if (el.getIdEvent() == events.get(i).getId()) {
									cltToCompare.setStatus(AppConstants.STATUS_TYPE_IDLE);
									dbClient.editClientWithStatus(context, cltToCompare);
								}
							}
						}
					}
				}
				{
					dbClient.editClient(context, cltToCompare);
					result = true;
				}
			} else {
//create
				System.out.println("in create");
				clt.setLocation_id(id_LocationByName);
				clt.setStatus(AppConstants.STATUS_TYPE_NONE);
				dbClient.addClient(context, clt);
				result = true;
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

		return result;
	}
}
