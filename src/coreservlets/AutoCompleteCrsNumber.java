/*
Student Name: Kai Yin Lau, Lisha Tan
Assignment # SOC Project
 Class: Fall 2013 Web App Development: CIS 4160 QMWA [2475] (Baruch )
Features Included: 1. Create auto complete list for coursr number
				   2. Create matched list

Advance Feature(if Any): 

References(if Any): 
Important: CODE COMMENTS

*/

package main.java.coreservlets;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


// Create auto complete list for coursr number
public class AutoCompleteCrsNumber {
    
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
	
    private int totalNum;
    private List<String> crsNumList; 
    
    
    public AutoCompleteCrsNumber() {    	
    	Connection conn = null;	
    	ResultSet rs = null;
    	PreparedStatement ps = null;
    	
    	//String semesterForSearch = semester.toLowerCase();
    	
    	try {
    	   	// Load DB
    		Class.forName(driver);
    	   	conn = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
    	   	
    	   	// Create statement objects
    	   	String pStatement = "SELECT DISTINCT crs_num FROM CRS_SEC_SCHEDULE_READER"
//    	   			+ " WHERE SEMESTER = ?"
    	   			+ " ORDER BY crs_num";
    	   	
    	   	ps = conn.prepareStatement(pStatement);
		    //ps.setString(1, semesterForSearch);
    	   	
    	   	
		    rs = ps.executeQuery();
		    crsNumList = new ArrayList<String>();
		    
		    while(rs.next()) {		    	
		    	crsNumList.add(rs.getString("crs_num"));                					
			}
		    totalNum = crsNumList.size();
		    
		    
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
     
    // Create matched list
    public List<String> getData(String query) {
        String crsNum = null;
        query = query.toLowerCase();
        List<String> matched = new ArrayList<String>();
        for(int i=0; i< totalNum; i++) {
        	crsNum = crsNumList.get(i).toLowerCase();
            if(crsNum.startsWith(query)) {
                matched.add(crsNumList.get(i));
            }
        }
        return matched;
    }
}