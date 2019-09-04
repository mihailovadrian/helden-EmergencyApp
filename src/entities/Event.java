package entities;

public class Event {
	private long id;
	private String Name;
	private String Description;
	private long Status;

	public Event(long id, String name, String description, int status) {
		super();
		this.id = id;
		Name = name;
		Description = description;
		Status = status;
	}

	public Event() {
		super();
		this.id = (long) 0;
		Name = "";
		Description = "";
		Status = -1;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", Name=" + Name + ", Description=" + Description + ", Status=" + Status + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public long getStatus() {
		return Status;
	}

	public void setStatus(long status) {
		Status = status;
	}

}
