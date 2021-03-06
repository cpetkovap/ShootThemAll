package model;

public class EnemyBooster extends Enemy{
	Booster booster;	

	public EnemyBooster(int type, int health, float duration, int damage,
			Booster booster) {
		super(type, health, duration, damage);
		setBooster(booster);
	}

	public Booster getBooster() {
		return booster;
	}

	private void setBooster(Booster booster) {
		if(booster != null){
			this.booster = booster;
		} else {
			this.booster = new FreezNextOneEnemyBooster();
		}
	}
	
	

}
