package at.crimsonbit.viergewinntbot.answermanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.log.Log;

public class AnswerManager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4472339189496045749L;
	private final File answerFile = new File(BotDefs.RESOURCE_FOLDER + "asnwerList.db");
	private AnswerList answerList;
	private static AnswerManager instance;

	public static AnswerManager getInstance() {
		if (instance == null) {
			instance = new AnswerManager();
		}
		return instance;
	}

	public AnswerManager() {
		if (!answerFile.exists()) {
			Log.log(BotDefs.TYPE_ANSWERMANAGER, true, "No answer lsit file found!");
			Log.log(BotDefs.TYPE_ANSWERMANAGER, false, "Createing new default answer list...");
			this.answerList = getDefaults();
			saveanswerList();

		} else
			loadanswerList();
	}

	public AnswerList getAnswerList() {
		return this.answerList;
	}

	public void setAnswerList(AnswerList p) {
		this.answerList = p;
	}

	public void loadanswerList() {

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(answerFile));
			this.answerList = (AnswerList) ois.readObject();
			ois.close();
			Log.log(BotDefs.TYPE_ANSWERMANAGER, false, "answerList loaded successfully!");
		} catch (IOException | ClassNotFoundException e) {
			Log.log(BotDefs.TYPE_ANSWERMANAGER, true, "Error while loading answerList!");
			e.printStackTrace();
		}
	}

	public AnswerList getDefaults() {
		AnswerList pList = new AnswerList();

		return pList;
	}

	public void saveanswerList() {
		try {
			ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(answerFile));
			ous.writeObject(this.answerList);
			Log.log(BotDefs.TYPE_ANSWERMANAGER, false, "Answer list saved successfully!");
			ous.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
