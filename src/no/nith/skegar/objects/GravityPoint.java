package no.nith.skegar.objects;

import java.awt.geom.Point2D;

/**
 * This class is used to save the different gravity points
 */
public class GravityPoint 
{
	private Point2D.Double position;
	private double power;
	
	public GravityPoint(Point2D.Double position, double power)
	{
		this.position = position;
		this.power = power;
	}

	public Point2D.Double getPos() 
	{
		return position;
	}

	public double getPower() 
	{
		return power;
	}
	
	/**
	 * Sets a new position
	 */
	public void updatePosition(Point2D.Double position) 
	{
		this.position = position;
	}
	
	/**
	 * Sets a new power
	 */
	public void updatePower(double power)
	{
		this.power = power;
	}
}
