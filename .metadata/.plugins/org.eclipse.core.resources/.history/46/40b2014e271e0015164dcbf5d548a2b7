package controller.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.dao.DBUserDao;
import model.dao.UserDao;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import controller.UsersComparator;
import db.DBManager;

/**
 * Servlet implementation class LeaderBord
 */
@WebServlet("/leaderBoard")
public class LeaderBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LeaderBoard() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		// репрезентация на класация
		String line = null;
		try{
			line = request.getParameter("userId");
		}catch(NullPointerException e){
			System.out.println("Invalid input");
		}

		// test
		//line = "1";

		JSONArray result = new JSONArray();
		JSONObject error = new JSONObject();

		if (line != null && !line.isEmpty()) {
			int userId = Integer.parseInt(line);
			
			UserDao ud = new DBUserDao();
			if(ud.getUser(userId) == null){
				response.setStatus(400);
				error.put("error", "Invalid parameter");
				response.getWriter().write(error.toJSONString());
				return;
			}
			
			// int userPosition = -1;
			//
			// int count = 10;
			//
			// // използваме кеша -> друг вариант е от базата данни
			// ArrayList<User> list = (ArrayList<User>) getServletConfig()
			// .getServletContext().getAttribute("cacheUsers");
			// list.sort(new UsersComparator());
			// for (Iterator<User> i = list.iterator(); i.hasNext();) {
			// User u = i.next();
			// if (u.getId() == userId) {
			// userPosition = list.indexOf(u);
			//
			// }
			//
			// if (count > 0) {
			// JSONObject userObj = new JSONObject();
			// userObj.put("position", list.indexOf(u) + 1);
			// userObj.put("username", u.getUsername());
			// userObj.put("score", u.getScore());
			// result.add(userObj);
			// }
			// count--;
			//
			// }
			//
			// if (userPosition > 9) {
			// JSONObject userObj = new JSONObject();
			// userObj.put("position", (userPosition + 1));
			// userObj.put("username", list.get(userPosition).getUsername());
			// userObj.put("score", list.get(userPosition).getUsername());
			// result.add(userObj);
			// }

			result = topUsers(userId);

			if (result != null) {
				response.setStatus(200);
			} else {
				response.setStatus(400);
				error.put("error", "DB Error");
				response.getWriter().write(error.toJSONString());
				return;
			}

		} else {
			response.setStatus(400);
			error.put("error", "Invalid parameter");
			response.getWriter().write(error.toJSONString());
			return;
		}

		System.out.println(result.toJSONString());
		response.getWriter().write(result.toJSONString());
	}

	public JSONArray topUsers(int userId) {
		JSONArray result = new JSONArray();
		
		UserDao ud = new DBUserDao();
		
		int userPosition = ud.getUserPosition(userId);
		System.out.println(userPosition);
		
		User u = ud.getUser(userId);
		ArrayList<User> topUsers = ud.getTopUsers();
		topUsers.sort(new UsersComparator());

			for(int i = 0 ; i < topUsers.size(); i++){
				// topUsers.put(username, score);
				JSONObject userObj = new JSONObject();
				userObj.put("position", (i+1));
				userObj.put("username", topUsers.get(i).getUsername());
				userObj.put("score", topUsers.get(i).getScore());
				result.add(userObj);
				}
			
			if (userPosition > 9) {

				// topUsers.put(userUsername, userScore);
				JSONObject userObj = new JSONObject();
				userObj.put("position", (userPosition + 1));
				userObj.put("username", u.getUsername());
				userObj.put("score", u.getScore());
				result.add(userObj);

			}


		return result;

	}
}
