package toolsJSON;

import java.util.List;

import com.google.gson.Gson;

import DBOperations.dbEvent;
import app.AppConstants;
import app.AppContext;
import entities.*;

public class CreateJSONEvents {

	private static List<Event> listEvent;
	private static Gson gson;

	static public String GetEvent_JSON(AppContext context) {
		String result = null;
		gson = new Gson();
		listEvent = dbEvent.listEvents(context, listEvent, AppConstants.STATUS_EVENT_ON);
		String jsonString = gson.toJson(listEvent);
		System.out.println(jsonString);
		result = jsonString;

		return result;
	}
}
