package com.mycompany.a3;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;

public class Ant extends Moveable implements ISteerable{
	private int maximumSpeed;
	private int foodLevel;
	private int foodConsumptionRate;
	private int healthLevel;
	private int lastFlagReached; 
	private static Ant theAnt;
	private GameWorld gw;
	private float deltaX;float deltaY;
	
	Point location;
	
	private Ant(Point location, int color, GameWorld gw) {
		
		super(100, location, color, 0, 5);
		healthLevel = 10;
		maximumSpeed = 10;
		foodLevel = 10;
		foodConsumptionRate = 1;
		lastFlagReached = 1;
		this.gw = gw;
	}
	
	public static Ant getAnt(Point location, int color, GameWorld gw) {  
		if (theAnt == null)
			theAnt =  new Ant(location, color, gw);
		return theAnt;
		}
	

	
	// method to update new location
	public void move(Point oldLocation, double heading, int speed, int elapsedTime, Dimension dCmpSize) {
		float currX;float currY;//float newX;float newY;
		
		
		currX = oldLocation.getX();
		currY = oldLocation.getY();
		
		currX += deltaX;
		currY += deltaY;
		// new location = old location + (deltaX, deltaY)
		// 0 = 90-heading
		// distance = rate * time
		// deltaX = cos(0) * distance
		// deltaY = sin(0) * distance
		
		if((currX + this.getSize() >= dCmpSize.getWidth()) || currX < 0) 
			heading = 360-heading;
			
		if((currY + this.getSize() >= dCmpSize.getHeight()) || currY < 0) 
			heading = (360-heading+180)%360;
			
		deltaX = (float) (Math.cos(Math.toRadians(90 - heading)))*(speed * (elapsedTime/1000));
		deltaY = (float) (Math.sin(Math.toRadians(90 - heading)))*(speed * (elapsedTime/1000));
		

				// sets the change in heading to the new heading
				super.setHeading(heading);
				
				location = new Point(Math.round(currX*10)/10, Math.round(currY*10)/10);
		
	super.move(location);
	}
	
	// return health level
	public int getHealth() {
		return healthLevel;
	}
	
	
	// method to set new health
	public void setHealth(int newHealth) {
		healthLevel = newHealth;
	}
	
	// return maximum speed
	public int getMaximumSpeed() {
		return maximumSpeed;
	}
	
	// set new maximum speed
	public void setMaximumSpeed(int newMax) {
		maximumSpeed = newMax;
	}
	
	
	// gets food level
	public int getFoodLevel() {
		return foodLevel;
	}
	
	// sets food level to new value
	public void setFoodLevel(int newFood) {
		foodLevel = newFood;
	}
	
	// returns the last flag reached
	public int getLastFlagReached() {
		return lastFlagReached;
	}
	
	// updates the last flag reached
	public void setLastFlagReached(int newLastFlag) {
		lastFlagReached = newLastFlag;
	}
	
	// gets/returns the food consumption rate
	public int getFoodConsumptionRate() {
		return foodConsumptionRate;
	}
	
	// method to set color from the subclasses
		public void setColor(int color) {
			super.setColor(color);
		}
	
	//
	//public void setFoodConsumptionRate(int newFoodRate) {
	//	foodConsumptionRate = newFoodRate;
	//}
	
	// overloaded method to set a new location
	public void setLocation(Point location) {
		 super.setLocation(location);
	 }
	
	public void changeHeading(double heading) {
		super.setHeading(heading);
	}
	
	// empty method
	public void setHeading(double heading) {
				
	}
	
	public String toString() {
		String colorDesc = "color=[" + ColorUtil.red(super.getColor())  + ", "
				 + ColorUtil.green(super.getColor()) + ", "
				 + ColorUtil.blue(super.getColor()) + "] ";

		String sLocation = "loc=" + super.getLocation().getX() + ", "
			  + super.getLocation().getY() + " ";

		String sSize = "size=" + super.getSize() + " ";
		
		String sHeading = "heading=" + super.getHeading() + " ";
		
		String sSpeed = "speed=" + super.getSpeed()+ " ";
		
		String sMaxSpeed = "maxSpeed=" + maximumSpeed + " ";
		
		String sFoodRate = "foodConsumptionRate=" + foodConsumptionRate + " ";
		
		return "Ant: " + sLocation + colorDesc + sHeading + sSpeed + sSize + sMaxSpeed + sFoodRate;
	}
	
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		
			int xLoc = (int)(pCmpRelPrnt.getX()+ (int)this.getLocation().getX());// shape location relative
			int yLoc = (int) (pCmpRelPrnt.getY()+ (int)this.getLocation().getY());// to parent's origin
			
			
				g.fillArc(xLoc, yLoc, this.getSize(), this.getSize(), 0, 360);
			
				
	}
	
	public void handleCollision(GameObject otherObject) {
		if(otherObject instanceof Spider)
			gw.spiderGotAnt();
		else if(otherObject instanceof FoodStation) {
			if(((FoodStation) otherObject).getCapacity() == 0) {
				// do nothing
			}
			else 
				gw.foodCollide();
			
		}
			
		else if(otherObject instanceof Flag)
			gw.flagReached(((Flag) otherObject).getFlagNumber());
	}

	@Override
	boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean collidesWIth(GameObject otherObject) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	


}