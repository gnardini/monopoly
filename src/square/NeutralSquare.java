package square;

import game.Game;
import player.Player;

public class NeutralSquare extends Square {

	public NeutralSquare(String name) {
		super(name);
	}

	public void action(Player p, Game state) {}
	
}
