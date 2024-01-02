package com.mycompany.a3;
import java.util.Random;
import java.lang.Math;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Dimension;

public class Spider extends Moveable{
	Random rand = new Random();
	Point location;
	private float deltaX;float deltaY;
	
	public Spider(int size, Point location, int color, double heading, int speed) {
		//    size, location, color, heading, speed
		super(size, location, color, heading, speed );
	}
	
	public void move(Point oldLocation, double heading, int speed, int elapsedTime, Dimension dCmpSize) {
		float currX;float currY;	

		
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

		// set new heading
		super.setHeading(heading);
		
		// round the floating point number to whole number
		location = new Point(Math.round(currX*10)/10, Math.round(currY*10)/10);
		
		
	// update location of the movable object
	super.move(location);
	}

	// empty method
	public void setLocation(Point location) {
		 
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
			
			String sHeading = "heading=" + super.getHeading()+ " ";
			
			String sSpeed = "speed=" + super.getSpeed()+ " ";
			
			return "Spider: " + sLocation + colorDesc + sHeading + sSpeed + sSize;
		}
		
		// empty method
		public void setColor(int color) {
					
		}
		
		public void draw(Graphics g, Point pCmpRelPrnt) {
			g.setColor(this.getColor());
			
			int xLoc = (int)(pCmpRelPrnt.getX()); //+ (int)this.getLocation().getX());// shape location relative
			int yLoc = (int) (pCmpRelPrnt.getY()); //+ (int)this.getLocation().getY());// to parent's origin
			int [] xPoints = {
					(int)this.getLocation().getX() + xLoc, // top left (upside down triangle)
					(int)this.getLocation().getX() - this.getSize()/2 + xLoc,
					(int)this.getLocation().getX() + this.getSize()/2 + xLoc
			};
				
			int [] yPoints = {
					(int)this.getLocation().getY() + this.getSize()/2 + yLoc, // top left (upside down triangle)
					(int)this.getLocation().getY() - this.getSize()/2 + yLoc,
					(int)this.getLocation().getY() - this.getSize()/2 + yLoc
			};
				
			
				g.drawPolygon(xPoints, yPoints, 3);
			
			
		}

		@Override
		boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
			// TODO Auto-generated method stub
			return false;
		}
		
		public void handleCollision(GameObject otherObject) {
			
		}

		@Override
		public boolean collidesWIth(GameObject otherObject) {
			// TODO Auto-generated method stub
			return false;
		}
}

