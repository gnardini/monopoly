package GUI;

import game.Property;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

public class AuctionPanel extends PropertyPanel{

	private String highestBidder;
	private int highestBid;
	
	public AuctionPanel(Property property, JButton[] buttons, String highestBidder, int highestBid) {
		super(property, buttons);
		this.highestBidder = highestBidder;
		this.highestBid = highestBid;
	}
	
	@Override
	public void paint(Graphics gg) {
		super.paint(gg);
		Graphics2D g = (Graphics2D) gg;
		
		g.setColor(Color.BLACK);
		g.setFont(g.getFont().deriveFont(Font.BOLD));
		g.drawString("Apuesta Máxima: ", 250, 170);
		g.drawString((highestBidder == null ? "-" : highestBidder), 250, 200);
		g.drawString("Cantidad: $" + highestBid, 250, 230);
		
		
		g.drawString("Ofertar: ", 255, 320);
	}
}
