package square;

import game.Game;
import player.Player;
import player.User;

public class TaxSquare extends Square {

	public TaxSquare(String name) {
		super(name);
	}
	
	@Override
	public void action(Player p, Game state) {
		User.askTaxAnswer(p,state);
	}

}
