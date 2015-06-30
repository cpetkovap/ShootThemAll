package controller.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.Weapon;
import model.dao.DBUserDao;
import model.dao.UserDao;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import charts.BarChart;
import controller.SettingsManager;
import cache.Cache;
import cache.UserCache;
import db.DBManager;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserCache users;
	
	private Cache cache;
	private ServletConfig config;
	private ServletContext sc;

	public Login() {

	}

	@Override
	public void init() throws ServletException {

		System.out.println("cache init");

		cache = Cache.getCache();
		users = UserCache.getUserCache();
		users.init();
		cache.getCacheItems().put("users", users);
		
	}
	
	@Override
	public void destroy() {
		DBManager.getDBManager().destroyConnection();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
		test.put("username", "Shoshi");
		test.put("password", "123");
		//inputText = test.toJSONString();


		JSONParser parser = new JSONParser();
		try {
			JSONObject userObj = (JSONObject) parser.parse(inputText);
			String username = userObj.get("username").toString();
			String password = userObj.get("password").toString();
			
			
			
			//MD5
			String cryptPass = SettingsManager.cryptMD5(password);
			password = cryptPass;

			UserDao ud = new DBUserDao();
			
			if(!ud.hasQuery()){		
				result.put("error", "Invalid username or password");
				response.setStatus(400);

			} else {

				if (username.isEmpty() && password.isEmpty()) {

					result.put("error", "Empty fields");
					response.setStatus(400);

				} else {
					//check if username is admin and redirect
					if(username.equals(SettingsManager.ADMIN.getUsername()) &&
							password.equals(SettingsManager.cryptMD5(SettingsManager.ADMIN.getPassword()))){
						response.getWriter().write("redirect");
						return;
					}
					int userId = -1;
					
					boolean existUser = false;					
					
					//chech if user exist in our cache
		
									
					if (users != null) {
						existUser = users.existUser(username, password);				
						userId = users.getUserId(username);
						
					}
					
					if (existUser) {
						//update last activity
						ud.updateCurrentDate(userId);
					    users.getUser(userId).setDate(ud.getUser(userId).getDate());
					    
						result.put("userId", userId);
						response.setStatus(200);
						//System.out.println(Cache.getCache().getUser(userId).getDate());
					} else {
						
						//user doestn't exist in cache 
						//check if exist in DB
						
						int existInDB = ud.existUser(username, password);
						
						if(existInDB > 0){
							//user exist in DB
													
							User user = ud.getUser(existInDB);
							
							//update user last activity in DB
							ud.updateCurrentDate(existInDB);
						    user.setDate(ud.getUser(existInDB).getDate());
							
						    //add user in cache
							users.addUser(user);
							
							//users.printNames();
							
							result.put("userId", existInDB);
							response.setStatus(200);
						}else{							
							result.put("error", "Invalid username or password");
							response.setStatus(400);							
						}
						
						
					}
					
					
					//  only DB 
//					userId = ud.existUser(username, password);
//					if(userId > 0){
//						result.put("userId", userId);
//						response.setStatus(200);
//					}else{
//						result.put("error", "Invalid username or password");
//						response.setStatus(400);
//					}
					

				}
			}

		} catch (ParseException e) {
			result.put("error", "Invalid JSON");
			response.setStatus(400);
		}

		System.out.println(result.toJSONString());
		response.getWriter().write(result.toJSONString());
	}



}
