package dev.str2b.sandgrains;

public class Range {
	private int min;
	private int max;

	public Range(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public boolean contains(int value) {
		return (value >= min && value <= max);
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	@Override
	public String toString() {
		return "Range [min=" + min + ", max=" + max + "]";
	}
}