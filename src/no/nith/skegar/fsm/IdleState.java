package no.nith.skegar.fsm;


public class IdleState extends StateAdapter
{
	
	public IdleState(Dominator dominator)
	{
		super(dominator);
	}
	
	@Override
	public double getRelevance() 
	{
		return 0;
	}

	@Override
	public void onExecute() 
	{
		
	}
}