package at.crimsonbit.viergewinntbot.wordcheck;

import java.util.ArrayList;
import java.util.List;

public class BlackList {

	private List<String> blackList = new ArrayList<>();

	public BlackList() {

	}

	public void addToBlackList(String s) {
		blackList.add(s);
	}

	public void removeFromBlackList(String s) {
		blackList.remove(s);
	}

	public List<String> getBlackList() {
		return blackList;
	}

	public void setBlackList(List<String> blackList) {
		this.blackList = blackList;
	}

}
