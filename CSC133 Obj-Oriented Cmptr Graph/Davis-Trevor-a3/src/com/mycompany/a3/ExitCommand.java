package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;

public class ExitCommand extends Command{
private static ExitCommand exit;
    
    private ExitCommand()
    {
        super("Exit");
    }
    
    public static ExitCommand getExit() {  
		if (exit == null)
			exit =  new ExitCommand();
		return exit;
		}
    
    public void actionPerformed(ActionEvent e) {
    	Boolean bOk = Dialog.show("Confirm quit", "Are you sure you want to quit?", 
    			"Ok", "Cancel");
  
        if (bOk)
        	Display.getInstance().exitApplication();
    }
}
