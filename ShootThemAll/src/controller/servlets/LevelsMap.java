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
		String line = null;
		try{
			line = request.getParameter("userId");
		}catch(NullPointerException e){
			System.out.println("Invalid input");
		}
		
		UserDao ud = new DBUserDao();
		Cache cache = Cache.getCache();
		UserCache users = (UserCache) cache.getCacheItems().get("users");

		// test
		//line = "1";

		JSONObject result = new JSONObject();

		int numberOfAllLevels = SettingsManager.MAX_LEVEL;

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
			
			int numberOfActiveLevels = 0;
			if(users.getAllUsers() != null){
				//if we have cache
				User u = users.getUser(userId);
				if(u != null){
					numberOfActiveLevels = u.getLevel();
				}
			}else{				
				numberOfActiveLevels = ud.getUserLevel(userId);
				//add in cache
				if(numberOfActiveLevels > 0){
					users.addUser(ud.getUser(userId));
				}
			}
			
			System.out.println("number Of Active Levels = " + numberOfActiveLevels);
					
			/*
			 * get user level from db or from cache
			 */
			
		
			
			if(numberOfActiveLevels <= 0 || numberOfActiveLevels > numberOfAllLevels){
				result.put("error", "Invalid parameter");
				response.setStatus(400);
			}else{
			
			response.setStatus(200);
			result.put("numberOfAllLevels", numberOfAllLevels);
			result.put("numberOfActiveLevels", numberOfActiveLevels);
			}

		} else {

			result.put("error", "Invalid parameter");
			response.setStatus(400);

		}
		response.getWriter().write(result.toJSONString());
	}

}
