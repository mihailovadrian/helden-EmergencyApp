package entities;

public class EventLocation {
	private long idEvent;
	private long idLocation;

	public EventLocation(int idEvent, int idLocation) {
		super();
		this.idEvent = idEvent;
		this.idLocation = idLocation;
	}

	public EventLocation() {
		super();
		this.idEvent = 0;
		this.idLocation = 0;
	}

	@Override
	public String toString() {
		return "EventLocation [idEvent=" + idEvent + ", idLocation=" + idLocation + "]";
	}

	public long getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(long id) {
		this.idEvent = id;
	}

	public long getIdLocation() {
		return idLocation;
	}

	public void setIdLocation(int idLocation) {
		this.idLocation = idLocation;
	}

}
