/*
Student Name: Kai Yin Lau, Lisha Tan
Assignment # SOC Project
 Class: Fall 2013 Web App Development: CIS 4160 QMWA [2475] (Baruch )
Features Included: 1. get data from result page and get details, then send to details page

Advance Feature(if Any): 

References(if Any): 
Important: CODE COMMENTS

*/

package main.java.coreservlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/DetailsServlets")
public class DetailsServlets extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Set of default value will be use in DataManager.java
	private String selectAll = "00";
	private String empty = "";
	private String undergraduate = "U";
	private String graduate = "G";
	
	private String summerAll = "summer all";
	private String summer00 = "summer00";
	private String summer01 = "summer01";
	private String summer02 = "summer02";
	
	// Connection string data
	/*
	private String dbURL = "jdbc:oracle:thin:@localhost:1521:XE";
	private String dbUserName = "DB_USER";
	private String dbPassword = "1a14";
	private String driver = "oracle.jdbc.OracleDriver";
	*/
	private String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
	private String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
	private String app_name = System.getenv("OPENSHIFT_APP_NAME");
	
	private String dbURL = "jdbc:mysql://" + host + ":" + port + "/" + app_name;
	private String dbUserName = "adminnTVQIR3";
	private String dbPassword = "Ukgn4Ag14fp2";
	private String driver = "com.mysql.jdbc.Driver";
	
	// Instance of beans
	private DataBean detailsDataBean;
	private DataManager dataManager;
	
	
	// Initialize DB login information into DataManager.java
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dataManager = new DataManager();
	    
		dataManager.setSelectAll(selectAll);
		dataManager.setEmpty(empty);
		dataManager.setUndergraduate(undergraduate);
		dataManager.setGraduate(graduate);
		dataManager.setSummerAll(summerAll);
		dataManager.setSummer00(summer00);
		dataManager.setSummer01(summer01);
		dataManager.setSummer02(summer02);
		
		dataManager.setDbURL(dbURL);
	    dataManager.setDbUserName(dbUserName);
	    dataManager.setDbPassword(dbPassword);
		try {
			Class.forName(driver);
		}
		catch (Exception ex) {
			System.out.println("Initialize connector string");
			ex.printStackTrace();
		}
	}
	
    public DetailsServlets() {
        super();
    }
    
    // Call Methods and Save Data when press "Search" button in search page, then forward to result page
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		// Create session		
		HttpSession session = request.getSession(true);
		
		// Get data's from result page by link
		String semester = request.getParameter("semester");		
		String crs_cd = request.getParameter("cd");
		String crs_sec = request.getParameter("sec");
		
		// Run methods from DataManager.java and save them in sessions		
		detailsDataBean = new DataBean();
		detailsDataBean = dataManager.getDataDetails(semester, crs_sec, crs_cd);
		session.setAttribute("detailsDataBean", detailsDataBean);
		
		// Forward to detail page
		RequestDispatcher dispatcher = request.getRequestDispatcher("coursedetails");
		dispatcher.forward(request, response);	
	}

}
