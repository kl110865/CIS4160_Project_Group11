/*
Student Name: Kai Yin Lau, Lisha Tan
Assignment # SOC Project
 Class: Fall 2013 Web App Development: CIS 4160 QMWA [2475] (Baruch )
Features Included: 1. Create auto complete list for instructor
				   2. Create matched list

Advance Feature(if Any): 

References(if Any): 
Important: CODE COMMENTS

*/


package main.java.coreservlets;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


// Create instructor list
public class AutoCompleteProf {
    
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
	
    private int totalName;
    private List<String> profNameList; 
    
    
    public AutoCompleteProf() {    	
    	Connection conn = null;	
    	ResultSet rs = null;
    	PreparedStatement ps = null;
    	
    	//String semesterForSearch = semester.toLowerCase();
    	
    	try {
    	   	// Load DB
    		Class.forName(driver);
    	   	conn = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
    	   	
    	   	// Create statement objects
    	   	String pStatement = "SELECT DISTINCT INSTRUCTOR_LNAME FROM CRS_SEC_SCHEDULE_READER"
//    	   			+ " WHERE SEMESTER = ?"
    	   			+ " ORDER BY INSTRUCTOR_LNAME";
    	   	
    	   	ps = conn.prepareStatement(pStatement);
		    //ps.setString(1, semesterForSearch);
    	   	
    	   	
		    rs = ps.executeQuery();
		    profNameList = new ArrayList<String>();
		    
		    while(rs.next()) {		    	
		    	profNameList.add(rs.getString("INSTRUCTOR_LNAME"));                					
			}
		    totalName = profNameList.size();
		    
		    
    	}
    	// Exceptions catching (ignored)
    	catch(ClassNotFoundException ex) {ex.printStackTrace();}
    	catch(SQLException ex) {ex.printStackTrace();}
    	finally {
    		// Close connection; Clean up resources
    		if(conn != null) {
    			try {
    				conn.close();
    			}
    			catch (Exception ex) {ex.printStackTrace();}
    		}
    	}
    }
     
    // Create matched List
    public List<String> getData(String query) {
        String profName = null;
        query = query.toLowerCase();
        List<String> matched = new ArrayList<String>();
        for(int i=0; i< totalName; i++) {
        	profName = profNameList.get(i).toLowerCase();
            if(profName.startsWith(query)) {
                matched.add(profNameList.get(i));
            }
        }
        return matched;
    }
}