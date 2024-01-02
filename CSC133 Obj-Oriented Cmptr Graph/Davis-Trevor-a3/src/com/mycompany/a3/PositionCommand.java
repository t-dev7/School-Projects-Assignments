package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PositionCommand extends Command{
	private GameWorld gw;
	private Button btnPosition;
	
	public PositionCommand(GameWorld gw, Button bPosition) {
		super("Position");
		this.gw = gw;
		btnPosition = bPosition;
	}
public void actionPerformed(ActionEvent e) {
	if (gw.getPaused()) 
		gw.setPosition(true);

}
}


