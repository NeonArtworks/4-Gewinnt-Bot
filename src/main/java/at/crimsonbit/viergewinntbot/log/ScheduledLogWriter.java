package at.crimsonbit.viergewinntbot.log;

import java.io.IOException;
import java.util.TimerTask;

public class ScheduledLogWriter extends TimerTask {

	public void run() {
		try {
			LogWriter.writeLogToFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
