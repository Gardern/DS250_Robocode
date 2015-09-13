package no.nith.skegar.fsm;

/**
 * State is the interface for child states compete states
 */
public interface State
{
	/**
	 * Method returns the states relevance from 0 - 1
	 */
	public double getRelevance();
	
	/**
	 * Method executes necessary processing before the state is executed
	 */
	public void onEnter();
	
	/**
	 * Method executes the states actions
	 */
	public void onExecute();
}