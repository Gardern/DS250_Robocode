package no.nith.skegar.fsm;

public class MoveState extends StateAdapter
{
	private int agmTypeCounter;
	
	public MoveState(Dominator dominator)
	{
		super(dominator);
		agmTypeCounter = -30;
	}
	
	@Override
	public double getRelevance() 
	{
		double relevance = 0;
		boolean ramming = ((RamState) dominator.getAttackStateMachine().getStates().get(3)).getRamming();
		
		dominator.setCounter(dominator.getCounter() - 1);
		if(!(dominator.isEnemyShooting()) && dominator.getCounter() <= 0 && !(ramming)) 
		{
			relevance = 1;
		}
		
		return relevance;
	}
	
	@Override
	public void onEnter()
	{
		dominator.getPriMovEng().getGravityPoints().get(0).updatePower(-1000);
	}

	@Override
	public void onExecute()
	{	
		if(agmTypeCounter > 0)
		{
			dominator.getSecMovEng().updateTotalForce();
			
			double xTotalForce = dominator.getSecMovEng().getXTotalForce();
			double yTotalForce = dominator.getSecMovEng().getYTotalForce();
			
			double distanceToMove = dominator.getSecMovEng().updateMove(dominator.getMyPos().getX() - xTotalForce, 
					dominator.getMyPos().getY() - yTotalForce, dominator.getHeading(), dominator.getMyPos());
			
			dominator.setTurnLeft(dominator.getSecMovEng().getTurnAngle());
			dominator.setAhead(distanceToMove);
			
			if(agmTypeCounter == 30)
			{
				agmTypeCounter = -30;
			}
		}
		else
		{
			usePrimaryAGMEngine();
		}
		agmTypeCounter++;
	}
}
