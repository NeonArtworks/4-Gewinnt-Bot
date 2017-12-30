package at.crimsonbit.viergewinntbot.settings;

import java.util.List;

import at.crimsonbit.viergewinntbot.commands.AbstractCommand;
import at.crimsonbit.viergewinntbot.subject.Subject;

public class Settings {

	private String version;
	private String token;
	private int autoLogTimeMS;
	private int numQuestions;
	private String currentGame;
	private String command_prefix;
	private Long musicChannelID;
	private int playlist_limit;
	private int audio_buffer;
	private Long currentGuild;
	private boolean printLogToChannels;
	private List<String> defualtOPs;
	private List<Subject> subjects;
	private List<AbstractCommand> commands;

	public List<Subject> getSubjects() {
		return subjects;
	}

	public Subject searchSubject(String name) {
		for (Subject s : getSubjects()) {
			if (s.getSubject().equals(name)) {
				return s;
			}
		}
		return null;
	}

	public void setQuestionTypes(List<Subject> questionTypes) {
		this.subjects = questionTypes;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<AbstractCommand> getCommands() {
		return commands;
	}

	public void setCommands(List<AbstractCommand> commands) {
		this.commands = commands;
	}

	public List<String> getDefualtOPs() {
		return defualtOPs;
	}

	public void setDefualtOPs(List<String> defualtOPs) {
		this.defualtOPs = defualtOPs;
	}

	public String getCommandPrefix() {
		return command_prefix;
	}

	public void setCommandPrefix(String command_prefix) {
		this.command_prefix = command_prefix;
	}

	public Long getMusicChannelID() {
		return musicChannelID;
	}

	public void setMusicChannelID(Long musicChannelID) {
		this.musicChannelID = musicChannelID;
	}

	public int getPlaylist_limit() {
		return playlist_limit;
	}

	public void setPlaylist_limit(int playlist_limit) {
		this.playlist_limit = playlist_limit;
	}

	public int getAudio_buffer() {
		return audio_buffer;
	}

	public void setAudio_buffer(int audio_buffer) {
		this.audio_buffer = audio_buffer;
	}

	public String getCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(String currentGame) {
		this.currentGame = currentGame;
	}

	public Long getCurrentGuild() {
		return currentGuild;
	}

	public void setCurrentGuild(Long currentGuild) {
		this.currentGuild = currentGuild;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isPrintLogToChannels() {
		return printLogToChannels;
	}

	public void setPrintLogToChannels(boolean printLogToChannels) {
		this.printLogToChannels = printLogToChannels;
	}

	public int getAutoLogTimeMS() {
		return autoLogTimeMS;
	}

	public void setAutoLogTimeMS(int autoLogTimeMS) {
		this.autoLogTimeMS = autoLogTimeMS;
	}

	public int getNumQuestions() {
		return numQuestions;
	}

	public void setNumQuestions(int numQuestions) {
		this.numQuestions = numQuestions;
	}
}
