package at.crimsonbit.viergewinntbot.audio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.settings.SettingsFactory;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;

public class TrackManager extends AudioEventAdapter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6075309035068867117L;
	private final AudioPlayer player;
	private final Queue<AudioInfo> queue;

	public TrackManager(AudioPlayer player) {
		this.player = player;
		this.queue = new LinkedBlockingQueue<>();
	}

	public void Queue(AudioTrack track, Member author) {
		AudioInfo info = new AudioInfo(track, author);
		queue.add(info);

		if (player.getPlayingTrack() == null) {
			player.playTrack(track);
		}
	}

	public Set<AudioInfo> getQueue() {
		return new LinkedHashSet<>(queue);
	}

	public AudioInfo getInfo(AudioTrack track) {
		return queue.stream().filter(info -> info.getTrack().equals(track)).findFirst().orElse(null);
	}

	public void clearQueue() {
		queue.clear();
	}

	private String getTimeStamp(long millis) {
		long seconds = millis / 1000;
		long hours = Math.floorDiv(seconds, 3600);
		seconds = seconds - (hours * 3600);
		long mins = Math.floorDiv(seconds, 60);
		seconds = seconds - (mins * 60);
		return (hours == 0 ? "" : hours + ":") + String.format("%02d", mins) + ":" + String.format("%02d", seconds);

	}

	public void shuffleQueue() {
		List<AudioInfo> curQueue = new ArrayList<>(getQueue());
		AudioInfo current = curQueue.get(0);
		curQueue.remove(0);
		Collections.shuffle(curQueue);
		curQueue.add(0, current);
		clearQueue();
		queue.addAll(curQueue);
	}

	@Override
	public void onTrackStart(AudioPlayer player, AudioTrack track) {
		// Adapter dummy method
		AudioInfo into = queue.element();
		VoiceChannel vChannel = into.getAuthor().getVoiceState().getChannel();

		if (vChannel == null)
			player.stopTrack();
		else
			into.getAuthor().getGuild().getAudioManager().openAudioConnection(vChannel);
		AudioTrackInfo info = track.getInfo();
		Guild g = queue.poll().getAuthor().getGuild();
		Log.log(BotDefs.TYPE_AUDIO, false, "Audio: Now Playing: " + info.title);
		g.getTextChannelById(SettingsFactory.getInstance().getSettings().getMusicChannelID())
				.sendMessage(new EmbedBuilder().setDescription("**NOW PLAYING**").addField("Title", info.title, false)
						.addField("Duration", "`[ " + getTimeStamp(track.getDuration()) + " ]`", false).build())
				.queue();
	}

	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		Guild g = queue.poll().getAuthor().getGuild();
		AudioTrackInfo info = track.getInfo();
		Log.log(BotDefs.TYPE_AUDIO, false, "Audio: Song: " + info.title + " ended!");
		if (queue.isEmpty())
			g.getAudioManager().closeAudioConnection();
		else
			player.playTrack(queue.element().getTrack());
	}
}
