package dev.str2b.sandgrains;

import java.awt.Point;
import java.util.HashMap;

public class Main {

	public static void main(String... strings) {
		Board b = new Board(new HashMap<>(), new Range(-50, 50), new Range(-50, 50), new Range(0, 3));
		b.increaseHeight(new Point(-1, 0));

		for (int i = 0; i < 20000; i++) {
			b.increaseHeight(new Point(0, 0));
		}
//			b.visualize();
		Statistics s = b.computeStatistics();

		b.writeCoordinatesCsv("results.csv", ";");
		System.out.format("Deviation: %s\n", s.getDeviation());
		System.out.println("Accumulated height: " + s.getTotal());

	}

}
