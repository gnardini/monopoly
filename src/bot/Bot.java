package bot;

import game.PropertyInformation;

import java.util.List;

import player.PlayerInformation;

public interface Bot {
	
	public void startGame(PlayerInformation pi);
	
	// if answer is less than 0 or bigger than actions.lenght, turn ends
	public String doAction(List<String> actions);
	
	// return true to buy property, false to auction it
	public boolean buyProperty(PropertyInformation pi);
}
