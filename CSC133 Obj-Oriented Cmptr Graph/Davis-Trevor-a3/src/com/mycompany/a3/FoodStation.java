package com.mycompany.a3;

import java.util.Random;


import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.plaf.Style;

public class FoodStation extends Fixed{
	Random rand;
	private int capacity;
	private GameWorld gw;
	
	
	// 
	public FoodStation (int size, Point location, int color, int capacity, GameWorld gw) {
	//  super(size, location, color)
		super(size, location, color);
		this.capacity = capacity;
		this.gw = gw;
		
	}
	
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
public void setColor(int color) {
		
		super.setColor(color);
	}
	
	
public String toString() {
	String colorDesc = "color=[" + ColorUtil.red(super.getColor())  + ", "
								 + ColorUtil.green(super.getColor()) + ", "
								 + ColorUtil.blue(super.getColor()) + "] ";
	
	String sLocation = "loc=" + super.getLocation().getX() + ", "
							  + super.getLocation().getY() + " ";
	
	String sSize = "size=" + super.getSize() + " ";
	
	String sSeqNum = "seqNum=" + capacity + " ";
	
	return "FoodStation: " + sLocation + colorDesc + sSize + sSeqNum;
}

public void draw(Graphics g, Point pCmpRelPrnt) {
	g.setColor(this.getColor());
	
		int xLoc = (int)(pCmpRelPrnt.getX()+ (int)this.getLocation().getX());// shape location relative
		int yLoc = (int) (pCmpRelPrnt.getY()+ (int)this.getLocation().getY());// to parent's origin
		
		if (this.isSelected() && gw.getPaused()) {
			g.drawRect(xLoc, yLoc, this.getSize(), this.getSize());
			g.setColor(ColorUtil.rgb(0,0,0));
			g.drawString(Integer.toString(capacity), xLoc + this.getSize()/2, yLoc + this.getSize()/2);
		}
		else {
			g.fillRect(xLoc, yLoc, this.getSize(), this.getSize());
			g.setColor(ColorUtil.rgb(0,0,0));
			g.drawString(Integer.toString(capacity), xLoc + this.getSize()/2, yLoc + this.getSize()/2);
		}
		
		
	
}
public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
	//point from screen origin by click by user
	int px = (int) pPtrRelPrnt.getX();
	int py = (int) pPtrRelPrnt.getY();
	
	//switch x and y location to screen origin to compare
	int xLoc = (int) (pCmpRelPrnt.getX() + this.getLocation().getX());
	int yLoc = (int) (pCmpRelPrnt.getY() + this.getLocation().getY());
	
	if ( (px >= xLoc) && (px <= xLoc+this.getSize()) && (py >= yLoc) && (py <= yLoc+this.getSize()) ) {

		return true;
	}else {

		return false;
	}
}

@Override
public boolean collidesWIth(GameObject otherObject) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void handleCollision(GameObject otherObject) {
	// TODO Auto-generated method stub
	
}

}
