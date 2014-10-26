package no.uib.mof077.shortbytes.decisiontree;

import java.util.HashMap;
import java.util.Map;

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
	
	private Map<String, String> properties;
	
	public Person(Gender gender, int numCars, TravelCost travelCost,
			IncomeLevel incomeLevel, TransportMode transportMode) {
		super();
		properties = new HashMap<String, String>();
		this.gender = gender;
		this.numCars = numCars;
		this.travelCost = travelCost;
		this.incomeLevel = incomeLevel;
		this.transportMode = transportMode;
		properties.put("Gender", gender.name());
		properties.put("NumCars", "" + numCars);
		properties.put("TravelCost", travelCost.name());
		properties.put("IncomeLevel", incomeLevel.name());
		properties.put("TransportMode", transportMode.name());
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

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	
	public static int countCategoryValues(String keyCat) {
		switch (keyCat.toLowerCase()) {
		case "gender":
			return Gender.values().length;
		case "travelcost":
			return TravelCost.values().length;
		case "incomelevel":
			return IncomeLevel.values().length;
		case "transportmode":
			return TransportMode.values().length;
		case "numcars":
			return 3;
		default:
			System.out.println("Unrecognized!");
			return 0;
		}
	}
}
