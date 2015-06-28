package controller.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.DBUserDao;
import model.dao.UserDao;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cache.Cache;
import cache.UserCache;

/**
 * Servlet implementation class UnlockWeapon
 */
@WebServlet("/weaponManager")
public class WeaponManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public WeaponManager() {
  
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		UserDao ud = new DBUserDao();
		Cache cache = Cache.getCache();
		UserCache users = (UserCache) cache.getCacheItems().get("users");
		
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
		}		

		String inputText = jb.toString();

		// test
		JSONObject test = new JSONObject();
		test.put("userId", 1);
		test.put("weaponType", 1);
		inputText = test.toJSONString();

		JSONParser parser = new JSONParser();
		try {
			JSONObject userObj = (JSONObject) parser.parse(inputText);
			int userId = Integer.parseInt(userObj.get("userId").toString());
			int weaponType = Integer.parseInt(userObj.get("weaponType")
					.toString());

			/*
			 * Р�Р—Р‘Р�Р РђРњР• РЎ РљРћР• РћР РЄР–Р�Р• Р©Р• Р�Р“Р РђР• РџРћРўР Р•Р‘Р�РўР•Р›РЇ
			 * 
			 * РїСЂРѕРІРµСЂРєР° РІ Р±Р°Р·Р°С‚Р° РґР°РЅРЅРё РґР°Р»Рё РѕСЂСЉР¶РёРµС‚Рѕ РѕС‚ С‚РѕР·Рё С‚РёРї Рµ РѕС‚РєР»СЋС‡РµРЅРѕ Р·Р°
			 * РїРѕС‚СЂРµР±РёС‚РµР»СЏ СЃ С‚РѕРІР° userId Р°РєРѕ Рµ РѕС‚РєР»СЋС‡РµРЅРѕ РіРѕ Р·Р°РїРёСЃРІР°РјРµ Р·Р°
			 * РїРѕС‚СЂРµР±РёС‚РµР»СЏ РєР°С‚Рѕ РёР·Р±СЂР°РЅРѕ РѕСЂСЉР¶РёРµ
			 * 
			 * РѕСЃРІРµРЅ Р·Р°РїРёСЃР° РІ Р±Р°Р·Р°С‚Р° РґР°РЅРЅРё РїСЂРѕРјРµРЅСЏРјРµ  РѕСЂСЉР¶РёРµС‚Рѕ Рё РІ РєРµС€Р°
			 * 
			 */
			
			ArrayList<Integer> unlockedWeapons = ud.getUnlockedWeapons(userId);
			
			boolean isUnlocked = false;
			for(int i = 0 ; i < unlockedWeapons.size(); i++){
				if(unlockedWeapons.get(i) == weaponType){
					isUnlocked = true;
				}				
			}
			
			if(isUnlocked){
				ud.updateUserWeapon(weaponType, userId);
				if(users.existUser(userId)){
					users.updateUserWeapon(weaponType, userId);					
				}
			}

			message = "success";
			response.setStatus(200);
			result.put("message", message);

		} catch (ParseException e) {

			result.put("error", "Invalid JSON");
			response.setStatus(400);

		}

		System.out.println(result.toJSONString());
		response.getWriter().write(result.toJSONString());
	}
	
	

}