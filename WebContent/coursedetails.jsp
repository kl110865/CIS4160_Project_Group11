<%--
Student Name: Kai Yin Lau, Lisha Tan
Assignment # SOC Project
 Class: Fall 2013 Web App Development: CIS 4160 QMWA [2475] (Baruch )
Features Included: 1. Show course details, if meeting days > 1, show the second meeting day

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
<jsp:useBean id="detailsDataBean" class="main.java.coreservlets.DataBean" scope="session" />



<%-- Display course details --%>
<table id="details" summary="This table contains details about each course.">
<caption>
Schedule of Classes Course Details 
</caption>  
  <tr>
    <th scope="row">Semester:</th>
    <td><%= detailsDataBean.getDetails_semester() %></td>
  </tr>
  <tr>
    <th scope="row">Course - Title:</th>
    <td><%= detailsDataBean.getDetails_couseTitle() %></td>
  </tr>
  <tr>
    <th scope="row">Code:</th>
    <td><%= detailsDataBean.getDetails_crsCode() %></td>
  </tr>
  <tr>
    <th scope="row">Section:</th>
    <td><%= detailsDataBean.getDetails_crsSection() %></td>
  </tr>
  <tr>
    <th scope="row">Department:</th>
    <td><%= detailsDataBean.getDetails_dept() %></td>
  </tr>
  <tr>
    <th scope="row">Division:</th>
    <td><%= detailsDataBean.getDetails_division() %></td>
  </tr>
  <tr>
    <th scope="row">Dates:</th>
    <td><%= detailsDataBean.getDetails_dates() %> </td>
  </tr>
  <tr>
    <th scope="row">Seats Available:</th>
    <td><%= detailsDataBean.getDetails_seatsAvail() %></td>
  </tr>
  <tr>
    <th scope="row">Meeting 1 - Day &amp; Time, Buildign &amp; Room, Instructor: </th>
    <td><%= detailsDataBean.getDetails_meetingDay_1() %></td>
  </tr>
  
  <%-- If meeting day > 1, show others if they are not null --%>
  <% if(detailsDataBean.getDetails_meetingDay_2() != null) {%>
  <tr>
    <th scope="row">Meeting 2 - Day &amp; Time, Buildign &amp; Room, Instructor: </th>
    <td><%= detailsDataBean.getDetails_meetingDay_2() %></td>
  </tr>
  <% } %>
  <% if(detailsDataBean.getDetails_meetingDay_3() != null) {%>
  <tr>
    <th scope="row">Meeting 3 - Day &amp; Time, Buildign &amp; Room, Instructor: </th>
    <td><%= detailsDataBean.getDetails_meetingDay_3() %></td>
  </tr>
  <% } %>
  <% if(detailsDataBean.getDetails_meetingDay_4() != null) {%>
  <tr>
    <th scope="row">Meeting 4 - Day &amp; Time, Buildign &amp; Room, Instructor: </th>
    <td><%= detailsDataBean.getDetails_meetingDay_4() %></td>
  </tr>
  <% } %>
  <% if(detailsDataBean.getDetails_meetingDay_5() != null) {%>
  <tr>
    <th scope="row">Meeting 5 - Day &amp; Time, Buildign &amp; Room, Instructor: </th>
    <td><%= detailsDataBean.getDetails_meetingDay_5() %></td>
  </tr>
  <% } %>
  
  <tr>
    <th scope="row">Credit Hours: </th>
    <td><%= detailsDataBean.getDetails_crsCreditHours() %></td>
  </tr>
  <tr>
    <th scope="row">Description:</th>
    <td><%= detailsDataBean.getDetails_crsDescription() %></td>
  </tr>
  <tr>
    <th scope="row">Course Comments: </th>
    <td><%= detailsDataBean.getDetails_crsComments() %></td>
  </tr>
  <tr>
    <th scope="row">Pre-requisite:</th>
    <td><%= detailsDataBean.getDetails_preRequisite() %></td>
  </tr>
</table>

<!--foot.html include Goes Here -->
<jsp:include page="/CIS4160/WebContent/foot.html"></jsp:include>