package AirlineManagmentSystem;

public class Airplane {

	private int id;
	private int EconomyCapacity;
	private int BusinessCapacity;
	private String model;
	
	public Airplane() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEconomyCapacity() {
		return EconomyCapacity;
	}

	public void setEconomyCapacity(int economyCapacity) {
		EconomyCapacity = economyCapacity;
	}

	public int getBusinessCapacity() {
		return BusinessCapacity;
	}

	public void setBusinessCapacity(int businessCapacity) {
		BusinessCapacity = businessCapacity;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public void print() {
        System.out.println("id: " + getId());
        System.out.println("Economy capacity: " + getEconomyCapacity());
        System.out.println("Business  capacity: " + getBusinessCapacity());
    }
	
}
