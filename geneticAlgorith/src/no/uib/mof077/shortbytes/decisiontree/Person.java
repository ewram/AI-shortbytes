package no.uib.mof077.shortbytes.decisiontree;

public class Person {
	public static enum Gender {
		MALE, FEMALE
	}
	
	public static enum TravelCost {
		CHEAP, STANDARD, EXPENSIVE
	}
	
	public static enum IncomeLevel {
		LOW, MEDIUM, HIGH
	}
	
	public static enum TransportMode {
		CAR, BUS, TRAIN
	}
	
	
	private Gender gender;
	private int numCars;
	private TravelCost travelCost;
	private IncomeLevel incomeLevel;
	private TransportMode transportMode;
	
	public Person(Gender gender, int numCars, TravelCost travelCost,
			IncomeLevel incomeLevel, TransportMode transportMode) {
		super();
		this.gender = gender;
		this.numCars = numCars;
		this.travelCost = travelCost;
		this.incomeLevel = incomeLevel;
		this.transportMode = transportMode;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getNumCars() {
		return numCars;
	}

	public void setNumCars(int numCars) {
		this.numCars = numCars;
	}

	public TravelCost getTravelCost() {
		return travelCost;
	}

	public void setTravelCost(TravelCost travelCost) {
		this.travelCost = travelCost;
	}

	public IncomeLevel getIncomeLevel() {
		return incomeLevel;
	}

	public void setIncomeLevel(IncomeLevel incomeLevel) {
		this.incomeLevel = incomeLevel;
	}

	public TransportMode getTransportMode() {
		return transportMode;
	}

	public void setTransportMode(TransportMode transportMode) {
		this.transportMode = transportMode;
	}
}
