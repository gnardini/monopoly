package game;


public class Street extends Property {

	private int houseCost, houses;
	private int[] rent;
	
	public Street(String name, int price, int h0, int h1, int h2, int h3, int h4, int hotel, int houseCost) {
		super(name, price);
		rent = new int[6];
		rent[0] = h0;
		rent[1] = h1;
		rent[2] = h2;
		rent[3] = h3;
		rent[4] = h4;
		rent[5] = hotel;
		this.houseCost = houseCost;
	}
	
	public int getRent(int diceValue){
		if(isMortgaged()) 
			return 0;
		if(formsMonopoly() && monopolyIsNotMortgaged())
			return (houses==0?rent[0]*2:rent[houses]);
		return rent[0];
	}
	
	public void putHouse(Game game){
		houses++;
		getOwner().payTo(null, houseCost);
		game.getBoard().takeHouse();
	}
	
	public void putHotel(Game game){
		houses++;
		getOwner().payTo(null, houseCost);
		game.getBoard().takeHotel();
		game.getBoard().returnHouses(4);
	}
	
	public void takeHouse(Game game){
		houses--;
		getOwner().addMoney(houseCost/2);
		game.getBoard().returnHouse();
	}
	
	public void takeHotel(Game game){
		houses--;
		getOwner().addMoney(houseCost/2);
		game.getBoard().takeHouses(4);
		
		while(game.getBoard().getHousesLeft() < 0){
			if(getHousesAndHotels() > 4*getMonopolySize())
				sellHotel(game);
			else
				sellHouse(game);
		}
		
	}
	
	public void buyHouse(Game game){
		if(!canBuyHouse(game)) 
			throw new RuntimeException("No podes comprar una casa.");
		for(Property property: getRelatedProperties()){
			Street street = (Street) property;
			if(street.getHouses() < getHouses()){
				street.putHouse(game);
				return;
			}
		}
		putHouse(game);
	}
	
	public void sellHouse(Game game){
		if(!canSellHouse(game)) 
			throw new RuntimeException("No podes vender una casa.");
		for(Property property: getRelatedProperties()){
			Street street = (Street) property;
			if(street.getHouses() > getHouses()){
				street.takeHouse(game);
				return;
			}
		}
		takeHouse(game);
	}
	
	public void buyHotel(Game game){
		if(!canBuyHotel(game)) 
			throw new RuntimeException("No podes comprar un hotel.");
		for(Property property: getRelatedProperties()){
			Street street = (Street) property;
			if(street.getHotels() < getHotels()){
				street.putHotel(game);
				return;
			}
		}
		putHotel(game);
	}
	
	public void sellHotel(Game game){
		if(!canSellHotel(game)) 
			throw new RuntimeException("No podes vender un hotel.");
		for(Property property: getRelatedProperties()){
			Street street = (Street) property;
			if(street.getHotels() > getHotels()){
				street.takeHotel(game);
				return;
			}
		}
		takeHotel(game);
	}
	
	public boolean canBuyHouse(Game game){
		return game.getCurrentPlayer().equals(getOwner()) && formsMonopoly() && game.getBoard().getHousesLeft() > 0 && getOwner().getMoney() >= getHouseCost() && getHousesAndHotels() < 4*getMonopolySize();
	}
	
	public boolean canSellHouse(Game game){
		return game.getCurrentPlayer().equals(getOwner()) && getHousesAndHotels() > 0 && getHousesAndHotels() <= 4*getMonopolySize();
	}
	
	public boolean canBuyHotel(Game game){
		return game.getCurrentPlayer().equals(getOwner()) && game.getBoard().getHotelsLeft() > 0 && game.getCurrentPlayer().getMoney() >= getHouseCost() && getHousesAndHotels() >= 4*getMonopolySize() && getHousesAndHotels() < 5*getMonopolySize();
	}
	
	public boolean canSellHotel(Game game){
		return game.getCurrentPlayer().equals(getOwner()) && getHousesAndHotels() > 4*getMonopolySize();
	}
	
	public boolean monopolyIsNotMortgaged(){
		for(Property property: getRelatedProperties()){
			if(property.isMortgaged())
				return false;
		}
		return true;
	}
	
	public int[] getAllRents(){
		return rent;
	}
	
	public int getHouseCost(){
		return houseCost;
	}
	
	public int getHouses() {
		return houses < 5 ? houses : 0;
	}
	
	public int getHousesAndHotels(){
		int houses = 0;
		for(Property property: getRelatedProperties()){
			Street street = (Street) property;
			houses += street.getHouses() + street.getHotels()*5;
		}
		return houses;
	}
	
	public int getMonopolySize(){
		return getRelatedProperties().size();
	}
	
	public int getHotels() {
		return houses == 5 ? 1 : 0;
	}

	@Override
	public int getValueToCompare() {
		return getPrice();
	}
}
