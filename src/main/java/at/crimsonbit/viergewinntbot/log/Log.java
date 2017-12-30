package at.crimsonbit.viergewinntbot.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.crimsonbit.viergewinntbot.annotation.InjectJDA;
import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.injector.JDAInjector;
import at.crimsonbit.viergewinntbot.settings.SettingsFactory;
import net.dv8tion.jda.core.JDA;

/**
 * Log class.
 * 
 * @author Florian W.
 *
 */
public class Log {

	private static StringBuilder sb = new StringBuilder();
	@InjectJDA
	private static JDA jda;
	static {
		JDAInjector.registerTarget(Log.class);
		sb.append("4-Gewinngt Bot Log" + System.lineSeparator());
		sb.append("Created by Alexander Daum, Florian Wagner." + System.lineSeparator());
		sb.append(System.lineSeparator());

	}
	
	public static void log(String type, boolean isError, String s) {

		if (!isError) {
			System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + type + ": " + s);
			sb.append("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + type + ": " + s
					+ System.lineSeparator());
			appendJDA(type, s);

		} else {
			System.err.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + type + ": " + s);
			sb.append("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + type + ": " + s
					+ System.lineSeparator());
			appendJDA(type, s);
		}
	}

	private static void appendJDA(String type, String s) {
		if (jda != null) {
			if (SettingsFactory.getInstance().getSettings().isPrintLogToChannels())

				if (type.equals(BotDefs.TYPE_GENERAL) || type.equals(BotDefs.TYPE_COMMAND)
						|| type.equals(BotDefs.TYPE_CHALLENGE) || type.equals(BotDefs.TYPE_FILESYSTEM)
						|| type.equals(BotDefs.TYPE_AUDIO) || type.equals(BotDefs.TYPE_PLUGINMANAGER)
						|| type.equals(BotDefs.TYPE_SHEETHANDLER))

					jda.getTextChannelsByName(BotDefs.CHANNEL_LOG_GENERAL, true).get(0).sendMessage("```css\n["
							+ new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + type + ": " + s + "\n```")
							.queue();
				else if (type.equals(BotDefs.TYPE_PERMISSION) || type.equals(BotDefs.TYPE_REGISTRY)
						|| type.equals(BotDefs.TYPE_LOADER))

					jda.getTextChannelsByName(BotDefs.CHANNEL_LOG_LOADERS, true).get(0).sendMessage("```css\n["
							+ new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + type + ": " + s + "\n```")
							.queue();
				else
					jda.getTextChannelsByName(BotDefs.CHANNEL_LOG_POINTMANAGER, true).get(0).sendMessage("```css\n["
							+ new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + type + ": " + s + "\n```")
							.queue();

		}
	}

	public static String getCurrentLogString() {
		sb.append("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + "End of log!");
		return sb.toString();
	}

	public static StringBuilder getLogStringBuilder() {
		return sb;
	}

}
