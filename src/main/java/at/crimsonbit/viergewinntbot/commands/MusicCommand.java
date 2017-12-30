package at.crimsonbit.viergewinntbot.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.handler.MusicHandler;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.plugins.ICommandPlugin;
import at.crimsonbit.viergewinntbot.settings.SettingsFactory;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class MusicCommand extends AbstractCommand implements ICommandPlugin {
	private boolean isActive = true;
	private String name = "Music";
	private String desc = "Controls the music bot in various different ways.";
	private boolean isOpCommand = false;
	private List<String> aliases = Arrays.asList("mStart", "mStop", "mQueue", "mShuffle", "mInfo", "mSkip");
	private MusicHandler handler = new MusicHandler();
	private Guild guild = handler.getGuild();

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return aliases;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return desc;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public List<String> getUsageInstructions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;

	}

	@Override
	public void setDescription(String desc) {
		// TODO Auto-generated method stub
		this.desc = desc;
	}

	public boolean isOpCommand() {
		return isOpCommand;
	}

	public void setOpCommand(boolean isOpCommand) {
		this.isOpCommand = isOpCommand;
	}

	@Override
	public void onCommand(MessageReceivedEvent e, String[] args) {
		if (isActive) {
			Log.log(BotDefs.TYPE_AUDIO, false, e.getMember().getEffectiveName() + " issued an music command!");
			guild = e.getGuild();
			handler.setGuild(guild);
			if (args.length < 1)
				return;
			args[0] = args[0].replace(SettingsFactory.getInstance().getSettings().getCommandPrefix(), "");
			switch (args[0]) {
			case "mStart":
				if (args.length < 2) {
					return;
				}

				// getManager(guild).clearQueue();
				// skip(guild);
				// guild.getAudioManager().closeAudioConnection();

				String input = Arrays.stream(args).skip(1).map(s -> " " + s).collect(Collectors.joining()).substring(1);
				Log.log(BotDefs.TYPE_AUDIO, false, "starting audio: " + input);
				if ((input.startsWith("http://") || input.startsWith("https://")))
					Log.log(BotDefs.TYPE_AUDIO, false, "trying to play audio from url!");
				else if (!input.startsWith("file:")) {
					Log.log(BotDefs.TYPE_AUDIO, false, "searching on youtube for " + input);
					input = "ytsearch: " + input;
				} else {
					Log.log(BotDefs.TYPE_AUDIO, false, "trying to play local file...");
					File file = new File(input.split("file:")[1]);
					if (file.exists())
						if (file.isFile())
							input = file.getAbsolutePath();
					Log.log(BotDefs.TYPE_AUDIO, false, "local file found at: " + input);
				}

				handler.LoadTrack(input, e.getMember(), e.getMessage());
				Log.log(BotDefs.TYPE_AUDIO, false, "starting...");

				while (handler.getPlayer(guild).getPlayingTrack() == null)
					;

				AudioTrack track = handler.getPlayer(guild).getPlayingTrack();
				AudioTrackInfo info = track.getInfo();
				// e.getTextChannel()
				// .sendMessage(new EmbedBuilder().setDescription("**NOW
				// PLAYING**")
				// .addField("Title", info.title, false)
				// .addField("Duration", "`[ " +
				// getTimeStamp(track.getDuration()) +
				// " ]`", false).build())
				// .queue();
				break;

			case "mSkip":

				if (handler.isIdle(guild))
					return;
				for (int i = (args.length > 1 ? Integer.parseInt(args[1]) : 1); i == 1; i--) {
					handler.skip(guild);
				}
				Log.log(BotDefs.TYPE_AUDIO, false, "skipped song!");
				break;

			case "mStop":
				if (handler.isIdle(guild))
					return;

				handler.getManager(guild).clearQueue();
				handler.skip(guild);
				guild.getAudioManager().closeAudioConnection();
				Log.log(BotDefs.TYPE_AUDIO, false, "AudioBot stopped!");

			case "mShuffle":

				if (handler.isIdle(guild))
					return;
				handler.getManager(guild).shuffleQueue();
				Log.log(BotDefs.TYPE_AUDIO, false, "Queue was shuffled!");
				break;

			case "mInfo":

				if (handler.isIdle(guild))
					return;

				track = handler.getPlayer(guild).getPlayingTrack();
				info = track.getInfo();

				e.getTextChannel()
						.sendMessage(new EmbedBuilder().setDescription("**CURRENT TRACK INFO:**")
								.addField("Title", info.title, false)
								.addField("Duration",
										"`[ " + handler.getTimeStamp(track.getPosition()) + "/ "
												+ handler.getTimeStamp(track.getDuration()) + " ]`",
										false)
								.addField("Author", info.author, false).build())
						.queue();
				Log.log(BotDefs.TYPE_AUDIO, false, "showing info of current song!");
				break;

			case "mQueue":

				if (handler.isIdle(guild))
					return;

				int sideNumb = args.length > 1 ? Integer.parseInt(args[1]) : 1;

				List<String> tracks = new ArrayList<>();
				List<String> trackSublist;

				handler.getManager(guild).getQueue()
						.forEach(audioInfo -> tracks.add(handler.buildQueueMessage(audioInfo)));

				if (tracks.size() > 20)
					trackSublist = tracks.subList((sideNumb - 1) * 20, (sideNumb - 1) * 20 + 20);
				else
					trackSublist = tracks;

				String out = trackSublist.stream().collect(Collectors.joining("\n"));
				int sideNumbAll = tracks.size() >= 20 ? tracks.size() / 20 : 1;

				e.getTextChannel()
						.sendMessage(new EmbedBuilder()
								.setDescription(
										"**CURRENT QUEUE:**\n" + "*[" + handler.getManager(guild).getQueue().stream()
												+ " Tracks | Side " + sideNumb + " / " + sideNumbAll + "]*" + out)
								.build())
						.queue();
				Log.log(BotDefs.TYPE_AUDIO, false, "showing queue!");
				break;

			default:
				break;
			}
		}
	}

	@Override
	public void onStart() {
		isActive = true;

	}

	@Override
	public void onStop() {
		isActive = false;

	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return isActive;
	}

	@Override
	public AbstractCommand getCommand() {
		return this;
	}
}
