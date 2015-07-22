package square;

import game.Game;
import game.Property;
import player.Player;
import player.User;

public class PropertySquare extends Square {

	private final Property property;
	
	public PropertySquare(String name, Property property){
		super(name);
		this.property=property;
	}

	public void action(Player player, Game state) {
		if(property.getOwner() != null){
			if(!property.getOwner().equals(player)){
				player.payTo(property.getOwner(), property.getRent(state.getDice1Value() + state.getDice2Value()));
			}
		}else{
			User.askBuyProperty(player, property);
		}
	}
	
	public Property getProperty(){
		return property;
	}
	
}
