<%--
Student Name: Kai Yin Lau, Lisha Tan
Assignment # SOC Project
 Class: Fall 2013 Web App Development: CIS 4160 QMWA [2475] (Baruch )
Features Included: 1. Show search result and user input and last update date

Advance Feature(if Any): 

References(if Any): 
Important: CODE COMMENTS

 --%>
 
 
<!--head.html include Goes Here -->
<jsp:include page="/CIS4160/WebContent/head.html"></jsp:include>
<!--body.html include Goes Here -->
<jsp:include page="/CIS4160/WebContent/body.html"></jsp:include>




<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%-- Load session information --%>
<%@ page import="main.java.coreservlets.DataBean"%>
<jsp:useBean id="dataBean" class="main.java.coreservlets.DataBean" scope="session" />
<jsp:useBean id="resultList" type="ArrayList<main.java.coreservlets.DataBean>" scope="session" />
 

  <p>Search results are based on the following keywords:</p>
  <table id="criteria" summary="This table contains the search criteria. Results are listed in next table.">
  	<%-- Display user input --%>
    <tr>
      <td><strong>Semester</strong>: <%= dataBean.getSemesterInput() %></td>
      <td><strong>Days</strong>: <%= dataBean.getDaysInput() %></td>
    </tr>
    <tr>
      <td><strong>Department</strong>: <%= dataBean.getDeptInput() %></td>
      <td><strong>Time</strong>: <%= dataBean.getTimeInput() %></td>
    </tr>
    <tr>
      <td><strong>Discipline</strong>: <%= dataBean.getDiscInput() %></td>
      <td><strong>Course number</strong>: <%= dataBean.getCrsNumInput() %></td>
    </tr>
    <tr>
      <td><strong>Division</strong>: <%= dataBean.getDiviInput() %> </td>
      <td><strong>Instructor</strong>: <%= dataBean.getInstructorInput() %></td>
    </tr>
    </table>
  <font color="red">
  <p><b>The schedule was LAST&nbsp; updated at <%= dataBean.getLastUpdateTime() %></b></p>
  <p>Due to the dynamic nature of the registration process, not all courses listed as open will have space for new registrants.</p>
  </font>
<table id="results" summary="This table contains the search results for schedule of classes.">
  <caption>
  Schedule of Classes Search Results
  </caption>
  <thead>
    <tr>
      <th scope="col">Course</th>
      <th scope="col">Code</th>
      <th scope="col">Section</th>
      <th scope="col">Day &amp; Time </th>
      <th scope="col">Dates</th>
      <th scope="col">Bldg &amp; Rm </th>
      <th scope="col">Instructor</th>
      <th scope="col">Seats Avail </th>
      <th scope="col">Comments</th>
    </tr>
  </thead>
  <tbody>         
	<%
	// Output first row data from resultset if resultset not empty
	DataBean firstData = new DataBean();
	if(!resultList.isEmpty()) {
		firstData = (DataBean) resultList.get(0);
		%>
		<tr>
			<td><a href="/DetailsServlets
				?cd=<%= firstData.getCode() %>
				&sec=<%= firstData.getSection() %>
				&semester=<%= request.getParameter("semester") %>">
				<%= firstData.getCourse() %></a></td>
			<td><%= firstData.getCode() %></td>
			<td><%= firstData.getSection() %></td>
			<td><%= firstData.getDayAndTime() %></td>
			<td><%= firstData.getDates() %></td>
			<td><%= firstData.getBldgAndRm() %></td>
			<td><%= firstData.getInstructor() %></td>
			<td><%= firstData.getSeatsAvail() %></td>
			<td><%= firstData.getComments() %></td>
		</tr>
		<%
	}
	
	// Compare the rest of data to see if they have same course code or not
	// If yes, display as a pair of result data in 2 rows
	for(int i = 1; i < resultList.size(); i++) {
		DataBean rsList = new DataBean();
		DataBean rsListForCheck = new DataBean();
		
		rsList = (DataBean) resultList.get(i);
		rsListForCheck = (DataBean) resultList.get(i - 1);
		
		if(!rsList.getCode().equalsIgnoreCase(rsListForCheck.getCode())) {
			%>
			<tr>
				<td><a href="/DetailsServlets
					?cd=<%= rsList.getCode() %>
					&sec=<%= rsList.getSection() %>
					&semester=<%= request.getParameter("semester") %>">
					<%= rsList.getCourse() %></a></td>
				<td><%= rsList.getCode() %></td>
				<td><%= rsList.getSection() %></td>
				<td><%= rsList.getDayAndTime() %></td>
				<td><%= rsList.getDates() %></td>
				<td><%= rsList.getBldgAndRm() %></td>
				<td><%= rsList.getInstructor() %></td>
				<td><%= rsList.getSeatsAvail() %></td>
				<td><%= rsList.getComments() %></td>
			</tr>
			<%
		}
		else {
			%>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td><%= rsList.getDayAndTime() %></td>
				<td><%= rsList.getDates() %></td>
				<td><%= rsList.getBldgAndRm() %></td>
				<td><%= rsList.getInstructor() %></td>
				<td><%= rsList.getSeatsAvail() %></td>
				<td></td>
			</tr>
			<%
		}
	} 
	%>  
  </tbody>
</table>


<!--foot.html include Goes Here -->
<jsp:include page="/CIS4160/WebContent/foot.html"></jsp:include>