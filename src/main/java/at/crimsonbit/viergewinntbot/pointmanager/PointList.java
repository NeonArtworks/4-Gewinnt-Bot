package at.crimsonbit.viergewinntbot.pointmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import at.crimsonbit.viergewinntbot.log.Log;
import net.dv8tion.jda.core.entities.User;

public class PointList {

	private HashSet<PointUser> pointList = new HashSet<PointUser>();

	public PointList() {

	}

	public void addPointsToUser(User m, int points) {
		int p = 0;
		for (PointUser u : pointList) {
			if (u.getId().equals(m.getId())) {
				p = u.getPoints();
			}
		}
		PointUser a = new PointUser(m.getId(), m.getName(), p + points);
		Log.log("PointManager", true, m.getName() + " now has " + a.getPoints());
		pointList.remove(a);
		pointList.add(a);

	}

	public int getPosition(int points) {
		List<Integer> list = new ArrayList<>();
		for (PointUser p : this.pointList) {
			list.add(p.getPoints());
		}
		Collections.sort(list);
		Collections.reverse(list);
		int c = 0;
		for (Integer i : list) {
			if (i == points) {
				return c;
			}
			c++;
		}
		return -1;
	}

	public int[] getSortedList() {
		int[] list = new int[this.pointList.size()];
		int c = 0;
		for (PointUser p : this.pointList) {
			list[c] = p.getPoints();
			c++;
		}
		Arrays.sort(list);
		return list;
	}

	public PointUser findUser(String id) {

		for (PointUser u : this.pointList) {
			if (u.getId().equals(id)) {
				return u;
			}
		}
		return null;
	}

	public PointUser findUserByName(String name) {

		for (PointUser u : this.pointList) {
			if (u.getName().equals(name)) {
				return u;
			}
		}
		return null;
	}

	public void addPointsToUser(String id, int points) {
		int p = 0;
		PointUser a = null;
		for (PointUser u : pointList) {
			if (u.getId().equals(id)) {
				a = u;
				p = u.getPoints();
			}
		}

		a.setPoints(p + points);
		Log.log("PointManager", true, a.getName() + " now has " + a.getPoints() + " points.");
		pointList.remove(a);
		pointList.add(a);

	}

	public void addNewUser(User m) {

		pointList.add(new PointUser(m.getId(), m.getName(), 5));
	}

	public HashSet<PointUser> getUserList() {
		return pointList;
	}

	public void setPointList(HashSet<PointUser> pointList) {
		this.pointList = pointList;
	}

	public int getUserPoints(User m) {
		PointUser a = null;
		for (PointUser u : pointList) {
			if (u.getId().equals(m.getId())) {
				a = u;
			}
		}
		return a.getPoints();
	}

}
