package game;

import GUI.UserInterface;
import player.Player;

public class Auction {
	private int highestBid = 0, activePlayer;
	private Player highestBidder = null;
	private Game game;
	private Property property;
	
	public Auction(Game game, Property property){
		this.game = game;
		this.property = property;
		activePlayer = game.getPlayerTurn();		
		
		UserInterface.auctionProperty(this, property, highestBidder == null ? null : highestBidder.getName(), highestBid);
	}
	
	public void raiseBid(int bid){
		if(bid > highestBid){
			highestBid = bid;
			highestBidder = game.getActualPlayer(activePlayer);
		}
		passBid();
	}
	
	public void passBid(){
		do{
			activePlayer = (activePlayer + 1) % game.getPlayerAmount();
		}while(!game.getPlayer(activePlayer).isPlaying());
		game.setActivePlayer(activePlayer);
		
		if(highestBidder == null && activePlayer == game.getPlayerTurn()){
			game.updateActivePlayer();
			UserInterface.endAuction();
			return;
		}
		System.out.println(activePlayer + " " + highestBidder.getID());
		if(highestBidder != null && activePlayer == highestBidder.getID()){
			
			highestBidder.addProperty(property);
			highestBidder.payTo(null, highestBid);
			game.updateActivePlayer();
			UserInterface.endAuction();
			return;
		}
		
		UserInterface.auctionProperty(this, property, highestBidder == null ? null : highestBidder.getName(), highestBid);
	}
}
