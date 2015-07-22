package game;


public class Service extends Property {

	private static int[] multipliers = {4, 10};
	
	public Service(String name, int price) {
		super(name, price);
	}

	@Override
	public int getRent(int diceValue) {
		if(isMortgaged()) 
			return 0;
		int services = 0;
		for(Property property: getRelatedProperties()){
			if(property.getOwner() == getOwner() && !property.isMortgaged())
				services++;
		}
		return multipliers[services-1] * diceValue;
	}
	
	public int[] getMultipliers() {
		return multipliers;
	}

	@Override
	public int getValueToCompare() {
		return getPrice()+400;
	}
}
