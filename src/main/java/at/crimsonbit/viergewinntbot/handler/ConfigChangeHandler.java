package at.crimsonbit.viergewinntbot.handler;

import at.crimsonbit.viergewinntbot.annotation.InjectJDA;
import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.injector.JDAInjector;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.settings.SettingsFactory;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ConfigChangeHandler {
	@InjectJDA
	private static JDA jda;
	static {
		JDAInjector.registerTarget(ConfigChangeHandler.class);
	}

	public ConfigChangeHandler() {
	};

	public void changeToken(MessageReceivedEvent e, String[] args) {
		Log.log(BotDefs.TYPE_COMMAND, true,
				e.getMember().getEffectiveName() + " tried to change the token to: " + args[2]);
		Log.log(BotDefs.TYPE_COMMAND, false, "Token changing is currently not available!");
		e.getTextChannel()
				.sendMessage(e.getAuthor().getAsMention() + " Sorry. Token changing is currently not available!")
				.queue();
	}

	public void changeGame(MessageReceivedEvent e, String[] args) {
		SettingsFactory.getInstance().getSettings().setCurrentGame(args[2]);
		Log.log(BotDefs.TYPE_COMMAND, false, "Current game was set to: " + args[2]);
		if (jda != null) {
			jda.getPresence().setGame(Game.of(SettingsFactory.getInstance().getSettings().getCurrentGame()));
			Log.log(BotDefs.TYPE_COMMAND, true, "CHANGE-JDA: Game presence was change to " + args[2]);
		} else {
			Log.log(BotDefs.TYPE_COMMAND, true, "CHANGE-JDA: JDA was null!");
		}
		saveAndReloadSettings();
	}

	public void changePrefix(MessageReceivedEvent e, String[] args) {
		Log.log(BotDefs.TYPE_COMMAND, false, "Command prefix was changed to " + args[2]);
		SettingsFactory.getInstance().getSettings().setCommandPrefix(args[2]);
		saveAndReloadSettings();
	}

	public void changeChannelID(MessageReceivedEvent e, String[] args) {
		Log.log(BotDefs.TYPE_COMMAND, false, "Music channel id was changed to: " + args[2]);
		SettingsFactory.getInstance().getSettings().setMusicChannelID(Long.valueOf(args[2]));
		saveAndReloadSettings();
	}

	public void changePlaylistLimit(MessageReceivedEvent e, String[] args) {
		Log.log(BotDefs.TYPE_COMMAND, false, "Playlist limit was changed to: " + args[2]);
		SettingsFactory.getInstance().getSettings().setPlaylist_limit(Integer.valueOf(args[2]));
		saveAndReloadSettings();
	}

	public void changeAudioBuffer(MessageReceivedEvent e, String[] args) {
		Log.log(BotDefs.TYPE_COMMAND, false, "Audio buffer was changed to: " + args[2]);
		SettingsFactory.getInstance().getSettings().setAudio_buffer(Integer.valueOf(args[2]));
		saveAndReloadSettings();
	}

	private void saveAndReloadSettings() {
		SettingsFactory.getInstance().saveSettings();
		SettingsFactory.getInstance().reloadSettings();
	}

}
