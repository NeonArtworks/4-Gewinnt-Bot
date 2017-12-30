package at.crimsonbit.viergewinntbot.pointmanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.log.Log;

public class PointManager {

	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private final Path pointFile = new File(BotDefs.RESOURCE_FOLDER + "pointList.json").toPath();
	private PointList pointList;
	private static PointManager instance;

	public static PointManager getInstance() {
		if (instance == null) {
			instance = new PointManager();
		}
		return instance;
	}

	public PointManager() {
		if (!pointFile.toFile().exists()) {
			Log.log(BotDefs.TYPE_POINTMANAGER, true, "No pointList file found!");
			Log.log(BotDefs.TYPE_POINTMANAGER, false, "Createing new default pointList...");
			this.pointList = getDefaults();
			savePointList();

		} else
			loadPointList();
	}

	public PointList getPointList() {
		return this.pointList;
	}

	public void setPointList(PointList p) {
		this.pointList = p;
	}

	public void loadPointList() {
		try {
			// checkBadEscapes(configFile);
			BufferedReader reader = Files.newBufferedReader(pointFile, StandardCharsets.UTF_8);
			this.pointList = gson.fromJson(reader, PointList.class);
			reader.close();
			Log.log(BotDefs.TYPE_POINTMANAGER, false, "PointList loaded successfully!");
		} catch (IOException e) {
			Log.log(BotDefs.TYPE_POINTMANAGER, true, "Error while loading PointList!");
			e.printStackTrace();
		}
	}

	public PointList getDefaults() {
		PointList pList = new PointList();

		return pList;
	}

	public void savePointList() {
		String jsonOut = gson.toJson(this.pointList);
		try {
			BufferedWriter writer = Files.newBufferedWriter(pointFile, StandardCharsets.UTF_8);
			writer.append(jsonOut);
			writer.close();
			Log.log(BotDefs.TYPE_POINTMANAGER, false, "Point list saved successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
