package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import player.Player;
import player.PlayerInformation;
import player.User;
import bot.Bot;
import bot.NardiBot;

public class Game {
	private final List<Player> players;
	private final List<PlayerInformation> playerinfo;
	private final Board board;
	private Player activePlayer;
	private int turn, doubleCounter, playerAmount;
	private Die d1, d2;
	private boolean canThrowDice;

	public Game(List<Player> players) {
		this.players = players;
		
		for(int i=0 ; i<players.size(); i++)
			players.get(i).setID(i);
		
		playerAmount = players.size();
		playerinfo = new ArrayList<PlayerInformation>();
		for(int i=0 ; i<playerAmount ; i++){
			playerinfo.add(new PlayerInformation(players.get(i)));
		}
		
		d1 = new Die();
		d2 = new Die();
		turn=0;
		activePlayer = getCurrentPlayer();
		board = new Board();
		board.addPlayers(players);
		User.setGame(this);
	//	board.doStuff(players.get(0), players.get(1), players.get(2));
		canThrowDice = true;
	}
	
	public void rollDice(){
		d1.rollDie();
		d2.rollDie();
	}

	public void executeMove() {
		if(!canThrowDice) throw new RuntimeException("No podes tirar los dados");
		Player player = players.get(turn);
		rollDice();
		canThrowDice=false;
		
		if(player.isInJail()){
			if(d1.getValue() != d2.getValue()){
				player.turnInJail();
				if(!player.jailTimeUp()){
					return;
				}
				player.payBail();
			}else
				player.getOutOfJail();
		}else{
			if(d1.getValue() == d2.getValue()){
				doubleCounter++;
				if(doubleCounter==3){
					sendPlayerToJail(player);
					return;
				}
				canThrowDice=true;
			}
		}
		/*
		if (player.isInJail()) {
			if (d1.getValue() != d2.getValue()) {
				player.turnInJail();
				if (player.jailTimeUp()) {
					player.payBail();
				}
				canThrowDice = false;
				return;
			} else {
				player.getOutOfJail();
			}
		}
		if (d1.getValue() == d2.getValue()) {
			doubleCounter++;
			if (doubleCounter == 3) {
				sendPlayerToJail(player);
				return;
			}
		}else{
			canThrowDice = false;
		}*/
		board.movePlayer(player , d1.getValue() + d2.getValue(), true);
		board.doAction(player, board.getPlayerPosition(player), this);
	}
	
	public void finishTurn(){
		if(canThrowDice) throw new RuntimeException("Todavia es tu turno");
		changeTurn();
	}
	
	public void sendPlayerToJail(Player player){
		player.goToJail();
		board.setPlayerPosition(player, 10, false);
		canThrowDice=false;
	}
	
	public boolean currentPlayerIsInJail(){
		return players.get(turn).isInJail();
	}
	
	public boolean currentPlayerHasGetOutOfJailCard(){
		return players.get(turn).hasGetOutOfJailCard();
	}
	
	public void payToGetCurrentPlayerOutOfJail(){
		players.get(turn).payBail();
	}
	
	public void removePlayer(Player player){
		//playerinfo.remove(players.indexOf(player));
		player.lost();
		
		int playersAlive = 0;
		for(int i=0 ; i < playerAmount ; i++)
			if(players.get(i).isPlaying())
				playersAlive++;
		
		if(playersAlive == 0) ;//lol
		else if(playersAlive == 1) ;
	}
	
	public void changeTurn(){
		canThrowDice = true;
		do{
			turn = (turn + 1) % playerAmount;
		}while(!players.get(turn).isPlaying());
		doubleCounter = 0;
		updateActivePlayer();
		
		Set<String> actions = new HashSet<String>();
		actions.add("THROW DICE");
		actions.add("NEGOTIATE");
		if(getCurrentPlayer().isInJail()){
			actions.add("PAY JAIL");
			if(getCurrentPlayer().hasGetOutOfJailCard())
				actions.add("USE JAIL CARD");
		}
		for(Property property : getCurrentPlayer().getProperties()){
			if(property.isMortgaged()) actions.add("UNMORTGAGE");
			else {
				if(!(property instanceof Street) || ((Street)property).getHousesAndHotels() == 0)
					actions.add("MORTGAGE");
			}
			
			if(property instanceof Street){
				Street street = (Street) property;
				if(street.canBuyHouse(this) || street.canBuyHotel(this))
					actions.add("BUY HOUSES");
				if(street.canSellHotel(this) || street.canSellHouse(this))
					actions.add("SELL HOUSES");
			}
		}
		
		askBot(new LinkedList<String>(actions));
	}
	
	private void askBot(List<String> actions){
		Bot bot = getCurrentPlayer().getBot();
		if(bot == null) return;
		
		if(actions == null) actions = new LinkedList<String>();
		String response = bot.doAction(actions);
		if(!actions.contains(response)) {
			removePlayer(getCurrentPlayer());
			changeTurn();
			return;
		}
		switch(response){
			case "THROW DICE": executeMove(); break;
		}
	}
	
	public Player getCurrentPlayer(){
		return players.get(turn);
	}
	
	public void useCardToGetCurrentPlayerOutOfJail(){
		players.get(turn).useGetOutOfJailCard();
		players.get(turn).getOutOfJail();
	}
	
	public Player getActivePlayer() {
		return activePlayer;
	}
	
	public Player getActualActivePlayer() {
		return players.get(activePlayer.getID());
	}
	
	public void updateActivePlayer(){
		activePlayer = getCurrentPlayer();
	}
	
	public void setActivePlayer(Player player){
		activePlayer = player;
	}
	
	public void setActivePlayer(int player){
		activePlayer = players.get(player);
	}
	
	public List<PlayerInformation> getPlayersInformation(){
		return playerinfo;
	}
	
	protected List<Player> getPlayers(){
		return players;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Player getPlayer(int i){
		return players.get(i);
	}
	
	public Player getActualPlayer(int i){
		return players.get(i);
	}
	
	public int getPlayerAmount() {
		return playerAmount;
	}
	
	public int getPlayerTurn(){
		return turn;
	}
	
	public boolean canThrowDice(){
		return canThrowDice;
	}
	
	public int getDice1Value(){
		return d1.getValue();
	}
	
	public int getDice2Value(){
		return d2.getValue();
	}

	public void applyOffer(Offer offer) {
		for(int i=0; i<2; i++){
			offer.getPlayers()[i].addProperties(offer.getProperties()[1-i]);
			offer.getPlayers()[i].removeProperties(offer.getProperties()[i]);
			offer.getPlayers()[i].payTo(offer.getPlayers()[1-i], offer.getMoney()[i]);
			offer.getPlayers()[i].removeCards(offer.getCards()[i]);
			offer.getPlayers()[i].addCards(offer.getCards()[1-i]);
		}
	}

}
