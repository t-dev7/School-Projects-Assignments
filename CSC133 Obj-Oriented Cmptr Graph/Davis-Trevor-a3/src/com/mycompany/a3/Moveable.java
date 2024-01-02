package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;


public abstract class Moveable extends GameObject{
	private double heading;
	private int speed;
	private boolean isSelected;
	
	
public Moveable(int size, Point location, int color, double heading, int speed) {
	super(size, location, color);
	this.speed = speed;
	this.heading = heading;
	
}

	
	public void move(Point location) {
		super.setLocation(location);
	};
	
	// get the speed
	public int getSpeed() {
		return speed;
	}
	
	// method to set a new speed
	public void setSpeed(int newSpeed) {
		speed = newSpeed;
	}
	
	// overloaded method to set a new location
	public void setLocation(Point location) {
		 super.setLocation(location);
	 }
	
	// method to set color from the subclasses
	public void setColor(int color) {
		super.setColor(color);
	 }
	
	// method to get heading
	public double getHeading() {
		return heading;
	}
	
	// method to get heading
		public void setHeading(double heading) {
			this.heading = heading;
		}
	
	
		
		public void setSelected(boolean b) 
		{ 
			isSelected = b; 
			}
		
		public boolean isSelected() 
		{ 
			return isSelected; 
		}
		
		public abstract void draw(Graphics g, Point pCmpRelPrnt);
		abstract boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt);	
	
	

}