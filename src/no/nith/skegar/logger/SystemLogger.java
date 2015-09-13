package no.nith.skegar.logger;

import no.nith.skegar.enums.ELevel;

/**
 * A singleton class that is used by Logger to write the information to
 * the screen
 * Variable console prints to screen only if it is true
 */
public class SystemLogger
{
	private static final SystemLogger INSTANCE = new SystemLogger();
	private boolean console;

	private SystemLogger()
	{
		console = true;
	}
	
	public static SystemLogger getInstance()
	{
		return INSTANCE;
	}
	
	/**
	 * Performs the printing
	 */
	public void write(String source, ELevel currentLevel, String logMessage)
	{
		String stream = "";
		stream += TimeStamp.getTimeStamp();
		stream += source + " - " + currentLevel + " - " + logMessage;
		
		if(console)
		{
			System.out.println(stream);
		}
	}
}