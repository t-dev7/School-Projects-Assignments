package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

public abstract class Fixed extends GameObject implements ISelectable{
	private boolean isSelected = false;
	
	public Fixed(int size, Point location, int color) {
		super(size, location, color);
	}
	

	public void setColor(int color) {
	 super.setColor(color);
	}

 	//empty method
	public void setLocation(Point location) {
		 super.setLocation(location);
	 }
	
	public void setSelected(boolean select) {
		this.isSelected = select;
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	public abstract boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt);
		
		
	
 
}