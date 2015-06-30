package controller.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Weapon;
import model.dao.DBUserDao;
import model.dao.DBWeaponDao;
import model.dao.UserDao;
import model.dao.WeaponDao;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class WeaponsStore
 */
@WebServlet("/weaponsStore")
public class WeaponsStore extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public WeaponsStore() {

	}



	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String line = request.getParameter("userId");

		// test
		// line = "1";

		JSONObject result = new JSONObject();

		if (line != null && !line.isEmpty()) {

			int userId = -1;
			try{
				userId = Integer.parseInt(line);
			}catch(NumberFormatException e){
				response.setStatus(400);
				result.put("error", "Invalid parameter");
				response.getWriter().write(result.toJSONString());
				return;
			}
			UserDao ud = new DBUserDao();
			WeaponDao wd = new DBWeaponDao();

			/*
			 * return user score , all weapons and unloched weapons for user
			 */

			if (ud.getUser(userId) == null) {
				result.put("error", "Invalid parameter");
				response.setStatus(400);
				response.getWriter().write(result.toJSONString());
				return;
			}

			int score = ud.getUserScore(userId);
			ArrayList<Weapon> weapons = wd.getWeapons();
			ArrayList<Integer> unlockedWeapons = ud.getUnlockedWeapons(userId);

			result.put("score", score);

			JSONArray weaponsArr = new JSONArray();

			for (int i = 0; i < weapons.size(); i++) {
				JSONObject weaponsObj = new JSONObject();
				weaponsObj.put("type", weapons.get(i).getType());
				weaponsObj.put("damage", weapons.get(i).getDamage());
				weaponsObj.put("price", weapons.get(i).getPrice());
				weaponsArr.add(weaponsObj);
			}

			result.put("weapons", weaponsArr);

			JSONArray unlockedWeaponsArr = new JSONArray();

			for (int i = 0; i < unlockedWeapons.size(); i++) {
				JSONObject unlockedWeaponsObj = new JSONObject();
				unlockedWeaponsObj.put("type", unlockedWeapons.get(i));
				unlockedWeaponsArr.add(unlockedWeaponsObj);
			}

			response.setStatus(200);
			result.put("unlockedWeapons", unlockedWeaponsArr);

		} else {

			result.put("error", "Invalid parameter");
			response.setStatus(400);

		}
		System.out.println(result.toJSONString());
		response.getWriter().write(result.toJSONString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		JSONObject result = new JSONObject();
		String message = null;
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) { 			
			System.out.println("Invalid input");
			result.put("error", "Invalid input");
			response.setStatus(400);
			return;
		}		

		String inputText = jb.toString();

		// test
		JSONObject test = new JSONObject();
		test.put("userId", 1);
		test.put("weaponType", 1);
		//inputText = test.toJSONString();
		
		JSONParser parser = new JSONParser();
		try {
			JSONObject userObj = (JSONObject) parser.parse(inputText);
			
			int userId;
			int weaponType;
			try{
			 userId = Integer.parseInt(userObj.get("userId").toString());
			 weaponType = Integer.parseInt(userObj.get("weaponType").toString());
			}catch(NumberFormatException e){
				throw new ParseException(1);
			}
			
			/*
			 * "buy"(unlock) weapon for user.
			 * if weapon is not already unlocked and user has enough points for unlock it ->
			 * insert weapon id and user id in unloched_weapons table in db
			 */
			
			if(weaponType <= 0 || weaponType > 3){
				throw new ParseException(1);
			}
			
			UserDao ud = new DBUserDao();
			WeaponDao wd = new DBWeaponDao();
			
			if(ud.getUser(userId) == null){
				throw new ParseException(1);
			}
			
			ArrayList<Integer> unlockedLeapons = ud.getUnlockedWeapons(userId);
			boolean isUnlocked = false;
			
			int userScore = ud.getUserScore(userId);
			
			for(int i = 0 ; i < unlockedLeapons.size(); i++){
				if(unlockedLeapons.get(i) == weaponType){
					isUnlocked = true;
				}
			}
			
			if(!isUnlocked){				
				Weapon weapon = wd.getWeapon(weaponType);
				if(weapon.getPrice() <= userScore){
					ud.addUnlockedWeapon(weaponType, userId);
					message = "success";
					result.put("message", message);
					response.setStatus(200);
				}else{
					result.put("error", "Not enought score");
					response.setStatus(400);
				
				}			
			}else{
				result.put("error", "Thie weapon is already unlocked");
				response.setStatus(400);
			
			}
			
			
					
			
		} catch (ParseException e) {

			result.put("error", "Invalid JSON");
			response.setStatus(400);
			
		}
		System.out.println(result.toJSONString());
		response.getWriter().write(result.toJSONString());
		
		
	}
}
