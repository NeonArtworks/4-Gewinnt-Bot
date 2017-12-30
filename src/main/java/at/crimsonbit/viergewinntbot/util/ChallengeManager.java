package at.crimsonbit.viergewinntbot.util;

import java.util.ArrayList;
import java.util.List;

import at.crimsonbit.viergewinntbot.bot.BotDefs;
import at.crimsonbit.viergewinntbot.pointmanager.PointManager;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;

public class ChallengeManager {
	
	private List<User> winnerList = new ArrayList<>();
	private List<Member> winnerListMemebers = new ArrayList<>();
	private boolean isActive = false;

	private static ChallengeManager instance;

	public static ChallengeManager getInstance() {
		if (instance == null) {
			instance = new ChallengeManager();
		}
		return instance;
	}

	public ChallengeManager() {
	}

	public void setActive(boolean active) {
		this.isActive = active;
	}

	public boolean isActive() {
		return this.isActive;
	}

	public void addUser(User u, Member m) {
		winnerList.add(u);
		winnerListMemebers.add(m);
	}

	public List<User> getWinnerList() {
		return winnerList;
	}

	public List<Member> getMemberList() {
		return winnerListMemebers;
	}

	public boolean isFilled() {
		if (winnerList.size() == 3) {
			return true;
		}
		return false;
	}

	public void addPointsToWinners() {
		if (winnerList.size() >= 3) {
			PointManager.getInstance().getPointList().addPointsToUser(winnerList.get(0),
					BotDefs.CHALLENGE_POINTS_FIRST);
			PointManager.getInstance().getPointList().addPointsToUser(winnerList.get(1),
					BotDefs.CHALLENGE_POINTS_SECOND);
			PointManager.getInstance().getPointList().addPointsToUser(winnerList.get(2),
					BotDefs.CHALLENGE_POINTS_THIRD);
		}
		clear();
	}

	public void clear() {
		isActive = false;
		winnerList.clear();
		winnerListMemebers.clear();
	}

}
