package test;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BulletsBooster;
import model.HealthBooster;
import model.PointsBooster;
import model.User;
import model.Weapon;
import model.dao.DBUserDao;
import model.dao.DBWeaponDao;
import model.dao.UserDao;
import model.dao.WeaponDao;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().write("<html><head></head>");
		response.getWriter().write("<h1>Hello World</h1>");
		
		WeaponDao wd = new DBWeaponDao();
		
//		ArrayList<Weapon> list = (ArrayList<Weapon>) wd.getWeapons();
//		StringBuffer sb = new StringBuffer();
//		for(int i =  0 ; i < list.size(); i++){
//			sb.append(list.get(i).getType() + " ");
//		}
//		System.out.println(sb.toString());
//		
//		Weapon w = wd.getWeapon(1);
//		System.out.println("price : " + w.getPrice());
//		
//		User a = new User("Petkan", "121", "aa@aaa.aa");
		UserDao ud = new DBUserDao();
//		ud.addUser(a);
		
		System.out.println();
//		User u1 = ud.getUser("Petq");
//		System.out.println(u1.getUsername());
//		System.out.println(ud.hasQuery());
		
//		ud.updateScore(3, 1);
		//User u2 = ud.getUserWithMaxScore();
		//System.out.println(u2.getUsername() + "  " + u2.getScore());
		
//		int score = ud.getUserScore(1);
//		System.out.println(score);
		
//		BulletsBooster bb = new BulletsBooster(10);
//		
//		System.out.println(bb.getId() + " " + bb.getDuration() + " " + bb.getDescription() + " " + bb.getNumBullets() );
//		
//		
//		HealthBooster bb2 = new HealthBooster(11);
//		System.out.println(bb2.getId() + " " + bb2.getDuration() + " " + bb2.getDescription() + " " + bb2.getHealtPoints() );
//		
//		PointsBooster pd = new PointsBooster(22);
//		System.out.println(pd.getId() + " " + pd.getDuration() + " " + pd.getDescription() + " " + pd.getPoints() );
		
		
		response.getWriter().write("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
