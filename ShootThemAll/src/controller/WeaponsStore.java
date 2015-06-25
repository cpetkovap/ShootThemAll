package controller;

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
	
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doPost(req, resp);
//	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String line = request.getParameter("userId");

		// test
		line = "1";

		JSONObject result = new JSONObject();

		if (line != null) {

			int userId = Integer.parseInt(line);
			UserDao ud = new DBUserDao();
			WeaponDao wd = new DBWeaponDao();

			/*
			 * select РІ Р±Р°Р·Р°С‚Р° РґР°РЅРЅРё РїРѕ userId Р·Р° С‚РѕС‡РєРёС‚Рµ РєРѕРёС‚Рѕ РїРѕС‚СЂРµР±РёС‚РµР»СЏ РёРјР°,
			 * Р·Р° РІСЃРёС‡РєРёС‚Рµ РѕСЂСЉР¶РёСЏ Рё Р·Р° Р°РєС‚РёРІРЅРёС‚Рµ Р·Р° РЅРµРіРѕ РѕСЂСЉР¶РёСЏ
			 */

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
		String line = request.getReader().readLine();

		// test
		JSONObject test = new JSONObject();
		test.put("userId", 1);
		test.put("weaponType", 2);
		line = test.toJSONString();
		
		JSONObject result = new JSONObject();
		String message = null;
		
		JSONParser parser = new JSONParser();
		try {
			JSONObject userObj = (JSONObject) parser.parse(line);
			int userId = Integer.parseInt(userObj.get("userId").toString());
			int weaponType = Integer.parseInt(userObj.get("weaponType").toString());
			
			/*
			 *  РћРўРљР›Р®Р§Р’РђРњР• РћР РЄР–Р�Р•
			 * 
			 *  РџСЂРѕРІРµСЂРєР° РІ Р±Р°Р·Р°С‚Р° РґР°РЅРЅРё РґР°Р»Рё РѕСЂСЉР¶РёРµС‚Рѕ РѕС‚ С‚РѕР·Рё С‚РёРї Рµ Р·Р°РєР»СЋС‡РµРЅРѕ Р·Р° РїРѕС‚СЂРµР±РёС‚РµР»СЏ СЃ С‚РѕРІР° userId
			 *  Рё РґР°Р»Рё РїРѕС‚СЂРµР±РёС‚РµР»СЏ СЂР°Р·РїРѕР»Р°РіР° СЃ РґРѕСЃС‚Р°С‚Р°С‡РЅРѕ С‚РѕС‡РєРё, Р·Р° РґР° РіРѕ РѕС‚РєР»СЋС‡Рё
			 *  РђРєРѕ Рµ Р·Р°РєР»СЋС‡РµРЅРѕ Рё РїРѕС‚СЂРµР±РёС‚РµР»СЏ РёРјР° РґРѕСЃС‚Р°С‚Р°С‡РЅРѕ С‚РѕС‡РєРё
			 *  РіРѕ Р·Р°РїРёСЃРІР°РјРµ Р·Р° РїРѕС‚СЂРµР±РёС‚РµР»СЏ РєР°С‚Рѕ РѕС‚РєР»СЋС‡РµРЅРѕ РѕСЂСЉР¶РёРµ
			 * 
			 */
			
			UserDao ud = new DBUserDao();
			WeaponDao wd = new DBWeaponDao();
			
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
				}else{
					result.put("error", "Not enought score");
					response.setStatus(400);
					return;
				}			
			}else{
				result.put("error", "Thie weapon is already unlocked");
				response.setStatus(400);
				return;
			}
			
			
			message = "success";
			result.put("message", message);
			response.setStatus(200);			
			
		} catch (ParseException e) {

			result.put("error", "Invalid JSON");
			response.setStatus(400);
			
		}
		System.out.println(result.toJSONString());
		response.getWriter().write(result.toJSONString());
		
		
	}

}
