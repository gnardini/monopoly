package game;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import player.Player;

public abstract class Property implements Comparable<Property>{
	private Player owner;
	private String name;
	private Color color;
	private int price, valueToCompare;
	private List<Property> relatedProperties;
	private boolean mortgaged;
	
	public Property(String name, int price) {
		this.name = name;
		this.price = price;
		relatedProperties = new LinkedList<Property>();
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void mortgage(){
		mortgaged = true;
		owner.addMoney(price/2);
	}
	
	public void unmortgage(){
		if(owner.getMoney()<price/2)
			throw new RuntimeException("No tienes suficiente dinero para deshipotecar");
		owner.payTo(null, price/2);
		mortgaged = false;
	}
	
	public boolean isMortgaged(){
		return mortgaged;
	}
	
	public boolean formsMonopoly(){
		for(Property property: relatedProperties)
			if(property.getOwner() != owner)
				return false;
		return true;
	}
	
	public int getPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
	
	public void setRelatedProperty(List<Property> property) {
		relatedProperties.addAll(property);
	}	
	
	protected List<Property> getRelatedProperties(){
		return relatedProperties;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Property)) return false;
		return name.equals(((Property)o).getName());
	}
	
	@Override
	public int compareTo(Property property) {
		return getValueToCompare() - property.getValueToCompare();
	}
	
	public abstract int getValueToCompare();
	
	public abstract int getRent(int diceValue);
	
	
}
