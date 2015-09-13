package no.nith.skegar.fsm;

public class ApproachState extends StateAdapter
{	
	public ApproachState(Dominator dominator)
	{
		super(dominator);
	}
	
	@Override
	public double getRelevance() 
	{
		double relevance = 0;
		
		boolean ramming = ((RamState) dominator.getAttackStateMachine().getStates().get(3)).getRamming();
		dominator.getPriMovEng().setApproaching(dominator.getTurretEng().getApproach()); 
		if(dominator.getTurretEng().getApproach() && (!ramming))
		{
			relevance = 1;
		}
		
		
		return relevance;
	}
	
	@Override
	public void onEnter()
	{
		dominator.getPriMovEng().getGravityPoints().get(0).updatePower(1000);
	}

	@Override
	public void onExecute()
	{
		if(!(dominator.getApproachEngine().stopApproaching(dominator.getMyPos(), 
				dominator.getEnemy().getPosition())))
		{
			usePrimaryAGMEngine();
		}
	}
}
