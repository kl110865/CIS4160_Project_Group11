<%--
Student Name: Kai Yin Lau
Assignment # 3
 Class: Fall 2013 Web App Development: CIS 4160 QMWA [2475] (Baruch )
Features Included: Information in order to Connect to Database

Advance Feature(if Any):

References(if Any):
Important: CODE COMMENTS

 --%>

<%-- Use for local DB - Oracle DB --%>
<%--

String driver = "oracle.jdbc.OracleDriver";// 1. Load the driver

// 2. Define the connection URL
String url = "jdbc:oracle:thin:@localhost:1521:XE"; //orcl is the SID
String myusername = "DB_USER"; // Your DB login ID
String mypassword = "1a14"; //Your Db pass

--%>

<%-- Online version of the connection to MySQL on OpenShift (7/8/2014@11:37PM)--%>
<%

String driver = "com.mysql.jdbc.Driver";

String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
String app_name = System.getenv("OPENSHIFT_APP_NAME");

String url = "jdbc:mysql://" + host + ":" + port + "/" + app_name;
String myusername = "adminnTVQIR3";
String mypassword = "Ukgn4Ag14fp2";

%>