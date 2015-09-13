package no.nith.skegar.fsm;

public class FireState extends StateAdapter
{

	public FireState(Dominator dominator)
	{
		super(dominator);
	}

	@Override
	public double getRelevance() 
	{
		double relevance = 0;
		
		if(dominator.getAimDone())
		{
			relevance = 1;
		}
		
		return relevance;
	}

	@Override
	public void onExecute() 
	{
		double power = dominator.getTurretEng().getBulletPower();
		dominator.setFire(power);
		dominator.getTurretEng().setGunHeat(1 + (power / 5));
		dominator.setAimDone(false);
	}
}