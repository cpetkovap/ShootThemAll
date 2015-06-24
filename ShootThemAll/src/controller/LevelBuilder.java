package controller;

import java.util.Random;

import model.Enemy;
import model.Weapon;
import model.dao.DBUserDao;
import model.dao.UserDao;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LevelBuilder {
	final int maxLevel = SettingsManager.getMaxLevel();
	
	public JSONObject buildLevel(int userID, int level){
		Random rand = new Random();
		JSONObject objLevel = new JSONObject();
		objLevel.put("userHealth", 3);
		
		

		/*
		 * проверка за валидно ниво от базата данни : userLevel = нивото до което е стигнал потребителя, но не е минал
		 */
		
		if(level > maxLevel){
			level = maxLevel;
		}
		
		int numEnemies = 10 + ( (level - 1) * 7);
		int randomNum[] = new int[level];
		Enemy enemies[] = new Enemy[level];
		
		randomNum[0] = numEnemies;
		for(int i = randomNum.length - 1; i > 0; i--){
			randomNum[i] = rand.nextInt(randomNum[0]) + 1;
			randomNum[0] = numEnemies - randomNum[i];
		}
		
//		for(int i = 0; i < randomNum.length; i++){
//			System.out.println(randomNum[i]);
//		}
		
		//Weapon weapon = new Weapon(1, 1, 100);
		/*
		 * тук оръжието ще си го вземем от базата данни като използваме userId
		 * 
		 */
		UserDao ud = new DBUserDao();
		Weapon weapon = ud.getUserWeapon(userID);
		if(weapon == null){
			weapon = new Weapon(1, 1, 0);
		}
		
		
		JSONObject weaponObj = new JSONObject();
		weaponObj.put("type", weapon.getType());
		weaponObj.put("damage", weapon.getDamage());
		
		//вариант 1
		for(int i = 0 ; i < enemies.length; i++){
			enemies[i] = new Enemy(i+1, i+1, (maxLevel * 2000) + 1000 - (i+1) * 2000, i+1);
		}
		
//		//вариант 2 -> за фиксирано максимум 3 нива
//		for(int i = 0 ; i < enemies.length; i++){
//			enemies[i] = EnemyFactory.getEnemy(i+1);
//		}
		
		
//		//за сега нямаме добри :)
//		//damage = 0
//		int goodEnemyCount = rand.nextInt(level * 4) + 1;
//		Enemy goodEnemy = EnemyFactory.getEnemy(100);
//		JSONObject goodEnemyObj = new JSONObject();
//		goodEnemyObj.put("type", goodEnemy.getType());
//		goodEnemyObj.put("health", goodEnemy.getHealth());
//		goodEnemyObj.put("duration", goodEnemy.getDuration());
//		goodEnemyObj.put("damage", goodEnemy.getDamage());
//		goodEnemyObj.put("count", goodEnemyCount);
		
		int bullets = 0;
		for(int i = 0; i < randomNum.length; i++){
			bullets += randomNum[i] * Math.ceil(enemies[i].getDamage()/weapon.getDamage());
			
		}
		
		System.out.println("level =" + level);
		System.out.println("max level =" + maxLevel);
		
		int randomBullets = rand.nextInt((maxLevel - level) * 3 + 1) + (maxLevel - level) ; 
		System.out.println("Bonus bullets = " + randomBullets);
		bullets += randomBullets;
		
		objLevel.put("bullets", bullets);
		objLevel.put("weapon", weaponObj);
		
		
		JSONArray enemiesArr = new JSONArray();
		for(int i = 0 ; i < enemies.length; i++){
			JSONObject enemyObj = new JSONObject();
			enemyObj.put("type", enemies[i].getType());
			enemyObj.put("health", enemies[i].getHealth());
			enemyObj.put("duration", enemies[i].getDuration());
			enemyObj.put("damage", enemies[i].getDamage());
			enemyObj.put("count", randomNum[i]);
			enemiesArr.add(enemyObj);
		}
		
		//за сега нямаме добри :)
		//enemiesArr.add(goodEnemyObj);
		
		objLevel.put("enemies", enemiesArr);
	
		
		
		return objLevel;
	}

}
