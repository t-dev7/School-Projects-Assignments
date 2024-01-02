package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.charts.util.ColorUtil;

public class ScoreView extends Container implements Observer{
	private GameWorld gw;
	private Label lifeLabel;
	private Label clockLabel;
	private Label lastFlagReachedLabel;
	private Label foodLevelLabel;
	private Label healthLevelLabel;
	private Label soundLabel;
	
	public ScoreView() {
		this.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		
		// clock label
		Label time = new Label("Time:");
		time.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		time.getAllStyles().setMarginLeft(300);
		clockLabel = new Label("0");
		clockLabel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		clockLabel.getAllStyles().setPadding(RIGHT, 5);
		this.add(time);
		this.add(clockLabel);
		
		// lives label
		Label lives = new Label("Lives Left:");
		lives.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		lifeLabel = new Label("3");
		lifeLabel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		lifeLabel.getAllStyles().setPadding(RIGHT, 5);
		this.add(lives);
		this.add(lifeLabel);
		
		// last flag
		Label lfr = new Label("Last Flag Reached:");
		lfr.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		lastFlagReachedLabel = new Label("1");
		lastFlagReachedLabel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		lastFlagReachedLabel.getAllStyles().setPadding(RIGHT, 5);
		this.add(lfr);
		this.add(lastFlagReachedLabel);

		// food level label
		Label food = new Label("Food Level:");
		food.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		foodLevelLabel = new Label("");
		foodLevelLabel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		foodLevelLabel.getAllStyles().setPadding(RIGHT, 5);
		this.add(food);
		this.add(foodLevelLabel);
		
		// health level label
		Label health = new Label("Health Level:");
		health.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		healthLevelLabel = new Label("10");
		healthLevelLabel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		healthLevelLabel.getAllStyles().setPadding(RIGHT, 5);
		this.add(health);
		this.add(healthLevelLabel);
		
		// sound label
		Label sound = new Label("Sound:");
		sound.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		soundLabel = new Label("OFF");
		soundLabel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		soundLabel.getAllStyles().setPadding(RIGHT, 5);
		this.add(sound);
		this.add(soundLabel);
		
	}
		
	
	public void update (Observable o, Object arg) {
		// code here to update labels from the game/ant state data
		gw = (GameWorld) arg;
		GameObjectCollection gameCollection = gw.getGameObject() ;
		IIterator theElements = gameCollection.getIterator();
		GameObject tempObj;
		
		while(theElements.hasNext()) {
			tempObj = (GameObject) theElements.getNext();
			
			if (tempObj instanceof Ant) {
				healthLevelLabel.setText(" "+((Ant)tempObj).getHealth());
				foodLevelLabel.setText(" "+((Ant)tempObj).getFoodLevel());;
				lastFlagReachedLabel.setText(" "+((Ant)tempObj).getLastFlagReached());;
				revalidate();
			}
			}
		
		clockLabel.setText(" "+gw.getTime());
		lifeLabel.setText(" "+gw.getLives());;
		
		
		if(gw.getSound()) {
				soundLabel.setText("ON");
				System.out.println("Sound ON");
			}
		else 
				soundLabel.setText("OFF");
		revalidate();
		}
}
