package io.fission.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

public class CarPoolValidator implements Function {

	private static Logger logger = Logger.getGlobal();
	final ObjectMapper mapper = new ObjectMapper();
	JedisPool pool = new JedisPool(new JedisPoolConfig(), "redis-single-redis.redis");
	
	public ResponseEntity call(RequestEntity req, Context context) {
		HashMap data = (HashMap) req.getBody();
		CarPool carPool = mapper.convertValue(data, CarPool.class);
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/json");
		
		if (carPool.getAvailableSeats() > 4 ) {
			logger.info("Ignoring the carpool request:"+ carPool);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(carPool);
		}	
		return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).build();
	}

}
