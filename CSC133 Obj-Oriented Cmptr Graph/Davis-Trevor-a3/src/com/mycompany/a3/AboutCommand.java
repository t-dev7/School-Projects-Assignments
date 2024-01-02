package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class AboutCommand extends Command{

	private static AboutCommand about;
    
    private AboutCommand()
    {
        super("About");
    }
    
    public static AboutCommand getAbt() {  
		if (about == null)
			about =  new AboutCommand();
		return about;
		}
    
	 public void actionPerformed(ActionEvent e)
	    {
		 String info = "Trevor Davis\n"
	        		+ "CSC-133 Object Oriented Programming\n"
	        		+ "A2Prj";
	        Dialog.show("About",info,"Ok",null);
	    }

}
