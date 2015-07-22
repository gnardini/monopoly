package GUI;

import game.Property;
import game.Service;
import game.Street;
import game.Train;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PropertyPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Property property;
	private JButton[] buttons;
	
	public PropertyPanel(Property property, JButton[] buttons){
		this.property=property;
		this.buttons = buttons;
	}
	
	@Override
	public void paint(Graphics gg) {
		super.paint(gg);
		Graphics2D g= (Graphics2D) gg;
		
		g.setColor(property.getColor());
		g.fillRect(0,0,400,40);
		
		g.setColor(Color.BLACK);
		g.setFont(g.getFont().deriveFont(16f));
		g.drawString("Propiedad: " + property.getName(), 40, 60);
		g.drawString("Propietario: " + (property.getOwner()!=null?property.getOwner().getName():"Banco"), 40, 90);
		g.drawString("Precio: $" + property.getPrice(), 250, 90);
		if(property instanceof Street)
			paint(g, (Street)property);
		if(property instanceof Train)
			paint(g, (Train)property);
		if(property instanceof Service)
			paint(g, (Service)property);
		
		for(int i=0 ; i<buttons.length ; i++)
			buttons[i].repaint();
	}
	
	private void paint(Graphics2D g, Street street){
		int[] rent = street.getAllRents();
		g.drawString("Alquiler: $" + rent[0], 40, 140);
		g.drawString("Una casa: $" + rent[1], 40, 170);
		g.drawString("Dos casas: $" + rent[2], 40, 200);
		g.drawString("Tres casas: $" + rent[3], 40, 230);
		g.drawString("Cuatro casas: $" + rent[4], 40, 260);
		g.drawString("Hotel: $" + rent[5], 40, 290);
		
		g.drawString("Costo de casas: $" + street.getHouseCost(), 40, 330);
		g.drawString("Valor hipotecario: $" + street.getPrice()/2, 40, 370);
	}
	
	private void paint(Graphics2D g, Train train){
		int rent[] = train.getAllRents();
		g.drawString("Un Tren: $" + rent[0], 40, 140);
		g.drawString("Dos Trenes: $" + rent[1], 40, 170);
		g.drawString("Tres Trenes: $" + rent[2], 40, 200);
		g.drawString("Cuatro Trenes: $" + rent[3], 40, 230);
		
		g.drawString("Valor hipotecario: $" + train.getPrice()/2, 40, 270);
	}
	
	private void paint(Graphics2D g, Service service){
		int[] multipliers = service.getMultipliers();
		g.drawString("Un Servicio: " + multipliers[0] + " veces", 40, 140);
		g.drawString( "Lo que indican los Dados", 40, 165);
		g.drawString("Dos Servicios: " + multipliers[1] + " veces", 40, 200);
		g.drawString("Lo que indican los Dados", 40, 225);
		
		g.drawString("Valor hipotecario: $" + service.getPrice()/2, 40, 280);
	}
}
