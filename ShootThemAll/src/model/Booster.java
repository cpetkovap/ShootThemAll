package model;

public class Booster {
	private int id;
	private float duration;
	private String description;
	
	public Booster(float duration, String description) {
		setDuration(duration);
		setDescription(description);
	}
	
	public Booster(int id, float duration, String description) {
		this(duration, description);
		setId(id);
	}
	
	//get
	public int getId() {
		return id;
	}
	public float getDuration() {
		return duration;
	}
	public String getDescription() {
		return description;
	}
	
	//set
	private void setId(int id) {
		if(id > 0){
			this.id = id;
		}else{
			this.id = 1;
		}
	}
	private void setDuration(float duration) {
		if(duration > 0){
			this.duration = duration;
		}else{
			this.duration = 1000;
		}
	}
	private void setDescription(String description) {
		if(description != null){
			this.description = description;
		}else{
			this.description = "default description";
		}
	}
	
	
	
	
	
	

}
