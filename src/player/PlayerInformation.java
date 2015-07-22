package player;

import java.awt.Color;

import eda.ftw.SortedList;
import game.Property;

public class PlayerInformation {

	private Player player;
	
	public PlayerInformation(Player player){
		this.player = player;
	}
	
	public String getName(){
		return player.getName();
	}
	
	public int getID(){
		return player.getID();
	}
	
	public int getMoney(){
		return player.getMoney();
	}
	
	public Color getColor(){
		return player.getColor();
	}
	
	public boolean isInJail(){
		return player.isInJail();
	}
	
	public boolean isPlaying(){
		return player.isPlaying();
	}
	
	public SortedList<Property> getProperties(){
		return player.getProperties();
	}
	
	public int amountOfGetOutOfJailCards(){
		return player.amountOfGetOutOfJailCards();
	}
	
	public boolean equals(Player p) {
		return getID() == p.getID();
	}

}
