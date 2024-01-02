package com.mycompany.a3;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Flag extends Fixed{
	private GameWorld gw;
	private int sequenceNumber;
	
	public Flag(int sNumber, Point location, int color, GameWorld gw) {
	 // super(size, location, color)
		super(100, location, color); 
		
		// sequence is set when there is new object
		sequenceNumber = sNumber; 
		this.gw = gw;
	}

public String toString() {
		String colorDesc = "color=[" + ColorUtil.red(super.getColor())  + ", "
									 + ColorUtil.green(super.getColor()) + ", "
									 + ColorUtil.blue(super.getColor()) + "] ";
		
		String sLocation = "loc=" + super.getLocation().getX() + ", "
								  + super.getLocation().getY() + " ";
		
		String sSize = "size=" + super.getSize() + " ";
		
		String sSeqNum = "seqNum=" + sequenceNumber + " ";
		
		return "Flag: " + sLocation + colorDesc + sSize + sSeqNum;
	}

//empty method
public void setColor(int color) {
	
}

public int getFlagNumber() {
	return sequenceNumber;
}

public void draw(Graphics g, Point pCmpRelPrnt) {
	g.setColor(this.getColor());
	
	int xLoc = (int)(pCmpRelPrnt.getX()); //+ (int)this.getLocation().getX());// shape location relative
	int yLoc = (int) (pCmpRelPrnt.getY()); //+ (int)this.getLocation().getY());// to parent's origin
	int [] xPoints = {
			(int)this.getLocation().getX() + xLoc, //triangle point... middle/bottom point
			(int)this.getLocation().getX() - this.getSize()/2 + xLoc, // left point
			(int)this.getLocation().getX() + this.getSize()/2 + xLoc // right point
	};
		
	int [] yPoints = {
			(int)this.getLocation().getY() + this.getSize() + yLoc, // middle/bottom point
			(int)this.getLocation().getY() - this.getSize()/2 + yLoc, // left point
			(int)this.getLocation().getY() - this.getSize()/2 + yLoc  // right point
	};
		
	// if it is selected, do not fill it with color
	if (this.isSelected() && gw.getPaused()) {
		g.drawPolygon(xPoints, yPoints, 3);
		g.setColor(ColorUtil.rgb(0,0,0));
		g.drawString(Integer.toString(sequenceNumber), xLoc + (int)this.getLocation().getX() , yLoc +  (int)this.getLocation().getY());
	}
	// if it is not selected, draw it normal
	else {
		g.fillPolygon(xPoints, yPoints, 3);
		g.setColor(ColorUtil.rgb(0,0,0));
		g.drawString(Integer.toString(sequenceNumber), xLoc + (int)this.getLocation().getX() , yLoc +  (int)this.getLocation().getY());
	
	}
}
public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
	//point from screen origin by click by user
	int px = (int) pPtrRelPrnt.getX();
	int py = (int) pPtrRelPrnt.getY();
	
	//switch x and y location to screen origin to compare
	int xLoc = (int) (pCmpRelPrnt.getX() + this.getLocation().getX());
	int yLoc = (int) (pCmpRelPrnt.getY() + this.getLocation().getY());
	
	//   clk >= point-size/2           && clk <= point+size/2
	if ( (px >= xLoc-this.getSize()/2) && (px <= xLoc+this.getSize()/2) && 
		//  clk >= point-size/2           && clk <= point+size
			(py >= yLoc-this.getSize()/2) && (py <= yLoc+this.getSize()) ) {
	//	System.out.println("TRUE");
		return true;
	}else {
	//	System.out.println("False");
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