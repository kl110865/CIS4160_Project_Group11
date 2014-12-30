<%--
Student Name: Kai Yin Lau, Lisha Tan
Assignment # SOC Project
 Class: Fall 2013 Web App Development: CIS 4160 QMWA [2475] (Baruch )
Features Included: Create Auto Complete List

Advance Feature(if Any): 

References(if Any):
Important: CODE COMMENTS

 --%>


<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="main.java.coreservlets.AutoCompleteProf" %>
<%
/*
String semester = request.getParameter("semester");  

String semesterForSearch = semester.toLowerCase();

if(semesterForSearch.equals("summer all")|| 
   semesterForSearch.equals("summer00") || 
   semesterForSearch.equals("summer01") || 
   semesterForSearch.equals("summer02")) {
	semesterForSearch = "summer";
}
*/

// Create new list, query with user input and list it using "iterator"
AutoCompleteProf ac = new AutoCompleteProf();
 
String query = request.getParameter("q");
 
List<String> profNameList = ac.getData(query);

Iterator<String> iterator = profNameList.iterator();
while(iterator.hasNext()) {
    String profName = (String)iterator.next();
    out.println(profName);
}
%>