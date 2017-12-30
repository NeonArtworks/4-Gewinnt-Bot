package at.crimsonbit.viergewinntbot.spreadsheet;

import at.crimsonbit.testparser.api.sheetinterface.ISolutionData;
import at.crimsonbit.testparser.parser.question.EnumTaskType;

public class SheetSolution implements ISolutionData {

	private EnumTaskType type;
	private String text;

	public SheetSolution(String type, String text) {
		super();
		this.type = EnumTaskType.valueOf(type.trim().toUpperCase());
		this.text = text;
	}

	public EnumTaskType getType() {
		return type;
	}

	public void setType(String type) {
		this.type = EnumTaskType.valueOf(type.trim().toUpperCase());
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "SheetSolution [type=" + type + ", text=" + text + "]";
	}

}
