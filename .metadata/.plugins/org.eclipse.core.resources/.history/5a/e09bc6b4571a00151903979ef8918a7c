package controller;

import model.Enemy;

public class EnemyFactory {
	
	public Enemy getEnemy(int type){
		Enemy result = null;
		switch(type){
		case 1 : result = new Enemy(type, 1, 5000, 1); break;
		case 2 : result = new Enemy(type, 2, 3000, 2); break;
		case 3 : result = new Enemy(type, 3, 1000, 3); break;
		default : result = new Enemy(100, 1, 2000, 0);
		}
		return result;
		
	}

}
