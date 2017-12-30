package at.crimsonbit.viergewinntbot.handler;

import java.util.ArrayList;
import java.util.List;

import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.settings.Settings;
import at.crimsonbit.viergewinntbot.settings.SettingsFactory;
import net.dv8tion.jda.core.entities.User;

public class PermissionHandler {

	private List<String> ops;
	private static PermissionHandler instance = null;

	public PermissionHandler() {
		ops = new ArrayList<String>();
	}

	public void addOP(String user) {
		if (user != null) {
			ops.add(user);
			SettingsFactory sF = SettingsFactory.getInstance();
			Settings s = sF.getSettings();
			s.setDefualtOPs(ops);
			sF.setSettings(s);
			sF.saveSettings();
		}
	}

	public void addOP(User user) {
		if (user != null) {
			ops.add(user.getId());
			SettingsFactory sF = SettingsFactory.getInstance();
			Settings s = sF.getSettings();
			s.setDefualtOPs(ops);
			sF.setSettings(s);
			sF.saveSettings();
		}
	}

	public void removeOP(String user) {
		if (user != null) {
			ops.remove(user);
			SettingsFactory sF = SettingsFactory.getInstance();
			Settings s = sF.getSettings();
			s.setDefualtOPs(ops);
			sF.setSettings(s);
			sF.saveSettings();
		}
	}

	public void removeOP(User user) {
		if (user != null) {
			ops.remove(user.getId());
			SettingsFactory sF = SettingsFactory.getInstance();
			Settings s = sF.getSettings();
			s.setDefualtOPs(ops);
			sF.setSettings(s);
			sF.saveSettings();
		}
	}

	public PermissionHandler loadOPS(Settings settings) {
		Log.log("Permission", false, "Loading permissions....");
		for (String id : settings.getDefualtOPs()) {
			Log.log("Permission", true, "Adding " + id + " to ops!");
			ops.add(id);
		}
		return this;
	}

	public boolean isOP(User user) {
		if (ops.contains(user.getId()))
			return true;
		return false;
	}

	public static PermissionHandler getInstance() {
		if (instance == null) {
			instance = new PermissionHandler();
		}
		return instance;
	}

}
