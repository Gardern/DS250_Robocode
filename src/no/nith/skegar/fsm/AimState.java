package no.nith.skegar.fsm;

public class AimState extends StateAdapter
{	
	public AimState(Dominator dominator)
	{
		super(dominator);
	}

	@Override
	public double getRelevance() 
	{
		double relevance = 0;
		
		if(dominator.getEnemy().getDistance() <= 700)
		{
			relevance = 0.9;
		}
		
		return relevance;
	}
	
	@Override
	public void onEnter()
	{
		aim();
	}

	@Override
	public void onExecute() 
	{	
		if(dominator.getTurretEng().getHeat() > 0)
		{
			aim();
		}
		
		else if(dominator.getGunTurnRemaining() == 0 && dominator.getTurretEng().getHeat() <= 0)
		{
			dominator.setAimDone(true);
		}
	}
	
	private void aim()
	{
		double turnAngle = dominator.getTurretEng().aimGun(dominator.getHeading(), 
				dominator.getGunHeading(), dominator.getEnergy(),
				dominator.getMyPos(), dominator.getBattleFieldHeight(),
				dominator.getBattleFieldWidth(), dominator.getEnemy());
		
		dominator.setTurnGunRight(turnAngle);
	}
}