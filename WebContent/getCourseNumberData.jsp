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
<%@ page import="main.java.coreservlets.AutoCompleteCrsNumber" %>
<%


// Create new list, query with user input and list it using "iterator"
AutoCompleteCrsNumber ac = new AutoCompleteCrsNumber();
 
String query = request.getParameter("q");
 
List<String> crsNumList = ac.getData(query);

Iterator<String> iterator = crsNumList.iterator();
while(iterator.hasNext()) {
    String crsNum = (String)iterator.next();
    out.println(crsNum);
}
%>