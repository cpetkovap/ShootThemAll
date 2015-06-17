package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class LevelsMap
 */
@WebServlet("/LevelsMap")
public class LevelsMap extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LevelsMap() {

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String line = request.getReader().readLine();

		// test
		JSONObject test = new JSONObject();
		test.put("userId", 1);
		line = test.toJSONString();
		
		JSONObject result = new JSONObject();

		int numberOfAllLevels = LevelSingleton.getMaxLevel();

		JSONParser parser = new JSONParser();
		try {
			JSONObject userIdObj = (JSONObject) parser.parse(line);
			int userId = Integer.parseInt(userIdObj.get("userId").toString());

			int numberOfActiveLevels = 1;
			/*
			 * 
			 * тук взимаме от потребителя нивото до което е стигнал от базата
			 * данни
			 */

			if (numberOfActiveLevels > numberOfAllLevels) {
				numberOfActiveLevels = numberOfAllLevels;
			}

			result.put("numberOfAllLevels", numberOfAllLevels);
			result.put("numberOfActiveLevels", numberOfActiveLevels);

			response.setStatus(200);
			response.getWriter().write(result.toJSONString());

		} catch (ParseException e) {

			result.put("error", "Invalid JSON");
			response.setStatus(400);
			
		}
	}

}
