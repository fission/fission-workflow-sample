package io.fission.workflow;

import java.io.Serializable;

public class CarPool implements Serializable {
	
	private String carPoolOwner;
	private String carNumberPlate;
	private int availableSeats;
	
	public CarPool() {
		
	}
	
	public CarPool(String owner, String numPlate, int seatsAvailable) {
		super();
		this.carPoolOwner = owner;
		this.carNumberPlate = numPlate;
		this.availableSeats = seatsAvailable;
	}
	
	public String getCarPoolOwner() {
		return carPoolOwner;
	}
	public void setCarPoolOwner(String carPoolOwner) {
		this.carPoolOwner = carPoolOwner;
	}
	public String getCarNumberPlate() {
		return carNumberPlate;
	}
	public void setCarNumberPlate(String carNumberPlate) {
		this.carNumberPlate = carNumberPlate;
	}
	public int getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	
	

}
