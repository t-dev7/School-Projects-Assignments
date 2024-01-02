package com.mycompany.a3;
import java.util.Observable;
import java.util.Random;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.geom.Dimension;

import java.util.Vector;
import com.codename1.charts.models.Point;

public class GameWorld extends Observable{
	private GameObjectCollection gameCollection;
	private boolean xTrue = false;
	private int clock = 0;
	private int lives = 3;
	Ant ant;
	Random rand = new Random();
	Point location = new Point();
	FoodStation fs;
	Spider spider;
	Flag flag;
	private boolean soundOn;
	private int width;
	private int height;
	private int originX;
	private int originY;
	private Sound spiderCollide;
	private Sound foodCollide;
	private Sound flagCollide;
	private BGSound bgSound;
	private boolean paused;
	private boolean bPosition = false;

	
	
	
	// responsible for creating the initial state of the world.
	public void init() {
		
		gameCollection = new GameObjectCollection();
		
		// Ant
		//                 Ant(            location,           color,              gw object)
		gameCollection.add(Ant.getAnt(new Point(1500, 800), ColorUtil.rgb(0,0,255), this));
	
		
		//Food Stations                               50 + (1-50)				
		//                 fs = new FoodStation(       size,           randomLocation(),       color,             capacity); 
		gameCollection.add(fs = new FoodStation(100 + rand.nextInt(50), new Point(1550, 1000) ,ColorUtil.rgb(0,255,0), 1 + rand.nextInt(2), this));
		gameCollection.add(fs = new FoodStation(100 + rand.nextInt(50), randomLocation(),ColorUtil.rgb(0,255,0), 1 + rand.nextInt(2), this));
	
		//Flags
		// add new instatiations to vector (sequence, x, y)
		gameCollection.add(flag = new Flag(1, new Point(200, 400), ColorUtil.rgb(255,0,0), this));
		gameCollection.add(flag = new Flag(2, new Point(200, 800), ColorUtil.rgb(255,0,0), this));
		gameCollection.add(flag = new Flag(3, new Point(700, 800), ColorUtil.rgb(255,0,0), this));
		gameCollection.add(flag = new Flag(4, new Point(100, 800), ColorUtil.rgb(255,0,0), this));
		
		// Spider
		//				 Spider(                       size,           random location,      color,             heading            speed
		gameCollection.add(spider = new Spider(100 + rand.nextInt(50), randomLocation(), ColorUtil.BLACK, rand.nextInt(359),1+rand.nextInt(2)));
		gameCollection.add(spider = new Spider(100 + rand.nextInt(50), randomLocation(), ColorUtil.BLACK, rand.nextInt(359),1+rand.nextInt(2)));

		this.setChanged();
		this.notifyObservers(this);
	}

	// accelerate the ant by a small amount
	public void accelerate() {
		IIterator theElements = gameCollection.getIterator();
		GameObject tempObj;
		
		while(theElements.hasNext()) {
			tempObj = (GameObject) theElements.getNext();
			
			if (tempObj instanceof Ant) {
//				Ant antObj = (Ant)tempObj; 
				if(((Ant)tempObj).getSpeed() == ((Ant)tempObj).getMaximumSpeed()) {
					System.out.println("Cannot accelerate ant beyond maximum speed\n");
				}// if
				
				else if (((Ant)tempObj).getSpeed() != ((Ant)tempObj).getMaximumSpeed()) {
					int s = ((Ant)tempObj).getSpeed();
					((Ant)tempObj).setSpeed(s + 1);
			        System.out.println("Acceleration was applied.\n");
					
				}// else if
			}//if	
		}// for loop	
		this.setChanged();
		this.notifyObservers(this);
	}// accelerate

	// brake the ant by a small amount
	public void brake() {
		IIterator theElements = gameCollection.getIterator();
		
		while(theElements.hasNext()) {
			if (theElements.getNext() instanceof Ant) { 
				Ant antObj = (Ant)theElements.getObject();
				if(antObj.getSpeed() == 0) // if the ant speed is 0
					System.out.println("Cannot brake ant beyond the minimum speed\n");
				else {
					antObj.setSpeed(antObj.getSpeed() - 1);
					}// else
				}// 1st if
		}// for loop
		this.setChanged();
		this.notifyObservers(this);
	}// brake

	//  change the heading of the ant by 5 degrees to the left
	public void left() {
		IIterator theElements = gameCollection.getIterator();
		
		while(theElements.hasNext()) {
			if (theElements.getNext() instanceof Ant) { 
				Ant antObj = (Ant)theElements.getObject();
				antObj.changeHeading(antObj.getHeading()-5);
			}// if statement
		}
		this.setChanged();
		this.notifyObservers(this);

	}

	// change the heading of the ant by 5 degrees to the right
	public void right() {
		IIterator theElements = gameCollection.getIterator();
		
		while(theElements.hasNext()) {
			if (theElements.getNext() instanceof Ant) { 
				Ant antObj = (Ant)theElements.getObject();
				antObj.changeHeading(antObj.getHeading()+5);
			}// if statement
		}
		
		this.setChanged();
		this.notifyObservers(this);
		System.out.println("Right turn was applied.\n");
	}

	// PRETEND that the ant has collided with a food station
	public void foodCollide() {
		
		 boolean bFinished = false; // looping flag
		 int tempCapacity = 0; // capacity holder
		boolean foundFood = false; // food found flag
		
		IIterator theElements = gameCollection.getIterator();

		// go through loop until ant has increased food level from food station
		// this is for if foodstation is either before OR after ant in collection
		do { 
			while(theElements.hasNext()) {
				 if(foundFood == true) { // if the food was found
					 theElements = gameCollection.getIterator();  // start from the beginning of collection
					 if (theElements.getNext() instanceof Ant) {
						Ant antObj = (Ant)theElements.getObject();
						antObj.setFoodLevel(antObj.getFoodLevel() +tempCapacity); // increase the food level 
						bFinished = true;
						break; // dont loop through whole thing again. exit do/while
					 }// if
				 }// if
				 
				 if(foundFood == false) { // if food element collided with has been found
					 if (theElements.getNext() instanceof FoodStation) { 
						FoodStation food = (FoodStation)theElements.getObject();;
							if (food.getCapacity() > 0) {; // if the food hasnt been colllided with
								foundFood = true; // set flag to true
									food.setColor(ColorUtil.rgb(0,150,0)); // dim the color
									tempCapacity = food.getCapacity(); 
									food.setCapacity(0); // set object capacity to 0
							}
					 			}//if 
						}// if statement
			 	}//for loop
			 }while(bFinished == false);
		
	
		// add a new food station into the program
		gameCollection.add(fs = new FoodStation(75 + rand.nextInt(50), randomLocation(),ColorUtil.rgb(0,255,00), 1 + rand.nextInt(2), this));
		
		this.setChanged();
		this.notifyObservers(this);
		System.out.println("Ant has collided with food.\n");
	}//foodCollide

	// PRETEND that a spider has gotten to the same location and collided with the ant
	public void spiderGotAnt() { 
		// finds the instance of the ant object in the vector list
		IIterator theElements = gameCollection.getIterator();
		while(theElements.hasNext()) {
			if (theElements.getNext() instanceof Ant) {
				Ant antObj = (Ant)theElements.getObject();
				// if the ant has more than 1 health left
				if(antObj.getHealth() > 1) {
				// maximum speed should decrease at the percentage the health declines
				//     New max speed =        speed            *         (health   / health - 1)	
				antObj.setMaximumSpeed(antObj.getMaximumSpeed()*(antObj.getHealth()/(antObj.getHealth() - 1)));
				// if the speed is  now greater than the max
				if(antObj.getSpeed() > antObj.getMaximumSpeed())
					antObj.setSpeed(antObj.getMaximumSpeed()); // make the speed equal to the max
				
				antObj.setHealth(antObj.getHealth() - 1); // decreases the current health by 1
				antObj.setColor(antObj.getColor()-50); // lightens the shade of the ant
				}//if
				
				// if the ant was on its last health
				else {
					// if the ant has more than one life left
					if (lives > 1) {	
						lives = lives - 1; // lose a life
						// re-initialize the ant
						antObj.setLocation(new Point(200, 400));
						antObj.setHealth(10);
						antObj.setFoodLevel(10);
						antObj.setLastFlagReached(1);
						antObj.setColor(ColorUtil.blue(255));
						
						System.out.println("You ran out of health. Your health went down by one.\n");
						//gameCollection = new GameObjectCollection();  // clears all object in the vector list
						init(); // re-initializes the game
					}// if
					
					// if you are on your last life
					else {	
						System.out.println("You ran out of lives. The Game is over.\n");
						System.exit(0);// exit the system
					}// else
				}//else
			}// 1s if statement
		}// while	
		this.setChanged();
		this.notifyObservers(this);
	}// spiderGotAnt     


	public void flagReached(int flagNumber) {
		// if the new flag is one greater than the last flag that was reached
		// ex) if(2 == 3-1) ant went to next flag......... if(2 == 4-1), the ant skipped flag 3
		
		
		IIterator theElements = gameCollection.getIterator();
		
		while(theElements.hasNext()) {
			if (theElements.getNext() instanceof Ant) { 
				Ant antObj = (Ant)theElements.getObject(); 
				// if the flag entered is one more than the current
				if(antObj.getLastFlagReached() == flagNumber - 1)
				{
					if(antObj.getLastFlagReached() == 9) {
						System.out.println("Game over, you win! Total time: #" + clock);
						System.exit(0);
					}
					else {
						antObj.setLastFlagReached(flagNumber);
						System.out.println("You are now at flag #" + flagNumber + "\n");
					}
					
				}
				
				// if the flag entered is the one they are currently at
				else if(antObj.getLastFlagReached() == flagNumber)
					System.out.println("You are currently at this flag #" +flagNumber + "\n");
				
				// if the flag number is less than the last flag reached
				else if(antObj.getLastFlagReached() > flagNumber)
					System.out.println("Keep going forward! Your flag will not be updated backwards\n");
				
				// if the ant tries to skip flags
				else if(antObj.getLastFlagReached() < flagNumber - 1)
					System.out.println("You cannot skip flags, please proceed in order.\n");
					
			}// if statement
		}// for loop
		this.setChanged();
		
		this.notifyObservers(this);
	}// flag reached
		
		
		
		
	
	
	// Spiders update their heading
	// all moveable objects are told to update their positions according to their current heading and speed
	// the ants food level is reduced by the amount indicated by its foodConsumptionRate
	// the elapsed time game clock is incremented by one
	public void ticked() {
		int r;
		
		
		
		IIterator theElements = gameCollection.getIterator();
		
		while(theElements.hasNext()) {
			if (theElements.getNext() instanceof Spider) {
				Spider sObj = (Spider)theElements.getObject();
				r = 1 + rand.nextInt(10);
				if(r % 2 == 0) { // decide randomly for plus or minus
					// minus 5 from heading
					sObj.move(sObj.getLocation(), sObj.getHeading() - 5, sObj.getSpeed(), 10000, new Dimension(height,width));
					}
				else {
					// add 5 to heading
					sObj.move(sObj.getLocation(), sObj.getHeading()+5, sObj.getSpeed(), 10000, new Dimension(height,width));
				}
			}
		}
		
		theElements = gameCollection.getIterator(); // re-initialize theElements
		
		while(theElements.hasNext()) {
			if (theElements.getNext() instanceof Ant) {
				Ant antObj = (Ant)theElements.getObject();
				if(clock % 2 == 0) // every other clock tick
					antObj.setFoodLevel(antObj.getFoodLevel()-antObj.getFoodConsumptionRate()); // current foodlevel - consumption
				// if foodLevel is above zero
				if(antObj.getFoodLevel() > 0) { 
				antObj.setFoodLevel(antObj.getFoodLevel()); // set new foodlevel
				antObj.move(antObj.getLocation(), antObj.getHeading(), antObj.getSpeed(), 10000, new Dimension(height,width)); // move the ant
				}
				// if foodLevel is at or below zero
				else { 
					if (lives > 1) {	// if you aren't on last life
						lives = lives - 1;
						antObj.setFoodLevel(10);
						antObj.setLocation(new Point(500,800));
						antObj.setHeading(0);
						init();
						System.out.println("You ran out of food and lost a life.\n");
					}
					else {	// if you are on your last life
						System.out.println("You ran out of lives. Game Over\n");
						System.exit(0);
					}
						
				}
			}
		}
		
		checkForCollide();
		clock = clock +1; // the elapsed time game clock is incremented by one
		this.setChanged();
		this.notifyObservers(this);
		System.out.println("The clock has ticked\n");
	
}// ticked()
	
	
	// method to create a random color
	private int randomColor() {
		
		return ColorUtil.rgb(1 + rand.nextInt(244), 1 + rand.nextInt(244),1 + rand.nextInt(244));
	}
	
	// method to create a random point
	private Point randomLocation() {
		Point location;

		location = new Point(originX+rand.nextInt(height-(originX+100)),originY+rand.nextInt(width-200));
		
		return  location;
	}

	public void map() {
		IIterator theElements = gameCollection.getIterator();
		while ( theElements.hasNext() ) { 
		GameObject gObject = (GameObject) theElements.getNext() ;
		System.out.println (gObject.toString());
		}
		
			  
			
		System.out.print("\n");

	}

	public void setSound(boolean sound) {
		soundOn = sound;
		this.setChanged();
		this.notifyObservers(this);
	}
	
	public boolean getSound() {
		return soundOn;
	}

	public int getTime() {
		return clock;
	}
	
	public int getLives() {
		return lives;
	}
		
	public GameObjectCollection getGameObject(){
		return gameCollection;
	}

	public boolean getPaused() {
		return paused;
	}
	
public void setPaused(boolean bPause) {
		
		if (bPause == false) {
			paused = bPause;
			System.out.println("Game has been unpaused, you can now play.");
		} else {
			paused = bPause;
			System.out.println("Game has been paused.");
		}
	}
	
	private void checkForCollide() {
		IIterator theElements = gameCollection.getIterator();
		while(theElements.hasNext()) { // while1
			ICollider curObj = (ICollider)theElements.getNext(); // get a collidable object
			
			// check if this object collides with any other object
			IIterator theElements2 = gameCollection.getIterator();
			while(theElements2.hasNext()) { // while2
				ICollider otherObj = (ICollider) theElements2.getNext();
				
				if(curObj!=otherObj) {
				
					// if the currentObject collides with otherObject          AND       current object contains other object
					if(((GameObject) curObj).collidesWith((GameObject)otherObj) && !((GameObject)curObj).contains((GameObject) otherObj)) {
						((GameObject) curObj).addCollidedObject((GameObject) otherObj); // add the other object as part of the collided vector
						curObj.handleCollision(((GameObject) otherObj));
						// play sounds
						if((GameObject) curObj instanceof Ant && (GameObject) otherObj instanceof Spider)
							spiderCollide.play();
						else if((GameObject) curObj instanceof Ant && (GameObject) otherObj instanceof FoodStation)
							foodCollide.play();
						else if((GameObject) curObj instanceof Ant && (GameObject) otherObj instanceof Flag)
							flagCollide.play();
					}// if
					
					else if (!((GameObject) curObj).collidesWith((GameObject)otherObj) && ((GameObject)curObj).contains((GameObject) otherObj)) {
						((GameObject)curObj).deleteCollided((GameObject)otherObj);
					}//else if
				}// if
			}//while2
		}//while1
	
	}

	public void setHeight(int height) {
	this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
		
	}
	public void setOriginX(int x) {
		originX = x;
	}

	public void setOriginY(int y) {
		originY = y;
	}
	
	
	public void createSounds() {
		spiderCollide = new Sound("squeak.wav");
		foodCollide = new Sound("crunch.wav");
		flagCollide = new Sound ("success.wav");
		bgSound = new BGSound("background-music.wav");
		
	}

	public void setPosition(boolean b) {
		bPosition = b;
		
	}
	public boolean positionSelected() {
		return bPosition;
		
	}

	public void reposition(Fixed fixedObj, Point pPtrRelPrnt) {
		fixedObj.setLocation(pPtrRelPrnt);
		this.setChanged();
		this.notifyObservers(this);
	}

	public BGSound getBgSound() {
		
		return bgSound;
	}
	
	}


	



