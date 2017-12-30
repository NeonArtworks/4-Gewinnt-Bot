package at.crimsonbit.viergewinntbot.log;

import java.util.Timer;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.settings.SettingsFactory;

public class ScheduledLog {
	private Timer timer = new Timer(); // Instantiate Timer Object
	private ScheduledLogWriter slw = new ScheduledLogWriter();
	private int t;

	private static ScheduledLog instance;

	public static ScheduledLog getInstance() {
		if (instance == null) {
			instance = new ScheduledLog();
		}
		return instance;
	}

	public ScheduledLog() {
		t = SettingsFactory.getInstance().getSettings().getAutoLogTimeMS();
	}

	public void reInitScheduler() {
		t = SettingsFactory.getInstance().getSettings().getAutoLogTimeMS();
	}

	public void startScheduledLog() {
		Log.log(BotDefs.TYPE_GENERAL, false, "Scheduled logger started!");
		timer.schedule(slw, 0, t);
	}

	public void endScheduledLog() {
		Log.log(BotDefs.TYPE_GENERAL, false, "Scheduled logger stopped!");
		timer.cancel();
	}
}
