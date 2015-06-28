package controller.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Achievement;
import model.User;
import model.dao.AchievementDao;
import model.dao.DBAchievementDao;
import model.dao.DBUserDao;
import model.dao.UserDao;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cache.Cache;
import cache.UserCache;
import controller.LevelBuilder;
import controller.SettingsManager;

/**
 * Servlet implementation class SaveLevelInfo
 */
@WebServlet("/levelManager")
public class LevelManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	public LevelManager() {

	}

//	 @Override
//	 protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//	 throws ServletException, IOException {
//	 // TODO Auto-generated method stub
//	 doPost(req, resp);
//	 }

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String user = request.getParameter("userId");
		String userLevel = request.getParameter("level");

		// test
		user = "1";
		userLevel = "2";

		JSONObject result = new JSONObject();

		if (user != null && !user.isEmpty() && userLevel != null
				&& !userLevel.isEmpty()) {

			int userId = Integer.parseInt(user);
			int level = Integer.parseInt(userLevel);

			LevelBuilder levelBuilder = new LevelBuilder();

			try {
				result = levelBuilder.buildLevel(userId, level);
			} catch (IllegalArgumentException e) {
				response.setStatus(400);
				result.put("error", "Invalid level");
			}

			response.setStatus(200);

		} else {

			response.setStatus(400);
			result.put("error", "Invalid parameter");
		}

		System.out.println(result.toJSONString());
		response.getWriter().write(result.toJSONString());
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		Cache cache = Cache.getCache();
		UserCache users = (UserCache) cache.getCacheItems().get("users");

		String message = null;
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
		test.put("userId", 1);
		test.put("level", 1);
		test.put("score", 800);
		inputText = test.toJSONString();

		JSONParser parser = new JSONParser();
		try {
			JSONObject scoreObj = (JSONObject) parser.parse(inputText);
			int userId = Integer.parseInt(scoreObj.get("userId").toString());
			int level = Integer.parseInt(scoreObj.get("level").toString());
			int score = Integer.parseInt(scoreObj.get("score").toString());

			UserDao ud = new DBUserDao();

			if (score > 0) {
				/*
				 * Тук записваме точките на потребителя с userId в базата данни
				 * Правим проверка дали потребителя е минал нечии точки и
				 * уведомяваме през мейл
				 * 
				 * Тук обновяваме освен записа в базата данни, но и кеша с
				 * потребители
				 */

				int userLevel = 0;
				if (users.getAllUsers() != null) {
					User u = users.getUser(userId);
					if(u != null){
						userLevel = u.getLevel();
					}
				} else {
					userLevel = ud.getUserLevel(userId);
					if(userLevel > 0){
						users.addUser(ud.getUser(userId));
					}
				}
				
				if (userLevel < SettingsManager.getMaxLevel()) {
					if (userLevel == level) {
						ud.updateLevelUp(userId);
						users.updateLevelUp(userId);
					}
				}

				User topUserBeforeUpdate = ud.getUserWithMaxScore();
				// System.out.println(topUserBeforeUpdate.getUsername());
				ud.updateScore(score , userId);
				users.updateScore(score , userId);

				addAchievment(userId, ud.getUserScore(userId));

				int userScore = ud.getUserScore(userId);
				User topUserAfterUpdate = ud.getUserWithMaxScore();
				// System.out.println(topUserAfterUpdate.getUsername());

				if (!topUserBeforeUpdate.getUsername().equals(
						topUserAfterUpdate.getUsername())
						&& topUserBeforeUpdate.isAllowNotification()) {
					String mailTo = topUserBeforeUpdate.getEmail();
					// мейл тест
					
					String mailMessage = "Hi " + topUserAfterUpdate.getUsername() + ",\n" 
							+ "You are no longer with the best score !!! \n " 
							+ "Be the first! Play again !";
					sendEmail("shootthemallgame@gmail.com", mailTo,
							"Game massage", mailMessage);
				}

			}

			message = "success";
			response.setStatus(200);
			result.put("message", message);

		} catch (ParseException e) {

			result.put("error", "Invalid JSON");
			response.setStatus(400);

		}
		System.out.println(result.toJSONString());
		response.getWriter().write(result.toJSONString());

	}

	public void sendEmail(String fromEmailAddr, String toEmailAddr,
			String subject, String body) {

		final String meilusername = "shootthemallgame@gmail.com";
		final String meilpassword = "shoot123";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(meilusername,
								meilpassword);
					}
				});

		MimeMessage message = new MimeMessage(session);
		try {

			message.setFrom(new InternetAddress(fromEmailAddr));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					toEmailAddr));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
			System.out.println("Send email :)");
		} catch (MessagingException ex) {
			System.err.println("Cannot send email. " + ex);
		}
	}

	public void addAchievment(int userId, int score) {
		AchievementDao ad = new DBAchievementDao();
		ArrayList<Achievement> userAcheivment = ad.getUserAchievements(userId);
		ArrayList<Achievement> acheivment = ad.getRecentAcheivment(score);

		if (userAcheivment.isEmpty()) {
			if (!acheivment.isEmpty()) {
				for (int i = 0; i < acheivment.size(); i++) {
					ad.setUserAchievement(userId, acheivment.get(i).getId());
					System.out.println(acheivment.get(i).getId());
				}
			}
		} else {
			if (!acheivment.isEmpty()) {
				boolean exist = false;
				for (int i = 0; i < acheivment.size(); i++) {
					// if(!userAcheivment.contains(acheivment.get(i))){
					// ad.setUserAchievement(userId, acheivment.get(i).getId());
					// }
					if (!ad.existAcheivment(acheivment.get(i).getId())) {
						ad.setUserAchievement(userId, acheivment.get(i).getId());
					}
				}

			}

		}

	}

}