package model;

import model.dao.BoosterDao;
import model.dao.DBBoosterDao;

public class FreezNextOneEnemyBooster extends Booster {

private static final int ID = 4;
	
	private static BoosterDao db = new DBBoosterDao();
    private static Booster booster = db.getBooster(ID);

	public FreezNextOneEnemyBooster() {
		super(ID, booster.getDuration(), booster.getDescription());

	}
}
