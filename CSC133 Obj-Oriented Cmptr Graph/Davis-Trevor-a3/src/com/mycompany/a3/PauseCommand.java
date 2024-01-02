package com.mycompany.a3;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PauseCommand extends Command{
	private GameWorld gw;
	private Game game;
	private Button button;
	public PauseCommand(GameWorld gw, Game game,Button button) {
		super("Pause");
		this.gw = gw;
		this.game = game;
		this.button = button;
	}
	@Override 
	public void actionPerformed(ActionEvent e) {
		// if the play clicks on play
		if (gw.getPaused()) {
			gw.setPaused(false);
			game.run();
			button.setText("Pause");
		}
		else {
			gw.setPaused(true);
			button.setText("Play");
			
		}
	}
}
