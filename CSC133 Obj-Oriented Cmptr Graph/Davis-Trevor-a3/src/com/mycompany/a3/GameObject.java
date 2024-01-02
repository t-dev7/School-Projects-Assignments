package com.mycompany.a3;
import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

import java.util.Random;
import java.util.Vector;

public abstract class GameObject implements IDrawable, ICollider{
	private int size;
	private Point location;
	private int color;
	Random random;
	private Vector<GameObject> collisionVector;
	
	// default size of game objects ( might be changed later) 
	public GameObject(int size) {
		this.size = size;
		location = new Point(random.nextInt(40),random.nextInt(40));
		
		
	}

	// constructor for the size of the object
	public GameObject(int size, Point location, int color) {
		this.size = size;
		this.location = location;
		this.color = color;	
		
		this.collisionVector = new Vector<GameObject>();
	}
	
	// method to get color (all objects able to get color)
	public int getColor() {
		return color;
	}
	
	// method to get Point (all objects able to get Location)
	public Point getLocation() {
		return location;
	}
	
	
	// method to set location from the subclasses
	 public void setLocation(Point location) {
		 this.location = location;
	 }
	 
	 public int getSize() {
			return size;
		}

	// method to set color from the subclasses
	public void setColor(int color) {
		this.color = color;
	}
	
	public boolean collidesWith(GameObject otherObject) {
		boolean result = false;
		
		// use bounding circles to determine whether this object has collided with another
		float thisCenterX= this.getLocation().getX() + otherObject.getSize()/2; // find centers
		float thisCenterY= this.getLocation().getY() + otherObject.getSize()/2;
		float otherCenterX = otherObject.getLocation().getX();
		float otherCenterY = otherObject.getLocation().getY();
		
		// find distance between centers
		float dx = thisCenterX - otherCenterX;
		float dy = thisCenterY - otherCenterY;
		int distBetweenCenterSqr = (int) (dx*dx + dy*dy);
		
		// find square of sum of radii
		int thisRadius = this.getSize()/2;
		int otherRadius = otherObject.getSize()/2;
		int radiiSqr = (thisRadius*thisRadius + 2*thisRadius*otherRadius + otherRadius*otherRadius);
		
		if(distBetweenCenterSqr <= radiiSqr) {
			result = true;
		}
		return result;
		
	}
	public void addCollidedObject(GameObject collide) {
		collisionVector.add(collide);
	}
	
	public void deleteCollided(GameObject collide) {
		collisionVector.remove(collide);
	}
	
	public boolean contains(GameObject object) {
		if(collisionVector.isEmpty())
			return false;
		return collisionVector.contains(object);
	}
	
	public abstract void draw(Graphics g, Point pCmpRelPrnt);
	
}