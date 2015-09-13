package no.nith.skegar.fsm;

import no.nith.skegar.enums.ELevel;
import no.nith.skegar.logger.Logger;

public class DriverStateMachine extends StateMachine
{
	private static Logger logger = new Logger("DriverStateMachine");
	
	public DriverStateMachine(Dominator dominator)
	{	
		super();
		
		states.add(new IdleState(dominator));
		states.add(new MoveState(dominator));
		states.add(new DodgeState(dominator));
		states.add(new ApproachState(dominator));
	}
	
	@Override
	public void onExecute() 
	{
		states.get(activeStateIndex).onExecute();
	}

	@Override
	public void onUpdate() 
	{
		String stateBefore = states.get(activeStateIndex).getClass().getSimpleName();
		
		highestRelevance = -1;
		
		int oldActiveStateIndex = activeStateIndex;
		
		// Loop through and transition to new state
		for(int i = 0; i < states.size(); i++) 
		{
			double relevance = states.get(i).getRelevance();
			if(relevance > highestRelevance)
			{
				highestRelevance = relevance;
				activeStateIndex = i;
			}
		}
		
		// If current state changed, call onEnter
		if(activeStateIndex != oldActiveStateIndex)
		{
			states.get(activeStateIndex).onEnter();
		}
		
		String stateAfter = states.get(activeStateIndex).getClass().getSimpleName();
		
		logger.log(ELevel.INFO, "From state " + stateBefore + " to state " + stateAfter);
	}
}