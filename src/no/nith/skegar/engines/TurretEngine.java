package no.nith.skegar.engines;

import java.awt.geom.Point2D;
import no.nith.skegar.enums.EShootTechnique;
import no.nith.skegar.objects.Enemy;
import robocode.util.Utils;

public class TurretEngine 
{
	private EShootTechnique shootTechnique;
	private double bulletPower;
	private double heat;
	private boolean approach;
	
	public TurretEngine()
	{
		shootTechnique = EShootTechnique.NO_TECHNIQUE;
		bulletPower = 0;
		heat = 0;
		approach = false;
	}
	
	/**
	 * Process the engine each tick
	 */
	public void processEngine()
	{
		heat -= 0.1f;
	}
	
	/**
	 * Algorithm that finds the aiming angle
	 */
	public double aimGun(double heading, double gunHeading, 
			double energy, Point2D.Double myPos, double battleFieldHeight, 
			double battleFieldWidth, Enemy enemy)
	{
		
		chooseShootTechnique(enemy.getDistance());
		
		calculatePower(enemy.getDistance());
		
		double turnAngle = 0;
		
		if(shootTechnique == EShootTechnique.HEAD_ON)
		{
			double absoluteBearing = heading + enemy.getBearing();
			
			turnAngle = Utils.normalRelativeAngleDegrees(
					absoluteBearing - gunHeading);
		} 
		
		else if(shootTechnique == EShootTechnique.LINEAR)
		{
			double bulletPower = Math.min(3.0, energy);
			double enemyHeading = enemy.getHeading();
			double enemyVelocity = enemy.getSpeed();
			double deltaTime = 0;
			double predictedX = enemy.getPosition().getX();
			double predictedY = enemy.getPosition().getY();
			
			while((++deltaTime) * (20.0 - 3.0 * bulletPower) < Point2D.Double.distance
					(myPos.getX(), myPos.getY(), predictedX, predictedY))
			{	
				predictedX += Math.sin(Math.toRadians(enemyHeading)) * enemyVelocity;	
				predictedY += Math.cos(Math.toRadians(enemyHeading)) * enemyVelocity;
				
				if(predictedX < 18.0 || predictedY < 18.0 || 
						predictedX > battleFieldWidth - 18.0 || 
						predictedY > battleFieldHeight - 18.0) 
				{
					predictedX = Math.min(Math.max(18.0, predictedX), 
			                    battleFieldWidth - 18.0);	
					predictedY = Math.min(Math.max(18.0, predictedY), 
			                    battleFieldHeight - 18.0);
					break;
				}
			}

			double theta = Utils.normalAbsoluteAngleDegrees(Math.toDegrees(Math.atan2(
				    predictedX - myPos.getX(), predictedY - myPos.getY())));
			
			turnAngle = Utils.normalRelativeAngleDegrees(theta - gunHeading);
		}
		
		return turnAngle;
	}
	
	/**
	 * Selects shooting technique based on our distance to the enemy
	 */
	private void chooseShootTechnique(double distance)
	{
		if(distance <= 200)
		{
			shootTechnique = EShootTechnique.HEAD_ON;
		} 
		else if(distance > 200 && distance <= 700)
		{
			shootTechnique = EShootTechnique.LINEAR;
		}
		else
		{
			shootTechnique = EShootTechnique.NO_TECHNIQUE;
		}
	}
	
	/**
	 * Calculates the shooting power based on targeting strategy
	 */
	private void calculatePower(double distance)
	{
		if(shootTechnique == EShootTechnique.HEAD_ON || approach)
		{
			bulletPower = 3;
		} 
		else if(shootTechnique == EShootTechnique.LINEAR)
		{
			bulletPower = 500 / distance;
			
			if(bulletPower > 3)
			{
				bulletPower = 3;
			}
		}
	}
	
	public double getBulletPower()
	{
		return bulletPower;
	}
	
	public double getHeat()
	{
		return heat;
	}
	
	public void setGunHeat(double heat)
	{
		this.heat = heat;
	}
	
	public boolean getApproach()
	{
		return approach;
	}
	
	public void setApproach(boolean approach)
	{
		this.approach = approach;
	}
}