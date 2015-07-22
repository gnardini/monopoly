package GUI;

import game.Game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import player.Player;

public class Monopoly extends JFrame{
	private static final long serialVersionUID = 1L;
	private final static int STARTING_MONEY = 1500;
	private static final Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
	private Game game;
	private GeneralPanel boardPanel;
	
	public Monopoly(List<String> playerNames) {
		List<Player> players = new ArrayList<Player>();
		for(int i=0 ; i<playerNames.size() ; i++)
			players.add(new Player(playerNames.get(i), STARTING_MONEY, colors[i], null));
		Collections.shuffle(players);
		
		setTitle("Monopoly");
		setBounds(100,100, 1100, 840);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game = new Game(players);
		boardPanel = new GeneralPanel(game, this);
		addMouseListener(boardPanel);
		add(boardPanel);
		boardPanel.setVisible(true);
		setVisible(true);
		
		
	}
	
	public static void main(String[] args) {
		List<String> names = new ArrayList<String>();
		names.add(0,"Gonzalo");
		names.add(1,"Diego");
		names.add(2,"John");
		names.add(3,"Osvald");
		new Monopoly(names);
	}
	
}
