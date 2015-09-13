package no.nith.skegar.logger;

import no.nith.skegar.enums.ELevel;

/**
 * A class that logs information
 */
public class Logger
{
	private String source;
	
	public Logger(String source)
	{
		this.source = source;
	}
	
	/**
	 * Used to log the information
	 */
	public void log(ELevel currentLevel, String logMessage)
	{
		SystemLogger.getInstance().write(source, currentLevel, logMessage);
	}
}