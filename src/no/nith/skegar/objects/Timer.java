package no.nith.skegar.objects;

/**
 * A simple timer class
 */
public class Timer
{
	private long currentTick;
	private long lastTick;

	public Timer()
	{
		currentTick = 0;
		lastTick = 0;
	}
	
	public void setCurrentTick(long currentTick)
	{
		this.currentTick = currentTick;
	}
	
	public void setLastTick(long lastTick)
	{
		this.lastTick = lastTick;
	}
	
	/**
	 * Returns the delta tick
	 */
	public int getDeltaTick()
	{
		return (int) (currentTick - lastTick);
	}
}