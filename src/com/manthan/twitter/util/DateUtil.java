package com.manthan.twitter.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This Date Util Class.
 * 
 * @author rharish
 *
 */
public class DateUtil {

	private static final String TON_EXPIRE_DATE_FORMAT = "E, dd MMM yyyy HH:mm:ss z";
	/**
	 * @param args
	 */
	public static String getTONExpirationDate() {
		//Add 7 Days ahead a Expiry
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 7);
		return new SimpleDateFormat(TON_EXPIRE_DATE_FORMAT).format(cal.getTime());
	}

}
