package no.nith.skegar.engines;

import java.awt.geom.Point2D;
/**
 * Class that is used to dodge bullets
 */
public class DodgeEngine 
{
	private static final int HIGH_POS_TURN = 90;
	private static final int LOW_POS_TURN = 60;
	private static final int HIGH_NEG_TURN = -90;
	
	public DodgeEngine()
	{
	}
	
	/**
	 * Generates a new random angle to turn in
	 * between 60 and 90 or -60 and -90
	 */
	public int findDodgeAngle()
	{
		int randomTurn = 0;
		
		int tall = (int) (Math.random() * 2);
		
		if(tall == 1)
		{
			randomTurn = (int) (Math.random() * (HIGH_POS_TURN - LOW_POS_TURN + 1) + LOW_POS_TURN);
		}
		else
		{
			randomTurn = (int) (Math.random() * (-HIGH_NEG_TURN + LOW_POS_TURN - 1) - LOW_POS_TURN);
		}
		
		return randomTurn;
	}
	
	/**
	 * Checks if we are close to a wall and reverse the tank
	 */
	public double avoidWalls(Point2D.Double myPos)
	{
		double reverse = 0;
		
		if(myPos.getX() > 720 || myPos.getX() < 80 || myPos.getY() > 520 || myPos.getY() < 80)
		{
			reverse = -1000;
		}
		return reverse;
	}
}