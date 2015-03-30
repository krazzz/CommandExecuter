<%--
  Created by IntelliJ IDEA.
  User: evgeniy
  Date: 21.03.15
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>CMExecutor</title>
</head>
<body>
<%=request.getAttribute("status")%>
<form>
    <h1>Execution command: </h1><input name="command" type="text" value="<%=request.getAttribute("command")%>">
    <h1>Execution interval: </h1><input name="interval" type="number" value="<%=request.getAttribute("interval")%>">
    <h1>Current state: </h1><input name="state" type="text" value="<%=request.getAttribute("state")%>"><br>
    <button type="submit" formmethod="post" formaction="./">Change</button>
</form>
</body>
</html>
