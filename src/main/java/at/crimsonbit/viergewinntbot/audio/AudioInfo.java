package at.crimsonbit.viergewinntbot.audio;

import java.io.Serializable;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.entities.Member;

/**
 * <h1>AudioInfo</h1>
 * <p>
 * implements {@link Serializable}
 * </p>
 * <p>
 * This class holds information for the Audio manager.
 * <hr>
 * <h2>Public Methods</h2>
 * <ul>
 * <li>public AudioTrack getTrack()</li>
 * <li>public Member getAuthor()</li>
 * </ul>
 * </p>
 * 
 * @author Florian Wagner
 *
 */
public class AudioInfo implements Serializable {

	private static final long serialVersionUID = -4804467398672052238L;
	private final AudioTrack track;
	private final Member author;

	public AudioInfo(AudioTrack track, Member author) {
		this.track = track;
		this.author = author;
	}

	/**
	 * <h1>public {@link AudioTrack} getTrack()</h1>
	 * <p>
	 * Returns the track which is currently played by the AudioManager.
	 * </p>
	 * 
	 * @return the track
	 */
	public AudioTrack getTrack() {
		return track;
	}

	/**
	 * <h1>public {@link Member} getAuthor()</h1>
	 * <p>
	 * Returns the author ({@link Member}) who has chosen the track.F
	 * </p>
	 * 
	 * @return the author
	 */
	public Member getAuthor() {
		return author;
	}

}
