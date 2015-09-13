package no.nith.skegar.engines;

import java.awt.geom.Point2D;
import no.nith.skegar.math.BasicMath;

/**
 * Class that defines the common functionality of AntiGravity movement
 */
public abstract class AGMEngine 
{
	private double xTotalForce;
	private double yTotalForce;
	private double turnAngle;
	private Point2D.Double myPos;
	
	public AGMEngine(Point2D.Double myPos)
	{
		xTotalForce = 0;
		yTotalForce = 0;
		turnAngle = 0;
		this.myPos = myPos;
	}
	
	/**
	 * Abstract methods that needs to be implemented by all AGM Engines
	 */
	public abstract void initGravityPoints();
	public abstract void updateGravityPoints();
	public abstract void updateTotalForce();
	
	/**
	 * Returns the distance to move multiplied by the direction
	 */
	public double updateMove(double x, double y, double heading, Point2D.Double myPos) 
	{
		double dist = 20; 
	    double angle = Math.toDegrees(BasicMath.absoluteBearing(myPos.getX(),myPos.getY(), x, y));
	    double dir = turnTo(angle, heading);
	    return dist * dir;
	}
	
	/**
	 * Returns the direction to move
	 */
	public int turnTo(double angle, double heading) 
	{
		turnAngle = 0;
    	int dir;
	    turnAngle = BasicMath.normalizeBearing(heading - angle);
	    if (turnAngle > 90) 
	    {
	    	turnAngle -= 180;
	        dir = -1;
	    } else if (turnAngle < -90) 
	    {
	    	turnAngle += 180;
	        dir = -1;
	    } else 
	    {
	        dir = 1;
	    }
	    
	    return dir;
	}
	
	/**
	 * Get and set methods
	 */
	public double getXTotalForce()
	{
		return xTotalForce;
	}
	
	public double getYTotalForce()
	{
		return yTotalForce;
	}
	
	public double getTurnAngle()
	{
		return turnAngle;
	}
	
	public Point2D.Double getMyPos()
	{
		return myPos;
	}
	
	public void setXTotalForce(double xTotalForce)
	{
		this.xTotalForce = xTotalForce;
	}
	
	public void setYTotalForce(double yTotalForce)
	{
		this.yTotalForce = yTotalForce;
	}
	
	public void setTurnAngle(double turnAngle)
	{
		this.turnAngle = turnAngle;
	}
	
	public void setMyPos(Point2D.Double myPos)
	{
		this.myPos = myPos;
	}
}
