package at.crimsonbit.viergewinntbot.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import at.crimsonbit.viergewinntbot.bot.BotDefs;

public class LogWriter {

	private static BufferedWriter writer;

	public LogWriter() {
	}

	public static void writeLogToFile() throws IOException {
		Log.log(BotDefs.TYPE_GENERAL, false, "Log: Creating log file...");
		DateFormat dateFormat = new SimpleDateFormat("EEE dd-MM-yyyy HH-mm-ss");
		Date date = new Date();
		String d = dateFormat.format(date);
		writer = new BufferedWriter(new FileWriter(new File(BotDefs.LOG_FOLDER + d + ".4log")));
		Log.log(BotDefs.TYPE_GENERAL, false, "Log: Creating: " + d + ".4log");
		Log.log(BotDefs.TYPE_GENERAL, false, "Log: Writing log to file...");
		writer.write(Log.getCurrentLogString());
		// Log.log(BotDeffs.TYPE_GENERAL, false, "Log: Log file written, closing
		// writer...");
		writer.close();
		// Log.log(BotDeffs.TYPE_GENERAL, false, "Log: Writer closed!");
	}

}
