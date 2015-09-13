package no.nith.skegar.engines;

import java.awt.geom.Point2D;
import no.nith.skegar.math.BasicMath;
import no.nith.skegar.objects.GravityPoint;

/**
 * Class that is used as the secondary AGM Engine
 * Contains a randomly generated gravity point to follow
 */
public class SecondaryAGMEngine extends AGMEngine
{
	private GravityPoint gravityPoint;
	
	public SecondaryAGMEngine(Point2D.Double myPos)
	{
		super(myPos);
	}
	
	/**
	 * Initializes the gravity point
	 */
	public void initGravityPoints()
	{
		gravityPoint = new GravityPoint(new Point2D.Double(300, 200), 1000);
	}
	
	/**
	 * Updates the gravity point
	 */
	public void updateGravityPoints()
	{
		double xPos = (int) (Math.random() * (720 - 80 + 1) + 80);
		double yPos = (int) (Math.random() * (520 - 80 + 1) + 80);
		
		gravityPoint.updatePosition(new Point2D.Double(xPos, yPos));
	}
	
	/**
	 * Adds together the forces in x and y direction
	 */
	public void updateTotalForce()
	{
		setXTotalForce(0);
		setYTotalForce(0);
		double singleForce = 0;
		double angle = 0;
		
		singleForce = gravityPoint.getPower() / Math.pow(getMyPos().distance
				(gravityPoint.getPos()), 2);

		angle = BasicMath.normalizeBearing(Math.PI/2 - Math.atan2
				(getMyPos().getY() - gravityPoint.getPos().getY(), 
						getMyPos().getX() - gravityPoint.getPos().getX())); 
		
		setXTotalForce(getXTotalForce() + Math.sin(angle) * singleForce);
		setYTotalForce(getYTotalForce() + Math.cos(angle) * singleForce);
	}
	
	/**
	 * Checks if the randomly generated position is reached, and then
	 * updates with a new one
	 */
	public void checkIfPositionIsReached(Point2D.Double myPos)
	{
		double xHigherPos = gravityPoint.getPos().getX() + 20;
		double xLowerPos = gravityPoint.getPos().getX() - 20;
		double yHigherPos = gravityPoint.getPos().getY() + 20;
		double yLowerPos = gravityPoint.getPos().getY() - 20;
		
		if(myPos.getX() > xLowerPos && myPos.getX() < xHigherPos && myPos.getY() > yLowerPos && 
				myPos.getY() < yHigherPos)
		{
			updateGravityPoints();
		}
	}
	
	public GravityPoint getGravityPoint()
	{
		return gravityPoint;
	}
}
