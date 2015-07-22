package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import player.Player;
import player.PlayerInformation;
import square.CardsSquare;
import square.GoToJailSquare;
import square.LuxuryTaxSquare;
import square.NeutralSquare;
import square.PropertySquare;
import square.Square;
import square.TaxSquare;


public class Board {
	protected final static int SIZE = 40;
	
	private final List<Square> board;
	private Map<Integer, Integer> playerPositions;
	private int housesLeft, hotelsLeft;

	
	public void doStuff(Player p1, Player p2, Player p3){
		
		int[] numeros = {1, 3, 6, 8,9,11,13,14};
		Street street;		
		
		p1.buyProperty(((PropertySquare)board.get(6)).getProperty());
		p2.buyProperty(((PropertySquare)board.get(8)).getProperty());
		p3.buyProperty(((PropertySquare)board.get(9)).getProperty());
		
//		for(int i=0 ; i<numeros.length ; i++){
//			street = (Street)((PropertySquare)board.get(numeros[i])).getProperty();
//			p1.buyProperty(street);
//		}
//			Property prop = ((PropertySquare)board.get(5)).getProperty();
//			p2.buyProperty(prop);
		
	}
	
	public Board() {
		board = new ArrayList<Square>(SIZE);
		playerPositions = new HashMap<Integer, Integer>();
		setBoardSquares();
		housesLeft = 32;
		hotelsLeft = 12;
	}
	
	public void doAction(Player player, int position, Game game){
		board.get(position).action(player, game);
	}
	
	public void movePlayer(Player player, int squaresToMove, boolean canCollect){
		int pos = playerPositions.get(player.getID());
		pos = (pos+squaresToMove)%SIZE;
		setPlayerPosition(player, pos, canCollect);
	}
	
	public int getPlayerPosition(Player player){
		return playerPositions.get(player.getID());
	}
	
	public int getPlayerPosition(PlayerInformation player){
		return playerPositions.get(player.getID());
	}
	
	public void setPlayerPosition(Player player, int position, boolean canCollect){
		int currentPosition = playerPositions.get(player.getID());
		playerPositions.put(player.getID(), position);
		if(canCollect && position < currentPosition)
			player.addMoney(200);
	}
	
	public void addPlayers(List<Player> players){
		for(int i=0 ; i<players.size() ; i++){
			playerPositions.put(players.get(i).getID(), 0);
		}
	}
	
	public Property getProperty(int position){
		return ((PropertySquare)board.get(position)).getProperty();
	}
	
	public int getHousesLeft(){
		return housesLeft;
	}
	
	public int getHotelsLeft() {
		return hotelsLeft;
	}
	
	public void takeHouse(){
		//if(housesLeft == 0) throw new RuntimeException("No hay mas casas");
		housesLeft--;
	}
	
	public void takeHouses(int n){
		System.out.println(getHousesLeft());
		housesLeft -= n;
	}
	
	public void takeHotel(){
		//if(hotelsLeft == 0) throw new RuntimeException("No hay mas hoteles");
		hotelsLeft--;
	}
	
	public void returnHouse(){
		housesLeft++;
	}
	
	public void returnHouses(int n){
		housesLeft += n;
	}
	
	public void returnHotel(){
		hotelsLeft++;
	}
	
	private void setBoardSquares() {
		Deck chance = Deck.createChanceDeck();
		Deck community = Deck.createCommunityChest();
		board.add(0, new NeutralSquare("Start"));
		board.add(1, new PropertySquare("Av. Mediterraneo",new Street("Av. Mediterranea",60,2,10,30,90,160,250,50)));
		board.add(2, new CardsSquare("Arca Comunal", community));
		board.add(3, new PropertySquare("Av. Báltica",new Street("Av. Baltica",60,4,20,60,180,320,450,50)));
		board.add(4, new TaxSquare("Impuestos a las ganancias"));
		board.add(5, new PropertySquare("Ferrocarril Reading", new Train("Ferrocarril Reading",200)));
		board.add(6, new PropertySquare("Av. Oriental",new Street("Av. Oriental",100,6,30,90,270,400,550,50)));
		board.add(7, new CardsSquare("Suerte", chance));
		board.add(8, new PropertySquare("Av. Vermont",new Street("Av. Vermont",100,6,30,90,270,400,550,50)));
		board.add(9, new PropertySquare("Av. Connecticut",new Street("Av. Connecticut",120,8,40,100,300,450,600,50)));
		board.add(10, new NeutralSquare("Carcel"));
		board.add(11, new PropertySquare("Av. San Carlos",new Street("Av. San Carlos",140,10,50,150,450,625,750,100)));
		board.add(12, new PropertySquare("Electricidad", new Service("Electricidad",150)));
		board.add(13, new PropertySquare("Av. De Los Estados",new Street("Av. De Los Estados",140,10,50,150,450,625,750,100)));
		board.add(14, new PropertySquare("Av. Virginia",new Street("Av. Virginia",160,12,60,180,500,700,900,100)));
		board.add(15, new PropertySquare("Ferrocarril Pensilvania", new Train("Ferrocarril Pensilvania",200)));
		board.add(16, new PropertySquare("Av. Santiago",new Street("Av. Santiago",180,14,70,200,550,750,950,100)));
		board.add(17, new CardsSquare("Arca Comunal", community));
		board.add(18, new PropertySquare("Av. Tennessee",new Street("Av. Tennessee",180,14,70,200,550,750,950,100)));
		board.add(19, new PropertySquare("Av. Nueva York",new Street("Av. Nueva York",200,16,80,220,600,800,1000,100)));
		board.add(20, new NeutralSquare("Estacionamiento"));
		board.add(21, new PropertySquare("Av. Kentucky",new Street("Av. Kentucky",220,18,90,250,700,875,1050,150)));
		board.add(22, new CardsSquare("Suerte", chance));
		board.add(23, new PropertySquare("Av. Indiana",new Street("Av. Indiana",220,18,90,250,700,875,1050,150)));
		board.add(24, new PropertySquare("Av. Illinois",new Street("Av. Illinois",240,20,100,300,750,925,1100,150)));
		board.add(25, new PropertySquare("Ferrocarril B&O", new Train("Ferrocarril B&O",200)));
		board.add(26, new PropertySquare("Av. Atlántico",new Street("Av. Atlantico",260,22,110,330,800,975,1150,150)));
		board.add(27, new PropertySquare("Av. Ventor",new Street("Av. Ventor",260,22,110,330,800,975,1150,150)));
		board.add(28, new PropertySquare("Agua", new Service("Agua",150)));
		board.add(29, new PropertySquare("Jardines Marvin",new Street("Jardines Marvin",280,24,120,360,850,1025,1200,150)));
		board.add(30, new GoToJailSquare("Ir a la Cárcel"));
		board.add(31, new PropertySquare("Av. Pacífico",new Street("Av. Pacifico",300,26,130,390,900,1100,1275,200)));
		board.add(32, new PropertySquare("Av. Carolina del Norte",new Street("Av. Carolina del Norte",300,26,130,390,900,1100,1275,200)));
		board.add(33, new CardsSquare("Arca Comunal", community));
		board.add(34, new PropertySquare("Av. Pensilvania",new Street("Av. Pensilvania",320,28,150,450,1000,1200,1400,200)));
		board.add(35, new PropertySquare("Ferrocarril Short Line", new Train("Ferrocarril Short Line",200)));
		board.add(36, new CardsSquare("Suerte", chance));
		board.add(37, new PropertySquare("Plaza del Parque",new Street("Plaza del Parque",350,35,175,500,1100,1300,1500,200)));
		board.add(38, new LuxuryTaxSquare("Impuesto de Lujo"));
		board.add(39, new PropertySquare("Paseo Tablado",new Street("Paseo Tablado",400,50,200,600,1400,1700,2000,200)));

		
		loadMonopoly(1, 3, new Color(134,76,56));
		loadMonopoly(6,8,9, new Color(173,220,240));
		loadMonopoly(11,13,14, new Color(197,56,132));
		loadMonopoly(16,18,19, new Color(236,139,44));
		loadMonopoly(21,23,24, new Color(219,36,40));
		loadMonopoly(26,27,29, new Color(255,240,4));
		loadMonopoly(31,32,34, new Color(19,168,87));
		loadMonopoly(37,39, new Color(0,102,164));
		loadMonopoly(5,15,25,35, Color.BLACK);
		loadMonopoly(12,28, Color.GRAY);
		
	}
	
	private void loadMonopoly(int i, int j, Color color){
		List<Property> monopolies = new LinkedList<Property>();
		monopolies.add(((PropertySquare)board.get(i)).getProperty());
		monopolies.add(((PropertySquare)board.get(j)).getProperty());
		((PropertySquare)board.get(i)).getProperty().setRelatedProperty(monopolies);
		((PropertySquare)board.get(j)).getProperty().setRelatedProperty(monopolies);
		((PropertySquare)board.get(i)).getProperty().setColor(color);
		((PropertySquare)board.get(j)).getProperty().setColor(color);
	}
	
	private void loadMonopoly(int i, int j, int k, Color color){
		List<Property> monopolies = new LinkedList<Property>();
		monopolies.add(((PropertySquare)board.get(i)).getProperty());
		monopolies.add(((PropertySquare)board.get(j)).getProperty());
		monopolies.add(((PropertySquare)board.get(k)).getProperty());
		((PropertySquare)board.get(i)).getProperty().setRelatedProperty(monopolies);
		((PropertySquare)board.get(j)).getProperty().setRelatedProperty(monopolies);
		((PropertySquare)board.get(k)).getProperty().setRelatedProperty(monopolies);
		((PropertySquare)board.get(i)).getProperty().setColor(color);
		((PropertySquare)board.get(j)).getProperty().setColor(color);
		((PropertySquare)board.get(k)).getProperty().setColor(color);
	}
	
	private void loadMonopoly(int i, int j, int k, int l, Color color){
		List<Property> monopolies = new LinkedList<Property>();
		monopolies.add(((PropertySquare)board.get(i)).getProperty());
		monopolies.add(((PropertySquare)board.get(j)).getProperty());
		monopolies.add(((PropertySquare)board.get(k)).getProperty());
		monopolies.add(((PropertySquare)board.get(l)).getProperty());
		((PropertySquare)board.get(i)).getProperty().setRelatedProperty(monopolies);
		((PropertySquare)board.get(j)).getProperty().setRelatedProperty(monopolies);
		((PropertySquare)board.get(k)).getProperty().setRelatedProperty(monopolies);
		((PropertySquare)board.get(l)).getProperty().setRelatedProperty(monopolies);
		((PropertySquare)board.get(i)).getProperty().setColor(color);
		((PropertySquare)board.get(j)).getProperty().setColor(color);
		((PropertySquare)board.get(k)).getProperty().setColor(color);
		((PropertySquare)board.get(l)).getProperty().setColor(color);
	}

}
