package at.crimsonbit.viergewinntbot.bot;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>BotDeffs</h1>
 * <p>
 * This class has holds almost every global definition for the Bot.
 * </p>
 * 
 * @author Florian Wagner
 * 
 *
 */
public class BotDefs {

	public static int EXIT_ON_USER = 0;
	public static int EXIT_ON_INVALIDLOGIN = 1;
	public static int EXIT_ON_ILLEGALARGUMENT = 2;
	public static int EXIT_ON_INTERRUPTED = 3;
	public static int EXIT_ON_RATELIMIT = 4;
	public static int EXIT_ON_USER_RESTART = 5;

	public static String TYPE_SETTINGS_FACTORY = "SettingsFactory";
	public static String TYPE_POINTMANAGER = "PointManager";
	public static String TYPE_PERMISSION = "Permission";
	public static String TYPE_ANSWERMANAGER = "AnswerManager";
	public static String TYPE_REGISTRY = "Registry";
	public static String TYPE_AUDIO = "Audio";
	public static String TYPE_COMMAND = "Command";
	public static String TYPE_CHALLENGE = "Challenge";
	public static String TYPE_GENERAL = "General";
	public static String TYPE_LOADER = "Loader";
	public static String TYPE_FILESYSTEM = "FileSystem";
	public static String TYPE_PLUGINMANAGER = "PluginManager";
	public static String TYPE_SHEETHANDLER = "SheetHandler";

	public static String CHANNEL_LOG_GENERAL = "4g_log_general";
	public static String CHANNEL_LOG_POINTMANAGER = "4g_log_pointmanager";
	public static String CHANNEL_LOG_LOADERS = "4g_log_registry";

	public static String QUESTION_FOLDER = FILE_LOCATION() + "/questions/";
	public static String LOG_FOLDER = FILE_LOCATION() + "/Log/";
	public static String RESOURCE_FOLDER = FILE_LOCATION() + "/resources/";
	public static String PLUGINS_FOLDER = FILE_LOCATION() + "/plugins/";

	public static String SPREADSHEET_ID = "1622RDJ0OL1rpwNrCFk9XHaBrPG9SjZNuQj1lOnwqrtw";
	public static String SPREADSHEET_URL = "https://docs.google.com/forms/d/e/1FAIpQLSeymDojlU6SMryUjrDdH3-pn-u9lO4dJC0jd7yoagGhCOHsVQ/viewform";
	public static boolean PrintStartUpMessage = false;
	public static String SPREADSHEET_RANGE_SPLITTER = ":";
	public static String SPREADSHEET_START = "A";
	public static String SPREADSHEET_END = SPREADSHEET_RANGE_SPLITTER+"H";
	
	public static int CHALLENGE_POINTS_FIRST = 10;
	public static int CHALLENGE_POINTS_SECOND = 7;
	public static int CHALLENGE_POINTS_THIRD = 5;
	

	/**
	 * h1>public void FILE_LOCATION()</h1>
	 * <hr>
	 * <p>
	 * Returns the file location of the .jar file. If it is run/build in an ide
	 * the target folder will be returned.
	 * </p>
	 * 
	 * @return the path as String.
	 */
	public static String FILE_LOCATION() {
		String path = null;
		try {
			path = BotDefs.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			// path = path.substring(1);
			if (path.endsWith(".jar"))
				path = path.split("4GBot.jar")[0];

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}
}
