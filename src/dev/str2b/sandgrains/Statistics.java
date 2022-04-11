package dev.str2b.sandgrains;

import java.util.HashMap;
import java.util.Map;

public class Statistics {
	private Map<Integer, Integer> deviation;
	private int total;

	public Statistics() {
		deviation = new HashMap<>();
	}

	public void count(int key) {
		if (deviation.containsKey(key)) {
			deviation.put(key, deviation.get(key) + 1);
		} else {
			deviation.put(key, 1);
		}
		total += key;
	}

	final public Map<Integer, Integer> getDeviation() {
		return deviation;
	}

	public int getTotal() {
		return total;
	}

}
