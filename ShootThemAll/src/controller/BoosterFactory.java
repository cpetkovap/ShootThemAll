package controller;

import model.Booster;
import model.BulletsBooster;
import model.HealthBooster;
import model.PointsBooster;

public class BoosterFactory {

	public static Booster getBooster(int type, int bonusCount) {
		Booster result = null;
		switch (type) {
		case 1:
			result = new BulletsBooster(bonusCount);
			break;

		case 2:
			result = new HealthBooster(bonusCount);
			break;
		case 3:
			result = new PointsBooster(bonusCount);
			break;
		default:
			break;
		}

		return result;
	}

}
