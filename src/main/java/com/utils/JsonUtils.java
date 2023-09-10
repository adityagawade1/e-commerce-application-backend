package com.utils;

import com.google.gson.Gson;

public class JsonUtils {

	private static Gson gson = null;

	public static Gson getGson() {
		if (gson == null) {
			gson = new Gson();
		}
		return gson;
	}

	
}
