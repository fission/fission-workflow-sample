package io.fission.workflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.fission.Context;
import io.fission.Function;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class CarPoolAllocator implements Function {

	private static List<String> CAR_RIDERS = new LinkedList<String>(
			Arrays.asList("Hilton ", "Leatha ", "Nicolasa ", "Peggie ", "Charlette ", "Noemi ", "Juliane ", "Ginger ",
					"Lorina ", "Bailey ", "Cheyenne ", "Libbie ", "Mitzi ", "Kristopher ", "Elli ", "Lindsay ",
					"Sharlene ", "Shela ", "Anastasia ", "Pablo ", "Russel ", "Granville ", "Kathyrn ", "Sheba ",
					"Santa ", "Reinaldo ", "Cristi ", "Amber ", "Doyle ", "Darcel ", "Madie ", "Basil ", "Brinda ",
					"Megan ", "Jacelyn ", "Fe ", "Kendal ", "Arie ", "Kathline ", "Bernard ", "Leandro ", "Savannah ",
					"Venice ", "China ", "Tennie ", "Estelle ", "Arielle ", "Leonel ", "Cammie ", "Tempie"));
	private static int LOWER_BOUND = 1;
	private static int UPPER_BOUND = 3;

	private static Logger logger = Logger.getGlobal();
	final ObjectMapper mapper = new ObjectMapper();
	JedisPool pool = new JedisPool(new JedisPoolConfig(), "redis-single-redis.redis");

	public ResponseEntity call(RequestEntity req, Context context) {
		List cplist = (ArrayList) req.getBody();
		logger.info("Data=" + cplist);
		// Invalid car pools will lead to null once from previous validator
		cplist.removeAll(Collections.singleton(null));
		logger.info("Data After cleaning =" + cplist);

		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/json");
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Headers", "*");
		headers.add("Access-Control-Allow-Credentials", "true");
		headers.add("Access-Control-Allow-Methods", "*");
		headers.add("Access-Control-Expose-Headers", "*");

		List<CpRider> riderList = this.generateRiders();
		List<CarPool> carsList = new ArrayList<CarPool>();

		for (Object cp : cplist) {
			HashMap data = (HashMap) cp;
			CarPool carPool = mapper.convertValue(data, CarPool.class);
			if (carPool != null) {
				logger.info("CarPool Available:" + carPool.toString());
				carsList.add(carPool);
			}
		}
		CpAllocation cp = this.matchRides(carsList, riderList);
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(cp);
	}

	private List<CpRider> generateRiders() {
		List<CpRider> riderList = new LinkedList<CpRider>();
		for (int j = 0; j < 50; j++) {
			Random rand = new Random();
			riderList.add(new CpRider(CAR_RIDERS.get(rand.nextInt(CAR_RIDERS.size())),
					rand.nextInt(UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND));
		}
		return riderList;
	}

	/*
	 * Right now a simple and rudimentry way to match Carpools with riders!
	 * 
	 */
	private CpAllocation matchRides(List<CarPool> cpList, List<CpRider> riderList) {
		logger.info("CpList = " + cpList);
		logger.info("RiderList = " + riderList);

		CpAllocation cpalloc = new CpAllocation();
		for (Iterator<CarPool> cpit = cpList.iterator(); cpit.hasNext();) {
			CarPool cp = cpit.next();
			int seatsAvailableTotal = cp.getAvailableSeats();
			int seatsAvailableNow = seatsAvailableTotal;
			logger.info("Current CP=" + cp);
			for (int i = 0; i <= seatsAvailableTotal; i++) {
				if (!riderList.isEmpty()) {
					CpRider rider = riderList.get(0);
					logger.info("Rider Processing:"+ rider);
					int seatsRequested = rider.getSeatsRequested();
					if (seatsAvailableNow >= seatsRequested) {
						seatsAvailableNow = seatsAvailableTotal - seatsRequested;
						cpalloc.addMatch(cp.getCarNumberPlate(), rider);
						riderList.remove(rider);
					} else {
						logger.info("Rider did not get allocation:"+ rider);
					}
				}
			}
			cpit.remove();
		}
		cpalloc.setUnmatchedRiders(riderList);
		cpalloc.setUnmatchedCars(cpList);
		return cpalloc;
	}
}
