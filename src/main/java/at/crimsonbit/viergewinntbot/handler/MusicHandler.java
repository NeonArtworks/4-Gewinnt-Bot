package at.crimsonbit.viergewinntbot.handler;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import at.crimsonbit.viergewinntbot.audio.AudioInfo;
import at.crimsonbit.viergewinntbot.audio.TrackManager;
import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.settings.SettingsFactory;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;

public class MusicHandler {

	private int PLAYLIST_LIMIT = SettingsFactory.getInstance().getSettings().getPlaylist_limit();
	private Guild guild;
	private AudioPlayerManager manager = new DefaultAudioPlayerManager();
	public Map<Guild, Map.Entry<AudioPlayer, TrackManager>> players = new HashMap<>();

	public int getPLAYLIST_LIMIT() {
		return PLAYLIST_LIMIT;
	}

	public void setPLAYLIST_LIMIT(int pLAYLIST_LIMIT) {
		PLAYLIST_LIMIT = pLAYLIST_LIMIT;
	}

	public Guild getGuild() {
		return guild;
	}

	public void setGuild(Guild guild) {
		this.guild = guild;
	}

	public AudioPlayerManager getManager() {
		return manager;
	}

	public void setManager(AudioPlayerManager manager) {
		this.manager = manager;
	}

	public Map<Guild, Map.Entry<AudioPlayer, TrackManager>> getPlayers() {
		return players;
	}

	public void setPlayers(Map<Guild, Map.Entry<AudioPlayer, TrackManager>> players) {
		this.players = players;
	}

	public MusicHandler() {
		AudioSourceManagers.registerRemoteSources(manager);
	}

	public AudioPlayer createPlayer(Guild g) {
		AudioPlayer p = manager.createPlayer();
		TrackManager m = new TrackManager(p);
		p.addListener(m);
		guild.getAudioManager().setSendingHandler(new PlayerSendHandler(p));
		players.put(g, new AbstractMap.SimpleEntry<>(p, m));
		return p;
	}

	public boolean hasPlayer(Guild g) {
		return players.containsKey(g);
	}

	public AudioPlayer getPlayer(Guild g) {
		if (hasPlayer(g))
			return players.get(g).getKey();
		else
			return createPlayer(g);
	}

	public TrackManager getManager(Guild g) {
		return players.get(g).getValue();
	}

	public boolean isIdle(Guild g) {
		return !hasPlayer(g) || getPlayer(g).getPlayingTrack() == null;
	}

	public void LoadTrack(String iden, Member author, Message msg) {

		Guild guild = author.getGuild();
		getPlayer(guild);

		manager.setFrameBufferDuration(SettingsFactory.getInstance().getSettings().getAudio_buffer());

		manager.loadItemOrdered(guild, iden, new AudioLoadResultHandler() {

			@Override
			public void trackLoaded(AudioTrack track) {
				// TODO Auto-generated method stub
				getManager(guild).Queue(track, author);
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				// TODO Auto-generated method stub
				for (int i = 0; i < (playlist.getTracks().size() > PLAYLIST_LIMIT ? PLAYLIST_LIMIT
						: playlist.getTracks().size()); i++) {
					getManager(guild).Queue(playlist.getTracks().get(i), author);
				}
			}

			@Override
			public void noMatches() {
				// TODO Auto-generated method stub

			}

			@Override
			public void loadFailed(FriendlyException exception) {
				// TODO Auto-generated method stub
				Log.log(BotDefs.TYPE_AUDIO, true, "Error while loading track!");
				Log.log(BotDefs.TYPE_AUDIO, true, exception.toString());
			}
		});
	}

	public void skip(Guild g) {
		getPlayer(g).stopTrack();
	}

	public String getTimeStamp(long millis) {
		long seconds = millis / 1000;
		long hours = Math.floorDiv(seconds, 3600);
		seconds = seconds - (hours * 3600);
		long mins = Math.floorDiv(seconds, 60);
		seconds = seconds - (mins * 60);
		return (hours == 0 ? "" : hours + ":") + String.format("%02d", mins) + ":" + String.format("%02d", seconds);

	}

	public String buildQueueMessage(AudioInfo info) {
		AudioTrackInfo trackInfo = info.getTrack().getInfo();
		String title = trackInfo.title;
		long length = trackInfo.length;
		return "`[ " + getTimeStamp(length) + " ]` " + title + "\n";
	}

}
