package GUI;

import game.Card;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class CardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int LINE_LIMIT = 40;
	private Card card;
	
	public CardPanel(Card card) {
		this.card = card;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.setFont(g.getFont().deriveFont(16f));
		String[] message = card.message().split(" ");
		
		int lineCounter = 0;
		for(int i=0 ; i<message.length ;) {
			StringBuffer line = new StringBuffer("");
			while(line.length() < LINE_LIMIT && i < message.length){
				line.append(message[i++] + " ");
			}
			g.drawString(line.toString(), 10, 40 + lineCounter * 30);
			lineCounter++;
		}
		
	}
}
