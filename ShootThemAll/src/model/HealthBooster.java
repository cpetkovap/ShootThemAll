package model;

import model.dao.BoosterDao;
import model.dao.DBBoosterDao;

public class HealthBooster extends Booster{
	private int healtPoints;
	
	private static BoosterDao db = new DBBoosterDao();
    private static Booster booster = db.getBooster(2);

	public HealthBooster(int healtPoints) {
		super(booster.getId(), booster.getDuration(), booster.getDescription());
		setHealtPoints(healtPoints);
	}

	//get
	public int getHealtPoints() {
		return healtPoints;
	}

	//set
	private void setHealtPoints(int healtPoints) {
		if(healtPoints > 0){
			this.healtPoints = healtPoints;
		}else{
			this.healtPoints = 1;
		}
	}
	
	

}