package controller;

import java.util.Random;

import model.Booster;
import model.BulletsBooster;
import model.Enemy;
import model.EnemyBooster;
import model.FreezNextOneEnemyBooster;
import model.HealthBooster;
import model.PointsBooster;
import model.User;
import model.Weapon;
import model.dao.DBUserDao;
import model.dao.UserDao;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import cache.Cache;
import cache.UserCache;

public class LevelBuilder {
	final int maxLevel = SettingsManager.MAX_LEVEL;

	public JSONObject buildLevel(int userID, int level) {
		
		UserDao ud = new DBUserDao();
		Cache cache = Cache.getCache();
		UserCache users = (UserCache) cache.getCacheItems().get("users");
		
		Random rand = new Random();
		JSONObject objLevel = new JSONObject();
		objLevel.put("userHealth", 3);

		/*
		 * check for an existing user and level achieved in DB
		 */

		int userLevel = 0;
		
		if(users.getAllUsers() != null){
			User u = users.getUser(userID);
			if(u != null){
				userLevel = u.getLevel();
			}
		}else{
			userLevel = ud.getUserLevel(userID);
			if(userLevel > 0){
				users.addUser(ud.getUser(userID));
			}
		}

		
		if (level < 0 || level > maxLevel) {
			throw new IllegalArgumentException();
		}
		
		if(level > userLevel){
			throw new IllegalArgumentException();
		}

		int numEnemies = 10 + ((level - 1) * 7);
		int randomNum[] = new int[level];
		Enemy enemies[] = new Enemy[level];

		randomNum[0] = numEnemies;
		for (int i = randomNum.length - 1; i > 0; i--) {
			randomNum[i] = rand.nextInt(randomNum[0]) + 1;
			randomNum[0] = numEnemies - randomNum[i];
		}

		// for(int i = 0; i < randomNum.length; i++){
		// System.out.println(randomNum[i]);
		// }

		// Weapon weapon = new Weapon(1, 1, 100);
		/*
		 * get weapon from DB
		 */
	
		Weapon weapon = ud.getUserWeapon(userID);
		if (weapon == null) {
			weapon = new Weapon(1, 1, 0);
		}

		JSONObject weaponObj = new JSONObject();
		weaponObj.put("type", weapon.getType());
		weaponObj.put("damage", weapon.getDamage());
		

		// boosters
		JSONArray boostersArr = new JSONArray();
		int countBooster = 1;
		int countBullets = 1;
		int countHealth = 1;
		int countPoints = 1;

		BulletsBooster bulletsBooster = (BulletsBooster) BoosterFactory
				.getBooster(1, level);
		HealthBooster healthBooster = (HealthBooster) BoosterFactory
				.getBooster(2, 1);
		PointsBooster pointsBooster = (PointsBooster) BoosterFactory
				.getBooster(3, level * 10);
		FreezNextOneEnemyBooster freezBooster = (FreezNextOneEnemyBooster) BoosterFactory
				.getBooster(4, 0); // tuk nqma broi

		int numBoosters[] = new int[4];
		JSONObject boosterObj;
		for (int i = 0; i < numBoosters.length; i++) {
			numBoosters[i] = rand.nextInt(2 * level + 1) + 1;			
		}

		boosterObj = makeBooster(bulletsBooster,
				bulletsBooster.getNumBullets(), numBoosters[0]);
		boostersArr.add(boosterObj);
		boosterObj = makeBooster(healthBooster, healthBooster.getHealtPoints(),
				numBoosters[1]);
		boostersArr.add(boosterObj);
		boosterObj = makeBooster(pointsBooster, pointsBooster.getPoints(),
				numBoosters[2]);
		boostersArr.add(boosterObj);
		boosterObj = makeSimpleBooster(freezBooster, numBoosters[3]);
		boostersArr.add(boosterObj);

		
		//Enemy
		// approach 1
		for (int i = 0; i < enemies.length; i++) {
			enemies[i] = new Enemy(i + 1, i + 1, (maxLevel * 2000) + 1000
					- (i + 1) * 2000, i + 1);
		}

		// //approach 2 -> for a fixed number of levels = 3
		// for(int i = 0 ; i < enemies.length; i++){
		// enemies[i] = EnemyFactory.getEnemy(i+1);
		// }

		// for now there are no good :)
		// damage = 0
		int goodEnemyCount = rand.nextInt(level * 4) + 1;
		Enemy goodEnemy = EnemyFactory.getEnemy(100);
		JSONObject goodEnemyObj = makeEnemy(goodEnemy, goodEnemyCount);

		
		//test enemy booster
		int enemyBoosterCount = 1;
		EnemyBooster enemyBooster = new EnemyBooster(4, enemies[0].getHealth(), enemies[0].getDuration(),
				enemies[0].getDamage(), freezBooster);		
		JSONObject enemyBoosterObj = makeEnemyBooster(enemyBooster, enemyBooster.getBooster(), 1);
		
		
		//bullets calculate
		int bullets = 0;
		for (int i = 0; i < randomNum.length; i++) {
			bullets += randomNum[i]
					* Math.ceil((double) enemies[i].getDamage()
							/ (double) weapon.getDamage());

		}
		
		//bullets enemyBooster
		bullets += enemyBoosterCount * Math.ceil((double) enemyBooster.getDamage() / (double) weapon.getDamage());

		//level test
		System.out.println("level =" + level);
		System.out.println("max level =" + maxLevel);

		
		//bullets
		int randomBullets = rand.nextInt((maxLevel - level) * 3 + 1)
				+ (maxLevel - level);
		System.out.println("Bonus bullets = " + randomBullets);
		bullets += randomBullets;
		System.out.println("bullets = " + bullets);

		objLevel.put("bullets", bullets);
		objLevel.put("weapon", weaponObj);

		
		//enemiesr array
		JSONArray enemiesArr = new JSONArray();
		for (int i = 0; i < enemies.length; i++) {
			JSONObject enemyObj = makeEnemy(enemies[i], randomNum[i]);
			enemiesArr.add(enemyObj);
		}

		// for now there are no good :)
		enemiesArr.add(goodEnemyObj);
		
		// for now there are no  enemy with booster :)
	     enemiesArr.add(enemyBoosterObj);

		objLevel.put("enemies", enemiesArr);

		// for now there are no boosters
		 objLevel.put("boosters", boostersArr);

		return objLevel;
	}

	public JSONObject makeBooster(Booster b, int numOfIncrement, int count) {
		JSONObject booster = new JSONObject();
		booster.put("type", b.getId());
		booster.put("duratin", b.getDuration());
		booster.put("description", b.getDescription());
		booster.put("numOfIncrement", numOfIncrement);
		booster.put("count", count);
		return booster;

	}

	public JSONObject makeSimpleBooster(Booster b, int count) {
		JSONObject booster = new JSONObject();
		booster.put("type", b.getId());
		booster.put("duratin", b.getDuration());
		booster.put("description", b.getDescription());
		booster.put("count", count);
		return booster;

	}

	public JSONObject makeEnemy(Enemy e, int num) {
		JSONObject enemyObj = new JSONObject();
		enemyObj.put("type", e.getType());
		enemyObj.put("health", e.getHealth());
		enemyObj.put("duration", e.getDuration());
		enemyObj.put("damage", e.getDamage());
		enemyObj.put("count", num);
		return enemyObj;
	}

	public JSONObject makeEnemyBooster(Enemy e, Booster b, int num) {
		JSONObject enemyObj = makeEnemy(e, num);
		JSONObject booster = new JSONObject();
		booster.put("type", b.getId());
		booster.put("description", b.getDescription());
		enemyObj.put("booster", booster);		
		return enemyObj;
	}
	
}
