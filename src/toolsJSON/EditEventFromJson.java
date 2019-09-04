package toolsJSON;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import DBOperations.dbEvent;
import app.AppContext;
import entities.Event;

public class EditEventFromJson {
	static public boolean EditEventFromJSON(AppContext context) {
		boolean result = false;
		Event ev = null;
		org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
		try {
			Object obj = parser.parse(new FileReader("JsonFiles\\eventEdit.json"));
			JSONObject jsonObj = (JSONObject) obj;
			ev = new Event();
			ev.setId((long) jsonObj.get("id"));

			if (ev != null) {
				dbEvent.editEvent(context, ev);
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
