

import java.util.ArrayList;
import java.util.List;

public class Placemark {
	private String type;
	private String name;
	private List<Coordinate> coordinates;

	public Placemark(List<Coordinate> points, String name, String type) {
		this.coordinates = new ArrayList<Coordinate>(points);
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Coordinate> getPoints() {
		return coordinates;
	}

	public void setPoints(List<Coordinate> points) {
		this.coordinates = coordinates;
	}
}
