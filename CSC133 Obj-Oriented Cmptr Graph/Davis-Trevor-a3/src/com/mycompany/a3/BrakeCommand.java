package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class BrakeCommand extends Command{
	private GameWorld gw;
	private static BrakeCommand brake;
	
	private BrakeCommand(GameWorld gw) {
		 super("Brake");
	      this.gw = gw;
		
	}
	
	 public static BrakeCommand getBrake(GameWorld gw) {  
			if (brake == null)
				brake =  new BrakeCommand(gw);
			return brake;
			}
	
	 public void actionPerformed(ActionEvent e)
	    {
	        gw.brake();
	        System.out.println("Brake applied");
	    }

}
