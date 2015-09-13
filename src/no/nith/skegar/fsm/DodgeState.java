package no.nith.skegar.fsm;

public class DodgeState extends StateAdapter
{
	private boolean firstTick;
	
	public DodgeState(Dominator dominator)
	{
		super(dominator);
		firstTick = true;
	}

	@Override
	public double getRelevance() 
	{
		double relevance = 0;
		boolean ramming = ((RamState) dominator.getAttackStateMachine().getStates().get(3)).getRamming();
		
		if(dominator.isEnemyShooting() && !(ramming))
		{
			firstTick = true;
			relevance = 0.9;
		}
		else if(!(dominator.isEnemyShooting()) && dominator.getCounter() > 0 && !(ramming)) 
		{
			relevance = 0.9;
		}
		
		return relevance;
	}

	@Override
	public void onExecute() 
	{
		if(firstTick)
		{
			int driveAhead = 175;
			int randomTurn = dominator.getDodgeEng().findDodgeAngle();
			
			dominator.setTurnLeft(randomTurn);
			dominator.setAhead(driveAhead);

			dominator.setEnemyShooting(false);
			firstTick = false;
			dominator.setCounter(30);
		}
		
		double reverse = dominator.getDodgeEng().avoidWalls(dominator.getMyPos());
		
		if(reverse != 0)
		{
			dominator.setAhead(reverse);
		}
	}
}
