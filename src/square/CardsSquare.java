package square;

import game.Card;
import game.Deck;
import game.Game;
import player.Player;
import player.User;

public class CardsSquare extends Square {

	private Deck deck;
	
	public CardsSquare(String name, Deck deck) {
		super(name);
		this.deck = deck;
	}

	@Override
	public void action(Player player, Game state) {
		Card card = deck.drawCard();
		User.showCard(player, state, card);
	}

}
