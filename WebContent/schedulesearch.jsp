<%--
Student Name: Kai Yin Lau, Lisha Tan
Assignment # SOC Project
 Class: Fall 2013 Web App Development: CIS 4160 QMWA [2475] (Baruch )
Features Included: Load data from database and show them in the page

Advance Feature(if Any): 

References(if Any): Change dropdown list
					(http://www.roseindia.net/answers/viewqa/JSP-Servlet/
					28767-Hi-I-m-new-to-JSP-I-want-to-create-3-dropdown-list-each-depend-on-
					the-other-and-get-the-options-from-the-database-using-JSP.html)
Important: CODE COMMENTS

 --%>
 
 
<!--head.html include Goes Here -->
<jsp:include page="/CIS4160/WebContent/head.html"></jsp:include>

<%-- Use for Auto Complete --%>
<link rel="stylesheet" type="text/css" href="CIS4160/WebContent/CSS/jquery.autocomplete.css" />
<script type="text/javascript"
        src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script src="CIS4160/WebContent/JS/jquery.autocomplete.js"></script>

<%-- Script to make error message show or hide, it depends on "errormsg" value --%>
<script type="text/javascript">
$(document).ready(function() {
	$("#errorMsg").hide();
	<%
	String error = (String) session.getAttribute("errormsg");
	// Show errormsg
	if("errorMsg".equals(error)) {
	%>
		$("#errorMsg").show();
	<%
		// Remove "error" and "errormsg" value, so the msg only appear when error happen
		error = null;
		session.removeAttribute("errormsg");
	}	
	%>
});
</script>

<%-- Use for DropDown List --%>
<script type="text/javascript">  
	var xmlHttp;
    var xmlHttp;
    function showDiscipline(str) {
    	if (typeof XMLHttpRequest != "undefined") {
    		xmlHttp= new XMLHttpRequest();
    	}
    	else if (window.ActiveXObject)	{
    		xmlHttp= new ActiveXObject("Microsoft.XMLHTTP");
    	}
    if (xmlHttp==null) {
    	alert("Browser does not support XMLHTTP Request");
    	return;
    } 
    // Pass "dept" value to disiplineChange.jsp
    var url = "disciplineChange";
    url += "?dept=" +str;
    xmlHttp.onreadystatechange = disciplineChange;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
    }

    function disciplineChange() {   
    	if (xmlHttp.readyState==4 || xmlHttp.readyState=="complete") {   
    		document.getElementById("discipline").innerHTML=xmlHttp.responseText;  
    	}   
    }
	<%--
    function showProf(str) {
    	if (typeof XMLHttpRequest != "undefined") {
			xmlHttp= new XMLHttpRequest();
		}
    	else if (window.ActiveXObject) {
      		xmlHttp= new ActiveXObject("Microsoft.XMLHTTP");
      	}
    	if (xmlHttp==null) {
    		alert("Browser does not support XMLHTTP Request");
    		return;
    	} 
    	var url="test_auto.jsp";
    	url +="?semester=" +str;
    	xmlHttp.onreadystatechange = profChange;
    	xmlHttp.open("GET", url, true);
    	xmlHttp.send(null);
    }
    function profChange() {   
    	if (xmlHttp.readyState==4 || xmlHttp.readyState=="complete") {   
    		document.getElementById("instructor").innerHTML=xmlHttp.responseText;
    	}   
    }
    --%>
</script>

<!--body.html include Goes Here -->
<jsp:include page="/CIS4160/WebContent/body.html"></jsp:include>


<%@ page import="java.sql.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ include file="connect_DB.jsp" %>
 

<%
Connection conn = null;		
try {
   	// Load DB
	// myusername and mypassword are declared in a file called connect_DB.jsp
	Class.forName(driver);
   	conn = DriverManager.getConnection(url, myusername, mypassword);
   
   	// Create statement objects
   	Statement stmt = conn.createStatement();
   	Statement stmt1 = conn.createStatement();
   	Statement stmt2 = conn.createStatement();
   	Statement stmt3 = conn.createStatement();
   	
    // Execute queries
	ResultSet semester = stmt.executeQuery("SELECT  START_DATE,"
			+ " END_DATE,"
			+ " SEMESTER_NAME,"
			//Use okay in Oracle, but not MySQL, so change it to CONCAT @ 2014/7/9 - 1:11AM
			//+ " SEMESTER_NAME ||' '|| EXTRACT(YEAR FROM START_DATE)AS SEMESTER"
			+ " CONCAT(SEMESTER_NAME, ' ', EXTRACT(YEAR FROM START_DATE))AS SEMESTER"
			+ " FROM SEMESTER_SCHEDULE_READER"); 
    ResultSet dept = stmt1.executeQuery("SELECT DEPT_NAME,"
    										+ " DEPT_ID"
    										+ " FROM DEPT_SCHEDULE_READER"
    										+ " ORDER BY DEPT_NAME");
    ResultSet discipline = stmt2.executeQuery("SELECT DISC_ABBREVIATION,"
    											  + " DEPT_ID"
    											  + " FROM DISCIPLINE_SCHEDULE_READER"
    											  + " ORDER BY DISCIPLINE_NAME");
    ResultSet meeting_days = stmt3.executeQuery("SELECT DISTINCT MEETING_DAYS FROM CRS_SEC_SCHEDULE_READER");
    

%>
<form method="post" action="/SearchServlets">

    <div align="center">
      <p>Enter the professor's name, discipline, course number and/or days you wish to search.</p>
      <div id="errorMsg">
      	<p><b><font color="#FF0000">Your time selection is wrong, should choose both "Select All" or both not "Select All".
		</font></b> </p>
	  </div>
      <table id="search" summary="This table contains search options for the schedule of classes.">
       <caption>
  Schedule of Classes Search
  </caption>
	  <tbody>        
	    <tr>
          <th>Semester:</th>
          <td><select id="semester" name="semester">
             
             <%-- Show Semester with Duration --%>
             <%
             // Set date format and then show data
             SimpleDateFormat df = new SimpleDateFormat ("MM-dd-yyyy");
             while(semester.next()) {
            	 out.println("<option value=\"" + semester.getString("SEMESTER_NAME") + "\">" 
            		            				+ semester.getString("SEMESTER") + "&nbsp;(" 
            		           					+ df.format(semester.getDate("START_DATE")) + "&nbsp;to&nbsp;" 
            		             				+ df.format(semester.getDate("END_DATE")) + ")</option>");
             }
             %>				  
          </select></td>
        </tr>
        <tr>
          <th>Dept:</th>
          <td><select name="department" size="1"  onchange="showDiscipline(this.value)">
			<option value="00">Select All</option>
			
			<%-- Show departments --%>
			<% while(dept.next()) { %>
			     <option value="<%= dept.getString("DEPT_ID") %>"><%= dept.getString("DEPT_NAME") %></option>
			<% } %>			
			</select></td>
        </tr>
        <tr>
          <th>Discipline:</th>
          <td><div id='discipline'><select name='discipline' size="1">  
			<option value='00'>Select All</option>
			
			<%-- Show disciplines --%>
			<% while(discipline.next()) { %>
				<option value="<%= discipline.getString("DISC_ABBREVIATION") %>"><%= discipline.getString("DISC_ABBREVIATION") %></option>
		    <% } %>
			</select></div></td>
        </tr>         
        <tr>
          <th>Division</th>
          <td>
            <label for="undergraduate">Undergraduate </label><input type="checkbox" id="undergraduate" value="U" name="div_undr" checked>
            <br>
            <label for="graduate">Graduate</label><input type="checkbox" id="gradaute" value="G" name="div_grad" checked>
          </td>
        </tr>
        <tr>
          <th><label for="number">Course number:</label></th>
          <td><input id="number" size="10" name="number" maxlength="5" type="text">
          <%-- Auto Complete function --%>
	          <script>
	        	$("#number").autocomplete("getCourseNumberData");
	    	  </script>
	      </td>
        </tr>
        <tr>
          <th><label for="days">Days:</label></th>
          <td><select id="days" name="week">
              <option value="00">Select	All </option>
              
              <option value="M">Mon </option>
              <option value="MTW">Mon-Tue-Wed </option>
              <option value="MTWF">Mon-Tue-Wed-Fri </option>
              <option value="MTWTH">Mon-Tue-Wed-Thr </option>
              <option value="MW">Mon-Wed </option>
              <option value="MWTH">Mon-Wed-Thr </option>
              <option value="MTH">Mon-Thr </option>
              <option value="T">Tue </option>
              <option value="TWF">Tue-Wed-Fri </option>
              <option value="TWTH">Tue-Wed-Thu </option>
              <option value="TTH">Tue-Thr </option>
              <option value="TF">Tue-Fri </option>
              <option value="W">Wed </option>
              <option value="TH">Thr </option>
              <option value="F">Fri </option>
              <option value="S">Sat </option>
              <option value="SU">Sun </option>
          </select></td>
        </tr>
        <tr>
          <th><label for="time">Time:</label></th>
          <td><select id="time" name="time_a_b">
              <option value="00">Select All </option>
              <option>before </option>
              <option>after </option>
              <option>around </option>
            </select>
            <select name="time">
              <option value="00">Select All </option>
              <option value="7">7:00am </option>
              <option value="8">8:00am </option>
              <option value="9">9:00am </option>
              <option value="10">10:00am </option>
              <option value="11">11:00am </option>
              <option value="12">12:00pm </option>
              <option value="13">1:00pm </option>
              <option value="14">2:00pm </option>
              <option value="15">3:00pm </option>
              <option value="16">4:00pm </option>
              <option value="17">5:00pm </option>
              <option value="18">6:00pm </option>
              <option value="19">7:00pm </option>
              <option value="20">8:00pm </option>
              <option value="21">9:00pm </option>
            </select>          </td>
        </tr>
        <tr>
          <th><label for="instructor">Instructor:</label></th>
          <td><input id="instructor" size="30" name="prof" type="text">
	          <%-- Auto Complete function --%>
	          <script>
	        	$("#instructor").autocomplete("getProfData");
	    	  </script>
    	  </td>
        </tr>
      </tbody>
      </table>
  </div>
    <p align="center">
      <input value="Start Search" type="submit">
   </p>
 
</form>
<%	    
	}
// Exceptions catching (ignored)
catch(ClassNotFoundException e) {		
	out.println("<font color=red>no class</font>");
	out.println("<BR>" + e.getMessage() + "<BR>");
}
catch(SQLException e) {		
	out.println("<font color=red>no sql connection</font>");
	out.println("<BR>" + e.getMessage() + "<BR>");
	}
finally {
	// Close connection; Clean up resources
	if(conn != null) {
		try {
			conn.close();
		}
		catch (Exception ignored) {}
	}
}
%>
<!--foot.html include Goes Here -->
<jsp:include page="/CIS4160/WebContent/foot.html"></jsp:include>
