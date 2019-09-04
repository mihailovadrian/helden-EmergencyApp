package toolsJSON;

import java.util.List;

import com.google.gson.Gson;

import DBOperations.dbClient;
import app.AppConstants;
import app.AppContext;
import entities.Client;

public class CreateJSONClient {

	private static List<Client> listClient;
	private static Gson gson;

	static public String GetClient_JSON(AppContext context) {
		String result = null;
		gson = new Gson();
		listClient = dbClient.listClients(context, listClient, AppConstants.STATUS_TYPE_DANGER,AppConstants.STATUS_TYPE_IDLE);
		String jsonString = gson.toJson(listClient);
		System.out.println(jsonString);
		result = jsonString;

		return result;
	}
}
