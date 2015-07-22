package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import game.Offer;
import game.Property;

import javax.swing.JPanel;

public class OfferPanel extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Offer offer;
	private GeneralPanel generalPanel;
	private static int HEIGHT=600, WIDTH=600;
	
	public OfferPanel(Offer offer, GeneralPanel generalPanel){
		this.offer=offer;
		this.generalPanel=generalPanel;
		addMouseListener(this);
		setBounds(0, 0, WIDTH,HEIGHT+40);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for(int i=0; i<2; i++){
			g.setColor(Color.BLACK);
			g.setFont(g.getFont().deriveFont(18f));
			g.drawString((i==0?"Entregado por ":"Solicitado de ")+ offer.getPlayers()[i].getName() ,15,(i*HEIGHT/2)+30);
			g.drawString("Dinero: $"+offer.getMoney()[i],15,(i*HEIGHT/2)+60);
			for(int j=0; j<offer.getCards()[i].size(); j++){
				g.setColor(Color.WHITE);
				g.fillRect(400+j*66, 20+(i*HEIGHT/2), 56, 36);
				g.setColor(Color.BLACK);
				g.drawRect(400+j*66, 20+(i*HEIGHT/2), 56, 36);
				g.setFont(g.getFont().deriveFont(30f));
				g.drawString("C", 400+j*66 + 18, 20+(i*HEIGHT/2) + 28);
				//TODO: probar con dos cartas
			}
			int contador=0;
			for(Property property : offer.getProperties()[i]){
				g.setColor(Color.WHITE);
				g.fillRect(15+(contador%10)*50, 75+(contador/10)*70+(i*HEIGHT/2), 40, 60);
				g.setColor(property.getColor());
				g.fillRect(15+(contador%10)*50, 75+(contador/10)*70+(i*HEIGHT/2), 40, 20);
				g.setColor(Color.BLACK);
				g.drawRect(15+(contador%10)*50, 75+(contador/10)*70+(i*HEIGHT/2), 40, 60);
				if(property.isMortgaged()){
					g.setColor(Color.RED);
					g.setFont(g.getFont().deriveFont(45f));
					g.drawString("H", 19 + (contador%10)*50, 120 + (contador/10) * 70+(i*HEIGHT/2));
				}
				contador++;
			}
		}
		g.setColor(Color.BLACK);
		g.drawLine(0,HEIGHT/2,WIDTH,HEIGHT/2);
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		if(UserInterface.isFreezed())
			return;
		int x=me.getX(), y=me.getY(), index=0, position=-1;
		Property property;
		if(y>HEIGHT/2){
			y-=HEIGHT/2;
			index=1;
		}
		for(int contador=0; contador<offer.getProperties()[0].size(); contador++){
			if(x>15+(contador%10)*50 && x<15+(contador%10)*50+40 && y>75+(contador/10)*70 && y<75+(contador/10)*70+60){
				position=contador;
				break;
			}				
		}
		try{
			property=offer.getProperties()[index].get(position);
			generalPanel.createPropertyPanel(property);
		}catch(Exception e){}
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
