package model;

import model.dao.BoosterDao;
import model.dao.DBBoosterDao;

public class PointsBooster extends Booster{
	private int points;	
	
	private static BoosterDao db = new DBBoosterDao();
    private static Booster booster = db.getBooster(3);

	public PointsBooster(int points) {
		super(booster.getId(), booster.getDuration(), booster.getDescription());
		setPoints(points);
	}

	//get
	public int getPoints() {
		return points;
	}

	//set
	private void setPoints(int points) {
		if(points > 0){
			this.points = points;
		}else{
			this.points = 10;
		}
	}
	
	

}
