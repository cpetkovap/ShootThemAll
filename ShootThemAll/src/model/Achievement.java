package model;

public class Achievement {
	private int id;
	private int achievement_points;
	private String description;
	
	
	public Achievement(int achievement_points, String description) {
		setAchievement_points(achievement_points);
		setDescription(description);
	}
	
	public Achievement(int id, int achievement_points, String description) {
		this(achievement_points,description);
		setId(id);
	}
	
	public int getId() {
		return id;
	}

	public int getAchievement_points() {
		return achievement_points;
	}
	
	public String getDescription() {
		return description;
	}
	
		public void setId(int id) {
			if(id > 0){
				this.id = id;
			}else{
				this.id = 1;
			}
	}
		
	public void setAchievement_points(int achievement_points) {
		if(achievement_points > 0){
			this.achievement_points = achievement_points;
		}else{
			this.achievement_points = 0;
		}
	}
	
	public void setDescription(String description) {
		if(description != null && description != ""){
			this.description = description;
		}else{
			this.description = "default description";
		}
	}
}
