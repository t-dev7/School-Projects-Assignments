package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;


public class Game extends Form implements Runnable{
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	private UITimer timer;
	private Container SelfAnimationContainer;
	private PauseCommand pause;
	private CheckBox chkBoxcmd;
	private AccelerateCommand acc;
	private AboutCommand about;
	private ExitCommand exit;
	private HelpCommand help;
	private BrakeCommand brake;
	private RightCommand right;
	private LeftCommand left;
	private PositionCommand position;
	private Button btnPause;
	private Button btnPosition;
	private Button btnLeft;
	private Button btnAcc;
	private Button btnRight;
	private Button btnBrake;
	private SoundCommand snc;

	
	public Game () {
		
		this.setLayout(new BorderLayout());
		gw = new GameWorld(); // Game constructor instantiates a GameWorld
		mv = new MapView(); // create an Observer for the map
		sv = new ScoreView(); // create an Observe for the game/ant state data
		gw.addObserver(mv); // register the map observer
		gw.addObserver(sv); // register the score observer
		
		
		
		
		
		
		// code here to create Command objects for each command,
			menuCommands();
			leftCommands();
			rightCommands();
			bottomCommands();
			
			this.add(BorderLayout.NORTH,sv);
			this.add(BorderLayout.CENTER,mv);
			
		
		this.show();
		
		gw.setHeight(mv.getWidth());
		gw.setWidth(mv.getHeight());
		MapView.setMapViewWidth(mv.getWidth());
		MapView.setMapViewHeight(mv.getHeight());
		gw.setOriginX(mv.getX());
		gw.setOriginY(mv.getY());
	
		
		
		gw.init(); // calls a GameWorld method init() to set the initial state of the game
		
		gw.createSounds();
		revalidate();
	
		timer = new UITimer(this);
		timer.schedule(1000, true, this);
		run();
	}
	
	
	
	private void menuCommands() {
		Toolbar tb = new Toolbar();
		this.setToolbar(tb);
		tb.setTitle("Walkit Game");
	
		// Sound
		chkBoxcmd = new CheckBox();
		chkBoxcmd.getAllStyles().setBgTransparency(255);
		chkBoxcmd.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		
		snc = new SoundCommand(gw);
		chkBoxcmd.setCommand(snc);
		tb.addComponentToSideMenu(chkBoxcmd);
		
		//Acceleration
		acc = AccelerateCommand.getAcc(gw);
		tb.addCommandToSideMenu(acc);
		
		//About
		about = AboutCommand.getAbt();
		tb.addCommandToSideMenu(about);
		
		// Exit
		exit = ExitCommand.getExit();
		tb.addCommandToSideMenu(exit);
		addKeyListener('x',exit);	
//		
//		//Help
		help = HelpCommand.getHelp();
		tb.addCommandToRightBar(help);
//		
	}
	
private void rightCommands() {
	Container rightContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
	//setting the back ground color of center container to light gray
	rightContainer.getAllStyles().setBgTransparency(255);
	rightContainer.getAllStyles().setBgColor(ColorUtil.LTGRAY);
	
	// Brake command
	brake = BrakeCommand.getBrake(gw);
	btnBrake = new Button(brake);
	// button styling 
	btnBrake.getAllStyles().setBgTransparency(255);
	btnBrake.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
	btnBrake.getAllStyles().setBorder(Border.createLineBorder(3,ColorUtil.rgb(0, 0, 0)));
	btnBrake.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	btnBrake.getAllStyles().setPadding(TOP, 5);
	btnBrake.getAllStyles().setPadding(BOTTOM, 5);
	btnBrake.getAllStyles().setPadding(LEFT, 5);
	btnBrake.setCommand(brake);
	rightContainer.add(btnBrake); // add the flag button to the container
	addKeyListener('b',brake);	
	
	// right command 
	right = RightCommand.getRight(gw);
	btnRight = new Button(right);
	// button styling
	btnRight.getAllStyles().setBgTransparency(255);
	btnRight.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
	btnRight.getAllStyles().setBorder(Border.createLineBorder(3,ColorUtil.rgb(0, 0, 0)));
	btnRight.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	btnRight.getAllStyles().setPadding(TOP, 5);
	btnRight.getAllStyles().setPadding(BOTTOM, 5);
	btnRight.getAllStyles().setPadding(LEFT, 5);
	btnRight.setCommand(right);
	rightContainer.add(btnRight); 
	addKeyListener('r',right);	
	
	rightContainer.getAllStyles().setBorder(Border.createLineBorder(4,
			ColorUtil.BLACK));
			add(BorderLayout.EAST,rightContainer); // put the container in the south
		
		
	}

private void leftCommands() {
	Container leftContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
	//setting the back ground color of center container to light gray
	leftContainer.getAllStyles().setBgTransparency(255);
	leftContainer.getAllStyles().setBgColor(ColorUtil.LTGRAY);

	// accelerate command
	acc = AccelerateCommand.getAcc(gw);
	btnAcc = new Button(acc);
	// button styling
	btnAcc.getAllStyles().setBgTransparency(255);
	btnAcc.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
	btnAcc.getAllStyles().setBorder(Border.createLineBorder(3,ColorUtil.rgb(0, 0, 0)));
	btnAcc.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	btnAcc.getAllStyles().setPadding(TOP, 5);
	btnAcc.getAllStyles().setPadding(BOTTOM, 5);
	btnAcc.getAllStyles().setPadding(LEFT, 5);
	btnAcc.setCommand(acc);
	leftContainer.add(btnAcc);
	addKeyListener('a',acc);	
	
	// left command
	left = LeftCommand.getLeft(gw);
	btnLeft = new Button(left);
	// button styling
	btnLeft.getAllStyles().setBgTransparency(255);
	btnLeft.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
	btnLeft.getAllStyles().setBorder(Border.createLineBorder(3,ColorUtil.rgb(0, 0, 0)));
	btnLeft.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
	btnLeft.getAllStyles().setPadding(TOP, 5);
	btnLeft.getAllStyles().setPadding(BOTTOM, 5);
	btnLeft.getAllStyles().setPadding(LEFT, 5);
	btnLeft.setCommand(left);
	// add to container
	leftContainer.add(btnLeft); 
	addKeyListener('l',left);	
	
	leftContainer.getAllStyles().setBorder(Border.createLineBorder(4,
			ColorUtil.BLACK));
			add(BorderLayout.WEST,leftContainer); // put the container in the west
		
	}


	
	
	private void bottomCommands() {
		
		Container bottomContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		//setting the back ground color of center container to light gray
		bottomContainer.getAllStyles().setBgTransparency(255);
		bottomContainer.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		
		// Event driven, flag command that is linked to button
		
		btnPosition = new Button("position");
		position = new PositionCommand(gw, btnPosition);
		btnPosition.getAllStyles().setBgTransparency(255);
		btnPosition.getUnselectedStyle().setBgColor(ColorUtil.rgb(255,255,255));
		btnPosition.getAllStyles().setBorder(Border.createLineBorder(3,ColorUtil.rgb(0, 0, 0)));
		btnPosition.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		btnPosition.getAllStyles().setMarginLeft(900);
		btnPosition.getAllStyles().setPadding(TOP, 5);
		btnPosition.getAllStyles().setPadding(BOTTOM, 5);
		btnPosition.setCommand(position);
		btnPosition.setEnabled(false);
		bottomContainer.add(btnPosition); // add the flag button to the container
		
		
		// Collide with Spider Button
	
		btnPause = new Button("Pause");;
	    pause = new PauseCommand(gw, this, btnPause);
		btnPause.getAllStyles().setBgTransparency(255);
		btnPause.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		btnPause.getAllStyles().setBorder(Border.createLineBorder(3,ColorUtil.rgb(0, 0, 0)));
		btnPause.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		btnPause.getAllStyles().setPadding(TOP, 5);
		btnPause.getAllStyles().setPadding(BOTTOM, 5);
		btnPause.setCommand(pause);
		bottomContainer.add(btnPause);
	
		
	
		
		
		//setting the border Color
		bottomContainer.getAllStyles().setBorder(Border.createLineBorder(4,
		ColorUtil.BLACK));
		add(BorderLayout.SOUTH,bottomContainer); // put the container in the south

	
	}
	
	
	public void run() {
//		Dimension dCmpSize = new Dimension(this.getWidth(),
//				this.getHeight());
		gw.ticked();
		this.repaint();
		
		// if the gameworld is paused
		if (gw.getPaused() == true) {
			gw.setSound(false);
			timer.cancel();
			this.disableCommands();
			this.btnPosition.setEnabled(true);
			btnPosition.getUnselectedStyle().setBgColor(ColorUtil.rgb(0,0,255));
			btnPosition.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
			gw.getBgSound().pause();
			revalidate();
		} 
		else {
			this.enableCommands();
			this.btnPosition.setEnabled(false);
			btnPosition.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 0));
			if (gw.getSound()) 
				gw.getBgSound().play();
			timer.schedule(1000, true, this);
			revalidate();
		}
		
		
	}
//	}



	private void enableCommands() {
		//enable commands
		acc.setEnabled(true);
		brake.setEnabled(true);
		right.setEnabled(true);
		left.setEnabled(true);
		snc.setEnabled(true);
		chkBoxcmd.setEnabled(true);
		
		// enable buttons
		btnBrake.setEnabled(true);
		btnBrake.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		
		btnRight.setEnabled(true);
		btnRight.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		
		btnAcc.setEnabled(true);
		btnAcc.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		
		btnLeft.setEnabled(true);
		btnLeft.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));

		addKeyListener('b',brake);
		addKeyListener('r',right);	
		addKeyListener('a',acc);
		addKeyListener('l',left);
		
		
		
	}



	private void disableCommands() {
		acc.setEnabled(false);
		brake.setEnabled(false);
		right.setEnabled(false);
		left.setEnabled(false);
		snc.setEnabled(false);
		chkBoxcmd.setEnabled(false);
		
		btnBrake.setEnabled(false);
		btnBrake.getDisabledStyle();
		btnBrake.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		
		btnRight.setEnabled(false);
		btnRight.getDisabledStyle();
		btnRight.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		
		btnAcc.setEnabled(false);
		btnAcc.getDisabledStyle();
		btnAcc.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		
		btnLeft.setEnabled(false);
		btnLeft.getDisabledStyle();
		btnLeft.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		
		
		removeKeyListener('b',brake);
		removeKeyListener('r',right);	
		removeKeyListener('a',acc);
		removeKeyListener('l',left);
		
		
		
		
		
	}
	
}