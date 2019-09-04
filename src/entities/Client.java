package entities;

public class Client {
	private int id;
	private String Name;
	private String Longitude;
	private String Latitude;
	private long Status;
	private long Location_id;

	public Client(int id, String name, String longitude, String latitude, long status, long location_id) {
		super();
		this.id = id;
		Name = name;
		Longitude = longitude;
		Latitude = latitude;
		Status = status;
		Location_id = location_id;
	}

	public Client() {
		super();
		this.id = 0;
		Name = "";
		Longitude = "";
		Latitude = "";
		Status = -1;
		Location_id = 0;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", Name=" + Name + ", Longitude=" + Longitude + ", Latitude=" + Latitude
				+ ", Status=" + Status + ", Location_id=" + Location_id + "]";
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

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public long getStatus() {
		return Status;
	}

	public void setStatus(long status) {
		Status = status;
	}

	public long getLocation_id() {
		return Location_id;
	}

	public void setLocation_id(long location_id) {
		Location_id = location_id;
	}

}
