package at.crimsonbit.viergewinntbot.subject;

public class Subject {

	private String subject = "";
	private int difficulty = 0;
	private int difficultyRange = 0;

	public Subject() {
	}

	public Subject(String subject, int difficultyRange, int difficulty) {
		this.subject = subject;
		this.difficultyRange = difficultyRange;
		if (difficulty > 0 && difficulty < difficultyRange)
			this.difficulty = difficulty;
	}

	public int getDifficultyRange() {
		return difficultyRange;
	}

	public void setDifficultyRange(int difficultyRange) {
		this.difficultyRange = difficultyRange;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		if (difficulty > 0 && difficulty < difficultyRange)
			this.difficulty = difficulty;
	}

}
