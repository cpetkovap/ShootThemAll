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
	//private ArrayList<User> cacheUsers;
	private UserCache users;
	
	private Cache cache;
	private ServletConfig config;
	private ServletContext sc;

	public Login() {

	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		System.out.println("cache init");
//		if (cacheUsers == null) {
//			cacheUsers = new ArrayList();
//		}
		/*
		 * РІР·РµРјР°РЅРµ РЅР° РїРѕС‚СЂРµР±РёС‚РµР»РёС‚Рµ РѕС‚ Р±Р°Р·Р°С‚Р° РґР°РЅРЅРё Рё РїСЉР»РЅРµРЅРµ РЅР° cacheUsers
		 */

		// С‚РµСЃС‚
//		User u1 = new User(1, "Ivan", "Ivan", "Ivan", 20, 1,
//				new Weapon(1, 1, 0), false);
//		User u2 = new User(2, "Petko", "Petko", "Petko", 10, 0, new Weapon(1,
//				1, 0), false);
//		User u3 = new User(3, "Tanq", "Tanq", "Tanq", 14, 0,
//				new Weapon(1, 1, 0), false);

		cache = Cache.getCache();
		users = UserCache.getUserCache();
		users.init();
		cache.getCacheItems().put("users", users);
		config = getServletConfig();

		sc = config.getServletContext();
		
		

		if (sc.getAttribute("cacheUsers") == null) {
			//sc.setAttribute("cacheUsers", cacheUsers);
			
			//С‚РѕРІР° Р·Р° РґР° РЅРµ СЃС‡СѓРїСЏ РЅР°РїРёСЃР°РЅРµС‚Рѕ РґРѕ С‚СѓРє :)
			
			
			ArrayList<User> list = new ArrayList<User>();
			for(int i = 0 ; i < users.getAllUsers().size(); i++){
				list.add(users.getAllUsers().get(i));
			}
			sc.setAttribute("cacheUsers", list);
		}

//		cacheUsers = (ArrayList<User>) sc.getAttribute("cacheUsers");
//		cacheUsers.add(u1);
//		cacheUsers.add(u2);
//		cacheUsers.add(u3);
//		cacheUsers.sort(new UsersComparator());
		// sc.setAttribute("cacheUsers", cacheUsers);
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
			
			//	if (getServletContext().getAttribute("cacheUsers") == null) {
			if(!ud.hasQuery()){		
				result.put("error", "Invalid username or password");
				response.setStatus(400);

			} else {

				if (username.isEmpty() && password.isEmpty()) {

					result.put("error", "Empty fields");
					response.setStatus(400);

				} else {
					if(username.equals("admin")){
						response.getWriter().write("redirect");
						return;
					}
					int userId = -1;
					
					boolean existUser = false;
					
//					//РёР·РїРѕР»Р·РІР°РјРµ РІРіСЂР°РґРµРЅРёСЏС‚ РєРµС€Р° -> РґСЂСѓРі РІР°СЂРёР°РЅС‚ Рµ РѕС‚ Р±Р°Р·Р°С‚Р° РґР°РЅРЅРё РёР»Рё РЅР°С€РёСЏС‚ РєРµС€
//					ArrayList<User> list = (ArrayList<User>) getServletConfig()
//							.getServletContext().getAttribute("cacheUsers");
//
//					for (int i = 0; i < list.size(); i++) {
//						if (list.get(i).getUsername().equals(username)
//								&& list.get(i).getPassword().equals(password)) {
//							userId = list.get(i).getId();
//							existUser = true;
//						}
//					}
//
//					if (existUser) {
//						result.put("userId", userId);
//						response.setStatus(200);
//					} else {
//						result.put("error", "Invalid username or password");
//						response.setStatus(400);
//					}
					
					
					//Р�Р·РїРѕР»Р·РІР°РјРµ РЅР°С€РёСЏС‚ СЃРё РЅР°С€РёСЏС‚ РєРµС€
		
									
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
						
						// РўСѓРє РїСЂРѕРІРµСЂСЏРІР°РјРµ РІ Р±Р°Р·Р°С‚Р° РґР°РЅРЅРё РґР°Р»Рё СЃСЉС‰РµСЃС‚РІСѓРІР° С‚РѕР·Рё РїРѕС‚СЂРµР±РёС‚РµР»
						
						int existInDB = ud.existUser(username, password);
						
						if(existInDB > 0){
							
							//potrebitel ot selekta v bazata danni -> testov
							User user = ud.getUser(existInDB);
							//update na datata v bazata danni za tozi potrebitel
							
							//update last activity
							ud.updateCurrentDate(existInDB);
						    user.setDate(ud.getUser(existInDB).getDate());
							
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
