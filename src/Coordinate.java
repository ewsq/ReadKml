

public class Coordinate {
	private double x;
	private double y;
	private double alt;//¸ß³Ì

	public Coordinate(double x, double y,double alt)
	{
		this.x = x;
		this.y = y;
		this.alt = alt;

	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setAlt(double alt) {
		this.alt = alt;
	}
	public double getAlt() {
		return alt;
	}
	public void setY(double y) {
		this.y = y;
	}

}
