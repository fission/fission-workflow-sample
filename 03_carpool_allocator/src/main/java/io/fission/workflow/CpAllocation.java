package io.fission.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CpAllocation {
	
	Map<String, List<CpRider>> matchedRides = new HashMap<String, List<CpRider>>();
	List<CpRider> unmatchedRiders = new ArrayList<CpRider>();
	List<CarPool> unmatchedCars = new ArrayList<CarPool>();
	
	public CpAllocation() {
	}
	
	public void addMatch(String carPlate, CpRider rider) {
		if (this.matchedRides.containsKey(carPlate)) {
			List<CpRider> riderList  = this.matchedRides.get(carPlate);
			riderList.add(rider);
		} else {
			List<CpRider> riderList = new ArrayList<CpRider>();
			riderList.add(rider);
			this.matchedRides.put(carPlate, riderList);	
		} 
		
	}
	
	public Map<String, List<CpRider>> getMatchedRides() {
		return matchedRides;
	}

	public void setMatchedRides(Map<String, List<CpRider>> matchedRides) {
		this.matchedRides = matchedRides;
	}

	public List<CpRider> getUnmatchedRiders() {
		return unmatchedRiders;
	}

	public void setUnmatchedRiders(List<CpRider> unmatchedRiders) {
		this.unmatchedRiders = unmatchedRiders;
	}

	public List<CarPool> getUnmatchedCars() {
		return unmatchedCars;
	}

	public void setUnmatchedCars(List<CarPool> unmatchedCars) {
		this.unmatchedCars = unmatchedCars;
	}
	
}
