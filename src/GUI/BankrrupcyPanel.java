package GUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import player.Player;

public class BankrrupcyPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Player toPay, toRecieve;
	private boolean hasEnoughMoney = true;
	
	public BankrrupcyPanel(Player toPay, Player toRecieve) {
		this.toPay = toPay;
		this.toRecieve = toRecieve;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.setFont(g.getFont().deriveFont(16f));
		if(toPay.getMoney()>=0){
			g.drawString("Ya tienes suficiente dinero.",5,20);
			g.drawString("Apreta en \"Arreglé mis deudas\" para seguir jugando.",5,45);
		}else{
			if(!hasEnoughMoney){
				g.drawString("No tienes suficiente dinero.", 5, 20);
				g.drawString("Aún le debes $" + (-toPay.getMoney()) + " a" + (toRecieve == null ? "l Banco." : (" " + toRecieve.getName())), 5, 45);
			}else{
				g.drawString("Tienes saldo negativo.",5, 20);
				g.drawString("Le debes $" + (-toPay.getMoney()) + " a" + (toRecieve == null ? "l Banco." : (" " + toRecieve.getName())), 5, 45);
			}
		}
	}
	
	public void showNotEnoughMoney(){
		hasEnoughMoney = false;
	}
}
