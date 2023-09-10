package com.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LOG {
	
	
	private static Logger logger;
	
	public LOG() {
		
	}
	
	
	public static Logger getInstance() {
		if(logger==null) {
			logger=LogManager.getLogger("E-Commerce-Application");
		}
		return logger;
	}

	public static void info(String msg) {
		logger.info(msg);
	}
	
	public static void warn(String msg) {
		logger.warn(msg);
	}

	public static void error(String msg) {
		logger.error(msg);
	}
	
	public static void error(Level level, String message, Exception e) {
		logger.log(level, message, e);
	}

	public static void debug(String msg) {
		logger.debug(msg);
	}
	
}
