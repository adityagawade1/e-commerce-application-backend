package com.utils;

import java.util.HashMap;
import java.util.Map;

public class UtilityMethod {

	private UtilityMethod() {
		
	}
	
	
	public static Map<String, String> parseQuery(String query){
			LOG.info("Query "+query);
			Map<String, String > parsetString = new HashMap<>();
				String[] arr = query.split("&");
				
				for (String s : arr) {
					String[] q = s.split("=",2);
					if(q[1] != null) {
						parsetString.put(q[0], q[1]);
					}
				}
		
				return parsetString;
		
		
		
	}
	
}
