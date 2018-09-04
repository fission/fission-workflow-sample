package io.fission.workflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		List<String> strlist = new LinkedList<String>(Arrays.asList("JOHN", "LINDA"));
		Map<String, List<String>> test = new HashMap<String, List<String>>();
		test.put("ONE", strlist);
		System.out.println("Data=" + test);
		List<String> copy = test.get("ONE");
		copy.add("VISHAL");
		System.out.println("Data=" + test);
	}

}
