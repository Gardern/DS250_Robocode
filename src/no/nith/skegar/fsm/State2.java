package no.nith.skegar.fsm;

/**
 * State2 is the superclass for active state generates states
 */
public abstract class State2
{
	protected Dominator dominator;
	
	public State2(Dominator dominator)
	{
		this.dominator = dominator;
	}
	
	/**
	 * Method checks for transitions to other states
	 */
	public abstract int onUpdate();
	
	/**
	 * Method executes the states actions
	 */
	public abstract void onExecute();
}