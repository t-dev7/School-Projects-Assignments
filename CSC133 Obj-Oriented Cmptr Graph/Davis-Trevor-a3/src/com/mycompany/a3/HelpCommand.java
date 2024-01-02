package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class HelpCommand extends Command{
private static HelpCommand help;
    
    private HelpCommand()
    {
        super("Help");
    }
    
    public static HelpCommand getHelp() {  
		if (help == null)
			help =  new HelpCommand();
		return help;
		}
    
    public void actionPerformed(ActionEvent e) {
    	String info = 
  			  "a: Accelerate\n"+
  			  "b: Brake\n"+
  			  "l: Left\n"+
  		      "r: Right\n"+
  			  "f: Collide with Food Station\n"+
  		      "g: Collide with Spider\n" +
  		      "t: Tick\n" 
  		       	+ "";
  Dialog.show("Help Comand",info,"Ok",null);
    }

}
