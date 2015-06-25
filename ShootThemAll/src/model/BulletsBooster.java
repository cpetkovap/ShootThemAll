package model;

import model.dao.BoosterDao;
import model.dao.DBBoosterDao;
import model.dao.DBUserDao;

public class BulletsBooster extends Booster{
	private int numBullets;
	private static final int ID = 1;
	
	private static BoosterDao db = new DBBoosterDao();
    private static Booster booster = db.getBooster(ID);
    
	
	public BulletsBooster(int numBullets) {
		super(ID, booster.getDuration(), booster.getDescription());
		setNumBullets(numBullets);
	}

	//get
	public int getNumBullets() {
		return numBullets;
	}

	//set
	private void setNumBullets(int numBullets) {
		if(numBullets > 0){
			this.numBullets = numBullets;
		}else{
			this.numBullets = 1;
		}
	}
	
	
	

}
