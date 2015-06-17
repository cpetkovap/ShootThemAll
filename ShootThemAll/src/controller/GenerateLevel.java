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
 * Servlet implementation class GenerateLevel
 */
@WebServlet("/GenerateLevel")
public class GenerateLevel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public GenerateLevel() {

    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String line = request.getReader().readLine();
		
		//test
		JSONObject test = new JSONObject();
		test.put("userId", 1);
		test.put("level", 3);
		line = test.toJSONString();
		
		JSONParser parser = new JSONParser();
		try {
			JSONObject levelObj = (JSONObject) parser.parse(line);
			int userId = Integer.parseInt(levelObj.get("userId").toString());		
			int level = Integer.parseInt(levelObj.get("level").toString());
			
			LevelBuilder levelBuilder = new LevelBuilder();
			JSONObject result = levelBuilder.buildLevel(userId, level);
			
			System.out.println(result.toJSONString());
			response.setStatus(200);
			response.getWriter().write(result.toJSONString());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
