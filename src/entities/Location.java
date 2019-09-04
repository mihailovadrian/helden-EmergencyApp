package entities;

public class Location {
	private int id;
	private String Name;

	public Location(int id, String name) {
		super();
		this.id = id;
		Name = name;
	}

	public Location() {
		super();
		this.id = 0;
		Name = "";
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", Name=" + Name + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

}
