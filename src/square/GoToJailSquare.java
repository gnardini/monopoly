package square;

import game.Game;
import player.Player;

public class GoToJailSquare extends Square {

	
	public GoToJailSquare(String name) {
		super(name);
	}

	@Override
	public void action(Player player, Game state) {
		state.sendPlayerToJail(player);
	}

}
