package GUI;

import game.Board;
import game.Game;
import game.Offer;
import game.Property;
import game.Street;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import player.Player;
import player.PlayerInformation;
import player.User;

public class GeneralPanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	private static final int squareWidth = 66, squareHeight = 108, SIZE = 800;
	private static final Color  boardColor = new Color(204,227,199);
	private static final  Image[] dice = {Toolkit.getDefaultToolkit().getImage("assets/dice_one.png"), Toolkit.getDefaultToolkit().getImage("assets/dice_two.png"),
		Toolkit.getDefaultToolkit().getImage("assets/dice_three.png"),Toolkit.getDefaultToolkit().getImage("assets/dice_four.png"),
		Toolkit.getDefaultToolkit().getImage("assets/dice_five.png"),Toolkit.getDefaultToolkit().getImage("assets/dice_six.png")};
	private static final Image boardImage = Toolkit.getDefaultToolkit().getImage("assets/board.png");
	private final JFrame frame;
	private static int[] dx = {0, 20, 0, 20}, dy = {0, 0, 20, 20};
	private Game game;
	private JButton rollDiceButton, endTurnButton, payToGetOutOfJailButton, useCardToGetOutOfJailButton;
	private JButton[] negotiateWithPlayerButton;
	private int inactive;
	private boolean negotiating;
	
	
	public GeneralPanel(Game game, JFrame frame) {
		this.game = game;
		this.frame = frame;
		UserInterface.setMyJPanel(this);
		addButtons(frame);
		
		negotiating = false;
		inactive = 0;
	}
	
	@Override
	public void paint(Graphics gg) {
		super.paint(gg);
		Graphics2D g = (Graphics2D)gg;
		
		g.drawImage(boardImage, 0,0,800,800, this);
		List<PlayerInformation> players = game.getPlayersInformation();
		Board board = game.getBoard();
		for(int i=0 ; i<players.size() ; i++){
			if(players.get(i).isPlaying()){
				printPlayerPosition(g, players.get(i), board.getPlayerPosition(players.get(i)), players.get(i).getColor(), dx[i], dy[i]);
				showPlayerInfo(g, players.get(i), 800, i*200, players.get(i).getColor());
			}
		}
		
		printHousesAndHotels(g);
		
		g.setColor(game.getActivePlayer().getColor());
		g.fillRect(squareHeight, squareHeight - 2, SIZE - 2*squareHeight, SIZE - 2*squareHeight + 4);
		g.setColor(boardColor);
		g.fillRect(squareHeight + 10, squareHeight-2 + 10, SIZE - 2*squareHeight - 20, SIZE - 2*squareHeight+4 - 20);
		
		
		rollDiceButton.repaint();
		endTurnButton.repaint();
		payToGetOutOfJailButton.repaint();
		useCardToGetOutOfJailButton.repaint();
		//checkNegotiateButtonsVisibility();
		for(int i=0 ; i<negotiateWithPlayerButton.length ; i++)
			negotiateWithPlayerButton[i].repaint();
		if(game.getDice1Value() != 0) g.drawImage(dice[game.getDice1Value()-1], 165, 210, 32, 32, this);
		if(game.getDice2Value() != 0) g.drawImage(dice[game.getDice2Value()-1], 205, 210, 32, 32, this);
	}
	
	private void printPlayerPosition(Graphics2D g, PlayerInformation player, int position, Color color, int dx, int dy){
		g.setColor(color);
		if(position < 10)
			g.fillOval(SIZE-(squareHeight + squareWidth*position - 20 - dx), SIZE - 55 + dy, 15, 15);
		if(position >= 10 && position < 20)
			g.fillOval(20 + dx, SIZE-(squareHeight + squareWidth*(position-10) - 20 - dy), 15, 15);
		if(position >= 20 && position < 30)
			g.fillOval(squareHeight - squareWidth + 13 + squareWidth * (position-20) + dx, 25 + dy, 15, 15);
		if(position >= 30)
			g.fillOval(SIZE - 55 + dx, squareHeight - squareWidth + 13 + squareWidth * (position-30) + dy, 15, 15);
	}
	
	private void showPlayerInfo(Graphics2D g, PlayerInformation player, int x, int y, Color color){
		g.setColor(Color.BLACK);
		g.setFont(g.getFont().deriveFont(12f));
		g.drawString("Nombre: " + player.getName(), x+35, y+25);
		g.drawString("Plata: $" + player.getMoney(), x+15, y+55);
		g.drawRect(x, y, 300, 200);
		for(int i=0; i<player.amountOfGetOutOfJailCards(); i++){
			g.setColor(Color.WHITE);
			g.fillRect(x+100+i*40, y+38, 30, 20);
			g.setColor(Color.BLACK);
			g.drawRect(x+100+i*40, y+38, 30, 20);
			g.drawString("C", x+100+i*40 + 11, y+53);
		}
		int contador = 0;
		for(Property property: player.getProperties()){
			g.setColor(Color.WHITE);
			g.fillRect(x+15 + (contador%10)*25, y+75 + (contador/10) * 40, 20, 30);
			g.setColor(property.getColor());
			g.fillRect(x+15 + (contador%10)*25, y+75 + (contador/10) * 40, 20, 10);
			g.setColor(Color.BLACK);
			g.drawRect(x+15 + (contador%10)*25, y+75 + (contador/10) * 40, 20, 30);
			
			if(property.isMortgaged()){
				g.setColor(Color.RED);
				g.setFont(g.getFont().deriveFont(18f));
				g.drawString("H", x+19 + (contador%10)*25, y+99 + (contador/10) * 40);
			}
			
			contador++;
		}
		
		g.setColor(color);
		g.fillOval(x+15, y+12, 15, 15);
	}
	
	public void printHousesAndHotels(Graphics2D g){
		Property property;
		for(int i=0 ; i < 10 ; i++){
			try{
				property = game.getBoard().getProperty(i);
				if(property instanceof Street){
					Street street = (Street) property;
					for(int j=0 ; j<street.getHouses() ; j++){
						g.setColor(Color.GREEN);
						g.fillRect(SIZE-(squareHeight + squareWidth*i - 20 - dx[j]), SIZE - squareHeight + 5 + dy[j], 15, 15);
						g.setColor(Color.BLACK);
						g.drawRect(SIZE-(squareHeight + squareWidth*i - 20 - dx[j]), SIZE - squareHeight + 5 + dy[j], 15, 15);
					}
					if(street.getHotels() == 1){
						g.setColor(Color.RED);
						g.fillRect(SIZE-(squareHeight + squareWidth*i - 19), SIZE - squareHeight + 5, 35, 15);
						g.setColor(Color.BLACK);
						g.drawRect(SIZE-(squareHeight + squareWidth*i - 19), SIZE - squareHeight + 5, 35, 15);
					}
				}
			}catch(Exception e){}
		}
		for(int i=11 ; i < 20 ; i++){
			try{
				property = game.getBoard().getProperty(i);
				if(property instanceof Street){
					Street street = (Street) property;
					for(int j=0 ; j<street.getHouses() ; j++){
						g.setColor(Color.GREEN);
						g.fillRect(squareHeight - 42 + dx[j], SIZE-(squareHeight + squareWidth*(i-10) - 20 - dy[j]), 15, 15);
						g.setColor(Color.BLACK);
						g.drawRect(squareHeight - 42 + dx[j], SIZE-(squareHeight + squareWidth*(i-10) - 20 - dy[j]), 15, 15);
					}
					if(street.getHotels() == 1){
						g.setColor(Color.RED);
						g.fillRect(squareHeight - 22, SIZE-(squareHeight + squareWidth*(i-10) - 20), 15, 35);
						g.setColor(Color.BLACK);
						g.drawRect(squareHeight - 22, SIZE-(squareHeight + squareWidth*(i-10) - 20), 15, 35);
					}
				}
			}catch(Exception e){}
		}
		for(int i=21 ; i < 30 ; i++){
			try{
				property = game.getBoard().getProperty(i);
				if(property instanceof Street){
					Street street = (Street) property;
					for(int j=0 ; j<street.getHouses() ; j++){
						g.setColor(Color.GREEN);
						g.fillRect(squareHeight - squareWidth + 11 + squareWidth * (i-20) + dx[j], squareHeight - 42 + dy[j], 15, 15);
						g.setColor(Color.BLACK);
						g.drawRect(squareHeight - squareWidth + 11 + squareWidth * (i-20) + dx[j], squareHeight - 42 + dy[j], 15, 15);
					}
					if(street.getHotels() == 1){
						g.setColor(Color.RED);
						g.fillRect(squareHeight - squareWidth + 11 + squareWidth * (i-20), squareHeight - 25, 35, 15);
						g.setColor(Color.BLACK);
						g.drawRect(squareHeight - squareWidth + 11 + squareWidth * (i-20), squareHeight - 25, 35, 15);
					}
				}
			}catch(Exception e){}
		}
		for(int i=31 ; i < 40 ; i++){
			try{
				property = game.getBoard().getProperty(i);
				if(property instanceof Street){
					Street street = (Street) property;
					for(int j=0 ; j<street.getHouses() ; j++){
						g.setColor(Color.GREEN);
						g.fillRect(SIZE - squareHeight + 5 + dx[j], squareHeight - squareWidth + 13 + squareWidth * (i-30) + dy[j], 15, 15);
						g.setColor(Color.BLACK);
						g.drawRect(SIZE - squareHeight + 5 + dx[j], squareHeight - squareWidth + 13 + squareWidth * (i-30) + dy[j], 15, 15);
					}
					if(street.getHotels() == 1){
						g.setColor(Color.RED);
						g.fillRect(SIZE - squareHeight + 5, squareHeight - squareWidth + 13 + squareWidth * (i-30), 15, 35);
						g.setColor(Color.BLACK);
						g.drawRect(SIZE - squareHeight + 5, squareHeight - squareWidth + 13 + squareWidth * (i-30), 15, 35);
					}
				}
			}catch(Exception e){}
		}
		
	}
	
	/*public void checkNegotiateButtonsVisibility(){
		for(int i=0 ; i<game.getPlayerAmount() ; i++)
			if(game.getPlayer(i) != game.getActivePlayer())
				negotiateWithPlayerButton[i].setVisible(true);
			else
				negotiateWithPlayerButton[i].setVisible(false);
	}*/
	
	public void updateButtons(){
		boolean panelFreezed= UserInterface.isFreezed();
		rollDiceButton.setVisible(game.canThrowDice() && inactive==0 && !panelFreezed);
		endTurnButton.setVisible(!game.canThrowDice() && inactive==0 && !panelFreezed);
		payToGetOutOfJailButton.setVisible(game.currentPlayerIsInJail() && inactive==0 && !panelFreezed);
		useCardToGetOutOfJailButton.setVisible(game.currentPlayerIsInJail() && inactive==0 && game.currentPlayerHasGetOutOfJailCard() && !panelFreezed);
		for(int i=0 ; i<game.getPlayerAmount() ; i++)
			if(game.getPlayer(i) != game.getActivePlayer() && game.getPlayer(i).isPlaying())
				negotiateWithPlayerButton[i].setVisible(!panelFreezed && !negotiating);
			else
				negotiateWithPlayerButton[i].setVisible(false);
	
	}
	
	public void enableButtons(){
		inactive--;
		if(inactive<0)
			inactive=0;
		if(inactive==0){
			boolean panelFreezed= UserInterface.isFreezed();
			rollDiceButton.setVisible(game.canThrowDice() && !panelFreezed);
			endTurnButton.setVisible(!game.canThrowDice() && !panelFreezed);
			payToGetOutOfJailButton.setVisible(game.currentPlayerIsInJail() && !panelFreezed);
			useCardToGetOutOfJailButton.setVisible(game.currentPlayerIsInJail() && game.currentPlayerHasGetOutOfJailCard() && !panelFreezed);
			for(int i=0 ; i<game.getPlayerAmount() ; i++)
				if(game.getPlayer(i) != game.getActivePlayer() && game.getPlayer(i).isPlaying())
					negotiateWithPlayerButton[i].setVisible(!panelFreezed && !negotiating);
				else
					negotiateWithPlayerButton[i].setVisible(false);
		}
		repaint();
	}
	
	public void disenableButtons(){
		inactive++;
		rollDiceButton.setVisible(false);
		endTurnButton.setVisible(false);
		payToGetOutOfJailButton.setVisible(false);
		useCardToGetOutOfJailButton.setVisible(false);
		for(int i=0 ; i<negotiateWithPlayerButton.length ; i++){
			if(i<game.getPlayerAmount()  && game.getPlayer(i) != game.getActivePlayer() && game.getPlayer(i).isPlaying())
				negotiateWithPlayerButton[i].setVisible(!UserInterface.isFreezed() && !negotiating);
			else
				negotiateWithPlayerButton[i].setVisible(false);
		}
		repaint();
	}
	
	public void addButtons(JFrame frame){
		rollDiceButton = new JButton("Tirar Dados");
		rollDiceButton.addActionListener(new rollDiceAction());
		rollDiceButton.setBounds(150, 150, 110, 40);
		frame.add(rollDiceButton);
		
		endTurnButton = new JButton("Terminar Turno");
		endTurnButton.addActionListener(new endTurnAction());
		endTurnButton.setBounds(300,150,140,40);
		frame.add(endTurnButton);
		
		payToGetOutOfJailButton = new JButton("Pagar para Salir de la Carcel");
		payToGetOutOfJailButton.addActionListener(new payToGetOutOfJailAction());
		payToGetOutOfJailButton.setBounds(150, 280, 250, 40);
		frame.add(payToGetOutOfJailButton);
		
		useCardToGetOutOfJailButton = new JButton("Usar Tarjeta para Salir de la Carcel");
		useCardToGetOutOfJailButton.addActionListener(new useCardToGetOutOfJailAction());
		useCardToGetOutOfJailButton.setBounds(150, 350, 250, 40);
		frame.add(useCardToGetOutOfJailButton);
		
		negotiateWithPlayerButton = new JButton[game.getPlayerAmount()];
		for(int i=0 ; i<game.getPlayerAmount() ; i++){
			negotiateWithPlayerButton[i] = new JButton("Negociar");
			negotiateWithPlayerButton[i].addActionListener(new negotiateAction(i));
			negotiateWithPlayerButton[i].setBounds(982, 10 + i*200, 90, 25);
			frame.add(negotiateWithPlayerButton[i]);
		}
		enableButtons();
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
	private class rollDiceAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			game.executeMove();
			if(inactive==0){
				enableButtons();
			}
			repaint();
		}
	}
	
	private class endTurnAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			game.changeTurn();
			game.updateActivePlayer();
			if(inactive==0){
				enableButtons();
			}
			repaint();
		}
	}
	
	private class payToGetOutOfJailAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			game.payToGetCurrentPlayerOutOfJail();
			if(inactive==0){
				enableButtons();
			}
			repaint();
		}
	}
	
	private class useCardToGetOutOfJailAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			game.useCardToGetCurrentPlayerOutOfJail();
			if(inactive==0){
				enableButtons();
			}
			repaint();
		}
	}
	
	private class negotiateAction implements ActionListener {
		private int playerNumber;
		
		public negotiateAction(int playerNumber){
			this.playerNumber = playerNumber;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			createOfferPanel(game.getActualActivePlayer(), game.getActualPlayer(playerNumber));
		}
	}
	
	public void stopNegotiating(){
		negotiating = false;
	}
	
	private void setButtonsVisibility(Property property, JButton[] buttons){
		
		for(int i=1 ; i<7 ; i++)
			buttons[i].setVisible(false);
		if(!game.getActivePlayer().equals(property.getOwner())) return;
		
		if(property instanceof Street && ((Street)property).monopolyIsNotMortgaged() && !negotiating){
			Street street = (Street) property;
			
			if(street.formsMonopoly() && game.getBoard().getHousesLeft() > 0 && game.getActivePlayer().getMoney() >= street.getHouseCost() && street.getHousesAndHotels() < 4*street.getMonopolySize())
				buttons[3].setVisible(true);
			if(street.getHousesAndHotels() > 0 && street.getHousesAndHotels() <= 4*street.getMonopolySize())
				buttons[4].setVisible(true);
			if(game.getBoard().getHotelsLeft() > 0 && game.getActivePlayer().getMoney() >= street.getHouseCost() && street.getHousesAndHotels() >= 4*street.getMonopolySize() && street.getHousesAndHotels() < 5*street.getMonopolySize())
				buttons[5].setVisible(true);
			if(street.getHousesAndHotels() > 4*street.getMonopolySize())
				buttons[6].setVisible(true);
		}
		if(game.getActivePlayer().equals(property.getOwner()) && !negotiating){
			if(!property.isMortgaged()){
				if(property instanceof Street){
					if(((Street)property).getHousesAndHotels() == 0)
						buttons[1].setVisible(true);
				}else
					buttons[1].setVisible(true);
			}else{
				if(property.getPrice()/2 <= property.getOwner().getMoney())
					buttons[2].setVisible(true);
			}
		}
	}
	
	private void createOfferPanel(final Player offerPlayer, final Player askPlayer){
		negotiating = true;
		
		final JFrame frame = new JFrame("Crear Oferta");
		frame.setSize(800,500);
		frame.setAlwaysOnTop(true);
		disenableButtons();
		Point framePosition = this.frame.getLocation();
		framePosition.translate(200, 175);
		frame.setLocation(framePosition);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		final NegotiatePlayerPanel offerPanel = new NegotiatePlayerPanel(this, offerPlayer);
		final NegotiatePlayerPanel askPanel = new NegotiatePlayerPanel(this, askPlayer);
		
		offerPanel.setBounds(0,0,800,240);
		askPanel.setBounds(0, 241, 800, 259);
		offerPanel.setVisible(true);
		askPanel.setVisible(true);
		
		final JButton[] buttons = new JButton[2];
		buttons[0] = new JButton("Cerrar");
		buttons[0].setBounds(800-90, 425, 80, 40);
		buttons[0].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(!UserInterface.isFreezed()){
					negotiating = false;
					frame.dispose();
					enableButtons();
				}
			}
		});
		
		buttons[1] = new JButton("Ofertar");
		buttons[1].setBounds(800-200, 425, 100, 40);
		buttons[1].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(!UserInterface.isFreezed()){
					Offer offer = new Offer();
					offerPanel.completeOffer(offer, 0);
					askPanel.completeOffer(offer, 1);
					if(offer.isValid()){
						enableButtons();
						User.proposeOffer(offer);
						frame.dispose();
					}else{
						//TODO: mensaje lindo
					}
					
				}
			}
		});
		
		buttons[0].setVisible(true);
		buttons[1].setVisible(true);
		
		frame.add(buttons[0]);
		frame.add(buttons[1]);
		
		frame.add(askPanel);
		frame.add(offerPanel);
		
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	protected void createPropertyPanel(final Property property){
		final JFrame frame = new JFrame(property.getName());
		frame.setSize(400,450);
		frame.setAlwaysOnTop(true);
		disenableButtons();
		UserInterface.setFreezzed(true);
		Point framePosition = this.frame.getLocation();
		framePosition.translate(200, 175);
		frame.setLocation(framePosition);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		final JButton[] buttons = new JButton[7];
		buttons[0] = new JButton("Cerrar");
		buttons[0].setBounds(160, 380, 80, 30);
		buttons[0].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				frame.dispose();
				UserInterface.setFreezzed(false);
				enableButtons();
			}
		});
		buttons[0].setVisible(true);
		
		buttons[1] = new JButton("Hipotecar");
		buttons[1].setBounds(225, 140, 150, 40);
		buttons[1].setVisible(false);
		
		buttons[2] = new JButton("Deshipotecar");
		buttons[2].setBounds(225, 140, 150, 40);
		buttons[2].setVisible(false);
		
		buttons[3] = new JButton("Comprar Casas");
		buttons[3].setBounds(225, 190, 150, 40);
		buttons[3].setVisible(false);
		
		buttons[4] = new JButton("Vender Casas");
		buttons[4].setBounds(225, 240, 150, 40);
		buttons[4].setVisible(false);
		
		buttons[5] = new JButton("Comprar Hotel");
		buttons[5].setBounds(225, 190, 150, 40);
		buttons[5].setVisible(false);
		
		buttons[6] = new JButton("Vender Hotel");
		buttons[6].setBounds(225, 240, 150, 40);
		buttons[6].setVisible(false);
		
		setButtonsVisibility(property, buttons);
		
		buttons[1].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				property.mortgage();
				setButtonsVisibility(property, buttons);
				Window[] windows = Frame.getOwnerlessWindows();
				for (int i = 0; i < windows.length; i++) {
				    windows[i].repaint();
				}
			}
		});
		buttons[2].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				property.unmortgage();
				setButtonsVisibility(property, buttons);
				Window[] windows = Frame.getOwnerlessWindows();
				for (int i = 0; i < windows.length; i++) {
				    windows[i].repaint();
				}
			}
		});
		buttons[3].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				((Street)property).buyHouse(game);
				setButtonsVisibility(property, buttons);
				repaint();
			}
		});
		buttons[4].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				((Street)property).sellHouse(game);
				setButtonsVisibility(property, buttons);
				repaint();
			}
		});
		buttons[5].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				((Street)property).buyHotel(game);
				setButtonsVisibility(property, buttons);
				repaint();
			}
		});
		buttons[6].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				((Street)property).sellHotel(game);
				setButtonsVisibility(property, buttons);
				repaint();
			}
		});
		
		frame.add(buttons[0]);
		frame.add(buttons[1]);
		frame.add(buttons[2]);
		frame.add(buttons[3]);
		frame.add(buttons[4]);
		frame.add(buttons[5]);
		frame.add(buttons[6]);
		
		PropertyPanel pp = new PropertyPanel(property, buttons);
		
		frame.add(pp);
		frame.setResizable(false);
		
		pp.setVisible(true);
		frame.setVisible(true);
	}
	
	public void mouseClicked(MouseEvent me) {
		if(UserInterface.isFreezed()) return;
		int x = me.getX(), y = me.getY(), position = -1;
		y -= 29; x -= 5;
		//System.out.println("x: " + x + "y: "  + y);
		if(x<SIZE && y<SIZE){
			if(y < squareHeight && x > squareHeight && x < SIZE - squareHeight)
				position = (x-squareHeight) / squareWidth + 21;
			if(y > SIZE - squareHeight && x > squareHeight && x < SIZE - squareHeight)
				position = - (x-squareHeight) / squareWidth + 9;
			if(x < squareHeight && y > squareHeight && y < SIZE - squareHeight)
				position = - (y-squareHeight) / squareWidth + 19;
			if(x > SIZE - squareHeight && y > squareHeight && y < SIZE - squareHeight)
				position = (y-squareHeight) / squareWidth + 31;
			try{
				createPropertyPanel(game.getBoard().getProperty(position));
			}catch(Exception e){}
		}else if(x > SIZE + 15){
			y -= 3; x -= 4;
			int playerNumber = y/200;
			y %= 200;
			x -= (SIZE+15);
			for(int i= 0 ; i<3 ; i++)
				if(y > 75+(i*40) && y < 75+(i*40)+30){
					for(int j=0 ; j<10 ; j++)
						if(x >= j*25 && x< j*25 + 20)
							position = (i*10) + j;
				}
			try{
				PlayerInformation player = game.getPlayersInformation().get(playerNumber);
				createPropertyPanel(player.getProperties().get(position));
			}catch(Exception e){}
		}
	}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}








