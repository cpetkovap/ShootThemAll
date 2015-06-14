package model;

public class Weapon {
	private int type;
	private int damage;
	private int price;
	private boolean isActive;
	
	//constructor
	public Weapon(int type, int damage, int price, boolean isActive) {
		setType(type);
		setDamage(damage);
		setPrice(price);
		setActive(isActive);
	}

	//get
	public int getType() {
		return type;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getPrice() {
		return price;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	//set
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	private void setType(int type) {
		if(type > 0){
			this.type = type;
		}else{
			this.type = 1;
		}
	}
	
	private void setDamage(int damage) {
		if(damage > 0){
			this.damage = damage;
		}else{
			this.damage = 1;
		}
	}
	
	private void setPrice(int price) {
		if(price > 0){
			this.price = price;
		}else{
			this.price = 100;
		}
	}
	


}
