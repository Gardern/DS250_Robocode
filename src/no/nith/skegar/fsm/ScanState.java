package no.nith.skegar.fsm;

public class ScanState extends State2
{	
	public ScanState(Dominator dominator)
	{
		super(dominator);
	}
	
	@Override
	public int onUpdate()
	{	
		int activeStateIndex = 0;
		
		if(dominator.isEnemyDetected())
		{
			activeStateIndex = 1;
		}
		
		return activeStateIndex;
	}
	
	@Override
	public void onExecute()
	{
		dominator.setTurnRadarRight(10);
	}
}