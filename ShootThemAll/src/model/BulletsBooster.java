package model;

public class BulletsBooster extends Booster{
	private int numBullets;
	
	public BulletsBooster(int numBullets) {
		super(1, 2000, "Add bullets.");
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
