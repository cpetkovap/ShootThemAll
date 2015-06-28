package controller.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.dao.DBUserDao;
import model.dao.UserDao;

import org.json.simple.JSONObject;

import cache.Cache;
import cache.UserCache;
import controller.SettingsManager;

/**
 * Servlet implementation class LevelsMap
 */
@WebServlet("/levelsMap")
public class LevelsMap extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LevelsMap() {

	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String line = request.getParameter("userId");
		
		UserDao ud = new DBUserDao();
		Cache cache = Cache.getCache();
		UserCache users = (UserCache) cache.getCacheItems().get("users");

		// test
		line = "1";

		JSONObject result = new JSONObject();

		int numberOfAllLevels = SettingsManager.getMaxLevel();

		if (line != null && !line.isEmpty()) {

			int userId = Integer.parseInt(line);
			
			int numberOfActiveLevels = 0;
			if(users.getAllUsers() != null){
				//ako imame kesh
				User u = users.getUser(userId);
				if(u != null){
					numberOfActiveLevels = u.getLevel();
				}
			}else{				
				numberOfActiveLevels = ud.getUserLevel(userId);
				//dobavqme v kesha
				if(numberOfActiveLevels > 0){
					users.addUser(ud.getUser(userId));
				}
			}
			
			System.out.println("number Of Active Levels = " + numberOfActiveLevels);
					
			/*
			 * тук взимаме от потребителя нивото до което е стигнал от базата
			 * данни
			 * 
			 * или използваме кеша
			 */
			
			if(numberOfActiveLevels <= 0 || numberOfActiveLevels > numberOfAllLevels){
				numberOfActiveLevels = numberOfAllLevels;
			}
			
			
			
//			//izpolzvame vgradeniqt kesh
//			if (getServletContext().getAttribute("cacheUsers") == null) {
//
//				result.put("error", "No users in cache");
//				response.setStatus(400);
//
//			} else {
//				// кеш
//				ArrayList<User> list = (ArrayList<User>) getServletConfig()
//						.getServletContext().getAttribute("cacheUsers");
//
//				for (int i = 0; i < list.size(); i++) {
//					if (list.get(i).getId() == userId) {
//						numberOfActiveLevels = list.get(i).getLevel();
//					}
//				}
//
//				if (numberOfActiveLevels > numberOfAllLevels) {
//					numberOfActiveLevels = numberOfAllLevels;
//				}
//
//				response.setStatus(200);
//				result.put("numberOfAllLevels", numberOfAllLevels);
//				result.put("numberOfActiveLevels", numberOfActiveLevels);
//			}
			
			response.setStatus(200);
			result.put("numberOfAllLevels", numberOfAllLevels);
			result.put("numberOfActiveLevels", numberOfActiveLevels);

		} else {

			result.put("error", "Invalid parameter");
			response.setStatus(400);

		}
		response.getWriter().write(result.toJSONString());
	}

}
