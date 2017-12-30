package at.crimsonbit.viergewinntbot.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import at.crimsonbit.viergewinntbot.adapter.ExceptionAdapter;
import at.crimsonbit.viergewinntbot.adapter.InterfaceAdapter;
import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.commands.AbstractCommand;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.subject.Subject;

public class SettingsFactory {

	private static SettingsFactory instance;
	private final static GsonBuilder gsonB = new GsonBuilder();

	private static final Gson gson;
	private Settings settings;
	private final static File configFile = new File(BotDefs.RESOURCE_FOLDER + "config.json");

	static {
		// System.out.println(configFile); DEBUG
		gsonB.registerTypeAdapter(Exception.class, new ExceptionAdapter());
		gsonB.registerTypeAdapter(AbstractCommand.class, new InterfaceAdapter<AbstractCommand>());
		gsonB.setPrettyPrinting();
		gson = gsonB.create();
	}

	public static SettingsFactory getInstance() {
		if (instance == null) {
			instance = new SettingsFactory();
		}
		return instance;
	}

	public SettingsFactory() {
		if (!configFile.exists()) {
			Log.log("SettingsFactory", true, "No Settings file found!");
			Log.log("SettingsFactory", false, "Createing new default settings...");
			this.settings = getDefaults();
			saveSettings();

		} else
			loadSettings();
	}

	public Settings getDefaults() {
		Settings settings = new Settings();
		settings.setToken("Mzg0Mzc1MTM3MTAyODU2MTky.DPx55A.W7KVYZ-n6-otRu3YUzdhjxvoi-w");
		settings.setCommandPrefix("!");
		settings.setAudio_buffer(5000);
		settings.setAutoLogTimeMS(1800000);
		settings.setPlaylist_limit(1000);
		settings.setMusicChannelID(394150731315281920L);
		settings.setCurrentGuild(384375304900182019L);
		settings.setPrintLogToChannels(false);
		settings.setVersion("V0.1.22 Beta Edition");
		settings.setCurrentGame("www.crimsonbit.at");

		List<Subject> questionTypes = new ArrayList<Subject>();
		questionTypes.add(new Subject("HWE", 3, 1));
		questionTypes.add(new Subject("KSN", 3, 1));
		questionTypes.add(new Subject("MTRS", 3, 1));
		questionTypes.add(new Subject("DIC", 3, 1));
		questionTypes.add(new Subject("FSST", 3, 1));
		questionTypes.add(new Subject("AM", 3, 1));
		questionTypes.add(new Subject("RECHT", 3, 1));
		questionTypes.add(new Subject("PH", 3, 1));
		questionTypes.add(new Subject("CH", 3, 1));

		List<String> defOPs = new ArrayList<String>();
		defOPs.add("266974844732571648");
		defOPs.add("242996588136366082");

		settings.setDefualtOPs(defOPs);
		settings.setQuestionTypes(questionTypes);

		return settings;
	}

	public void setSettings(Settings s) {
		this.settings = s;
	}

	public void saveSettings() {
		Log.log(BotDefs.TYPE_SETTINGS_FACTORY, true, "Saving settings...");
		Log.log(BotDefs.TYPE_SETTINGS_FACTORY, false, "Reading settings class...");

		String jsonOut = gson.toJson(this.settings);
		Log.log(BotDefs.TYPE_SETTINGS_FACTORY, false, "Settings read successfully, attempting to write config.json");
		try {
			BufferedWriter writer = Files.newBufferedWriter(configFile.toPath(), StandardCharsets.UTF_8);
			writer.append(jsonOut);
			Log.log(BotDefs.TYPE_SETTINGS_FACTORY, false, "json written!");
			writer.close();
			Log.log(BotDefs.TYPE_SETTINGS_FACTORY, false, "Writer closed!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.log(BotDefs.TYPE_SETTINGS_FACTORY, false, "Settings successfully saved!");
	}

	public Settings getSettings() {
		return this.settings;
	}

	public void reloadSettings() {
		Log.log(BotDefs.TYPE_SETTINGS_FACTORY, true, "Reloading settings file!");
		try {
			// checkBadEscapes(configFile);
			BufferedReader reader = Files.newBufferedReader(configFile.toPath(), StandardCharsets.UTF_8);
			this.settings = gson.fromJson(reader, Settings.class);
			reader.close();
			Log.log("SettingsFactory", false, "Settings reloaded successfully!");
		} catch (IOException e) {
			Log.log("SettingsFactory", true, "Error while reloading settings!");
			e.printStackTrace();
		}
	}

	public void loadSettings() {
		Log.log(BotDefs.TYPE_SETTINGS_FACTORY, true, "Loading settings file!");
		try {
			// checkBadEscapes(configFile);
			BufferedReader reader = Files.newBufferedReader(configFile.toPath(), StandardCharsets.UTF_8);
			this.settings = gson.fromJson(reader, Settings.class);
			reader.close();
			Log.log("SettingsFactory", false, "Settings loaded successfully!");
		} catch (IOException e) {
			Log.log("SettingsFactory", true, "Error while loading settings!");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void checkBadEscapes(Path filePath) throws IOException {
		final byte FORWARD_SOLIDUS = 47; // /
		final byte BACKWARDS_SOLIDUS = 92; // \

		boolean modified = false;
		byte[] bytes = Files.readAllBytes(filePath);
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] == BACKWARDS_SOLIDUS) {
				modified = true;
				bytes[i] = FORWARD_SOLIDUS;
			}
		}

		if (modified) {
			Files.write(filePath, bytes);
		}
	}
}
