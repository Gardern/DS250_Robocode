package no.nith.skegar.fsm;

public class StateAdapter implements State
{
	protected Dominator dominator;
	
	public StateAdapter(Dominator dominator)
	{
		this.dominator = dominator;
	}
	
	@Override
	public double getRelevance()
	{
		return 0;
	}

	@Override
	public void onEnter()
	{
	}

	@Override
	public void onExecute()
	{
	}
	
	public void usePrimaryAGMEngine()
	{
		dominator.getPriMovEng().updateTotalForce();
		
		double xTotalForce = dominator.getPriMovEng().getXTotalForce();
		double yTotalForce = dominator.getPriMovEng().getYTotalForce();
		
		double distanceToMove = dominator.getPriMovEng().updateMove(dominator.getMyPos().getX() - xTotalForce, 
				dominator.getMyPos().getY() - yTotalForce, dominator.getHeading(), dominator.getMyPos());
		
		dominator.setTurnLeft(dominator.getPriMovEng().getTurnAngle());
		dominator.setAhead(distanceToMove);
	}
}