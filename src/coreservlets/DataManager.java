/*
Student Name: Kai Yin Lau, Lisha Tan
Assignment # SOC Project
 Class: Fall 2013 Web App Development: CIS 4160 QMWA [2475] (Baruch )
Features Included: 1. Get user input from search page and save them in DataBean format
 				   2. Process result page depends on user input
 				   3. Process details data to show in details page

Advance Feature(if Any): 

References(if Any): SQL left outer join (http://www.w3schools.com/sql/sql_join_left.asp)
					Optional prepared statement's parameters (http://www.coderanch.com/
						t/300635/JDBC/databases/PreparedStatement-number-parameters-runtime)
					SQL CASE WHEN
						(http://stackoverflow.com/questions/5024979/oracle-sql-help-using-case-in-a-select-statement)
Important: CODE COMMENTS

*/

package main.java.coreservlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataManager {
	// Set of default value
	private String selectAll = "";
	private String empty = "";
	private String undergraduate = "";
	private String graduate = "";
	
	private String summerAll = "";
	private String summer00 = "";
	private String summer01 = "";
	private String summer02 = "";
	
	/*
	// Use for Auto Complete
	private int totalName;
    private List<String> profNameList;
    */
	
	// DB connection data
	private String dbURL = "";
	private String dbUserName = "";
	private String dbPassword = "";
	
	// DB connection data get/set methods
	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}
	
	public String getDbURL() {
		return dbURL;
	}
	
	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
    }
    
    public String getDbUserName() {
    	return dbUserName;
    }
    
    public void setDbPassword(String dbPassword) {
    	this.dbPassword = dbPassword;
    }
    
    public String getDbPassword() {
    	return dbPassword;
    }
    
    // Set of default value get/set methods
    public String getSelectAll() {
		return selectAll;
	}

	public void setSelectAll(String selectAll) {
		this.selectAll = selectAll;
	}

	public String getEmpty() {
		return empty;
	}

	public void setEmpty(String empty) {
		this.empty = empty;
	}

	public String getUndergraduate() {
		return undergraduate;
	}

	public void setUndergraduate(String undergraduate) {
		this.undergraduate = undergraduate;
	}

	public String getGraduate() {
		return graduate;
	}

	public void setGraduate(String graduate) {
		this.graduate = graduate;
	}

	public String getSummerAll() {
		return summerAll;
	}

	public void setSummerAll(String summerAll) {
		this.summerAll = summerAll;
	}

	public String getSummer00() {
		return summer00;
	}

	public void setSummer00(String summer00) {
		this.summer00 = summer00;
	}

	public String getSummer01() {
		return summer01;
	}

	public void setSummer01(String summer01) {
		this.summer01 = summer01;
	}

	public String getSummer02() {
		return summer02;
	}

	public void setSummer02(String summer02) {
		this.summer02 = summer02;
	}

	
    // Get DB Connection
	public Connection getConnection() {
		Connection conn = null;
	    try {
	    	conn = DriverManager.getConnection(getDbURL(), getDbUserName(), getDbPassword());
	    }
	    catch (SQLException e) {
	    	System.out.println("Could not connect to DB");
	    	e.printStackTrace();
	    	
	    }
	    return conn;
	}
	
	// Close DB Connection
	public void closeConnection(Connection conn) {
	    if (conn != null) {
	    	try { 
	    		conn.close(); 
	    	}
	    	catch (SQLException e) {
	    		System.out.println("Enable to close DB connection");
	    		e.printStackTrace(); 
	    	}
	    }
    }
	
	// Set Summer Sections Start Date and End Date from DB
	public DataBean getStartDateOfSummerSec(String semester) throws SQLException {
		DataBean setStartAndEndDate = new DataBean();
		
		Connection conn = getConnection();	
		
		if (conn != null) {
			ResultSet rs = null;	    	
	    	PreparedStatement ps = null;
	    	
	    	
			try {
				// Query for searching summer section start date and end date
				// Then set it in DataBean
				String pStatement = "SELECT TO_CHAR(START_DATE, 'MM/DD/YY') AS START_DATE,"
										+ " TO_CHAR(END_DATE, 'MM/DD/YY') AS END_DATE"
										+ " FROM SEMESTER_SCHEDULE_READER"
										+ " WHERE SEMESTER_NAME = ?";
				
				ps = conn.prepareStatement(pStatement);
			    ps.setString(1, semester);
			    rs = ps.executeQuery();
			    
				while(rs.next()) {					
					setStartAndEndDate.setSummer_sDate(rs.getString("START_DATE"));
					setStartAndEndDate.setSummer_eDate(rs.getString("END_DATE"));					
				}
			}//end of try
			catch(SQLException ex){ex.printStackTrace();}
			finally {
				try {
	        		ps.close();
					rs.close();	        		
	        	}
	          	catch (SQLException e) {e.printStackTrace();}
	          	closeConnection(conn);	        
	        }//end of finally
	    }//end of if
		return setStartAndEndDate;
	}
	
	
	// Use for translate 24hours based time to 12hours based time
	// Use it in DataManager.java -> getInputAndLastUpdate(...)
	public String getTimeIn12hFormat(String time) {
		String amPm = "am";
		int timeForProcess = Integer.parseInt(time);
		if(timeForProcess > 12) {
			timeForProcess -= 12;
			amPm = "pm";
		}
		String formattedTime = Integer.toString(timeForProcess) + ":00" + amPm;
		return formattedTime;
	}
		
	
	// Get user input from schedulesearch.jsp and set them in DataBean format
	// Then they will show in result page
	public DataBean getInputAndLastUpdate(String semester, String disc, String dept, String crs_num, String instructor,
			String time_a_b, String time, String div_undr, String div_grad, String meeting_days) {
		
		DataBean dataBean = new DataBean();
		Connection conn = getConnection();	
				
		if (conn != null) {
			ResultSet rs = null;
	    	ResultSet rs1 = null;
	    	ResultSet rs2 = null;
	    	PreparedStatement ps = null;
	    	PreparedStatement ps1 = null;
	    	PreparedStatement ps2 = null;
	    	
	    	String semesterForSearch = semester.toLowerCase();
	    	
	    	if(semesterForSearch.equals(getSummerAll())|| 
	 	       semesterForSearch.equals(getSummer00()) || 
	 	       semesterForSearch.equals(getSummer01()) || 
	 	       semesterForSearch.equals(getSummer02())) {
	    		semesterForSearch = "summer";
	    	}
	    	
	    	
			try {
				// Query semester with year, and then set it into dataBean				
				String pStatement = "SELECT"
										//+ " SEMESTER_NAME ||' '||EXTRACT(YEAR FROM START_DATE)AS SEMESTER"
										+ " CONCAT(SEMESTER_NAME, ' ', EXTRACT(YEAR FROM START_DATE))AS SEMESTER"
										+ " FROM SEMESTER_SCHEDULE_READER"
										+ " WHERE SEMESTER_NAME = ?"; 
				ps = conn.prepareStatement(pStatement);
				ps.setString(1, semester);
				rs = ps.executeQuery();
				while(rs.next()) {
			    	dataBean.setSemesterInput(rs.getString("SEMESTER"));
			    }
				
				// Set the rest user input from sechedulesearch.jsp			    
				if(!disc.equals(getSelectAll())) { dataBean.setDiscInput(disc); }
				else { dataBean.setDiscInput("Select All"); }				
				if(!crs_num.equals(getEmpty())) { dataBean.setCrsNumInput(crs_num); }
				else { dataBean.setCrsNumInput("none entered"); }				
				if(!instructor.equals(getEmpty())) { dataBean.setInstructorInput(instructor); }
				else { dataBean.setInstructorInput("none entered"); }
				if(div_undr.equals(getUndergraduate()) && div_grad.equals(getGraduate())) { dataBean.setDiviInput("Select All"); }
				if(div_undr.equals(getUndergraduate()) && !div_grad.equals(getGraduate())) { dataBean.setDiviInput("Undergraduate"); }			    
			    if(!div_undr.equals(getUndergraduate()) && div_grad.equals(getGraduate())) { dataBean.setDiviInput("Graduate"); }
			    if(!div_undr.equals(getUndergraduate()) && !div_grad.equals(getGraduate())) { dataBean.setDiviInput("none selected"); }
				if(!time_a_b.equals(getSelectAll()) && !time.equals(getSelectAll())) { dataBean.setTimeInput(time_a_b + " " + getTimeIn12hFormat(time)); }
				else { dataBean.setTimeInput("Select All"); }
				if(!meeting_days.equals(getSelectAll())) { dataBean.setDaysInput(meeting_days); }
				else { dataBean.setDaysInput("Select All"); }
				
				// Query department name and put it in dataBean
				if(!dept.equals(getSelectAll())) {
					String pStatement2 = "SELECT DEPT_NAME"
							+ " FROM DEPT_SCHEDULE_READER"
							+ " WHERE DEPT_ID = ?"
							+ " ORDER BY DEPT_NAME";
					ps2 = conn.prepareStatement(pStatement2);
					ps2.setString(1, dept);
					rs2 = ps2.executeQuery();
					while(rs2.next()) {
						dataBean.setDeptInput(rs2.getString("DEPT_NAME"));
				    }				
				}
				else { dataBean.setDeptInput("Select All"); }				
				
				// Query for searching semester, get its update time and set it in dataBean
				String pStatement1 = "SELECT UPDATE_TIME"
										 + " FROM UPDATE_TIME_SCHEDULE_READER"
										 + " WHERE SEMESTER = ?";
			    ps1 = conn.prepareStatement(pStatement1);
			    ps1.setString(1, semesterForSearch);
			    rs1 = ps1.executeQuery();
				while(rs1.next()) {
					dataBean.setLastUpdateTime(rs1.getString("UPDATE_TIME"));
				}						
			}//end of try
			catch(SQLException ex){ex.printStackTrace();}
			finally {
				try {
	        		rs.close();
	        		if(rs1 != null) rs1.close();
	        		ps.close();
	        		ps1.close();
	        	}
	          	catch (SQLException e) {e.printStackTrace();}
	          	closeConnection(conn);	        
	        }//end of finally
	    }//end of if
		return dataBean;
	}
	
	// Create list of schedule search result
	public ArrayList<DataBean> getDataList(String semester, String disc, String dept, String crs_num, String instructor,
			String time_a_b, String time, String div_undr, String div_grad, String meeting_days) {
		
		ArrayList<DataBean> list = new ArrayList<DataBean>();
		
		Connection conn = getConnection();
		
		
	    if (conn != null) {
	    	ResultSet rs = null;
	    	PreparedStatement ps = null;
	    	
	    	String semesterForSearch = semester.toLowerCase();
	    	
	    	if(semesterForSearch.equals(getSummerAll())|| 
	    	   semesterForSearch.equals(getSummer00()) || 
	    	   semesterForSearch.equals(getSummer01()) || 
	    	   semesterForSearch.equals(getSummer02())) {
	    		semesterForSearch = "summer";
	    	}
	    	
	    	int paramsSet = 1;
	    	
			try {
				// Query for getting information, formating data with SQL				
				/*
				String pStatement = "SELECT DISTINCT SEC.DISC||' '|| SEC.CRS_NUM AS COURSE,"
						+ " SEC.CRS_CD,"
						+ " SEC.CRS_SEC,"
						+ " SEC.MEETING_DAYS||' '||SEC.START_TIME||' - '||SEC.STOP_TIME||LOWER(SEC.AM_PM) AS \"DAY & TIME\","
						+ " TO_CHAR(SEC.START_DATE,'MM/DD/YY')|| ' - ' ||TO_CHAR(SEC.END_DATE,'MM/DD/YY') AS DATES,"
						+ " SEC.BUILDING||' '||SEC.RM AS \"BUILDING & RM\","
						+ " UPPER(SEC.INSTRUCTOR_LNAME) AS INSTRUCTOR_LNAME,"
						+ " TO_CHAR(SEC.SEATS_AVAIL,'0000') AS SEATS_AVAIL,"
						+ " NVL(COMM.CRS_COMMENTS1, ' ') AS CRS_COMMENTS1,"
						+ " DISCT.DEPT_ID,"
						
						
						// Create start time in 24hours format to use in time search
						+ " CASE WHEN SEC.AM_PM = 'PM' AND SEC.STOP_TIME != ':' AND SEC.START_TIME != 'ON:LI'"
						+ " AND SUBSTR(SEC.STOP_TIME, 1, 2) + 12 < 24"
						+ " AND SUBSTR(SEC.START_TIME, 1, 2) + 12 <= SUBSTR(SEC.STOP_TIME, 1, 2) + 12"
						+ " THEN TO_CHAR(SUBSTR(SEC.START_TIME, 1, 2) + 12)"
						+ " ELSE SUBSTR(SEC.START_TIME, 1, 2) END AS START_TIME_24H"
						
						
						
						+ " FROM CRS_SEC_SCHEDULE_READER SEC"
						+ " LEFT OUTER JOIN CRS_COMMENTS_SCHEDULE_READER COMM"
						+ " ON (SEC.SEMESTER = COMM.SEMESTER"
						+ " AND SEC.CRS_CD = COMM.CRS_CD)"
						+ " LEFT OUTER JOIN DISCIPLINE_SCHEDULE_READER DISCT"
						+ " ON (SEC.DISC = DISCT.DISC_ABBREVIATION)"
						+ " WHERE SEC.SEMESTER = ?";
				*/
				
				String pStatement = "SELECT DISTINCT CONCAT(SEC.DISC, ' ', SEC.CRS_NUM)AS COURSE,"
						+ " SEC.CRS_CD,"
						+ " SEC.CRS_SEC,"
						+ " CONCAT(SEC.MEETING_DAYS, ' ', SEC.START_TIME, ' - ', SEC.STOP_TIME, LOWER(SEC.AM_PM))AS 'DAY & TIME',"
						+ " CONCAT(date_format(SEC.START_DATE,'%m/%d/%y'), ' - ' , DATE_FORMAT(SEC.END_DATE,'%m/%d/%y'))AS DATES,"
						+ " CONCAT(SEC.BUILDING, ' ', SEC.RM)AS 'BUILDING & RM',"
						+ " UPPER(SEC.INSTRUCTOR_LNAME) AS INSTRUCTOR_LNAME,"
						+ " CAST(SEC.SEATS_AVAIL AS CHAR(4)) AS SEATS_AVAIL,"
						+ " ifnull(COMM.CRS_COMMENTS1, ' ') AS CRS_COMMENTS1,"
						+ " DISCT.DEPT_ID,"
						
						+ " CASE WHEN SEC.AM_PM = 'PM' AND SEC.STOP_TIME != ':' AND SEC.START_TIME != 'ON:LI'"
						+ " AND SUBSTR(SEC.STOP_TIME, 1, 2) + 12 < 24"
						+ " AND SUBSTR(SEC.START_TIME, 1, 2) + 12 <= SUBSTR(SEC.STOP_TIME, 1, 2) + 12"
						+ " THEN CAST((SUBSTR(SEC.START_TIME, 1, 2) + 12) AS CHAR(2))"
						+ " ELSE SUBSTR(SEC.START_TIME, 1, 2) END AS START_TIME_24H"
						
						+ " FROM CRS_SEC_SCHEDULE_READER SEC"
						+ " LEFT OUTER JOIN CRS_COMMENTS_SCHEDULE_READER COMM"
						+ " ON (SEC.SEMESTER = COMM.SEMESTER"
						+ " AND SEC.CRS_CD = COMM.CRS_CD)"
						+ " LEFT OUTER JOIN DISCIPLINE_SCHEDULE_READER DISCT"
						+ " ON (SEC.DISC = DISCT.DISC_ABBREVIATION)"
						+ " WHERE SEC.SEMESTER = ?";
				
				/*
				String pStatement = "SELECT SEC.DISC||' '|| SEC.CRS_NUM AS COURSE,"
						+ " SEC.CRS_CD,"
						+ " SEC.CRS_SEC,"
						+ " SEC.MEETING_DAYS||' '||SEC.START_TIME||' - '||SEC.STOP_TIME||LOWER(SEC.AM_PM) AS \"DAY & TIME\","
						+ " TO_CHAR(SEC.START_DATE,'MM/DD/YY')|| ' - ' ||TO_CHAR(SEC.END_DATE,'MM/DD/YY') AS DATES,"
						+ " SEC.BUILDING||' '||SEC.RM AS \"BUILDING & RM\","
						+ " UPPER(SEC.INSTRUCTOR_LNAME) AS INSTRUCTOR_LNAME,"
						+ " TO_CHAR(SEC.SEATS_AVAIL,'0000') AS SEATS_AVAIL,"
						+ " NVL(COMM.CRS_COMMENTS1, '-') AS CRS_COMMENTS1"
						+ " FROM CRS_SEC_SCHEDULE_READER SEC, CRS_COMMENTS_SCHEDULE_READER COMM"
						+ " WHERE SEC.SEMESTER = COMM.SEMESTER(+)"
						+ " AND SEC.CRS_CD = COMM.CRS_CD(+)"
						+ " AND SEC.SEMESTER = ?";
				 */	

				
				
				
			    // Optional search
				// 1. Search for Discipline and Department
				if(!disc.equals(getSelectAll())) {			    	
				    pStatement += " AND SEC.DISC = ?";
				    ++paramsSet;
				}
				else if(!dept.equals(getSelectAll()) && disc.equals(getSelectAll())) {
					pStatement += " AND DISCT.DEPT_ID = ?";
					++paramsSet;
				}
				
				
				// 2. Search for Course Number
			    if(!crs_num.equals(getEmpty())) {			    	
			    	pStatement += " AND LOWER(SEC.CRS_NUM) = ?";
			    	++paramsSet;
			    }
			    // 3. Search for Instructor Last Name
			    if(!instructor.equals(getEmpty())) {			    	
			    	pStatement += " AND LOWER(INSTRUCTOR_LNAME) = ?";
			    	++paramsSet;
			    }			    
			    // 4. Search for Start Time
			    if(!time_a_b.equals(getSelectAll()) && !time.equals(getSelectAll())) {			    	
			    	// SQL to create start time in 24hours format to use in time search
			    	/*
			    	String start_time_24h = " AND (CASE WHEN SEC.AM_PM = 'PM' AND SEC.STOP_TIME != ':' AND SEC.START_TIME != 'ON:LI'"
			    				+ " AND SUBSTR(SEC.STOP_TIME, 1, 2) + 12 < 24"						
			    				+ " AND SUBSTR(SEC.START_TIME, 1, 2) + 12 <= SUBSTR(SEC.STOP_TIME, 1, 2) + 12"
			    				+ " THEN TO_CHAR(SUBSTR(SEC.START_TIME, 1, 2) + 12)"
			    				+ " ELSE SUBSTR(SEC.START_TIME, 1, 2) END)";
			    	*/
			    	String start_time_24h = " AND (CASE WHEN SEC.AM_PM = 'PM' AND SEC.STOP_TIME != ':' AND SEC.START_TIME != 'ON:LI'"
		    				+ " AND SUBSTR(SEC.STOP_TIME, 1, 2) + 12 < 24"						
		    				+ " AND SUBSTR(SEC.START_TIME, 1, 2) + 12 <= SUBSTR(SEC.STOP_TIME, 1, 2) + 12"
		    				+ " THEN CAST((SUBSTR(SEC.START_TIME, 1, 2) + 12) AS CHAR(2))"
		    				+ " ELSE SUBSTR(SEC.START_TIME, 1, 2) END)";
			    	
			    	if(time_a_b.equals("before")) { 
			    		pStatement += (start_time_24h + " < ?");
			    		++paramsSet;
			    	}
			    	if(time_a_b.equals("after")) {
			    		pStatement += (start_time_24h + " > ?");
			    		++paramsSet;
			    	}
			    	if(time_a_b.equals("around")) {
			    		pStatement += (start_time_24h + " = ?");
			    		++paramsSet;
			    	}			    	
			    }
			    // 5. Search for Summer Sections
			    if(semesterForSearch.equalsIgnoreCase("summer")) {
			    	if(semester.equalsIgnoreCase(getSummerAll())||
			    	   semester.equalsIgnoreCase(getSummer00()) ||
			    	   semester.equalsIgnoreCase(getSummer01()) || 
			    	   semester.equalsIgnoreCase(getSummer02())) {
			    		/*
			    		pStatement += " AND TO_CHAR(SEC.START_DATE,'MM/DD/YY') = ?"
			    					+ " AND TO_CHAR(SEC.END_DATE,'MM/DD/YY') = ?";
			    		*/
			    		
			    		pStatement += " AND date_format(SEC.START_DATE,'%m/%d/%y') = ?"
		    					+ " AND date_format(SEC.END_DATE,'%m/%d/%y') = ?";
			    		++paramsSet;
			    		++paramsSet;
			    	}
			    }
			    // 6. Search for Meeting Days
			    if(!meeting_days.equalsIgnoreCase(getSelectAll())) {
			    	pStatement += " AND SEC.MEETING_DAYS = ?";
			    	++paramsSet;
			    }
			    // 7. Search for Division (no parameter needed)
			    if(div_undr.equals(getUndergraduate()) && div_grad.equals(getGraduate())) {
			    	pStatement += " AND (SEC.D_E_G = 'D' OR SEC.D_E_G = 'E' OR SEC.D_E_G = 'G')";
			    }
			    if(div_undr.equals(getUndergraduate()) && !div_grad.equals(getGraduate())) {
			    	pStatement += " AND(SEC.D_E_G = 'D' OR SEC.D_E_G = 'E')";
			    }			    
			    if(!div_undr.equals(getUndergraduate()) && div_grad.equals(getGraduate())) {
			    	pStatement += " AND SEC.D_E_G = 'G'";
			    }
			    if(!div_undr.equals(getUndergraduate()) && !div_grad.equals(getGraduate())) {
			    	pStatement += " AND SEC.D_E_G = 'nothing_selected'";
			    }
			    			    
			    // Sort by Course and Code
			    pStatement += " ORDER BY COURSE, CRS_CD";
			    
			    ps = conn.prepareStatement(pStatement);
			    ps.setString(1, semesterForSearch);
			    
			    // Optional Parameters
			    int i = 2;
			    while(i <= paramsSet) {
			    	// 1. Discipline and Department parameter
			    	if(!disc.equals(getSelectAll())) {
				    	ps.setString(i, disc);
				    	++i;
				    }
			    	else if(!dept.equals(getSelectAll()) && disc.equals(getSelectAll())) {
						ps.setString(i, dept);
						++i;
					}
			    	// 2. Course Number parameter
				    if(!crs_num.equals(getEmpty())) {
				    	ps.setString(i, crs_num.toLowerCase());
				    	++i;
				    }
				    // 3. Instructor Last Name parameter
				    if(!instructor.equals(getEmpty())) {
				    	ps.setString(i, instructor.toLowerCase());
				    	++i;
				    }
				    // 4. Start Time parameter				    
				    if(!time_a_b.equals(getSelectAll()) && !time.equals(getSelectAll())) {
				    		ps.setString(i, time);
				    		++i;
				    }
				    // 5. Summer Sections parameter
				    if(semesterForSearch.equalsIgnoreCase("summer")) {
				    	ps.setString(i, getStartDateOfSummerSec(semester).getSummer_sDate());
			    		++i;
			    		ps.setString(i, getStartDateOfSummerSec(semester).getSummer_eDate());
			    		++i;
				    }		
				    // 6. Meeting Days parameter
				    if(!meeting_days.equalsIgnoreCase(getSelectAll())) {
				    	ps.setString(i, meeting_days);
				    	++i;
				    }
			    }
			    
			    // Execute Query
			    rs = ps.executeQuery();

				// Create data list in DataBean format and put it in to ArrayList
				while(rs.next()) {
					DataBean rsList = new DataBean();
					rsList.setCourse(rs.getString("COURSE"));
	                rsList.setCode(rs.getString("CRS_CD"));
	                rsList.setSection(rs.getString("CRS_SEC"));
	                rsList.setDayAndTime(rs.getString("DAY & TIME"));
	                rsList.setDates(rs.getString("DATES"));
	                rsList.setBldgAndRm(rs.getString("BUILDING & RM"));
	                rsList.setInstructor(rs.getString("INSTRUCTOR_LNAME"));
	                rsList.setSeatsAvail(rs.getString("SEATS_AVAIL"));	                
	                rsList.setComments(rs.getString("CRS_COMMENTS1"));
	                
					list.add(rsList);
				}
			}//end of try
			catch(SQLException ex){ex.printStackTrace();}
			finally {
				try {
	        		if(rs != null) rs.close();
	        		ps.close();
	        	}
	          	catch (SQLException e) {e.printStackTrace();}
	          	closeConnection(conn);
	        }//end of finally
	    }//end of if
	    return list;
	}
	
	// Use for get Course Details and save in dataBean format
	public DataBean getDataDetails(String semester, String crs_sec, String crs_cd) {
		
		DataBean dataBean = new DataBean();
		Connection conn = getConnection();	
				
		if (conn != null) {
			ResultSet rs = null;
	    	PreparedStatement ps = null;
	    	
	    	String semesterForSearch = semester.toLowerCase();
	    	
	    	if(semesterForSearch.equals(getSummerAll())|| 
	 	       semesterForSearch.equals(getSummer00()) || 
	 	       semesterForSearch.equals(getSummer01()) || 
	 	       semesterForSearch.equals(getSummer02())) {
	    		semesterForSearch = "summer";
	    	}
	    	
	    	
			try {
				// Query semester with year, and then set it into dataBean				
				/*
				String pStatement = "SELECT distinct disc||' '||crs_num||' - '||course_schedule_reader.title as course_title,"
						+ " initcap(crs_sec_schedule_reader.semester) ||' '|| EXTRACT(YEAR FROM start_date)as semester,"
						+ " crs_sec_schedule_reader.crs_cd,"
						
						+ " DEPT_SCHEDULE_READER.DEPT_NAME,"
						
						+ " crs_sec,"
						
						+ " case when d_e_g = 'G' then 'Graduate'"
						+ " when (d_e_g='D' OR d_e_g='E') then 'Undergrade'"
						+ " end as d_e_g,"
						
						+ " seats_avail,"
						+ " TO_CHAR(START_DATE,'MM/DD/YY')||' to '||TO_CHAR(END_DATE,'MM/DD/YY') as dates,"
						+ " meeting_days||', '||start_time||' - '||stop_time||LOWER(am_pm)||', '||building||' '||rm||', '||instructor_lname as meetingday,"
						+ " NVL(crs_comments_schedule_reader.crs_comments1, 'None') AS CRS_COMMENTS1,"
						+ " course_schedule_reader.credithour,"
						+ " course_schedule_reader.description,"
						+ " NVL(course_schedule_reader.prereq, 'None') AS PREREQ"
						+ " FROM crs_sec_schedule_reader, crs_comments_schedule_reader, course_schedule_reader,"
						+ " discipline_schedule_reader, DEPT_SCHEDULE_READER"
						+ " WHERE crs_sec_schedule_reader.semester = crs_comments_schedule_reader.semester"
						+ " AND crs_sec_schedule_reader.crs_cd = crs_comments_schedule_reader.crs_cd"
						+ " AND course_schedule_reader.discipline =crs_sec_schedule_reader.disc"
						+ " AND course_schedule_reader.coursenumber = crs_sec_schedule_reader.crs_num"
						+ " AND discipline_schedule_reader.dept_id = DEPT_SCHEDULE_READER.dept_id"
						+ " AND crs_sec_schedule_reader.disc = discipline_schedule_reader.DISC_ABBREVIATION"
						+ " AND crs_sec_schedule_reader.semester = ?"
						+ " AND crs_sec = ?"
						+ " AND crs_sec_schedule_reader.crs_cd = ?";
				*/
				String pStatement = "SELECT distinct CONCAT(disc,' ',crs_num,' - ',COURSE_SCHEDULE_READER.title) as course_title,"
						+ " CONCAT(concat(ucase(substr(CRS_SEC_SCHEDULE_READER.semester, 1,1)), substr(CRS_SEC_SCHEDULE_READER.semester, 2)),' ',EXTRACT(YEAR FROM start_date))as semester,"
						+ " CRS_SEC_SCHEDULE_READER.crs_cd,"
						
						+ " DEPT_SCHEDULE_READER.DEPT_NAME,"
						
						+ " crs_sec,"
						
						+ " case when d_e_g = 'G' then 'Graduate'"
						+ " when (d_e_g='D' OR d_e_g='E') then 'Undergrade'"
						+ " end as d_e_g,"
						
						+ " seats_avail,"
						+ " CONCAT(date_format(START_DATE,'%m/%d/%y'),' to ',date_format(END_DATE,'%m/%d/%y')) as dates,"
						+ " CONCAT(meeting_days,', ',start_time,' - ',stop_time,LOWER(am_pm),', ',building,' ',rm,', ',instructor_lname) as meetingday,"
						+ " IFNULL(CRS_COMMENTS_SCHEDULE_READER.crs_comments1, 'None') AS CRS_COMMENTS1,"
						+ " COURSE_SCHEDULE_READER.credithour,"
						+ " COURSE_SCHEDULE_READER.description,"
						+ " IFNULL(COURSE_SCHEDULE_READER.prereq, 'None') AS PREREQ"
						+ " FROM CRS_SEC_SCHEDULE_READER, CRS_COMMENTS_SCHEDULE_READER, COURSE_SCHEDULE_READER,"
						+ " DISCIPLINE_SCHEDULE_READER, DEPT_SCHEDULE_READER"
						+ " WHERE CRS_SEC_SCHEDULE_READER.semester = CRS_COMMENTS_SCHEDULE_READER.semester"
						+ " AND CRS_SEC_SCHEDULE_READER.crs_cd = CRS_COMMENTS_SCHEDULE_READER.crs_cd"
						+ " AND COURSE_SCHEDULE_READER.discipline = CRS_SEC_SCHEDULE_READER.disc"
						+ " AND COURSE_SCHEDULE_READER.coursenumber = CRS_SEC_SCHEDULE_READER.crs_num"
						+ " AND DISCIPLINE_SCHEDULE_READER.dept_id = DEPT_SCHEDULE_READER.dept_id"
						+ " AND CRS_SEC_SCHEDULE_READER.disc = DISCIPLINE_SCHEDULE_READER.DISC_ABBREVIATION"
						+ " AND CRS_SEC_SCHEDULE_READER.semester = ?"
						+ " AND crs_sec = ?"
						+ " AND CRS_SEC_SCHEDULE_READER.crs_cd = ?";
				
				ps = conn.prepareStatement(pStatement);
				ps.setString(1, semesterForSearch);
				ps.setString(2, crs_sec);
				ps.setString(3, crs_cd);
				rs = ps.executeQuery();
				
				int i = 0;
				while(rs.next()) {					
					// Initialized set details data
					if(i == 0) {
						dataBean.setDetails_couseTitle(rs.getString("COURSE_TITLE"));
				    	dataBean.setDetails_semester(rs.getString("SEMESTER"));
				    	dataBean.setDetails_crsCode(rs.getString("CRS_CD"));
				    	dataBean.setDetails_dept(rs.getString("DEPT_NAME"));
				    	dataBean.setDetails_division(rs.getString("d_e_g"));
				    	dataBean.setDetails_crsSection(rs.getString("CRS_SEC"));
				    	dataBean.setDetails_seatsAvail(rs.getString("SEATS_AVAIL"));
				    	dataBean.setDetails_dates(rs.getString("DATES"));
				    	dataBean.setDetails_meetingDay_1(rs.getString("MEETINGDAY"));
				    	dataBean.setDetails_crsComments(rs.getString("CRS_COMMENTS1"));
				    	dataBean.setDetails_crsCreditHours(rs.getString("CREDITHOUR"));
				    	dataBean.setDetails_crsDescription(rs.getString("DESCRIPTION"));
				    	dataBean.setDetails_preRequisite(rs.getString("PREREQ"));
				    	// After finish the first set process, ++i
				    	++i;						
					}
					// If there have more meeting day (meaning right now is running 2nd time result set)
					// Set the 2nd meeting day in meetingDay_2
					else if(i == 1) {
						dataBean.setDetails_meetingDay_2(rs.getString("MEETINGDAY"));
						++i;
					}
					// Same thing when 3rd meeting day
					else if(i == 2) {
						dataBean.setDetails_meetingDay_3(rs.getString("MEETINGDAY"));
						++i;
					}
					// Same thing when 4th meeting day
					else if(i == 3) {
						dataBean.setDetails_meetingDay_4(rs.getString("MEETINGDAY"));
						++i;
					}
					// Since maximum is 5 meeting days, break the while loop
					else if(i == 4) {
						dataBean.setDetails_meetingDay_5(rs.getString("MEETINGDAY"));
						break;
					}
			    }
			}//end of try
			catch(SQLException ex){ex.printStackTrace();}
			finally {
				try {
	        		rs.close();
	        		ps.close();
	        	}
	          	catch (SQLException e) {e.printStackTrace();}
	          	closeConnection(conn);	        
	        }//end of finally
	    }//end of if
		return dataBean;
	}
	
}
