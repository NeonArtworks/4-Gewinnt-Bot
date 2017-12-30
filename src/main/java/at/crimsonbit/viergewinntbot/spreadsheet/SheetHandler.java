package at.crimsonbit.viergewinntbot.spreadsheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.settings.SettingsFactory;

public class SheetHandler {
	/** Application name. */
	private final String APPLICATION_NAME = "4Gewinnt Bot";

	/** Directory to store user credentials for this application. */
	private final File DATA_STORE_DIR = new File(BotDefs.RESOURCE_FOLDER);

	/** Global instance of the {@link FileDataStoreFactory}. */
	private FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private HttpTransport HTTP_TRANSPORT;

	private Sheets service = null;
	private String spreadsheetId = BotDefs.SPREADSHEET_ID;
	private int r = SettingsFactory.getInstance().getSettings().getNumQuestions() + 2;
	private static SheetHandler instance;

	public static SheetHandler getInstance() {
		if (instance == null) {
			instance = new SheetHandler();
		}
		return instance;
	}

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/sheets.googleapis.com-java-quickstart
	 */
	private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public Credential authorize() throws IOException {
		// Load client secrets.
		Log.log(BotDefs.TYPE_SHEETHANDLER, false, "Authorizing...");
		File f = new File(BotDefs.RESOURCE_FOLDER + "client_id.json");
		if (!f.exists()) {
			Log.log(BotDefs.TYPE_SHEETHANDLER, true, "No client id json found!");
		}
		InputStream in = new FileInputStream(f);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
		Credential credential = null;
		Log.log(BotDefs.TYPE_SHEETHANDLER, false, "Authorizing credentials....");
		try {
			credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.log(BotDefs.TYPE_SHEETHANDLER, true, "error while authorizisation...");
		}
		Log.log(BotDefs.TYPE_SHEETHANDLER, false, "Succsessfully autorized!");
		return credential;
	}

	/**
	 * Build and return an authorized Sheets API client service.
	 * 
	 * @return an authorized Sheets API client service
	 * @throws IOException
	 */
	public Sheets getSheetsService() throws IOException {
		Log.log(BotDefs.TYPE_SHEETHANDLER, false, "Getting sheet service...");
		Credential credential = authorize();
		
		return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME)
				.build();
		
	}

	private void initServices() {
		Log.log(BotDefs.TYPE_SHEETHANDLER, false, "Initializing SheetHandler");
		try {
			Log.log(BotDefs.TYPE_SHEETHANDLER, false, "Getting service...");
			service = getSheetsService();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private SheetHandler() {
		
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		initServices();

	}

	public int getTotalNumberOfQuestions() {
		try {
			ValueRange result = service.spreadsheets().values().get(spreadsheetId, "A2:H").execute();
			int numRows = result.getValues() != null ? result.getValues().size() : 0;
			// System.out.println(numRows);
			return numRows;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public List<SheetQuestion> getAllQuestions() {
		List<SheetQuestion> questions = new ArrayList<>();
		Log.log(BotDefs.TYPE_SHEETHANDLER, false, "Adding question to list...");
		for (int i = 2; i < getTotalNumberOfQuestions(); i++) {
			String range = BotDefs.SPREADSHEET_START + i + BotDefs.SPREADSHEET_END;
			ValueRange response = null;
			SheetQuestion q = null;

			try {
				response = service.spreadsheets().values().get(spreadsheetId, range).execute();
			} catch (IOException e) {

				e.printStackTrace();
			}

			List<List<Object>> values = response.getValues();
			if (values == null || values.size() == 0) {
				Log.log(BotDefs.TYPE_GENERAL, true, "Question parser -> no question found in range " + range);
			} else {

				for (List row : values) {
					List<String> tasks = getSplittedResource((String) row.get(4));
					List<String> hints = getSplittedResource((String) row.get(5));
					List<SheetSolution> solutions = getSolutions(getSplittedResource((String) row.get(6)));
					List<SheetKey> keys = getKeys(getSplittedResource((String) row.get(7)));
					q = new SheetQuestion(Integer.parseInt((String) row.get(1)), (String) row.get(2),
							(String) row.get(3), tasks, hints, solutions, keys);
					
					questions.add(q);

				}
			}
		}
		Log.log(BotDefs.TYPE_SHEETHANDLER, false, "Added " + questions.size() + " questions to the database!");

		return questions;

	}

	public SheetQuestion getQuestion(String subject, int dif) {

		for (int i = 2; i < getTotalNumberOfQuestions(); i++) {
			String range = BotDefs.SPREADSHEET_START + i + BotDefs.SPREADSHEET_END;
			ValueRange response = null;
			SheetQuestion q = null;
			try {
				response = service.spreadsheets().values().get(spreadsheetId, range).execute();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(response);
			List<List<Object>> values = response.getValues();
			if (values == null || values.size() == 0) {
				Log.log(BotDefs.TYPE_GENERAL, true, "Question parser -> no question found in range " + range);
			} else {
				for (List row : values) {
					if ((((String) row.get(3)).equalsIgnoreCase(subject))) {
						if (Integer.parseInt((String) row.get(1)) == dif) {
							List<String> tasks = getSplittedResource((String) row.get(4));
							List<String> hints = getSplittedResource((String) row.get(5));
							List<SheetSolution> solutions = getSolutions(getSplittedResource((String) row.get(6)));
							List<SheetKey> keys = getKeys(getSplittedResource((String) row.get(7)));
							q = new SheetQuestion(Integer.parseInt((String) row.get(1)), (String) row.get(2),
									(String) row.get(3), tasks, hints, solutions, keys);
							return q;
						}
					}
				}
			}
		}
		return null;
	}

	private List<SheetKey> getKeys(List<String> res) {

		List<SheetKey> ret = new ArrayList<>();

		for (String r : res) {
			String[] g = r.split("_");

			if (g.length > 2) {
				SheetKey sk = new SheetKey();
				if (g[0].equalsIgnoreCase("double")) {
					sk.setDigits(Integer.parseInt(g[3]));
					sk.setType(g[0]);
					sk.setMin(g[1]);
					sk.setMax(g[2]);
					ret.add(sk);
				} else {
					sk.setType(g[0]);
					sk.setMin(g[1]);
					sk.setMax(g[2]);
					ret.add(sk);
				}
			} else if (g.length <= 2) {
				SheetKey sk = new SheetKey();
				sk.setType(g[0]);

				if (g[0].equalsIgnoreCase("expression")) {
					sk.setExpr(g[1]);

				} else if (g[0].equalsIgnoreCase("string")) {
					sk.setText(g[1]);

				}
				ret.add(sk);
			}

		}
		return ret;
	}

	private List<SheetSolution> getSolutions(List<String> res) {

		List<SheetSolution> ret = new ArrayList<>();

		for (String r : res) {
			String[] g = r.split("_");
			ret.add(new SheetSolution(g[0], g[1]));

		}
		return ret;
	}

	private List<String> getSplittedResource(String res) {
		List<String> ret = new ArrayList<>();

		for (String r : res.split(";")) {
			ret.add(r);
		}
		return ret;
	}

}