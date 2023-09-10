package com.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ExpirationDate {
	
	private ExpirationDate() {
		
	}
	
	public static long getExpirationDate() {
		
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime newExpirayTime = currentTime.plusDays(Constants.EXPIRATION_DAYS);
		
		return newExpirayTime.toEpochSecond(ZoneOffset.UTC);
		
	}
	
	public static boolean checExpiredDate(String expiredDate) {
		
		long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
		
		return expiredDate.compareTo(String.valueOf(currentTime)) < 0;
	}

}
