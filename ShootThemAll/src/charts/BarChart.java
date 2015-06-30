	
	package charts;

	import java.io.File;
	import java.io.IOException;
	import java.util.ArrayList;

	import model.User;
	import model.dao.DBUserDao;
	import model.dao.UserDao;

	import org.jfree.chart.ChartFactory;
	import org.jfree.chart.ChartUtilities;
	import org.jfree.chart.JFreeChart;
	import org.jfree.chart.plot.PlotOrientation;
	import org.jfree.data.category.DefaultCategoryDataset;

	public class BarChart {
		UserDao ud = new DBUserDao();
		final static String chartTitle = "ScoreChart";
		final static String OX = "Users";
		final static String OY = "Score";
		
		public BarChart() {
			 JFreeChart barChart = ChartFactory.createBarChart3D(
					 chartTitle,           
			         OX,     
			         OY,      
			         createBarChart(),          
			         PlotOrientation.VERTICAL,           
			         true, true, false);
			  int width = 600; 
		      int height = 400;  
		      
		      File BarChart = new File( "WebContent/BarChart.jpeg" ); 
				try {
					ChartUtilities.saveChartAsJPEG( BarChart , barChart , width , height );
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		public DefaultCategoryDataset createBarChart(){
			ArrayList<User> users =  ud.getTopUsers();
			final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
			final String score = "score";  
			for(int i=0; i<users.size();i++){
			      dataset.addValue(users.get(i).getScore(), users.get(i).getUsername(), score);
			    }
			      return dataset; 
		}		
	}


