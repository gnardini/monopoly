package GUI;

import eda.ftw.SortedList;
import game.Card;
import game.Offer;
import game.Property;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import player.Player;

public class NegotiatePlayerPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private GeneralPanel generalPanel;
	private Player player;
	private SortedList<Property> properties;
	private List<Card> cards;
	private boolean[] selectedProperties;
	private boolean[] selectedCards;
	private JTextField moneyField;
	
	public NegotiatePlayerPanel(GeneralPanel generalPanel, Player player) {
		this.generalPanel = generalPanel;
		this.player = player;
		addMouseListener(this);
		setLayout(null);
		selectedProperties = new boolean[player.getProperties().size()];
		for(int i=0 ; i< selectedProperties.length ; i++)
			selectedProperties[i] = false;
		selectedCards = new boolean[player.amountOfGetOutOfJailCards()];
		
		moneyField = new JTextField();
		moneyField.setFont(moneyField.getFont().deriveFont(18f));
		moneyField.setBounds(800-100, 50, 80, 30);
		add(moneyField);
		properties= new SortedList<Property>();
		cards= new LinkedList<Card>();
	}
	
	@Override
	public void paint(Graphics gg) {
		super.paint(gg);
		Graphics2D g = (Graphics2D) gg;
		
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, 800, 0);
		g.setFont(g.getFont().deriveFont(20f));
		g.drawString("Dinero: ", 800-170, 72);
		
		int contador = 0;
		for(Property property: player.getProperties()){
			
			if(selectedProperties[contador]){
				g.setColor(new Color(36, 180, 120));
				g.fillRect(12 + (contador%10)*50, 7 + (contador/10) * 70, 46, 66);
			}
			
			g.setColor(Color.WHITE);
			g.fillRect(15 + (contador%10)*50, 10 + (contador/10) * 70, 40, 60);
			g.setColor(property.getColor());
			g.fillRect(15 + (contador%10)*50, 10 + (contador/10) * 70, 40, 20);
			g.setColor(Color.BLACK);
			g.drawRect(15 + (contador%10)*50, 10 + (contador/10) * 70, 40, 60);
			
			if(property.isMortgaged()){
				g.setColor(Color.RED);
				g.setFont(g.getFont().deriveFont(45f));
				g.drawString("H", 19 + (contador%10)*50, 60 + (contador/10) * 70);
			}
			
			contador++;
		}
		
		for(int i=0 ; i<player.amountOfGetOutOfJailCards() ; i++){
			if(selectedCards[i]){
				g.setColor(new Color(36, 180, 120));
				g.fillRect(800-153 + i*70, 117, 62, 42);
			}
			g.setColor(Color.WHITE);
			g.fillRect(800-150 + i*70, 120, 56, 36);
			g.setColor(Color.BLACK);
			g.drawRect(800-150 + i*70, 120, 56, 36);
			g.setFont(g.getFont().deriveFont(20f));
			g.drawString("C", 800-130 + i*70, 147);
		}
		
		moneyField.repaint();
	}

	public void completeOffer(Offer offer, int position){
		offer.setPlayer(player, position);
		
		int contador = 0;
		for(Property property: player.getProperties())
			if(selectedProperties[contador++])
				properties.add(property);
		offer.setProperties(properties, position);
		
		try{
			offer.setMoney(Integer.parseInt(moneyField.getText()), position);
		}catch(Exception e){
			offer.setMoney(0, position);
		}
		
		contador=0;
		for (Card card : player.getOutOfJailCards()) {
			if(selectedCards[contador++])
				cards.add(card);
		}
		offer.setCards(cards, position);
	}
	
	
	public void mouseClicked(MouseEvent me) {
		int x = me.getX(), y = me.getY(), position = -1;
		//System.out.println("x: " + x + "y: "  + y);
		for(int i=0; i < selectedCards.length; i++){
			if(x > 800-150 + i*70 && x < 800-150 + i*70 +56 && y > 120 && y < 156){
				selectedCards[i]=!selectedCards[i];
				getParent().repaint();
				return;
			}
				
		}
		x -= 15;
		y -= 10;
		for(int i=0 ; i < selectedProperties.length ; i++){
			if(x > (i%10)*50 && x < (i%10)*50 + 40 && y > (i/10)*70 && y < (i/10)*70 + 60){
				position = i;
				break;
			}
		}
		
		if(position == -1) return;
		if(SwingUtilities.isRightMouseButton(me)){
			generalPanel.createPropertyPanel(player.getProperties().get(position));
		}else if(SwingUtilities.isLeftMouseButton(me)){
			if(UserInterface.isFreezed())
				return;
			selectedProperties[position] = !selectedProperties[position];
		}
		
		getParent().repaint();
	}
	
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {	}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}
