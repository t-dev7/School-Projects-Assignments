package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class LeftCommand extends Command{
	private GameWorld gw;
	private static LeftCommand left;
    
    private LeftCommand(GameWorld gw)
    {
        super("Left");
        this.gw = gw;
    }
    
    public static LeftCommand getLeft(GameWorld gw) {  
		if (left == null)
			left =  new LeftCommand(gw);
		return left;
		}
    
	 public void actionPerformed(ActionEvent e)
	    {
	        gw.left();
	    }

}
