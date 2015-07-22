package game;

public class Train extends Property {
	
	private static int[] rents = {25, 50, 100, 200};
	
	public Train(String name, int price) {
		super(name, price);
	}
	
	@Override
	public int getRent(int diceValue) {
		if(isMortgaged())
			return 0;
		int trains = 0;
		for(Property property: getRelatedProperties()){
			if(property.getOwner() == getOwner() && !property.isMortgaged())
				trains++;
		}
		return rents[trains-1];
	}
	
	public int[] getAllRents(){
		return rents;
	}

	@Override
	public int getValueToCompare() {
		return getPrice()+600;
	}
}
