package toolsJSON;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import DBOperations.dbClient;
import DBOperations.dbLocation;
import app.AppConstants;
import app.AppContext;
import entities.Client;

public class UpdateClientStatusJSON {

	static public boolean UpdateStatus(AppContext context) {
		boolean result = false;
		org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
		Client clt = null;
		Client cltToCompare = null;
		try {
			Object obj = parser.parse(new FileReader("JsonFiles\\clientStatus.json"));
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

				cltToCompare.setLatitude(clt.getLatitude());
				cltToCompare.setLongitude(clt.getLongitude());
				cltToCompare.setLocation_id(id_LocationByName);
				cltToCompare.setStatus(AppConstants.STATUS_TYPE_DANGER);
				dbClient.editClientWithStatus(context, cltToCompare);
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

	static public boolean UpdateStatusSafe(AppContext context) {
		boolean result = false;
		org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
		Client clt = null;
		Client cltToCompare = null;
		try {
			Object obj = parser.parse(new FileReader("JsonFiles\\clientStatus.json"));
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

				cltToCompare.setLatitude(clt.getLatitude());
				cltToCompare.setLongitude(clt.getLongitude());
				cltToCompare.setLocation_id(id_LocationByName);
				cltToCompare.setStatus(AppConstants.STATUS_TYPE_SAVED);
				dbClient.editClientWithStatus(context, cltToCompare);
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
