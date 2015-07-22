package player;

import game.Auction;
import game.Card;
import game.Game;
import game.Offer;
import game.Property;
import game.PropertyInformation;

import java.util.LinkedList;
import java.util.Queue;

import GUI.UserInterface;
import bot.Bot;

public class User {
	private static Game game;
	private static Queue<BankrrupcyCandidate> possibleBankrrupts = new LinkedList<BankrrupcyCandidate>();
	private static Player backupPlayer;
	
	public static void setGame(Game game) {
		User.game = game;
	}
	
	public static void askBuyProperty(Player player, Property property){
		Bot bot = player.getBot();
		if(bot == null) UserInterface.buyProperty(player, property);
		else{
			boolean buy = bot.buyProperty(new PropertyInformation(property))
					&& player.getMoney() >= property.getPrice();
			if(buy){
				player.buyProperty(property);
			}else{
				auction(property);
			}
		}
	}
	
	public static void buyProperty(Player player, Property property){
		player.buyProperty(property);
	}
	
	public static void auction(Property property){
		new Auction(game, property);
	}
	
	public static void showCard(Player player, Game state, Card card){
		UserInterface.showCard(player, state, card);
	}
	
	public static void acceptCard(Player player, Game state, Card card){
		card.action(player, state);
	}
	
	public static void showNegativeMoney(Player toPay, Player toRecieve){		
		if(possibleBankrrupts.isEmpty()){
			possibleBankrrupts.add(new BankrrupcyCandidate(toPay, toRecieve));
			game.setActivePlayer(toPay);
			UserInterface.showNegativeMoney(toPay, toRecieve);
		}
		else
			possibleBankrrupts.add(new BankrrupcyCandidate(toPay, toRecieve));
	}

	public static void askTaxAnswer(Player player, Game state) {
		UserInterface.askTaxAnswer(player, state);
	}
	
	public static void payTaxPercentage(Player player, Game state){
		int totalMoney=player.getMoney();
		for(Property property: player.getProperties()){
			totalMoney+=property.getPrice();
		}
		player.payTo(null, totalMoney/10);
	}
	
	public static void payTaxConstant(Player player, Game state){
		player.payTo(null, 200);
	}

	public static void playerInBankrrupcyBy(Player toPay, Player toRecieve) {
		toPay.bankrruptedBy(toRecieve);
		game.removePlayer(toPay);
		UserInterface.showPlayerLost(toPay);
		if(!toPay.isPlaying())
			game.changeTurn();
		bankrrupcyCaseSolved();
	}
	
	public static void bankrrupcyCaseSolved(){
		possibleBankrrupts.poll();
		if(!possibleBankrrupts.isEmpty()){
			BankrrupcyCandidate bc = possibleBankrrupts.peek();
			if(!bc.getPayer().isPlaying() || bc.getPayer().getMoney()>=0){
				bankrrupcyCaseSolved();
			}else{
				game.setActivePlayer(bc.getPayer());
				UserInterface.showNegativeMoney(bc.getPayer(), bc.getReciever());
			}
		}else{
			game.updateActivePlayer();
		}
	}
	
	public static void proposeOffer(Offer offer){
		backupPlayer = game.getActivePlayer();
		game.setActivePlayer(offer.getPlayers()[1]);
		UserInterface.askAcceptOffer(offer);
	}
	
	public static void acceptOffer(Offer offer){
		game.setActivePlayer(backupPlayer);
		game.applyOffer(offer);
	}
	
	public static void declineOffer(Offer offer){
		game.setActivePlayer(backupPlayer);
	}
	
	private static class BankrrupcyCandidate {
		private Player toPay, toRecieve;
		
		public BankrrupcyCandidate(Player toPay, Player toRecieve) {
			this.toPay = toPay;
			this.toRecieve = toRecieve;
		}
		
		public Player getPayer(){
			return toPay;
		}
		
		public Player getReciever(){
			return toRecieve;
		}
		
		@Override
		public String toString() {
			return toPay.getName() + " " + (toRecieve == null ? "Banco" : toRecieve.getName());
		}
	}
}
