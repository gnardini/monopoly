package square;

import game.Game;
import player.Player;

public class LuxuryTaxSquare extends Square {

	public LuxuryTaxSquare(String name) {
		super(name);
	}
	
	@Override
	public void action(Player p, Game state) {
		p.payTo(null, 75);
	}

}
