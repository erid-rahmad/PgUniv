<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" type="text/css" 
	href="<c:url value="/resources/Site.css" />">
<link rel="stylesheet" type="text/css" 
	href="<c:url value="/resources/menu.css" />">
<link rel="stylesheet" type="text/css" 
	href="<c:url value="/resources/jquery-ui-1.7.2.custom.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/jquery-ui.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/table.form.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/data.grid.css" />">	

<script src="<c:url value="/resources/jquery-1.9.1.js" />"></script>
<!-- <script src="<c:url value="/resources/jquery-ui.js" />"></script> -->
<script src="<c:url value="/resources/jquery-ui.min.js" />"></script>

<script type="text/javascript">	
  $(function() {
    $( "#btnNewFade" ).click(function() {
    	var options = {};
    	$( "#inputFormDisplay" ).toggle( "fold", options, 500 );
    });
    $( "#imgCloseInputForm" ).click(function() {
    	var options = {};
    	$( "#inputFormDisplay" ).slideUp();
    });
  });
  
  function openFormInput(){
		var options = {};
		$( "#inputFormDisplay" ).toggle( "fold", options, 500 );
  }
  </script>