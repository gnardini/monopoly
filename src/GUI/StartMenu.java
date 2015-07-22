package GUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartMenu extends JFrame{
	
	public StartMenu(){
		
		setTitle("Monopoly");
		setBounds(100,100, 500, 500);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final PlayerAmountPanel pap = new PlayerAmountPanel();
		pap.setLayout(null);
		pap.setBounds(0,0,500,500);
		
		final JComboBox<Integer> playerAmount = new JComboBox<Integer>();
		playerAmount.addItem(2);
		playerAmount.addItem(3);
		playerAmount.addItem(4);
		playerAmount.setBounds(160, 10, 40, 30);
		playerAmount.setVisible(true);
		
		JButton acceptPlayers = new JButton("Aceptar");
		acceptPlayers.setBounds(220, 10, 80, 30);
		acceptPlayers.setVisible(true);
		acceptPlayers.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				final int players = (int) playerAmount.getSelectedItem();
				StartMenu.this.remove(pap);
				
				PlayerNamesPanel pnp = new PlayerNamesPanel(players);
				pnp.setLayout(null);
				pnp.setBounds(0,0,500,500);
				
				final JTextField playerNamesText[] = new JTextField[players];
				for(int i = 0 ; i < players ; i++){
					playerNamesText[i] = new JTextField();
					playerNamesText[i].setBounds(90,15+45*i, 100, 22);
					playerNamesText[i].setVisible(true);
					StartMenu.this.add(playerNamesText[i]);
				}
				
				JButton startGame = new JButton("Empezar!");
				startGame.setBounds(90, 15+45*players, 80, 30);
				startGame.setVisible(true);
				startGame.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						List<String> playerNames = new ArrayList<String>(players);
						
						for(int i=0 ; i<players ; i++)
							playerNames.add(i, playerNamesText[i].getText());
						
						
						new Monopoly(playerNames);
						StartMenu.this.dispose();						
					}
				});
				
				pnp.add(startGame);
				pnp.setVisible(true);
				StartMenu.this.add(pnp);
				StartMenu.this.repaint();
			}
		});
		
		
		
		pap.add(acceptPlayers);
		pap.add(playerAmount);
		add(pap);
		pap.setVisible(true);
		setVisible(true);
	}
	
	private class PlayerNamesPanel extends JPanel {
		
		int players;
		
		public PlayerNamesPanel(int players) {
			this.players = players;
		}
		
		@Override
		public void paint(Graphics gg) {
			super.paint(gg);
			Graphics2D g = (Graphics2D) gg;
			
			for(int i=0 ; i<players ; i++)
				g.drawString("Jugador " + (i+1) + ":", 20, 30 + 45*i);
			
		}
		
	}
	
	
	private class PlayerAmountPanel extends JPanel {
		
		@Override
		public void paint(Graphics gg) {
			super.paint(gg);
			Graphics2D g = (Graphics2D) gg;
			
			g.drawString("Cantidad de jugadores: ", 20, 30);
			
		}
	}
	/*
	public static void main(String[] args) {
		new StartMenu();
	}*/
}
