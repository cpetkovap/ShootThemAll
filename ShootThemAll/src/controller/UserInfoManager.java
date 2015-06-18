package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doPost(req, resp);
//	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// репрезентация на информацията за потребителя

		String line = request.getParameter("userId");
		// test
		line = "2";

		JSONObject result = new JSONObject();

		if (line != null) {

			if (getServletContext().getAttribute("cacheUsers") == null) {

				result.put("error", "No Users ! Error");
				response.setStatus(400);

			} else {

				int userId = Integer.parseInt(line);

				// използваме кеша -> друг вариант е от базата данни
				ArrayList<User> list = (ArrayList<User>) getServletConfig()
						.getServletContext().getAttribute("cacheUsers");

				boolean existUser = false;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getId() == userId) {
						existUser = true;
						result.put("userId", list.get(i).getId());
						result.put("username", list.get(i).getUsername());
						result.put("password", list.get(i).getPassword());
						result.put("email", list.get(i).getEmail());
						result.put("allowNotification", list.get(i)
								.isAllowNotification());
						result.put("score", list.get(i).getScore());
						result.put("level", list.get(i).getLevel());
						JSONObject userWeaponObj = new JSONObject();
						userWeaponObj.put("type", list.get(i).getWeapon()
								.getType());
						userWeaponObj.put("damage", list.get(i).getWeapon()
								.getDamage());
						result.put("weapon", userWeaponObj);
					}
				}

				if (!existUser) {
					result.put("error", "Not existing id");
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

		String line = request.getReader().readLine();

		// test
		JSONObject test = new JSONObject();
		test.put("userId", "2");
		test.put("password", "123");
		test.put("email", "aaa@aa");
		test.put("allowNotification", true);
		line = test.toJSONString();

		JSONObject result = new JSONObject();

		if (getServletContext().getAttribute("cacheUsers") == null) {

			result.put("error", "No users in cache");
			response.setStatus(400);

		} else {

			JSONParser parser = new JSONParser();
			try {

				JSONObject userObj = (JSONObject) parser.parse(line);

				int userId = Integer.parseInt(userObj.get("userId").toString());
				System.out.println(userId);

				/*
				 * Промяна на информацията за потребителя в базата данни и в
				 * кеша
				 */
				
				//използваме кеша -> друг вариант е от базата данни
				ArrayList<User> list = (ArrayList<User>) getServletConfig()
						.getServletContext().getAttribute("cacheUsers");
				int user = -1;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getId() == userId){
						user = list.get(i).getId();
						System.out.println("aaa " + user );
						}
					}

				try {

					String password = userObj.get("password").toString();
					// update password:

					// - update in database  --> to do
					
					// - update in cache
					list.get(user).setPassword(password);
					getServletConfig().getServletContext().setAttribute("cacheUsers", list);
				} catch (NullPointerException e) {
				}

				try {

					String email = userObj.get("emal").toString();
					// update email:

					// - update in database --> to do

					// - update in cache
					list.get(user).setEmail(email);
					getServletConfig().getServletContext().setAttribute("cacheUsers", list);
				} catch (NullPointerException e) {
				}

				try {

					boolean allowNotification = Boolean.parseBoolean(userObj
							.get("allowNotification").toString());
					// update notification:

					// - update in database --> to do

					//- update in cache
					list.get(user).setAllowNotification(allowNotification);
					getServletConfig().getServletContext().setAttribute("cacheUsers", list);
				} catch (NullPointerException e) {
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

}
