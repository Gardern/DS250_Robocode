// Authors: Gorm and Gard Skevik

package no.nith.skegar.fsm;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Random;
import no.nith.skegar.math.RightTriangle;
import no.nith.skegar.objects.*;
import no.nith.skegar.engines.*;
import robocode.*;
import robocode.util.*;

/**
 * Dominator - a robot by (Gorm and Gard Skevik)
 */
public class Dominator extends AdvancedRobot
{
	private static Random random = new Random();
	
	private AttackStateMachine attackStateMachine;
	private DriverStateMachine driverStateMachine;
	private ScannerStateMachine scannerStateMachine;
	
	private double radarTurn;
	private boolean enemyDetected;
	private boolean canRam;
	private boolean aimDone;
	private Point2D.Double myPos;
	private Enemy enemy;
	private double enemyEnergy;
	private boolean enemyShooting;
	private int counter;
	private boolean collidedWithEnemy;
	private boolean enemyHit;
	private boolean enemyCollidedWithWall;
	private double rightWall;
	private double leftWall;
	private double upperWall;
	private double bottomWall;
	private Timer approachTimer;
	
	private PrimaryAGMEngine primaryMovementEngine;
	private SecondaryAGMEngine secondaryMovementEngine;
	private DodgeEngine dodgeEngine;
	private TurretEngine turretEngine;
	private ApproachEngine approachEngine;
	
	private void init()
	{
		attackStateMachine = new AttackStateMachine(this);
		driverStateMachine = new DriverStateMachine(this);
		scannerStateMachine = new ScannerStateMachine(this);

		enemyDetected = false;
		radarTurn = 0;
		canRam = false;
		aimDone = false;
		myPos = new Point2D.Double();
		enemy = new Enemy();
		enemyEnergy = 100;
		enemyShooting = false;
		counter = 0;
		collidedWithEnemy = false;
		enemyHit = false;
		enemyCollidedWithWall = false;
		approachTimer = new Timer();
		
		primaryMovementEngine = new PrimaryAGMEngine(getBattleFieldWidth(), 
				getBattleFieldHeight(), enemy.getPosition(), myPos);
		secondaryMovementEngine = new SecondaryAGMEngine(myPos);
		dodgeEngine = new DodgeEngine();
		turretEngine = new TurretEngine();
		approachEngine = new ApproachEngine();
		
		primaryMovementEngine.initGravityPoints();
		secondaryMovementEngine.initGravityPoints();
		
		rightWall = getBattleFieldWidth() - getWidth() / 2;
		leftWall = 0 + getWidth() / 2;
		upperWall = getBattleFieldHeight() - getHeight() / 2;
		bottomWall = 0 + getHeight() / 2;
		
		setColors(Color.GREEN, Color.BLUE, Color.RED);
		setBulletColor(Color.ORANGE);
		setScanColor(Color.WHITE);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
	}
	
	/**
	 * Checks if enemy collided with a wall
	 */
	private void checkEnemyCollisionWithWall()
	{
		double posX = enemy.getPosition().getX();
		double posY = enemy.getPosition().getY();
		
		if(posX >= rightWall || posX <= leftWall || posY >= upperWall || posY <= bottomWall) 
		{
			enemyCollidedWithWall = true;
		}
	}
	
	/**
	 * Checks if enemy is shooting 
	 */
	private void checkIfEnemyIsShooting(double energy)
	{
		if(!(collidedWithEnemy || enemyCollidedWithWall || enemyHit))
		{
			double bulletPower = enemyEnergy - energy;
			
			if(energy == (enemyEnergy - bulletPower) && bulletPower != 0)
			{
				enemyShooting = true;
			}
		}
	}
	
	/**
	 * fills the tank with randomly generated colors each tick
	 */
	private void updateDominator()
	{
		Color color1 = new Color(random.nextInt(256), random.nextInt(256), 
				random.nextInt(256));
		Color color2 = new Color(random.nextInt(256), random.nextInt(256), 
				random.nextInt(256));
		Color color3 = new Color(random.nextInt(256), random.nextInt(256), 
				random.nextInt(256));
		Color color4 = new Color(random.nextInt(256), random.nextInt(256), 
				random.nextInt(256));
		Color color5 = new Color(random.nextInt(256), random.nextInt(256), 
				random.nextInt(256));
		
		setColors(color1, color2, color3);
		setBulletColor(color4);
		setScanColor(color5);
	}
	
	/**
	 * run: Dominator's default behavior
	 */
	@Override
	public void run()
	{
		init();
		
		//The Game loop
		while(true)
		{
			// Comment to get default appearance
			updateDominator();
			
			turretEngine.processEngine();
			
			approachTimer.setCurrentTick(getTime());
			
			if(approachTimer.getDeltaTick() == 90)
			{
				turretEngine.setApproach(approachEngine.checkApproach(enemy.getSpeed()));
				approachTimer.setLastTick(getTime());
			}
			
			scannerStateMachine.onUpdate();
			driverStateMachine.onUpdate();
			attackStateMachine.onUpdate();
			
			scannerStateMachine.onExecute();
			driverStateMachine.onExecute();
			attackStateMachine.onExecute();
			
			execute();
		}
	}
	
	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	@Override
	public void onScannedRobot(ScannedRobotEvent e)
	{
		super.onScannedRobot(e);
		
		myPos.setLocation(getX(), getY());
		
		RightTriangle.calculateCathetis(e.getDistance(), e.getBearing() + getHeading(),
				myPos);
		
		enemy.setInfo("", e.getBearing(), e.getHeading(), e.getVelocity(), e.getDistance(),
				RightTriangle.getCatheti(), e.getEnergy());
		
		checkEnemyCollisionWithWall();
		checkIfEnemyIsShooting(e.getEnergy());
		enemyEnergy = e.getEnergy();
		
		enemyHit = false;
		collidedWithEnemy = false;
		enemyCollidedWithWall = false;
		enemyDetected = true;
		canRam = true;
		
		primaryMovementEngine.updateGravityPoints();
		secondaryMovementEngine.checkIfPositionIsReached(myPos);
		approachEngine.updatePositions(enemy.getPosition());
		
		radarTurn = Utils.normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
	}
	
	/**
	 * onBulletHit: What to do when you hit the enemy
	 */
	@Override
	public void onBulletHit(BulletHitEvent e) 
	{
		super.onBulletHit(e);
		enemyHit = true;
	}
	
	/**
	 * onHitRobot: What to do when you rammed the enemy
	 */
	@Override
	public void onHitRobot(HitRobotEvent e) 
	{
		super.onHitRobot(e);
		collidedWithEnemy = true;
	}
	
	/**
	 * Get and Set methods
	 */	
	public AttackStateMachine getAttackStateMachine()
	{
		return attackStateMachine;
	}
	
	public boolean isEnemyDetected() 
	{
		return enemyDetected;
	}
	
	public void setEnemyDetected(boolean enemyDetected)
	{
		this.enemyDetected = enemyDetected;
	}
	
	public double getRadarTurn()
	{
		return radarTurn;
	}
	
	public boolean getCanRam()
	{
		return canRam;
	}
	
	public void setCanRam(boolean canRam)
	{
		this.canRam = canRam;
	}
	
	public boolean getAimDone()
	{
		return aimDone;
	}
	
	public void setAimDone(boolean aimDone)
	{
		this.aimDone = aimDone;
	}
	
	public Point2D.Double getMyPos()
	{
		return myPos;
	}
	
	public Enemy getEnemy()
	{
		return enemy;
	}
	
	public boolean isEnemyShooting()
	{
		return enemyShooting;
	}
	
	public void setEnemyShooting(boolean enemyShooting)
	{
		this.enemyShooting = enemyShooting;
	}
	
	public int getCounter()
	{
		return counter;
	}
	
	public void setCounter(int counter)
	{
		this.counter = counter; 
	}
	
	public PrimaryAGMEngine getPriMovEng()
	{
		return primaryMovementEngine;
	}
	
	public SecondaryAGMEngine getSecMovEng()
	{
		return secondaryMovementEngine;
	}
	
	public DodgeEngine getDodgeEng()
	{
		return dodgeEngine;
	}
	
	public TurretEngine getTurretEng()
	{
		return turretEngine;
	}
	
	public ApproachEngine getApproachEngine()
	{
		return approachEngine;
	}
}