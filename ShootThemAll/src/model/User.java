package model;

public class User {
	private final int id;
	private final String username;
	private String password;
	private String email;
	private int score;
	private int level;
	private Weapon weapon;
	private boolean allowNotification;
	
	
	
	public User(int id, String username, String password, String email,
			int score, int level, Weapon weapon, boolean allowNotification) {
		this.id = id;
		this.username = username;
		setPassword(password);
		setEmail(email);
		setScore(score);
		setLevel(level);
		setWeapon(weapon);
		setAllowNotification(allowNotification);
	}

	//get
	public int getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getLevel() {
		return level;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public boolean isAllowNotification() {
		return allowNotification;
	}

	//set
	public void setPassword(String password) {
		if(password != null){
			this.password = password;
		}
	}

	public void setEmail(String email) {
		if(email != null){
			this.email = email;
		}
	}

	public void setScore(int score) {
		if(score >= 0){
			this.score = score;
		}
	}

	public void setLevel(int level) {
		if(level >= 0){
			this.level = level;
		}
	}

	public void setWeapon(Weapon weapon) {
		if(weapon != null){
			this.weapon = weapon;
		}
	}

	public void setAllowNotification(boolean allowNotification) {
		this.allowNotification = allowNotification;
	}
	
	
	

}
