package at.crimsonbit.viergewinntbot.spreadsheet;

import at.crimsonbit.testparser.api.sheetinterface.IKeyData;

public class SheetKey implements IKeyData{

	private String type;
	private double min;
	private double max;
	private String expr;
	private String[] values;
	private int digits;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min == null ? 0 : Double.parseDouble(min);
	}

	public double getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max == null ? 0 : Double.parseDouble(max);
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public String[] getValues() {
		return values;
	}

	public void setText(String text) {
		this.values = text == null ? null : text.split(";");
	}

	@Override
	public String toString() {
		return "SheetKey [type=" + type + ", min=" + min + ", max=" + max + ", expr=" + expr + ", text=" + values + "]";
	}

	public int getDigits() {
		return digits;
	}

	public void setDigits(int digits) {
		this.digits = digits;
	}

}
