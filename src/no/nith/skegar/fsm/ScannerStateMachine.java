package no.nith.skegar.fsm;

import java.util.ArrayList;
import no.nith.skegar.enums.ELevel;
import no.nith.skegar.logger.Logger;

public class ScannerStateMachine
{
	private static Logger logger = new Logger("ScannerStateMachine");
	
	private ArrayList<State2> states;
	private int activeStateIndex;
	
	ScannerStateMachine(Dominator dominator)
	{
		states = new ArrayList<State2>();
		activeStateIndex = 0;
		
		states.add(new ScanState(dominator));
		states.add(new KeepLockState(dominator));
	}
	
	public void onExecute()
	{
		states.get(activeStateIndex).onExecute();
	}

	public void onUpdate()
	{
		String stateBefore = states.get(activeStateIndex).getClass().getSimpleName();
	
		// Check for transitions
		activeStateIndex = states.get(activeStateIndex).onUpdate();
		
		String stateAfter = states.get(activeStateIndex).getClass().getSimpleName();
		
		logger.log(ELevel.INFO, "From state " + stateBefore + " to state " + stateAfter);
	}
}