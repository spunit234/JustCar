package com.edios.cdf.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter  {
	SimpleDateFormat format1 = new SimpleDateFormat("MMMM");
	SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat format3 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	
	public String formaDate(Date date) {
		if (date!=null) {
			return format2.format(date); 
		}
		else {
			return null;
		}	 
	}
	
	
	public String formatMonth(Date date) {
		if (date!=null) {
			return format1.format(date);
		}
		else {
			return null;
		}
	}

	public String formatDateTime(Date date) {
		if (date!=null) {
			return format3.format(date); 
		}
		else {
			return null;
		}	 
	}
}
