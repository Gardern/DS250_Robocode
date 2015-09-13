package no.nith.skegar.fsm;

public class RamState extends StateAdapter
{
	private boolean ramming;
	
	public RamState(Dominator dominator)
	{
		super(dominator);
		ramming = false;
	}
	
	@Override
	public double getRelevance() 
	{
		double relevance = 0;
		ramming = false;

		if(dominator.getCanRam() && dominator.getEnemy().getEnergy() <= 25 &&
				dominator.getEnergy() >= 50) 
		{
			ramming = true;
			dominator.getPriMovEng().setRamming(true);
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
		usePrimaryAGMEngine();
	}
	
	public boolean getRamming()
	{
		return ramming;
	}
	
	public void setRamming(boolean ramming)
	{
		this.ramming = ramming;
	}
}