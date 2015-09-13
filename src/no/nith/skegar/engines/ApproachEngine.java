package no.nith.skegar.engines;

import java.lang.Double;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that contains algorithms for when to approach the enemy and when to stop
 */
public class ApproachEngine
{
	private ArrayList<Double> positionsX;
	private ArrayList<Double> positionsY;
	boolean approach;
	
	public ApproachEngine()
	{
		positionsX = new ArrayList<Double>();
		positionsY = new ArrayList<Double>();
		approach = false;
	}
	
	/**
	 * Updates the enemies position in an array list
	 */
	public void updatePositions(Point2D.Double enemyPos)
	{
		positionsX.add(enemyPos.getX());
		positionsY.add(enemyPos.getY());
	}
	
	/**
	 * Method that returns the result of checkApproach and clears the array list
	 */
	public boolean checkApproach(double speed)
	{
		approach = approachEnemy(speed);

		positionsX.clear();
		positionsY.clear();

		return approach;
	}
	
	/**
	 * Method that decides when to stop approaching
	 */
	public boolean stopApproaching(Point2D.Double myPos, Point2D.Double enemyPos)
	{
		double xHigherPos = enemyPos.getX() + 100;
		double xLowerPos = enemyPos.getX() - 100;
		double yHigherPos = enemyPos.getY() + 100;
		double yLowerPos = enemyPos.getY() - 100;
		
		if(myPos.getX() > xLowerPos && myPos.getX() < xHigherPos && myPos.getY() > yLowerPos && 
				myPos.getY() < yHigherPos)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Method that decides when to approach the enemy
	 */
	private boolean approachEnemy(double speed)
	{
		boolean approach = false;
		
		double lowestX = 0;
		double lowestY = 0;
		double highestX = 0;
		double highestY = 0;
		double diffX = 0;
		double diffY = 0;
		
		Collections.sort(positionsX);
		Collections.sort(positionsY);
		
		if(positionsX.size() > 0 && positionsY.size() > 0)
		{	
			lowestX = positionsX.get(0);
			lowestY = positionsY.get(0);
			highestX = positionsX.get(positionsX.size() - 1);
			highestY = positionsY.get(positionsY.size() - 1);
		}
		
		diffX = highestX - lowestX;
		diffY = highestY - lowestY;

		if((speed <= 3 && speed >= -3) || diffX <= 40 || diffY <= 35)
		{
			approach = true;
		}
		
		return approach;
	}
}