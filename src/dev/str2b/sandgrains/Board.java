package dev.str2b.sandgrains;

import java.awt.Point;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Board {

	private Map<Point, Field> fields;
	private Range xRange, yRange, zRange;

	public Board(Map<Point, Field> board, Range xRange, Range yRange, Range zRange) {
		this.fields = board;

		this.xRange = xRange;
		this.yRange = yRange;
		this.zRange = zRange;
	}

	public Field getParticle(Point pt) {
		return fields.get(pt);
	}

	private void setField(Point pt, Field f) {
		fields.put(pt, f);
	}

	private boolean isInRange(final Point pt) {
		return xRange.contains((int) pt.getX()) && yRange.contains((int) pt.getY());
	}

	public List<Point> getNeighboringPoints(Point pt) {
		List<Point> coordinates = Arrays.asList(new Point(-1, 0), new Point(0, -1), new Point(0, +1), new Point(+1, 0));
		List<Point> neighbors = new LinkedList<>();
		for (Point v : coordinates) {
			Point neighboringPt = new Point(pt);
			neighboringPt.translate((int) v.getX(), (int) v.getY());
			if (isInRange(pt)) {
				neighbors.add(neighboringPt);
			}
		}
		return neighbors;
	}

	public List<Field> getNeighbors(Point pt) {
		List<Point> neighboringPoints = getNeighboringPoints(pt);
		List<Field> neighbors = new LinkedList<>();
		for (Point p : neighboringPoints) {
			try {
				Field f = getField(p);
				neighbors.add(f);
			} catch (OutOfRangeException e) {
				e.printStackTrace();
			}
		}
		return neighbors;
	}

	public Field getField(Point pt) throws OutOfRangeException {
		if (isInRange(pt)) {
			Field f = fields.get(pt);
			if (f == null) {
				f = new Field(zRange, pt);
				this.setField(pt, f);
			}
			return f;
		} else {
			throw new OutOfRangeException(pt);
		}
	}

	public void increaseHeight(Point pt) throws OutOfRangeException {
		Field f = this.getField(pt);
		try {
			f.increase();
		} catch (OutOfSpaceException e) {
			f.collapse();
			List<Point> neighboringPoints = getNeighboringPoints(pt);
			for (Point neighborPoint : neighboringPoints) {
				increaseHeight(neighborPoint);
			}
		}
	}

	@Override
	public String toString() {
		return "Board [fields=" + fields + ", xRange=" + xRange + ", yRange=" + yRange + "]";
	}

	public void visualize() {
		for (int y = yRange.getMin(); y <= yRange.getMax(); y++) {
			for (int x = xRange.getMin(); x <= xRange.getMax(); x++) {
				try {
					System.out.format("| %d ", getField(new Point(x, y)).getHeight());
				} catch (OutOfRangeException e) {
					e.printStackTrace();
				}
			}
			System.out.format("|\n");
		}
	}

	final public Statistics computeStatistics() {
		Statistics s = new Statistics();
		for (Field f : fields.values()) {
			s.count(f.getHeight());
		}
		return s;
	}

	public void writeCoordinatesCsv(String fileName, String seperator) {
        List<String> lines = new LinkedList<>();
		for (int y = yRange.getMin(); y <= yRange.getMax(); y++) {
			for (int x = xRange.getMin(); x <= xRange.getMax(); x++) {
				try {
					lines.add(String.format("%d%s%d%s%d", x, seperator, y, seperator, getField(new Point(x, y)).getHeight()));
				} catch (OutOfRangeException e) {
					e.printStackTrace();
				}
			}
		}
        Path path = Paths.get(fileName);
        try {
			Files.write(path, lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
