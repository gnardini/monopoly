package game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import player.Player;


//TODO: Ver que pasa cuando se pierde en el turno de otro (nada que ver con esto)

public class Deck {

	Queue<Card> cards;
	
	public Deck(){
		cards=new LinkedList<Card>();
	}
	
	private void addCard(Card c){
		cards.add(c);
	}
	
	public Card drawCard(){
		return cards.poll();
	}
	
	public static Deck createChanceDeck(){
		final Deck deck=new Deck();
		
		deck.addCard(new Card() {
			public String message() {
				return "Salga de la carcel, gratis.";
			}
			public void action(Player p, Game state){				
				p.addGetOutOfJailCard(this);
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() { 
			public String message() {
				return "Usted ha sido elegido Presidente del consejo. Pague $50 a cada uno de los jugadores.";
			}
			public void action(Player p, Game state){		
				for(Player player: state.getPlayers()){
					if(p.isPlaying())
						p.payTo(player, 50);
				}
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Avance su señal hasta el ferrocarril más cercano y pague a su dueño el doble del alquiler que le corresponde.";
			}
			public void action(Player p, Game state){
				int position = state.getBoard().getPlayerPosition(p);
				position = (position/5) * 5;
				position += position%10 == 0 ? 5 : 10;
				position%=Board.SIZE;
				state.getBoard().setPlayerPosition(p, position, true);
				
				if(state.getBoard().getProperty(position).getOwner() == null)
					state.getBoard().doAction(p, position, state);
				else{
					state.getBoard().doAction(p, position, state);
					state.getBoard().doAction(p, position, state);
				}
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Avance su señal hasta el ferrocarril más cercano y pague a su dueño el doble del alquiler que le corresponde.";
			}
			public void action(Player p, Game state){
				int position = state.getBoard().getPlayerPosition(p);
				position = (position/5) * 5;
				position += position%10 == 0 ? 5 : 10;
				position %= Board.SIZE;
				state.getBoard().setPlayerPosition(p, position, true);
				
				if(state.getBoard().getProperty(position).getOwner() == null)
					state.getBoard().doAction(p, position, state);
				else{
					state.getBoard().doAction(p, position, state);
					state.getBoard().doAction(p, position, state);
				}
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Adelantese hasta la Plaza San Carlos.";
			}
			public void action(Player p, Game state){
				state.getBoard().setPlayerPosition(p, 11, true);
				state.getBoard().doAction(p, state.getBoard().getPlayerPosition(p), state);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Háganse reparaciones Generales a todas sus propiedades. Pagará $25 por cada casa y $100 para cada hotel.";
			}
			public void action(Player p, Game state){
				int houses = 0, hotels = 0;
				for(Property property: p.getProperties()){
					if(property instanceof Street){
						Street street = (Street) property;
						houses += street.getHouses();
						hotels += street.getHotels();
					}
					
				}
				p.payTo(null, 25 * houses + 100 * hotels);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Atras! Retroceda su señal 3 espacios.";
			}
			public void action(Player p, Game state){
				state.getBoard().movePlayer(p, -3, false);
				state.getBoard().doAction(p, state.getBoard().getPlayerPosition(p), state);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Avance hasta la avenida Illinois.";
			}
			public void action(Player p, Game state){
				state.getBoard().setPlayerPosition(p, 24, true);
				state.getBoard().doAction(p, state.getBoard().getPlayerPosition(p), state);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Mueva su señal hasta la utilidad más cercana. Si tiene propietario, lance los dados y pague al propietario diez veces la suma lanzada.";
			}
			public void action(Player p, Game state){
				int position=0;
					if(state.getBoard().getPlayerPosition(p)==22){
						state.getBoard().setPlayerPosition(p, 28, true);
						position=28;
					}else{
						state.getBoard().setPlayerPosition(p, 12, true);
						position=12;
					}
					if(state.getBoard().getProperty(position).getOwner() == null){
						state.getBoard().doAction(p, position, state);
					}else{
						if(!state.getBoard().getProperty(position).isMortgaged()){
							state.rollDice();
							p.payTo(state.getBoard().getProperty(position).getOwner(), 10 * (state.getDice1Value() + state.getDice2Value()));
						}
					}
					returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Páguese para los pobres un impuesto de $15.";
			}
			public void action(Player p, Game state){	
				p.payTo(null, 15);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Vaya a caminar al Paseo Tablado.";
			}
			public void action(Player p, Game state){
				state.getBoard().setPlayerPosition(p, 39, true);
				state.getBoard().doAction(p, state.getBoard().getPlayerPosition(p), state);		
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Adelante su señal hasta la Salida. (Cobre $200)";
			}
			public void action(Player p, Game state){		
				state.getBoard().setPlayerPosition(p, 0, true);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Tomese un paseo por el F.C. Reading. Si pasa sobre Salida, cobrense $200 al banco.";
			}
			public void action(Player p, Game state){	
				state.getBoard().setPlayerPosition(p, 5, true);
				state.getBoard().doAction(p, state.getBoard().getPlayerPosition(p), state);			
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "El banco le paga un dividendo de $50.";
			}
			public void action(Player p, Game state){	
				p.addMoney(50);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Se vence el plazo de su inversión en la caja de ahorros. Cobrense $150 al banco.";
			}
			public void action(Player p, Game state){			
				p.addMoney(150);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Vaya directamente a la cárcel! (Sin cobrar $200 si se pasa por salida)";
			}
			public void action(Player p, Game state){	
				state.sendPlayerToJail(p);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		
		Collections.shuffle((List<Card>)deck.cards);
		return deck;
	}
	
	public static Deck createCommunityChest(){
		final Deck deck=new Deck();
		
		deck.addCard(new Card() {
			public String message() {
				return "Apertura de la Opera. Cobre $50 de cada jugador para pagar por sus butacas de apertura.";
			}
			public void action(Player p, Game state){				
				for(Player player: state.getPlayers())
					player.payTo(p, 50);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		
		deck.addCard(new Card() {
			public String message() {
				return "Usted ha ganado el segundo premio en un certamen de belleza. Puede cobrar $10.";
			}
			public void action(Player p, Game state){				
				p.addMoney(10);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Su hospital le exige un pago de $100.";
			}
			public void action(Player p, Game state){				
				p.payTo(null, 100);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Le toca un reembolso a cuenta de su Contribución sobre sus ingresos. Cobrar $20 del banco.";
			}
			public void action(Player p, Game state){				
				p.addMoney(20);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Usted hereda $100";
			}
			public void action(Player p, Game state){				
				p.addMoney(100);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Error del banco en favor de usted. Cobrar $200";
			}
			public void action(Player p, Game state){				
				p.addMoney(200);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Al calabozo! Vaya directamente a la Cárcel, sin pasar por \"Salida\" ni cobrar los $200.";
			}
			public void action(Player p, Game state){				
				state.sendPlayerToJail(p);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Se vence la póliza total de sus Seguros sobre la vida. El banco le pagará $100.";
			}
			public void action(Player p, Game state){				
				p.addMoney(100);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Se complicó el plazo de los ahorros para la Navidad. Pase al banco para cobrar $100.";
			}
			public void action(Player p, Game state){				
				p.addMoney(100);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Paguense al banco $50 para su médico";
			}
			public void action(Player p, Game state){				
				p.payTo(null, 50);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "El banco le pagará $45 procedente de la venta de sus acciones";
			}
			public void action(Player p, Game state){				
				p.addMoney(45);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Hay que pagar su contribución de $150 para las escuelas.";
			}
			public void action(Player p, Game state){				
				p.payTo(null, 150);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Le toca recibir $25 por servicios prestados.";
			}
			public void action(Player p, Game state){				
				p.addMoney(25);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Salga de la carcel, gratis.";
			}
			public void action(Player p, Game state){				
				p.addGetOutOfJailCard(this);
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Se impone una contribución para reaparición de calzadas. Pague $40 por cada casa y $115 por cada hotel.";
			}
			public void action(Player p, Game state){		
				int houses = 0, hotels = 0;
				for(Property property: p.getProperties()){
					if(property instanceof Street){
						Street street = (Street) property;
						houses += street.getHouses();
						hotels += street.getHotels();
					}
				}
				p.payTo(null, 40 * houses + 115 * hotels);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		deck.addCard(new Card() {
			public String message() {
				return "Avance hasta la Salida (cobrar $200)";
			}
			public void action(Player p, Game state){				
				state.getBoard().setPlayerPosition(p, 0, true);
				returnToDeck();
			}
			public void returnToDeck() {
				deck.addCard(this);
			}
		});
		
		
		Collections.shuffle((List<Card>)deck.cards);
		return deck;
	}
	
}
