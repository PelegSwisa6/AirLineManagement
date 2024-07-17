package AirlineManagmentSystem;

public class Airport {

	private int id;
	private String city;
	
	public Airport() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	  public void print() {
	        System.out.println("id: " + getId());
	        System.out.println("City: " + getCity());
	    }
}
