package toolsJSON;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import app.AppContext;

public class ReadNameClientJSON {
	static public String readName(AppContext context) {
		String result = "";
		String name = null;
		org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
		try {
			Object obj = parser.parse(new FileReader("JsonFiles\\name_client.json"));
			JSONObject jsonObj = (JSONObject) obj;
			name = new String();
			name = ((String) jsonObj.get("userName"));

			if (name != null) {
				result = name;
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}

		return result;
	}
}
