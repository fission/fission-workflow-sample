package io.fission.workflow;

public class CpRider {
	
	private String name;
	private int seatsRequested;
	
	
	public CpRider() {
		
	}
	
	public CpRider(String name, int seats) {
		super();
		this.name = name;
		this.seatsRequested = seats;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSeatsRequested() {
		return seatsRequested;
	}
	public void setSeatsRequested(int seatsRequested) {
		this.seatsRequested = seatsRequested;
	}
	
	public String toString() {
		return "CpRider: "+ this.name + " & Seats Needed:" + this.seatsRequested;
	}

}
