package at.crimsonbit.viergewinntbot.handler;

import java.io.File;
import java.io.IOException;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.log.Log;

public class ResourcePathHandler {

	public ResourcePathHandler() {
	}

	public static void onStartupCheckAndCreateNewFileSystem() {
		// Log.log(BotDeffs.TYPE_FILESYSTEM, false, "Initializing file
		// system...");
		File res = new File(BotDefs.RESOURCE_FOLDER);
		if (!res.exists()) {
			// Log.log(BotDeffs.TYPE_FILESYSTEM, true, "./resource/ directory
			// not found!");
			// Log.log(BotDeffs.TYPE_FILESYSTEM, false, "creating new
			// ./resource/ directory...");
			createAndCheck(res);
			// Log.log(BotDeffs.TYPE_FILESYSTEM, false, "./resource/ directory
			// created!");
		}
		res = new File(BotDefs.LOG_FOLDER);
		if (!res.exists()) {
			// Log.log(BotDeffs.TYPE_FILESYSTEM, true, "./Log/ directory not
			// found!");
			// Log.log(BotDeffs.TYPE_FILESYSTEM, false, "creating new ./Log/
			// directory...");
			createAndCheck(res);
			// Log.log(BotDeffs.TYPE_FILESYSTEM, false, "./Log/ directory
			// created!");
		}
		// res = new File(BotDefs.QUESTION_FOLDER);
		// if (!res.exists()) {
		// Log.log(BotDeffs.TYPE_FILESYSTEM, true, "./questions/ directory
		// not found!");
		// Log.log(BotDeffs.TYPE_FILESYSTEM, false, "creating new
		// ./questions/ directory...");
		// createAndCheck(res);
		// Log.log(BotDeffs.TYPE_FILESYSTEM, false, "./questions/ directory
		// created!");
		// }
		res = new File(BotDefs.PLUGINS_FOLDER);
		if (!res.exists()) {
			// Log.log(BotDeffs.TYPE_FILESYSTEM, true, "./questions/ directory
			// not found!");
			// Log.log(BotDeffs.TYPE_FILESYSTEM, false, "creating new
			// ./questions/ directory...");
			createAndCheck(res);
			// Log.log(BotDeffs.TYPE_FILESYSTEM, false, "./questions/ directory
			// created!");
		}
	}

	private static void createAndCheck(File res) {
		res.mkdirs();
		try {
			res.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
