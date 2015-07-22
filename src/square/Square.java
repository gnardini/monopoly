package square;

import game.Game;
import player.Player;

public abstract class Square {
	
	private String name;
	
	public Square(String name){
		this.name=name;
	}
	abstract public void action(Player p, Game state);
}
