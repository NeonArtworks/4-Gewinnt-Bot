package at.crimsonbit.viergewinntbot.answermanager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import at.crimsonbit.testparser.api.UniqueID;
import net.dv8tion.jda.core.entities.User;

public class AnswerList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6559580353478411964L;
	private Map<String, HashSet<UniqueID>> answerMap = new HashMap<String, HashSet<UniqueID>>();

	public AnswerList() {

	}

	public boolean isAnswered(User u, UniqueID id) {
		HashSet<UniqueID> ids = answerMap.get(u.getId());
		return ids != null && ids.contains(id);
	}

	public Map<String, HashSet<UniqueID>> getAnswerMap() {
		return answerMap;
	}

	public void setAnswerMap(Map<String, HashSet<UniqueID>> answerMap) {
		this.answerMap = answerMap;
	}

	public void addUser(User u) {
		Set<UniqueID> ids = answerMap.get(u.getId());
		if (ids == null) {
			answerMap.put(u.getId(), new HashSet<UniqueID>());
		}
	}

	public void addAnswer(User u, UniqueID id) {
		HashSet<UniqueID> ids = answerMap.get(u.getId());
		if (ids == null) {
			ids = new HashSet<UniqueID>();
			answerMap.put(u.getId(), ids);
		}
		ids.add(id);
	}

}
