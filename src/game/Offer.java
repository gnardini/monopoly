package game;

import java.util.List;

import player.Player;
import eda.ftw.SortedList;

public class Offer {

//	private Player offerPlayer, askPlayer;
//	private List<Property> offerProperties, askProperties;
//	private int offerMoney, askMoney, offerCards, askCards;
	
	private Player[] players;
	private SortedList<Property>[] properties;
	private List<Card>[] cards;
	private int[] money;
	
	@SuppressWarnings("unchecked")
	public Offer(){
		players = new Player[2];
		properties = (SortedList<Property>[])(new SortedList<?>[2]);
		money = new int[2];
		cards = (List<Card>[])(new List<?>[2]);
	}
	
	public void setPlayer(Player player, int position){
		players[position] = player;
	}
	
	public void setProperties(SortedList<Property> properties, int position){
		this.properties[position] = properties;
	}
	
	public void setMoney(int money, int position){
		this.money[position] = money;
	}
	
	public void setCards(List<Card> cards2, int position){
		this.cards[position] = cards2;
	}

	public Player[] getPlayers() {
		return players;
	}

	public SortedList<Property>[] getProperties() {
		return properties;
	}

	public List<Card>[] getCards() {
		return cards;
	}

	public int[] getMoney() {
		return money;
	}
	
	public boolean isValid(){
		for(int i=0 ; i<2 ; i++){
			if(players[i].getMoney() < money[i]) return false;
			for(Property property: properties[i]){
				if(property instanceof Street){
					if(((Street)property).getHousesAndHotels() > 0) return false;
				}
			}
		}
		return true;
	}
	
	
	
//	public Offer(Player offerPlayer, List<Property> offerProperties, int offerMoney, int offerCards, Player askPlayer, List<Property> askProperties, int askMoney, int askCards) {
//		this.offerPlayer = offerPlayer;
//		this.offerProperties = offerProperties;
//		this.offerMoney = offerMoney;
//		this.offerCards = offerCards;
//		this.askPlayer = askPlayer;
//		this.askProperties = askProperties;
//		this.askMoney = askMoney;
//		this.askCards = askCards;
//	}
	
}
