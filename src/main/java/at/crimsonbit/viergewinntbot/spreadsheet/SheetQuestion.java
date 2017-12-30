package at.crimsonbit.viergewinntbot.spreadsheet;

import java.util.List;

import at.crimsonbit.testparser.api.sheetinterface.IQuestionData;

public class SheetQuestion implements IQuestionData {

	private int dif;
	private String name;
	private String subject;
	private List<String> tasks;
	private List<String> hints;
	private List<SheetSolution> solutions;
	private List<SheetKey> keys;

	public SheetQuestion(int dif, String name, String subject, List<String> tasks, List<String> hints,
			List<SheetSolution> solutions, List<SheetKey> keys) {
		super();
		this.dif = dif;
		this.name = name;
		this.subject = subject;
		this.tasks = tasks;
		this.hints = hints;
		this.solutions = solutions;
		this.keys = keys;
	}

	public int getDifficulty() {
		return dif;
	}

	public void setDif(int dif) {
		this.dif = dif;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<String> getTasks() {
		return tasks;
	}

	public void setTasks(List<String> tasks) {
		this.tasks = tasks;
	}

	public List<String> getHints() {
		return hints;
	}

	public void setHints(List<String> hints) {
		this.hints = hints;
	}

	public List<SheetSolution> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<SheetSolution> solutions) {
		this.solutions = solutions;
	}

	public List<SheetKey> getKeys() {
		return keys;
	}

	public void setKeys(List<SheetKey> keys) {
		this.keys = keys;
	}

	@Override
	public String toString() {
		return "Question [dif=" + dif + ", name=" + name + ", subject=" + subject + ", tasks=" + tasks + ", hints="
				+ hints + ", solutions=" + solutions + ", keys=" + keys + "]";
	}

}
