package controller.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cache.Cache;
import cache.UserCache;
import controller.SettingsManager;

/**
 * Servlet implementation class UpdateProfile
 */
@WebServlet("/userInfoManager")
public class UserInfoManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserInfoManager() {

	}


	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// репрезентация на информацията за потребителя
		response.setHeader("Access-Control-Allow-Origin", "*");

		UserDao ud = new DBUserDao();
		Cache cache = Cache.getCache();
		UserCache users = (UserCache) cache.getCacheItems().get("users");

		String line = null;
		try {
			line = request.getParameter("userId");
		} catch (NullPointerException e) {
			System.out.println("Invalid input");
		}
		// test
		// line = "1";

		JSONObject result = new JSONObject();

		if (line != null && !line.isEmpty()) {

			if (!ud.hasQuery()) {

				result.put("error", "No Users ! Error");
				response.setStatus(400);

			} else {

				int userId = -1;
				try{
					userId = Integer.parseInt(line);
				}catch(NumberFormatException e){
					response.setStatus(400);
					result.put("error", "Invalid parameter");
					response.getWriter().write(result.toJSONString());
					return;
				}

				
				User u = null;
				if (users != null) {
					u = users.getUser(userId);
				} else {
					u = ud.getUser(userId);
					if (u != null) {
						users.addUser(ud.getUser(userId));
					}
				}

				if (u != null) {
					result = makeUserJSON(u);
					response.setStatus(200);
				} else {
					result.put("error", "Invalid parameter");
					response.setStatus(400);

				}

			}

		} else {
			result.put("error", "Invalid parameter");
			response.setStatus(400);
		}

		response.getWriter().write(result.toJSONString());
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// промяна на информацията за потребителя
		response.setHeader("Access-Control-Allow-Origin", "*");

		UserDao ud = new DBUserDao();
		Cache cache = Cache.getCache();
		UserCache users = (UserCache) cache.getCacheItems().get("users");

		JSONObject result = new JSONObject();

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
		test.put("userId", "1");
		// test.put("password", "123");
		test.put("email", "promqna@aa");
		// test.put("allowNotification", true);
		// inputText = test.toJSONString();


		if (!ud.hasQuery()) {
			result.put("error", "No users");
			response.setStatus(400);

		} else {

			JSONParser parser = new JSONParser();
			int userId = -1;
			try {

				JSONObject userObj = (JSONObject) parser.parse(inputText);
				try{
					userId = Integer.parseInt(userObj.get("userId").toString());
				}catch(NumberFormatException e){
					throw new ParseException(1);
				}

				/*
				 * Промяна на информацията за потребителя в базата данни и в
				 * кеша
				 */

				

				User u = null;
				if (users != null) {
					u = users.getUser(userId);
				} else {
					u = ud.getUser(userId);
					if (u != null) {
						users.addUser(ud.getUser(userId));
					}
				}

				if (u == null) {
					throw new ParseException(1);
				}

				try {

					String password = userObj.get("password").toString();
					if (password.isEmpty()) {
						throw new NullPointerException();
					}

					// MD5
					String cryptPass = SettingsManager.cryptMD5(password);
					password = cryptPass;

					// update password:

					// - update in database --> to do

					ud.updatePassword(password, userId);

					// - update in cache
					users.update("password", password, userId);
					
				} catch (NullPointerException e) {
					// e.printStackTrace();
					System.out.println("no change pass");
				}

				try {

					String email = userObj.get("email").toString();
					String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

					if (email.isEmpty()) {
						throw new NullPointerException();

					}

					Boolean validEmail = email.matches(emailreg);
					if (validEmail) {

						// update email:

						// - update in database --> to do

						ud.updateEmail(email, userId);

						// - update in cache
						users.update("email", email, userId);
					
					}else{
						throw new NullPointerException();
					}
				} catch (NullPointerException e) {
					System.out.println("no change email");
				}

				try {

					boolean allowNotification = Boolean.parseBoolean(userObj
							.get("allowNotification").toString());
					// update notification:

					// - update in database --> to do
					ud.updateNotification(allowNotification, userId);

					// - update in cache
					users.update("allowNotification",
							String.valueOf(allowNotification), userId);
					
				} catch (NullPointerException e) {
					System.out.println("no change allowNotification");
				}

				response.setStatus(200);
				result.put("message", "success");

			} catch (ParseException e) {

				result.put("error", "Invalid JSON");
				response.setStatus(400);

			}
		}

		
		System.out.println(result.toJSONString());
		response.getWriter().write(result.toJSONString());
	}

	public JSONObject makeUserJSON(User u) {
		JSONObject result = new JSONObject();
		result.put("userId", u.getId());
		result.put("username", u.getUsername());
		result.put("password", u.getPassword());
		result.put("email", u.getEmail());
		result.put("allowNotification", u.isAllowNotification());
		result.put("score", u.getScore());
		result.put("level", u.getLevel());
		JSONObject userWeaponObj = new JSONObject();
		userWeaponObj.put("type", u.getWeapon().getType());
		userWeaponObj.put("damage", u.getWeapon().getDamage());
		result.put("weapon", userWeaponObj);
		return result;

	}

}
