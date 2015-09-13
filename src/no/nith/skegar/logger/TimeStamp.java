package no.nith.skegar.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Used to get the current time
 */
public class TimeStamp 
{
	private static SimpleDateFormat dateFormat = 
				new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss:S");
	
	public static String getTimeStamp()
	{
		return dateFormat.format(new Date()) + " - ";
	}
}