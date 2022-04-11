package dev.str2b.sandgrains;

import java.awt.Point;

public class Field {

	private int height;
	private Range maxHeight;
	private Point position;

	public Field(Range zRange, Point position) {
		this.maxHeight = zRange;
		this.position = position;
	}

	public void increase() throws OutOfSpaceException {
		if (maxHeight.contains(height + 1)) {
			height++;
		} else {
			throw new OutOfSpaceException();
		}
	}

	public void collapse() {
		height = 0;
	}

	public int getHeight() {
		return height;
	}
	
	@Override
	public String toString() {
		return "Field [height=" + height + ", maxHeight=" + maxHeight + ", position=" + position + "]";
	}

}
