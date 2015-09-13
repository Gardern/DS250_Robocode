package no.nith.skegar.fsm;

import java.util.ArrayList;

/**
 * Contains methods that every child states compete needs to implement
 */
public abstract class StateMachine
{	
	protected ArrayList<State> states;
	protected int activeStateIndex;
	protected double highestRelevance;
	
	public StateMachine()
	{
		states = new ArrayList<State>();
		activeStateIndex = 0;
		highestRelevance = -1;
	}
	
	/**
	 * Method updates the AI logic by processing 
	 * the necessary transitions between the states
	 */
	public abstract void onUpdate();
	
	/**
	 * Method executes the AI logic by calling 
	 * the current states onExecute function
	 */
	public abstract void onExecute();
	
	public ArrayList<State> getStates()
	{
		return states;
	}
}