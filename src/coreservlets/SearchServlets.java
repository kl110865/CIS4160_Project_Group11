/*
Student Name: Kai Yin Lau, Lisha Tan
Assignment # SOC Project
 Class: Fall 2013 Web App Development: CIS 4160 QMWA [2475] (Baruch )
Features Included: 1. Set default value and default DB information
				   2. Using "doPost" to get input data, process result set and save it in session

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

import java.util.*;


@WebServlet("/SearchServlets")
public class SearchServlets extends HttpServlet {
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
	private DataBean dataBean;
	private ArrayList<DataBean> resultList;
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
	
    public SearchServlets() {
        super();
    }
    
    // Call Methods and Save Data when press "Search" button in search page, then forward to result page
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		// Get data's input from search page
		String semester = request.getParameter("semester");
		String disc = request.getParameter("discipline");
		String crs_num = request.getParameter("number");
		String instructor = request.getParameter("prof");
		String time_a_b = request.getParameter("time_a_b");
		String time = request.getParameter("time");
		String div_undr = request.getParameter("div_undr");
		String div_grad = request.getParameter("div_grad");
		if(div_undr == null) { div_undr = "none selected"; }
		if(div_grad == null) { div_grad = "none selected"; }
		String meeting_days = request.getParameter("week");
		String dept = request.getParameter("department");
		
		// Create session
		HttpSession session = request.getSession(true);
		
		
		if((time_a_b.equals(selectAll) && time.equals(selectAll)) || (!time_a_b.equals(selectAll) && !time.equals(selectAll))) {
			// Run methods from DataManager.java and save them in sessions
			dataBean = new DataBean();
			dataBean = dataManager.getInputAndLastUpdate(semester, disc, dept, crs_num, instructor, time_a_b, time, div_undr, div_grad, meeting_days);
			session.setAttribute("dataBean", dataBean);
			resultList = new ArrayList<DataBean>(dataManager.getDataList(semester, disc, dept, crs_num, instructor, time_a_b, time, div_undr, div_grad, meeting_days));
			session.setAttribute("resultList", resultList);
			
			// Forward to result page
			RequestDispatcher dispatcher = request.getRequestDispatcher("scheduleresults");
			dispatcher.forward(request, response);	
		}
		else {
	    	// Set value of "errormsg" in order to make "login.jsp" shows error message
	    	session.setAttribute("errormsg", "errorMsg");
	    	// Stay in same page if validation fail
	    	String encodedURL = response.encodeRedirectURL("schedulesearch");
	    	response.sendRedirect(encodedURL);
	    }
		
	}

}
