package no.nith.skegar.objects;

import java.awt.geom.Point2D;

/**
 * An enemy class that contains all info we need to know about the enemy
 */
public class Enemy 
{
	private String name;
	private double bearing;
	private double heading;
	private Point2D.Double velocity;
	private double speed;
	private double distance;
	private Point2D.Double position;
	private double energy;
	
	public Enemy()
	{
		name = "";
		bearing = 0;
		heading = 0;
		velocity = new Point2D.Double();
		speed = 0;
		distance = 0;
		position = new Point2D.Double();
		energy = 0;
	}

	public String getName() 
	{
		return name;
	}

	public double getBearing() 
	{
		return bearing;
	}

	public double getHeading() 
	{
		return heading;
	}
	
	public Point2D.Double getVelocity()
	{
		return velocity;
	}

	public double getSpeed() 
	{
		return speed;
	}

	public double getDistance() 
	{
		return distance;
	}

	public Point2D.Double getPosition() 
	{
		return position;
	}
	
	public double getEnergy()
	{
		return energy;
	}
	
	/**
	 * Update all info about the enemy
	 */
	public void setInfo(String name, double bearing, double heading, double speed, double distance, 
			Point2D.Double position, double energy)
	{
		this.name = name;
		this.bearing = bearing;
		this.heading = heading;
		this.speed = speed;
		this.distance = distance;
		this.position.setLocation(position);
		this.energy = energy;
		
		this.velocity.x = speed * Math.sin(Math.toRadians(heading));
		this.velocity.y = speed * Math.cos(Math.toRadians(heading));
	}
}
