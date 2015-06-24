package model;

import java.util.Date;

public class User {
	private int id;
	private final String username;
	private String password;
	private String email;
	private int score;
	private int level;
	private Weapon weapon;
	private boolean allowNotification;
	private Date date;
		
	public User(String username, String password) {
		this.username = username;
		setPassword(password);

	}
	
	public User(String username, String password, String email) {
		this(username, password);
		setEmail(email);
		
	}
	

 //testing	
	public User(int id, String username, String password, String email,
			int score, int level, Weapon weapon, boolean allowNotification) {
		this(username, password, email);
		setScore(score);
		setLevel(level);
		setWeapon(weapon);
		setAllowNotification(allowNotification);
		setDate(new Date());

	}

	public User(int id, String username, String password, String email,
			int score, int level, Weapon weapon, boolean allowNotification, Date date) {
		this(username, password, email);
		setScore(score);
		setLevel(level);
		setWeapon(weapon);
		setAllowNotification(allowNotification);
		setDate(date);

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
	
	public Date getDate() {
		return date;
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
	
	private void setDate(Date date) {
		if(!date.equals(null)){
			this.date = date;
		}else{
			this.date = new Date();
		}
		
	}
	

}
