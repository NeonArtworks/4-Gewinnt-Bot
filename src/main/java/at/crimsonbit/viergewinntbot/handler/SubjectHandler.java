package at.crimsonbit.viergewinntbot.handler;

import java.util.HashSet;

import at.crimsonbit.viergewinntbot.commands.AbstractCommand;
import at.crimsonbit.viergewinntbot.log.Log;
import at.crimsonbit.viergewinntbot.subject.Subject;

public class SubjectHandler {

	private HashSet<Subject> commands;

	public SubjectHandler() {
		commands = new HashSet<Subject>();

	}

	public Subject registerSubject(Subject subject) {

		if (subject == null) {
			Log.log("Registry", true, "ERROR while register: Subject was null!");
			return subject;

		} else {
			Log.log("Registry", false, "Subject Name: " + subject.getSubject());
			Log.log("Registry", false, "Subject difficulty range: " + subject.getDifficultyRange());
			try {
				commands.add(subject);
			} catch (NullPointerException e) {
				Log.log("Registry", true, "ERROR while register: Subject was null!");
			}

			Log.log("Registry", false, "Command registry for " + subject.getSubject() + " was successfull!");
		}

		return subject;
	}

	public int getNumberOfRegisteredSubjects() {
		return this.commands.size();
	}

	public HashSet<Subject> getRegisteredSubjects() {
		return this.commands;
	}

}
