package a_Introductory;

public class Point {
	public int x, y;

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point add(Point p) {
		return new Point(this.x + p.x, this.y + p.y);
	}

	public Point sub(Point p) {
		return new Point(this.x - p.x, this.y - p.y);
	}
}
