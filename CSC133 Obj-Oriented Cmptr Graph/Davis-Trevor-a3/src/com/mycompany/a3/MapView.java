package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;

public class MapView extends Container implements Observer{
	private TextArea gameText;
	private static int height;
	private static int width;
	private GameWorld gw;
	private GameObjectCollection gameObjects= new GameObjectCollection();
	private Fixed fixedObj;
	
	public MapView() {
		this.getAllStyles().setBorder(Border.createLineBorder(10,ColorUtil.rgb(255, 0, 0)));
		this.setLayout(new BorderLayout());
		gameText = new TextArea();
		gameText.setEditable(false);
		gameText.getAllStyles().setBgTransparency(0);
		this.setHeight(1000);
		this.setWidth(1000);
		
		MapView.height = this.getHeight();
		MapView.width = this.getWidth();
		
		
//		this.add(BorderLayout.NORTH, gameText);
		
	}
	public static void setMapViewWidth(int width) { MapView.width = width; }
	public static void setMapViewHeight(int height) { MapView.height = height; }
	
	public static int getMapHeight() {
		return height;
	}
	
	public static int getMapWidth() {
		return width;
	}
	
	public void update (Observable o, Object arg) {
		gw = (GameWorld) o;
		this.repaint();
	}

	public void pointerPressed(int x, int y) {
		if(gw.getPaused()) {
		x = x - getParent().getAbsoluteX();
		y = y - getParent().getAbsoluteY();
		Point pPtrRelPrnt = new Point(x, y);
		Point pCmpRelPrnt = new Point(getX(), getY());
		Point newLocation = new Point(x-getX(), y- getY());
		
		IIterator theElements = gw.getGameObject().getIterator();
		while(theElements.hasNext()) { // while1
//			ISelectable gameObj = (ISelectable)theElements.getNext();
			if(theElements.getNext() instanceof Fixed) {
				 fixedObj = (Fixed)theElements.getObject();
				 // if the position button is selected and the fixed object is selected
				 if(gw.positionSelected() && fixedObj.isSelected()) {
					 gw.reposition(fixedObj, newLocation); // move to new location
					 gw.setPosition(false); // reset the position to false
				 }
				 else {
					 if (fixedObj.contains(pPtrRelPrnt, pCmpRelPrnt)) {
					fixedObj.setSelected(true);
						//break;
					 } 
					 else {
						 fixedObj.setSelected(false);
						 //break;
					 }
				}
			}
		}
		repaint();
		}
	}
	
	//It is then the duty of paint() to	iterate through the game objects invoking draw() 
	//in each object - thus redrawing all the objects in the world in the container
	public void paint(Graphics g) {
		super.paint(g);
		Point pCmpRelPrnt = new Point(this.getX(), this.getY());
		GameObjectCollection gameCollection = gw.getGameObject() ;
		IIterator theElements = gameCollection.getIterator();
		while(theElements.hasNext()) {
			GameObject tempObject = (GameObject) theElements.getNext();
			if (tempObject instanceof IDrawable) {
				((IDrawable) tempObject).draw(g, pCmpRelPrnt);
			}
		}
	}
	
	
}
