<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<title>${param.title}</title>
	<jsp:include page="/WEB-INF/pages/include.jsp"/>
</head>
<body>
	<jsp:include page="/WEB-INF/pages/header.jsp"/>

	<h1>${param.title}</h1>

	<jsp:include page="/WEB-INF/pages/${param.content}.jsp"/>
	
	<jsp:include page="/WEB-INF/pages/footer.jsp"/>
	
	
</body>
</html>