package at.crimsonbit.viergewinntbot.parser;

import at.crimsonbit.testparser.api.DataQuestionParser;
import at.crimsonbit.viergewinntbot.spreadsheet.SheetHandler;

public class Parser {

	private static DataQuestionParser instance;

	public Parser() {
	}

	public static DataQuestionParser getInstance() {
		if (instance == null) {
			instance = new DataQuestionParser();
			instance.addQuestions(SheetHandler.getInstance().getAllQuestions());
		}
		return instance;
	}
}
