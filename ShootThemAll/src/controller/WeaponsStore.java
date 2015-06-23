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

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String line = request.getParameter("userId");

		// test
		line = "1";

		JSONObject result = new JSONObject();

		if (line != null) {

			int userId = Integer.parseInt(line);
			UserDao ud = new DBUserDao();
			WeaponDao wd = new DBWeaponDao();

			/*
			 * select в базата данни по userId за точките които потребителя има,
			 * за всичките оръжия и за активните за него оръжия
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
			int weponType = Integer.parseInt(userObj.get("weaponType").toString());
			
			/*
			 *  ОТКЛЮЧВАМЕ ОРЪЖИЕ
			 * 
			 *  Проверка в базата данни дали оръжието от този тип е заключено за потребителя с това userId
			 *  и дали потребителя разполага с достатачно точки, за да го отключи
			 *  Ако е заключено и потребителя има достатачно точки
			 *  го записваме за потребителя като отключено оръжие
			 * 
			 */
			
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
