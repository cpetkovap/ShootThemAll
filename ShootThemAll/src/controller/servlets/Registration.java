package controller.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Vector;

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

import controller.SettingsManager;
import cache.UserCache;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Registration() {

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// test
		resp.setHeader("Access-Control-Allow-Origin", "*");
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");

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
		test.put("username", "Shoshi");
		test.put("password", "123");
		test.put("email", "abv@abv.bg");
		// inputText = test.toJSONString();

		JSONParser parser = new JSONParser();
		try {

			JSONObject userObj = (JSONObject) parser.parse(inputText);
			String username = userObj.get("username").toString();
			String password = userObj.get("password").toString();

			// MD5
			String cryptPass = SettingsManager.cryptMD5(password);
			password = cryptPass;

			String email = userObj.get("email").toString();
			String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			Boolean validEmail = email.matches(emailreg);
			if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty()
					&& validEmail) {

				/*
				 * Проверка дали този username e зает Проверкa в базата данни
				 * или в хеша
				 */

				User user = new User(username, password, email);
				UserDao ud = new DBUserDao();

				// проверка в хеша
				// if (Cache.getCache().isEmpty()) {

				// prowerqvame dali ima zapisi v bazata danni
				if (!ud.hasQuery()) {

					// намя записи => няма такъв потребител = > insert в базата
					// данни и
					// добавяне в хеша

					int userId = ud.addUser(user);

					if (userId > 0) {
						response.setStatus(200);
						result.put("userId", userId);
					} else {
						result.put("error", "DataBase error ");
						response.setStatus(400);

					}

				} else {
					// проверка дали съществува такъв запис
					User existUser = ud.getUser(user.getUsername());

					if (existUser != null) {

						result.put("error", "Existing username");
						response.setStatus(400);

					} else {
						int userId = ud.addUser(user);

						if (userId > 0) {
							response.setStatus(200);
							result.put("userId", userId);
						} else {
							result.put("error", "DataBase error ");
							response.setStatus(400);

						}

					}

				}

			} else {

				result.put("error", "Invalid JSON");
				response.setStatus(400);

			}

		} catch (ParseException e) {

			result.put("error", "Invalid JSON");
			response.setStatus(400);

		}
		System.out.println(result.toJSONString());
		response.getWriter().write(result.toJSONString());
	}
}
