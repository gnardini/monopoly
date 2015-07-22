package player;

import java.awt.Color;
import java.util.List;

import bot.Bot;
import eda.ftw.SortedList;
import game.Card;
import game.Property;

public class Player {
	private Bot mBot;
	private final String mName;
	private int mMoney;
	private int mID;
	private Color mColor;
	private final SortedList<Property> mProperties;
	private InJail mInJail;
	private boolean mIsPlaying = true;
	
	public Player(String name, int money, Color color, Bot bot) {
		this.mName = name;
		this.mMoney = money;
		this.mColor = color;
		mBot = bot;
		
		mInJail = new InJail();
		mProperties = new SortedList<Property>();
	}
	
	public void setID(int id){
		this.mID = id;
	}
	
	public void addMoney(int n){
		if(n<0) throw new RuntimeException("No se puede agregar una cantidad negativa de dinero.");
		mMoney += n;
	}
	
	public int getMoney() {
		return mMoney;
	}
	
	public void buyProperty(Property property){
		payTo(null, property.getPrice());
		mProperties.add(property);
		property.setOwner(this);
	}
	
	public void addProperty(Property property){
		mProperties.add(property);
		property.setOwner(this);
	}

	public void payTo(Player player, int amount){ 
		if (player != null) player.addMoney(amount);
		mMoney -= amount;
		if(mMoney < 0)	
			User.showNegativeMoney(this, player);
	}
	
	public void bankrruptedBy(Player player){
		if(player == null)
			for(Property property: mProperties)
				property.setOwner(null);
		else{
			player.addProperties(mProperties);
			player.mMoney+=this.mMoney;	//Como el dinero del que esta en bancarrota es negativo no puedo usar addMoney();
		}
		mIsPlaying = false;
	}
	
	public void addProperties(SortedList<Property> properties){
		for(Property property: properties){
			property.setOwner(this);
			this.mProperties.add(property);
		}
	}
	
	public void removeProperties(SortedList<Property> properties){
		for(Property property: properties){
			//property.setOwner(null);
			this.mProperties.remove(property);
		}
	}
	
	public int getID() {
		return mID;
	}
	
	public void setBot(Bot bot){
		mBot = bot;
	}
	
	public Bot getBot(){
		return mBot;
	}
	
	public SortedList<Property> getProperties() {
		return mProperties;
	}
	
	public boolean isInJail(){
		return mInJail.isInJail();
	}
	
	public void turnInJail(){
		mInJail.nextTurn();
	}
	
	public boolean jailTimeUp(){
		return mInJail.turnsInJail() == 3;
	}
	
	public void getOutOfJail(){
		mInJail.getOutOfJail();
	}
	
	public void useGetOutOfJailCard(){
		mInJail.useGetOutOfJailCard();
	}
	
	public void addGetOutOfJailCard(Card card){
		mInJail.addGetOutOfJailCard(card);
	}
	
	public boolean hasGetOutOfJailCard(){
		return mInJail.amountOfGetOutOfJailCards() > 0;
	}
	
	public int amountOfGetOutOfJailCards(){
		return mInJail.amountOfGetOutOfJailCards();
	}
	
	public List<Card> getOutOfJailCards(){
		return mInJail.getCards();
	}
	
	public void addCards(List<Card> cards){
		mInJail.addCards(cards);
	}
	
	public void removeCards(List<Card> cards){
		mInJail.removeCards(cards);
	}
	
	public void goToJail(){
		mInJail.goToJail();
	}
	
	public void payBail(){
		payTo(null, 50);
		mInJail.getOutOfJail();
	}
	
	public String getName() {
		return mName;
	}
	
	public Color getColor() {
		return mColor;
	}
	
	public boolean isPlaying(){
		return mIsPlaying;
	}
	
	public void lost(){
		mIsPlaying = false;
	}

}
