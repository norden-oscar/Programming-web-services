package parsing;

public class Office {

	private String streetAddress;
	private String streetNumber;
	private String workerCount;
	public Office(String streetAddress, String streetNumber, String workerCount) {

		this.streetAddress = streetAddress;
		this.streetNumber = streetNumber;
		this.workerCount = workerCount;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public String getStreetNumber() {
		return streetNumber;
	}
	public String getWorkerCount() {
		return workerCount;
	}
	
}
