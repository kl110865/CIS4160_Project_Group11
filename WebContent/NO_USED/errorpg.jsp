  <%@ page import="java.sql.*, java.io.*, java.lang.*" isErrorPage="true" contentType="text/html; charset=utf-8"%>
    <h1>ERROR PAGE</h1>
<font color="#CC0000"><h2>An error has occurred.</h2></font>
<p>&nbsp;</p>
Look for this
<%-- Exception Handler --%>
<font color="red">
<%= exception.toString() %><br>
</font>

<%
out.println("<!--");
StringWriter sw = new StringWriter();
PrintWriter pw = new PrintWriter(sw);
exception.printStackTrace(pw);
out.print(sw);
sw.close();
pw.close();
out.println("-->");
%>

<%
if(exception instanceof java.lang.ClassNotFoundException){
%>
<strong><p>The application is unable to locate the file.</p></strong>
<%
}else if(exception instanceof java.sql.SQLException){
%>
<p>The application is unable to connect to the database.</p>

<%
}else{
%>
<%
}
%>
<hr>