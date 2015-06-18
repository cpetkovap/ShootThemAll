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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// репрезентация на информацията за потребителя
		
		String line = request.getParameter("userId");
		//test
		line = "2";
		
		JSONObject result = new JSONObject();
		
		if(line != null){
			
			if (getServletContext().getAttribute("cacheUsers") == null) {

				result.put("error", "No Users ! Error");
				response.setStatus(400);

			} else {
				
				int userId = Integer.parseInt(line);
				
				ArrayList<User> list = (ArrayList<User>) getServletConfig()
						.getServletContext().getAttribute("cacheUsers");

				boolean existUser = false;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getId() == userId) {
						existUser = true;
						result.put("userId", list.get(i).getId());
						result.put("username", list.get(i).getUsername());
						result.put("email", list.get(i).getEmail());
						result.put("allowNotification", list.get(i).isAllowNotification());
						result.put("score", list.get(i).getScore());
						result.put("level", list.get(i).getLevel());
						JSONObject userWeaponObj = new JSONObject();
						userWeaponObj.put("type", list.get(i).getWeapon().getType());
						userWeaponObj.put("damage", list.get(i).getWeapon().getDamage());
						result.put("weapon", userWeaponObj);
					}
				}
				
				if(!existUser){
					result.put("error", "Not existing id");
					response.setStatus(400);
				}
				
			}
			
		}else{
			result.put("error", "Invalid parameter");
			response.setStatus(400);
		}
		
		response.getWriter().write(result.toJSONString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// промяна на информацията за потребителя
	}

}
