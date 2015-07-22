package GUI;

import game.Auction;
import game.Card;
import game.Game;
import game.Offer;
import game.Property;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import player.Player;
import player.User;

public class UserInterface {
	
	private static GeneralPanel generalPanel;
	private static int freezed=0;
	
	public static void buyProperty(final Player player, final Property property){
		generalPanel.disenableButtons();
		final JFrame frame = new JFrame("Comprar Propiedad");
		frame.setSize(400,500);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setResizable(false);
		Point framePosition = generalPanel.getFrame().getLocation();
		framePosition.translate(200, 175);
		frame.setLocation(framePosition);
		
		JButton[] buttons = new JButton[2];
		buttons[0] = new JButton("Comprar");
		buttons[1] = new JButton("Subastar");
		buttons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(player.getMoney() >= property.getPrice()){
					User.buyProperty(player, property);
					UserInterface.generalPanel.enableButtons();
					frame.dispose();	
				}else{
					JFrame internalFrame= new JFrame("Mensaje");
					internalFrame.setSize(250,80);
					internalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);					
					internalFrame.setVisible(true);
					internalFrame.setResizable(false);
					internalFrame.setAlwaysOnTop(true);
					Point framePosition = frame.getLocation();
					framePosition.translate(50, 400);
					internalFrame.setLocation(framePosition);
					String[] message = {"No tienes suficiente dinero."};
					JPanel internalPanel = new TextPanel(message);
					internalPanel.setBounds(0, 0, 250, 80);
					internalPanel.setVisible(true);
					internalFrame.add(internalPanel);
					internalPanel.repaint();
				}
			}
		});
		buttons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				User.auction(property);
				UserInterface.generalPanel.enableButtons();
				frame.dispose();
			}
		});
		buttons[0].setBounds(65, 405,120,40);
		buttons[0].setVisible(true);
		buttons[1].setBounds(205, 405,120,40);
		buttons[1].setVisible(true);
		
		JPanel jpanel = new PropertyPanel(property, buttons);
		jpanel.setLayout(null);
		jpanel.setVisible(true);
		jpanel.setBounds(0, 0, 400, 500);
		frame.add(jpanel);
		jpanel.repaint();
		
		frame.add(buttons[0]);
		frame.add(buttons[1]);
	}
	
	public static void showCard(final Player player, final Game state, final Card card){
		generalPanel.disenableButtons();
		final JFrame frame = new JFrame("Carta");
		frame.setSize(400,300);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setResizable(false);
		Point framePosition = generalPanel.getFrame().getLocation();
		framePosition.translate(200, 175);
		frame.setLocation(framePosition);
		
		JButton acceptButton = new JButton("Aceptar");
		acceptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				UserInterface.generalPanel.enableButtons();
				User.acceptCard(player, state, card);
				frame.dispose();
			}
		});
		acceptButton.setBounds(140, 200,120,40);
		acceptButton.setVisible(true);
		frame.add(acceptButton);
		acceptButton.repaint();
		
		JPanel cardPanel = new CardPanel(card);
		cardPanel.setLayout(null);
		cardPanel.setVisible(true);
		cardPanel.setBounds(0, 0, 400, 300);
		frame.add(cardPanel);
		cardPanel.repaint();
		
	}
	
	public static void showNegativeMoney(final Player toPay, final Player toRecieve){
		generalPanel.disenableButtons();
		final JFrame frame = new JFrame("Alerta de Bancarrota");
		frame.setSize(400, 200);
		frame.setLayout(null);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Point framePosition = generalPanel.getFrame().getLocation();
		framePosition.translate(200, 175);
		frame.setLocation(framePosition);
		
		final BankrrupcyPanel bp = new BankrrupcyPanel(toPay, toRecieve);
		bp.setBounds(0,0,400,200);
		bp.setVisible(true);
		
		final JButton bankrruptButton = new JButton("Declarar Bancarrota");
		final JButton keepPlayingButton = new JButton("Arreglé mis deudas");
		bankrruptButton.setBounds(5, 110, 180, 40);
		keepPlayingButton.setBounds(200, 110, 180, 40);
		
		bankrruptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(toPay.getMoney()<0){
					frame.dispose();
					//generalPanel.enableButtons();
					User.playerInBankrrupcyBy(toPay,toRecieve);
					//generalPanel.repaint();
					generalPanel.enableButtons();
				}else{
					bp.repaint();
					SwingUtilities.invokeLater(new Runnable(){
						@Override
						public void run() {
							bankrruptButton.repaint();
							keepPlayingButton.repaint();
						}
					});
				}
					
			}
		});
		keepPlayingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(toPay.getMoney() >= 0){
					frame.dispose();
					//generalPanel.enableButtons();
					User.bankrrupcyCaseSolved();
					//generalPanel.repaint();
					generalPanel.enableButtons();
				}else{
					bp.showNotEnoughMoney();
					bp.repaint();
					SwingUtilities.invokeLater(new Runnable(){
						@Override
						public void run() {
							bankrruptButton.repaint();
							keepPlayingButton.repaint();
						}
					});
				}				
			}
		});
		bankrruptButton.setVisible(true);
		keepPlayingButton.setVisible(true);
		frame.add(bankrruptButton);
		frame.add(keepPlayingButton);
		frame.add(bp);
		
		frame.setVisible(true);
	}
	
	public static void askTaxAnswer(final Player player, final Game state) {
		generalPanel.disenableButtons();
		final JFrame frame = new JFrame("Impuesto a las ganancias");
		frame.setSize(400, 200);
		frame.setLayout(null);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Point framePosition = generalPanel.getFrame().getLocation();
		framePosition.translate(200, 175);
		frame.setLocation(framePosition);
		frame.setVisible(true);
		String[] message = {"Hay que pagar impuestos a las ganancias.", "Puede pagar $200 o el 10% de su capital."};
		final TextPanel bp = new TextPanel(message);
		bp.setBounds(0,0,400,200);
		bp.setVisible(true);
		
		final JButton[] buttons = new JButton[2];
		buttons[0] = new JButton("Pagar $200.");
		buttons[1] = new JButton("Pagar 10% del capital.");
		buttons[0].setBounds(5, 110, 180, 40);
		buttons[1].setBounds(200, 110, 180, 40);
		
		buttons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				User.payTaxConstant(player, state);
				generalPanel.enableButtons();
				frame.dispose();
			}
		});
		buttons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				User.payTaxPercentage(player, state);
				generalPanel.enableButtons();
				frame.dispose();		
			}
		});
		bp.repaint();
		buttons[0].setVisible(true);
		buttons[1].setVisible(true);
		frame.add(buttons[0]);
		frame.add(buttons[1]);
		frame.add(bp);
	}
	
	public static void auctionProperty(final Auction auction, Property property, String highestBidder, int highestBid){
		//TODO: poner carteles de bidders y eso
		generalPanel.disenableButtons();
		final JFrame frame = new JFrame("Subasta");
		frame.setSize(400,500);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setResizable(false);
		Point framePosition = generalPanel.getFrame().getLocation();
		framePosition.translate(200, 175);
		frame.setLocation(framePosition);
		
		
		final JTextField moneyField = new JTextField();
		moneyField.setFont(moneyField.getFont().deriveFont(18f));
		moneyField.setBounds(320, 300, 60, 30);
		frame.add(moneyField);
		
		
		
		JButton[] buttons = new JButton[2];
		buttons[0] = new JButton("Ofrecer");
		buttons[1] = new JButton("Pasar");
		buttons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				UserInterface.generalPanel.enableButtons();
				frame.dispose();
				try{
					auction.raiseBid(Integer.valueOf(moneyField.getText()));
				}catch(Exception e){
					auction.passBid();
				}
				
			}
		});
		buttons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				UserInterface.generalPanel.enableButtons();
				frame.dispose();
				auction.passBid();
			}
		});
		buttons[0].setBounds(65, 405,120,40);
		buttons[0].setVisible(true);
		buttons[1].setBounds(205, 405,120,40);
		buttons[1].setVisible(true);
		
		JPanel jpanel = new AuctionPanel(property, buttons, highestBidder, highestBid);
		jpanel.setLayout(null);
		jpanel.setVisible(true);
		jpanel.setBounds(0, 0, 400, 500);
		frame.add(jpanel);
		jpanel.repaint();
		
		frame.add(buttons[0]);
		frame.add(buttons[1]);
	}
	
	public static void setMyJPanel(GeneralPanel gp){
		generalPanel = gp;
	}

	
	public static void askAcceptOffer(final Offer offer) {
		generalPanel.disenableButtons();
		final JFrame frame = new JFrame("Propuesta de oferta");
		frame.setSize(600, 640);
		frame.setLayout(null);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Point framePosition = generalPanel.getFrame().getLocation();
		framePosition.translate(200, 175);
		frame.setLocation(framePosition);
		OfferPanel panelOffer = new OfferPanel(offer,generalPanel);
		panelOffer.setVisible(true);
		JButton acceptButton, declineButton;
		acceptButton=new JButton("Aceptar");
		acceptButton.setBounds(380, 560, 100, 40);
		acceptButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!UserInterface.isFreezed()){
					User.acceptOffer(offer);
					generalPanel.stopNegotiating();
					generalPanel.enableButtons();
					frame.dispose();
				}
			}
		});
		declineButton = new JButton("Rechazar");
		declineButton.setBounds(490, 560, 100, 40);
		declineButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!UserInterface.isFreezed()){
					User.declineOffer(offer);
					generalPanel.stopNegotiating();
					generalPanel.enableButtons();
					frame.dispose();
				}
			}
		});
		acceptButton.setVisible(true);
		declineButton.setVisible(true);
		frame.add(acceptButton);
		frame.add(declineButton);
		frame.add(panelOffer);
		frame.setVisible(true);
	}
	
	public static void showPlayerLost(Player player){
		//TODO: ventana
	}
	
	public static void endAuction(){
		generalPanel.enableButtons();
	}
	
	public static void setFreezzed(boolean freeze) {
		if(freeze)
			freezed++;
		else
			freezed--;
		if(freezed<0)
			freezed=0;
		generalPanel.updateButtons();
	}
	
	public static boolean isFreezed() {
		return freezed != 0;
	}
}
