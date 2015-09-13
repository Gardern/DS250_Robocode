package no.nith.skegar.engines;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import no.nith.skegar.math.BasicMath;
import no.nith.skegar.objects.GravityPoint;

/**
 * Class that is used as the primary AGM Engine
 * Contains gravity points on walls, center and the enemy
 */
public class PrimaryAGMEngine extends AGMEngine
{
	private double battleFieldWidth;
	private double battleFieldHeight;
	private Point2D.Double enemyPos;
	private boolean ramming;
	private boolean approaching;
	private double midpointPower;
	private int midpointCounter;
	private ArrayList<GravityPoint> gravityPoints;
	
	public PrimaryAGMEngine(double battleFieldWidth, double battleFieldHeight, 
			Point2D.Double enemyPos, Point2D.Double myPos)
	{
		super(myPos);
		gravityPoints = new ArrayList<GravityPoint>();
		this.battleFieldWidth = battleFieldWidth;
		this.battleFieldHeight = battleFieldHeight;
		this.enemyPos = enemyPos;
		ramming = false;
		approaching = false;
		midpointPower = 0;
		midpointCounter = 0;
	}
	
	/**
	 * Initializes the gravity points
	 */
	public void initGravityPoints()
	{
		gravityPoints.add(new GravityPoint(new Point2D.Double(0, 0), -1000));
		gravityPoints.add(new GravityPoint
				(new Point2D.Double(battleFieldWidth / 2, battleFieldHeight / 2), 0));
		
		gravityPoints.add(new GravityPoint(new Point2D.Double(battleFieldWidth, 0), 5000));
		gravityPoints.add(new GravityPoint(new Point2D.Double(0, 0), 5000));
		gravityPoints.add(new GravityPoint(new Point2D.Double(0, battleFieldHeight), 5000));
		gravityPoints.add(new GravityPoint(new Point2D.Double(0, 0), 5000));
	}
	
	/**
	 * Updates the gravity points
	 */
	public void updateGravityPoints()
	{
		gravityPoints.get(0).updatePosition(enemyPos);
		if(!(ramming || approaching))
		{
			gravityPoints.get(1).updatePower(midpointPower);
		}
		else 
		{
			gravityPoints.get(1).updatePower(0);
		}
		
		gravityPoints.get(2).updatePosition(new Point2D.Double(battleFieldWidth, getMyPos().getY()));
		gravityPoints.get(3).updatePosition(new Point2D.Double(0, getMyPos().getY()));
		gravityPoints.get(4).updatePosition(new Point2D.Double(getMyPos().getX(), battleFieldHeight));
		gravityPoints.get(5).updatePosition(new Point2D.Double(getMyPos().getX(), 0));
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
		boolean walls = false;
		
		for(int i = 0; i < gravityPoints.size(); i++)
		{
			if(i == 0)
			{
				singleForce = gravityPoints.get(i).getPower() / Math.pow(getMyPos().distance(gravityPoints.get(i).getPos()), 2);
			} 
			else if(i == 1)
			{
				singleForce = gravityPoints.get(i).getPower() / Math.pow(getMyPos().distance(gravityPoints.get(i).getPos()), 1.5);
			} 
			else if(i >= 2 && i <= 5)
			{
				if(i == 2)
				{
					setXTotalForce(getXTotalForce() + gravityPoints.get(i).getPower() / 
							Math.pow(getMyPos().distance(gravityPoints.get(i).getPos()), 3));
				} else if(i == 3)
				{
					setXTotalForce(getXTotalForce() - gravityPoints.get(i).getPower() / 
							Math.pow(getMyPos().distance(gravityPoints.get(i).getPos()), 3));
				
				} else if(i == 4)
				{
					setYTotalForce(getYTotalForce() + gravityPoints.get(i).getPower() / 
							Math.pow(getMyPos().distance(gravityPoints.get(i).getPos()), 3));
				
				} else if(i == 5)
				{
					setYTotalForce(getYTotalForce() - gravityPoints.get(i).getPower() /
							Math.pow(getMyPos().distance(gravityPoints.get(i).getPos()), 3));
				}
				walls = true;
			}
			
			if(!(walls))
			{
				angle = BasicMath.normalizeBearing(Math.PI/2 - Math.atan2
						(getMyPos().getY() - gravityPoints.get(i).getPos().getY(), 
								getMyPos().getX() - gravityPoints.get(i).getPos().getX())); 
				
				setXTotalForce(getXTotalForce() + Math.sin(angle) * singleForce);
				setYTotalForce(getYTotalForce() + Math.cos(angle) * singleForce);
			}
		}
		
		midpointCounter++;
		if(midpointCounter > 5) 
		{
			midpointCounter = 0;
			midpointPower = (Math.random() * 2000) - 1000;
		}
	}
	
	public ArrayList<GravityPoint> getGravityPoints()
	{
		return gravityPoints;
	}
	
	public void setRamming(boolean ramming)
	{
		this.ramming = ramming;
	}
	
	public void setApproaching(boolean approaching)
	{
		this.approaching = approaching;
	}
}
