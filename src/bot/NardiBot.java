package bot;

import game.PropertyInformation;

import java.util.List;

import player.PlayerInformation;

public class NardiBot implements Bot {
	private PlayerInformation mPlayerInformation;
	
	@Override
	public void startGame(PlayerInformation pi) {
		mPlayerInformation = pi;
	}
	
	public String doAction(List<String> actions) {
		return actions.get(0);
	}
	
	public boolean buyProperty(PropertyInformation pi) {
		return true;
	}
}
