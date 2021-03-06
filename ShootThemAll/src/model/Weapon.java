package model;

public class Weapon {
	private int type;
	private int damage;
	private int price;
	
	//constructor
	public Weapon(int type, int damage, int price) {
		setType(type);
		setDamage(damage);
		setPrice(price);

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
	
	
	//set
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
