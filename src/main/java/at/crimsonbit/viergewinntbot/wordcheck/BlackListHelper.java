package at.crimsonbit.viergewinntbot.wordcheck;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.log.Log;

public class BlackListHelper {

	private BlackList bList;
	private static BlackListHelper instance;
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private final Path blFile = new File(BotDefs.RESOURCE_FOLDER + "blackList.json").toPath();

	public static BlackListHelper getInstance() {
		if (instance == null) {
			instance = new BlackListHelper();
		}
		return instance;
	}

	public BlackListHelper() {
		if (!blFile.toFile().exists()) {
			Log.log(BotDefs.TYPE_LOADER, true, "No blacklist file found!");
			Log.log(BotDefs.TYPE_LOADER, false, "Createing new default blacklist...");
			this.bList = getDefaults();
			saveBlackList();

		} else
			loadBlackList();
	}

	public static String replaceRandom(String s) {

		char[] characters = s.toCharArray();
		int rand = (int) (Math.random() * s.length());
		characters[rand] = '*';

		return new String(characters).replaceAll("\\*", "\\*");
	}

	public void loadBlackList() {
		try {
			// checkBadEscapes(configFile);
			BufferedReader reader = Files.newBufferedReader(blFile, StandardCharsets.UTF_8);
			this.bList = gson.fromJson(reader, BlackList.class);
			reader.close();
			Log.log(BotDefs.TYPE_LOADER, false, "BlackList loaded successfully!");
		} catch (IOException e) {
			Log.log(BotDefs.TYPE_LOADER, true, "Error while loading BlackList!");
			e.printStackTrace();
		}
	}

	public BlackList getDefaults() {
		BlackList bList = new BlackList();

		List<String> l = new ArrayList<>();
		l.add("fuck");
		l.add("scheiﬂe");
		l.add("motherfucker");
		l.add("arschloch");
		l.add("hurensohn");
		l.add("ananas");
		bList.setBlackList(l);
		return bList;
	}

	public void saveBlackList() {
		String jsonOut = gson.toJson(this.bList);
		try {
			BufferedWriter writer = Files.newBufferedWriter(blFile, StandardCharsets.UTF_8);
			writer.append(jsonOut);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setbList(BlackList bList) {
		this.bList = bList;
	}

	public BlackList getBlackList() {
		return this.bList;
	}
}
