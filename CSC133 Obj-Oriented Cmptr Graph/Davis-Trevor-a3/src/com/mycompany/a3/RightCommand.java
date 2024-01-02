package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class RightCommand extends Command{
	private GameWorld gw;
	private static RightCommand right;
	
	
	
	private RightCommand(GameWorld gw) {
		 super("Right");
	      this.gw = gw;
		
	}
	
	 public static RightCommand getRight(GameWorld gw) {  
			if (right == null)
				right =  new RightCommand(gw);
			return right;
			}
	
	 public void actionPerformed(ActionEvent e)
	    {
	        gw.right();
	        System.out.println("Right turn");
	    }

}