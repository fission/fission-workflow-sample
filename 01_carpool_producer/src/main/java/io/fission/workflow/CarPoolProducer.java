package io.fission.workflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import io.fission.Context;
import io.fission.Function;

public class CarPoolProducer implements Function {

	private static Logger logger = Logger.getGlobal();
	private static List<String> CAR_OWNERS = new LinkedList<String>(Arrays.asList("JOHN", "LINDA", "TINA", "JACK", "MARTIN", "JOSEPH", "BEN",
			"LINUS", "KAREN", "TIFFANY"));
	private static List<String> CAR_PLATES = new LinkedList<String>(Arrays.asList("ABC-123", "DAD-OF3", "CAR-LOV", "THE-JON", "123-CYZ",
			"MOB-PDX", "SFO-FAN", "COL-RAD", "MAY-BEE", "MAN-VAL", "BAR_CAB", "JOH-N12", "MUM-BOY", "TOO-HIP",
			"BAD-MAN", "GHO-ST1", "PNQ-MUM", "PNQ-SFO"));
	private static int LOWER_BOUND = 1;
	private static int UPPER_BOUND = 5;

	public ResponseEntity call(RequestEntity req, Context context) {
		CarPoolProducer cp = new CarPoolProducer();
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/json");
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(cp.generateCarpools());
	}

	private List<CarPool> generateCarpools() {
		List<CarPool> poolList = new ArrayList<CarPool>();
		for (int j = 0; j < 15; j++) {
			Random rand = new Random();
			int randLoc = rand.nextInt(CAR_PLATES.size());
			poolList.add(new CarPool(CAR_OWNERS.get(rand.nextInt(CAR_OWNERS.size())), CAR_PLATES.get(randLoc),
					rand.nextInt(UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND));
		}
		return poolList;
	}

}
