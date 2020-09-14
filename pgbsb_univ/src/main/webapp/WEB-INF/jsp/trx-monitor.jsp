<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page isELIgnored="false" %>
<%@ page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<title>Transaksi</title>
	<jsp:include page="/WEB-INF/pages/header.jsp"/>
<script type="text/javascript">
function editChannel(token,prefix,auth,port,remark,row){
	var title= document.getElementById("modalHeader");
	var mToken = document.getElementById("modalTokenNote");
	var mPrefix = document.getElementById("modalPrefix");
	var mAuth = document.getElementById("modalAuth");
	var mPort = document.getElementById("modalPort");
	var mRemark = document.getElementById("modalRemark");
	title.innerHTML = "Edit Channel";
	mToken.value = token;
	mPrefix.value = prefix;
	mAuth.value = auth;
	mPort.value = port;
	mRemark.value = remark;
	name.autofocus;
	document.getElementById("modalSubmit").onclick = function(){ saveBranch(row); } ;
}
function saveBranch(row){
	var title= document.getElementById("modalHeader");
	var mToken = document.getElementById("modalTokenNote");
	var mPrefix = document.getElementById("modalPrefix");
	var mAuth = document.getElementById("modalAuth");
	var mPort = document.getElementById("modalPort");
	var mRemark = document.getElementById("modalRemark");
	var message = document.getElementById("modalMessage");
	
	
	var colToken = document.getElementById(row +"token");
	var colPrefix = document.getElementById(row +"prefix");
	var colAuth = document.getElementById(row +"auth");
	var colPort = document.getElementById(row +"port");
	var colRemark = document.getElementById(row +"remark");
	
	if(mToken.value.trim() == "" || mPrefix.value.trim() == "" || mAuth.value.trim() =="" || mPort.value <= 0){
		message.style.display = "block";
		return;
	}
	$('#myModal').modal('toggle');
	swal({title : "Good job!",
		text : "",
		  type:"success"
	},function(){
	});
	colToken.innerHTML = mToken.value;
	colPrefix.innerHTML = mPrefix.value;
	colAuth.innerHTML = mAuth.value;
	colPort.innerHTML = mPort.value;
	colRemark.innerHTML = mRemark.value;
	/* var formData = new FormData();
	formData.append("action",title.innerHTML === "Add Branch" ? "save" :(title.innerHTML === "Edit Branch" ? "update" : "delete") );
	formData.append("branchCode",code);
	formData.append("branchName",name);
	$.ajax({
		url : "/cms/admin/getallbranch", // Url to which the request is send
		type : "POST", // Type of request to be send, called as method
		data : formData, // Data sent to server, a set of key/value pairs (i.e. form fields and values)
		contentType : false, // The content type used when sending data to the server.
		cache : false, // To unable request pages to be cached
		processData : false, // To send DOMDocument or non processed data file it is set to false
		success : function(callback) // A function to be called if request succeeds
		{
			if(callback=="sukses"){ 
				$('#myModal').modal('toggle');
				swal({title : "Good job!",
					text : "",
					  type:"success"
				},function(){
					window.location.replace("<c:url value='/admin/getallbranch?pageNumber=${param.pageNumber}&filter=${param.filter}'/>");	
				});
			 }else{
				message.innerHTML=callback;
				message.style.display = "block";		
			}
		},
		error : function(callback) {
			message.innerHTML="Silahkan coba lagi, ada gangguan!";
			message.style.display = "block";
		} 
	});*/
}
function deleteBranch(code,name){
	swal({
	  title: "Are you sure?",
	  text: "You will not be able to recover this branch data!",
	  type: "warning",
	  showCancelButton: true,
	  confirmButtonColor: "#DD6B55",
	  confirmButtonText: "Yes, delete it!",
	  closeOnConfirm: false,
	  closeOnConfirm:false
	},
	function(isConfirm){
		if(isConfirm){
			var formData = new FormData();
			formData.append("action","delete");
			formData.append("branchCode",code);
			formData.append("branchName",name);
			$.ajax({
				url : "/cms/admin/getallbranch", // Url to which the request is send
				type : "POST", // Type of request to be send, called as method
				data : formData, // Data sent to server, a set of key/value pairs (i.e. form fields and values)
				contentType : false, // The content type used when sending data to the server.
				cache : false, // To unable request pages to be cached
				processData : false, // To send DOMDocument or non processed data file it is set to false
				success : function(callback) // A function to be called if request succeeds
				{
					if(callback=="sukses"){
						swal({
							title: "Deleted!", 
							text : "Your branch data has been deleted.", 
							type :"success"}
						,function(){
							window.location.replace("<c:url value='/admin/getallbranch?pageNumber=${param.pageNumber}&filter=${param.filter}'/>");	
						});
					}
				}
			});
			
		}
	});
}
</script>
</head>
<body >
<body class="no-skin">
	<div class="main-container ace-save-state" id="navbar-container">
		<jsp:include page="/WEB-INF/pages/sidebar.jsp"/>
		<div class="main-content">
			<div class="main-content-inner">
				<div id="breadcrumbs" class="breadcrumbs">
					<ul class="breadcrumb">
						<li><span><i
								class="menu-icon fa fa-credit-card bigger-150"></i></span>&nbsp;<a>Middleware</a></li>
						<li class="active">Transaksi</li>
					</ul>
					<!-- /.breadcrumb -->
				</div>
				<div class="page-content">
	<div class="col-xs-12">
		<div class="modal fade" role="dialog" tabindex="-1" id="myModal">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="modalHeader"></h4>
									</div>
									<div class="modal-body">
										<table align="center" style="margin-left:10px">
											<tr>
												<td colspan="3">&nbsp;</td>
											</tr>
											
											<tr>
												<td style="padding:5px;">Token</td>
												<td>&nbsp;:&nbsp;</td>
												<td style="padding:5px;"><input type="text" name="modalPrefix"
													id="modalPrefix" placeholder="channel name"
													style="width: 200%;" value=""/></td>
											</tr>
											
											<tr>
												<td style="padding:5px;">Token Note</td>
												<td>&nbsp;:&nbsp;</td>
												<td style="padding:5px;"><input type="text" name="modalTokenNote"
													id="modalTokenNote" placeholder="channel name"
													style="width: 200%;" value=""/></td>
											</tr>
											
											<tr>
												<td style="padding:5px;">Authentication</td>
												<td>&nbsp;:&nbsp;</td>
												<td style="padding:5px;"><input type="text" name="modalAuth"
													id="modalAuth" placeholder="interface"
													style="width: 200%;" value=""/></td>
											</tr>
											<tr>
												<td style="padding:5px;">Port</td>
												<td>&nbsp;:&nbsp;</td>
												<td style="padding:5px;"><input type="number" name="modalPort"
													id="modalPort" placeholder="port"
													style="width: 50%;" min="0"/></td>
											</tr>
											<tr>
												<td style="padding:5px;vertical-align:top;">Remark</td>
												<td style="padding:5px;vertical-align:top;">&nbsp;:&nbsp;</td>
												<td style="padding:5px;"><textarea name="modalRemark"
													id="modalRemark" placeholder="interface"
													style="width: 200%;" value=""></textarea></td>
											</tr>
											<tr>
												<td colspan="3">&nbsp;</td>
											</tr>
										</table>
										<div id="modalMessage" class="alert alert-warning" style="display:none">
												The data you entered is incorrect</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default"
											data-dismiss="modal">Cancel</button>
										<button type="button" id="modalSubmit"
											class="btn btn-primary">Submit</button>
									</div>
								</div>
								<!-- /.modal-content -->
							</div>
							<!-- /.modal-dialog -->
						</div>						
		<div id="accordion" class="accordion-style1 panel-group">
			<div class="panel panel-default"  style="margin:auto;width:100%"  >
				<div class="panel-collapse collapse in"  id="collapseOne" aria-expanded="true"  style="overflow-x:auto;">
					<div style="overflow-x:auto;">
						<table id="jqueryDataTable"  
						class="table table-striped table-bordered table-hover dataTable no-footer"
						role="grid" aria-describedby="dynamic-table_info" >
							
						<thead>
							<tr>
								<th style="text-align:center;">Token</th>
								<th style="text-align:center">Token Note</th>
								<th style="text-align:center">Authentication</th>
								<th style="text-align:center">Port</th>
								<th style="text-align:center;min-width:200px;">Remark</th>
								<th style="text-align:center;width:50px;">Action</th>
							</tr>
						</thead>
						<tbody>
							
							<tr>
								 <td class="tdstring" id="1prefix" style="text-align:center">*</td>
								 <td class="tdstring" id="1token" style="text-align:center">F3</td>
	                             <td class="tdstring" id="1auth" style="text-align:center">VLINK</td>
	                             <td class="tdstring" id="1port" style="text-align:center">8888</td>
	                             <td class="tdstring" id="1remark" style="text-align:center">Default</td>
	                             <td class="tdstring" style="text-align:center">
	                             	<button class="btn btn-primary btn-xs" onclick="editChannel('F3','*','VLINK','8888','Default','1')" 
	                             		data-toggle="modal" data-target="#myModal">
	                             		<i class="ace-icon fa fa-pencil align-top bigger-125"></i></button>
	                             </td>
							</tr>
							<tr>
								<td class="tdstring" id="2prefix" style="text-align:center">010001;010002;010003;010004;010005;010006;010007;010008;010009;010010</td>
								 <td class="tdstring" id="2token" style="text-align:center">F3</td>
	                             <td class="tdstring" id="2auth" style="text-align:center">Finnet</td>
	                             <td class="tdstring" id="2port" style="text-align:center">8080</td>
	                             <td class="tdstring" id="2remark" style="text-align:center">Default</td>
	                             <td class="tdstring" style="text-align:center">
	                             	<button class="btn btn-primary btn-xs" onclick="editChannel('F3','010001;010002;010003;010004;010005;010006;010007;010008;010009;010010','Finnet','8080','Default','2')" data-toggle="modal" data-target="#myModal">
										<i class="ace-icon fa fa-pencil align-top bigger-125"></i></button>
	                             </td>
							</tr>
							<tr>
								<td class="tdstring" id="3prefix" style="text-align:center">010011;010012;010013;010014;010015;010016;010017;010018;010019;010020</td>
								 <td class="tdstring" id="3token" style="text-align:center">F3</td>
	                             <td class="tdstring" id="3auth" style="text-align:center">Artajasa Payment</td>
	                             <td class="tdstring" id="3port" style="text-align:center">8081</td>
	                             <td class="tdstring" id="3remark" style="text-align:center">Default</td>
	                             <td class="tdstring" style="text-align:center">
	                             	<button class="btn btn-primary btn-xs" onclick="editChannel('F3','010011;010012;010013;010014;010015;010016;010017;010018;010019;010020','Artajasa Payment','8081','Default','3')" data-toggle="modal" data-target="#myModal">
										<i class="ace-icon fa fa-pencil align-top bigger-125"></i></button>
	                             </td>
							</tr>
							<%--
							<c:if test="${page == null || page.totalPages==0}">
								<tr > 
									<td colspan = "15" style="text-align:center">
										---Data kosong---
									</td>
								</tr>
							</c:if>
							<c:forEach var="item" items="${page.content }">
									<fmt:formatDate value="${shclog.localDate}" pattern="dd MMMM yyyy" var="fmtDate"/>
                                   <tr style="cursor: pointer">
                                       <td class="tdstring" style="text-align:center">${item.accFlag}</td>
                                       <td class="tdstring" style="text-align:center">${item.cif}</td>
                                       <td class="tdstring" style="text-align:center">${item.nameOnCard}</td>
                                       <td class="tdstring" style="text-align:center">${item.phoneNum}</td>
                                       <td class="tdstring" style="text-align:center">
                                       	<c:choose>
                                       		<c:when test="${item.accountStatus == '-1' }">
                                       			Data tidak sesuai
                                       		</c:when>
                                       		<c:when test="${item.accountStatus == '0' }">
                                       			Bisa digunakan
                                       		</c:when>
                                       		<c:when test="${item.accountStatus == '1' }">
                                       			Rekening sudah dipakai
                                       		</c:when>
                                       	</c:choose>
                                       </td>
									</tr>
							</c:forEach> --%>
						</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>