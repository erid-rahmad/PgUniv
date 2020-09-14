<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page isELIgnored="false" %>

<html>
<head>
<title>Monitor Connection</title>
<jsp:include page="/WEB-INF/pages/header.jsp"/>

<style type="text/css">
	#login-panel{
		background-color:#f7f7f7;
		box-shadow: 0 2px 1px 0 rgba(0,0,0,0.2);
		border-radius: 3px;
		margin:20px;
		padding:16px;
	}
</style>
</head>
<body >

<div class="row">
	<div class="login-container">
		<div id="login-panel">
			<table class="table" style="margin-top:15x;">
				<thead>
					<tr >
						<td colspan="3" style="text-align:center"><b>MONITOR CONNECTION</b></td>
					</tr>
				</thead>
				<tbody>
					<tr >
						<td>IST</td>
						<td> : </td>
						<td>${istStatus}</td>
					</tr>
					<tr>
						<td>UMP</td>
						<td> : </td>
						<td>${pdamStatus}</td>
					</tr>
					<tr>
						<td>Univ others</td>
						<td> : </td>
						<td>${mdpStatus}</td>
					</tr>
				</tbody>
			</table>
		</div>
		
	</div>
</div>

</body>
</html>