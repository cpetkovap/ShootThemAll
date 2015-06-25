package controller;

import java.io.IOException;
import java.util.ArrayList;

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

import cache.Cache;
import db.DBManager;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private ArrayList<User> cacheUsers;
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
		 * вземане на потребителите от базата данни и пълнене на cacheUsers
		 */

		// тест
//		User u1 = new User(1, "Ivan", "Ivan", "Ivan", 20, 1,
//				new Weapon(1, 1, 0), false);
//		User u2 = new User(2, "Petko", "Petko", "Petko", 10, 0, new Weapon(1,
//				1, 0), false);
//		User u3 = new User(3, "Tanq", "Tanq", "Tanq", 14, 0,
//				new Weapon(1, 1, 0), false);

		cache = Cache.getCache();
		cache.init();
		
		config = getServletConfig();

		sc = config.getServletContext();
		
		

		if (sc.getAttribute("cacheUsers") == null) {
			//sc.setAttribute("cacheUsers", cacheUsers);
			
			//това за да не счупя написането до тук :)
			
			
			ArrayList<User> list = new ArrayList<User>();
			for(int i = 0 ; i < cache.getAllUsers().size(); i++){
				list.add(cache.getAllUsers().get(i));
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
		String line = request.getReader().readLine();

		// test
		JSONObject test = new JSONObject();
		test.put("username", "Tanq");
		test.put("password", "Tanq");
		line = test.toJSONString();

		JSONObject result = new JSONObject();
		String message = null;

		JSONParser parser = new JSONParser();
		try {
			JSONObject userObj = (JSONObject) parser.parse(line);
			String username = userObj.get("username").toString();
			String password = userObj.get("password").toString();
			
			//MD5
			String cryptPass = SettingsManager.cryptMD5(password);
			password = cryptPass;

			UserDao ud = new DBUserDao();
			
			//	if (getServletContext().getAttribute("cacheUsers") == null) {
			//if (Cache.getCache().isEmpty()) {
			if(!ud.hasQuery()){		
				result.put("error", "Invalid username or password");
				response.setStatus(400);

			} else {

				if (username.isEmpty() && password.isEmpty()) {

					result.put("error", "Empty fields");
					response.setStatus(400);

				} else {

//					boolean existUser = false;
					int userId = -1;
					
//					//използваме кеша -> друг вариант е от базата данни
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
					
//					existUser = Cache.getCache().existUser(username, password);
//					userId = Cache.getCache().getUserId(username);
					
					

//					if (existUser) {
//						result.put("userId", userId);
//						response.setStatus(200);
//						//System.out.println(Cache.getCache().getUser(userId).getDate());
//					} else {
//						
//						// Тук проверяваме в базата данни дали съществува този потребител
//						
//						boolean existInDB = false;
//						if(existInDB){
//							
//							//potrebitel ot selekta v bazata danni -> testov
//							User user = new User(12, "Sonq", "Sonq", "Sonq", 100, 3, new Weapon(1, 1, 0), false);
//							//update na datata v bazata danni za tozi potrebitel
//							
//							Cache.getCache().addUser(user);
//							result.put("userId", userId);
//							response.setStatus(200);
//						}else{
//							result.put("error", "Invalid username or password");
//							response.setStatus(400);							
//						}
//						
//						
//					}
					
					userId = ud.existUser(username, password);
					if(userId > 0){
						result.put("userId", userId);
						response.setStatus(200);
					}else{
						result.put("error", "Invalid username or password");
						response.setStatus(400);
					}
					

				}
			}

		} catch (ParseException e) {
			result.put("error", "Invalid JSON");
			response.setStatus(400);
		}

		response.getWriter().write(result.toJSONString());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setHeader("Access-Control-Allow-Origin", "*");
		doPost(req, resp);
	}

}
