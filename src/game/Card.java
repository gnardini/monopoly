package game;

import player.Player;

public interface Card {

	public void action(Player p, Game state);
	
	public String message();
	
	public void returnToDeck();
}
