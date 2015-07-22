package player;

import game.Card;

import java.util.LinkedList;
import java.util.List;

public class InJail {
	private boolean inJail;
	private int turns;
	private List<Card> cards = new LinkedList<Card>();
	
	public boolean isInJail() {
		return inJail;
	}
	
	public void goToJail(){
		inJail = true;
		turns = 0;
	}
	
	public void addGetOutOfJailCard(Card card){
		cards.add(card);
	}
		
	public void useGetOutOfJailCard(){ 
		if(cards.isEmpty()) throw new RuntimeException("No tenes ninguna carta.");
		Card card = cards.remove(0);
		card.returnToDeck();
		turns = 0;
		inJail = false;
	}
	
	public List<Card> getCards(){
		return cards;
	}
	
	public void nextTurn(){
		turns++;
	}
	
	public void getOutOfJail(){
		inJail = false;
		turns = 0;
	}
	
	public int turnsInJail(){
		return turns;
	}

	public int amountOfGetOutOfJailCards() {
		return cards.size();
	}
	
	public void addCards(List<Card> cards){
		cards.addAll(cards);
	}
	
	public void removeCards(List<Card> cards){
		cards.removeAll(cards);
	}

}
