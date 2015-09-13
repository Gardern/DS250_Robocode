package no.nith.skegar.fsm;

public class KeepLockState extends State2
{
	
	public KeepLockState(Dominator dominator)
	{
		super(dominator);
	}
	
	@Override
	public int onUpdate()
	{	
		int activeStateIndex = 1;
		
		if(!(dominator.isEnemyDetected()))
		{
			activeStateIndex = 0;
		}
	
		return activeStateIndex;
	}
	
	@Override
	public void onExecute()
	{
		dominator.setTurnRadarRight(dominator.getRadarTurn());
		dominator.setEnemyDetected(false);
	}
}