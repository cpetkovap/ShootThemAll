package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Weapon;

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


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String line = request.getReader().readLine();

		// test
		JSONObject test = new JSONObject();
		test.put("userId", 1);
		line = test.toJSONString();
		
		JSONObject result = new JSONObject();
		
		JSONParser parser = new JSONParser();
		try {
			JSONObject userObj = (JSONObject) parser.parse(line);
			int userId = Integer.parseInt(userObj.get("userId").toString());
			
			/*
			 *  select в базата данни по userId за точките които потребителя има,
			 *  за всичките оръжия 
			 *  и за активните за него оръжия
			 * 
			 */
			
			//тест -> това идва от базата данни
			int score = 400;
			ArrayList<Weapon> weapons = new ArrayList<Weapon>();
			weapons.add(new Weapon(1, 1, 0));
			weapons.add(new Weapon(2, 2, 200));
			weapons.add(new Weapon(3, 3, 500));
			
			// тест -> това идва от базата данни
			ArrayList<Integer> unlockedWeapons = new ArrayList<Integer>();
			unlockedWeapons.add(new Weapon(1, 1, 0).getType());
			unlockedWeapons.add(new Weapon(3, 3, 500).getType());
			
			result.put("score", score);
			

			JSONArray weaponsArr = new JSONArray();
			
			for(int i = 0 ; i < weapons.size(); i++){
				JSONObject weaponsObj = new JSONObject();
				weaponsObj.put("type", weapons.get(i).getType());
				weaponsObj.put("damage", weapons.get(i).getDamage());
				weaponsObj.put("price", weapons.get(i).getPrice());			
				weaponsArr.add(weaponsObj);
			}
			
			result.put("weapons", weaponsArr);
			
					
			JSONArray unlochedWeaponsArr = new JSONArray();
			
			for(int i = 0 ; i < unlockedWeapons.size(); i++){
				JSONObject unlockedWeaponsObj = new JSONObject();
				unlockedWeaponsObj.put("type", unlockedWeapons.get(i));			
				unlochedWeaponsArr.add(unlockedWeaponsObj);
			}
			
			response.setStatus(200);
			result.put("unlockedWeapons", unlochedWeaponsArr);			
			
		} catch (ParseException e) {

			result.put("error", "Invalid JSON");
			response.setStatus(400);
			
		}
		System.out.println(result.toJSONString());
		response.getWriter().write(result.toJSONString());
	}

}
