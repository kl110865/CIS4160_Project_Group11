<%--
Student Name: Kai Yin Lau, Lisha Tan
Assignment # SOC Project
 Class: Fall 2013 Web App Development: CIS 4160 QMWA [2475] (Baruch )
Features Included: Change Discipline input in search page if user chooses different Department

Advance Feature(if Any): 

References(if Any): Change dropdown list
					(http://www.roseindia.net/answers/viewqa/JSP-Servlet/
					28767-Hi-I-m-new-to-JSP-I-want-to-create-3-dropdown-list-each-depend-on-
					the-other-and-get-the-options-from-the-database-using-JSP.html)
Important: CODE COMMENTS

 --%>
 
<%@ page import="java.sql.*" %>
<%@ include file="connect_DB.jsp" %>

<%
// Get dept data
String dept = request.getParameter("dept");  
// Print result depends on dept value
String buffer = "<select name='discipline' size='1'><option value='00'>Select All</option>";  
	try {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, myusername, mypassword);
 		Statement stmt = conn.createStatement();
 		PreparedStatement ps = null;
 		
 		if(!dept.equals("00")) {
 			String pStatement = "SELECT DISC_ABBREVIATION,"
	  				+ " DEPT_ID"
	  				+ " FROM DISCIPLINE_SCHEDULE_READER"
	  				+ " WHERE DEPT_ID = ?"
	  				+ " ORDER BY DISCIPLINE_NAME";
			ps = conn.prepareStatement(pStatement);
			ps.setString(1, dept);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
	   			buffer = buffer + "<option value='" + rs.getString("DISC_ABBREVIATION") + "'>"+rs.getString("DISC_ABBREVIATION") + "</option>";  
	   		}
 		}
 		else {
 			ResultSet rs = stmt.executeQuery("SELECT DISC_ABBREVIATION,"
					  + " DEPT_ID"
					  + " FROM DISCIPLINE_SCHEDULE_READER"
					  + " ORDER BY DISCIPLINE_NAME");
			while(rs.next()) {
	   			buffer = buffer + "<option value='" + rs.getString("DISC_ABBREVIATION") + "'>" + rs.getString("DISC_ABBREVIATION") + "</option>";  
	   		}    
 		}
 		
 		buffer = buffer + "</select>";  
 		response.getWriter().println(buffer); 
 	}
 	catch(Exception e) {
		System.out.println(e);
 	}
 %>