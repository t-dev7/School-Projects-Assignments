package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class AccelerateCommand extends Command{
	private GameWorld gw;
	private static AccelerateCommand acc;
    
    private AccelerateCommand(GameWorld gw)
    {
        super("Accelerate");
        this.gw = gw;
    }
    
    public static AccelerateCommand getAcc(GameWorld gw) {  
		if (acc == null)
			acc =  new AccelerateCommand(gw);
		return acc;
		}
	
	 public void actionPerformed(ActionEvent e)
	    {
	        gw.accelerate();
	    }

}
