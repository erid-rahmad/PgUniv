<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<title>Channel</title>
	<jsp:include page="/WEB-INF/pages/header.jsp"/>
<script type="text/javascript">

function addBranch(){
	var title= document.getElementById("modalHeader");
	var code = document.getElementById("modalBranchCode");
	var name = document.getElementById("modalBranchName");
	code.readOnly=false;
	title.innerHTML = "Add Branch";
	code.value = "";
	name.value = "";
	code.autofocus;
}
function editChannel(channel,interFace,port,row){
	var title= document.getElementById("modalHeader");
	var mChannel = document.getElementById("modalChannelName");
	var mIntrface = document.getElementById("modalInterface");
	var mPort = document.getElementById("modalPort");
	title.innerHTML = "Edit Channel";
	mChannel.value = channel;
	mIntrface.value = interFace;
	mPort.value = port;
	name.autofocus;
	document.getElementById("modalSubmit").onclick = function(){ saveBranch(row); } ;
}
function saveBranch(row){
	var title= document.getElementById("modalHeader");
	var mChannel = document.getElementById("modalChannelName");
	var mIntrface = document.getElementById("modalInterface");
	var mPort = document.getElementById("modalPort");
	var message = document.getElementById("modalMessage");
	var colChannel = document.getElementById(row +"channel");
	var colInterface = document.getElementById(row +"interface");
	var colPort = document.getElementById(row +"port");
	
	if(mChannel.value.trim() == "" || mIntrface.value.trim() == "" || mPort.value <= 0){
		message.style.display = "block";
		return;
	}
	$('#myModal').modal('toggle');
	swal({title : "Good job!",
		text : "",
		  type:"success"
	},function(){
	});
	colChannel.innerHTML = mChannel.value;
	colInterface.innerHTML = mIntrface.value;
	colPort.innerHTML = mPort.value;
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
<style type="text/css">

.cycle {
	display: inline-block;
	border-radius: 50px;
	height:12px;
	width:12px;
	
}
.red{
	background-color:RED;
}
.green{
	background-color:GREEN;
}
</style>
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
						<li class="active">Channel</li>
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
												<td style="padding:5px;">Channel Name</td>
												<td>&nbsp;:&nbsp;</td>
												<td style="padding:5px;"><input type="text" name="modalChannelName"
													id="modalChannelName" placeholder="channel name"
													style="width: 200%;" value=""/></td>
											</tr>
											<tr>
												<td style="padding:5px;">Interface</td>
												<td>&nbsp;:&nbsp;</td>
												<td style="padding:5px;"><input type="text" name="modalInterface"
													id="modalInterface" placeholder="interface"
													style="width: 200%;" value="" readonly/></td>
											</tr>
											<tr>
												<td style="padding:5px;">Port</td>
												<td>&nbsp;:&nbsp;</td>
												<td style="padding:5px;"><input type="number" name="modalPort"
													id="modalPort" placeholder="port"
													style="width: 50%;" min="0"/></td>
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
			<div class="panel panel-default" style="margin:auto;width:80%" >
				<div class="panel-collapse collapse in"  id="collapseOne" aria-expanded="true"  style="overflow-x:auto;">
					<div style="overflow-x:auto;">
						<table id="jqueryDataTable" 
						class="table table-striped table-bordered table-hover dataTable no-footer"
						role="grid" aria-describedby="dynamic-table_info" >
							
						<thead>
							<tr>
								<th style="text-align:center">Channel Name</th>
								<th style="text-align:center">Interface</th>
								<th style="text-align:center">Port</th>
								<th style="text-align:center">Status</th>
								<th style="text-align:center;width:50px;">Action</th>
							</tr>
						</thead>
						<tbody>
							
							<tr>
								 <td class="tdstring" id="1channel" style="text-align:left">Mobile Banking</td>
	                             <td class="tdstring" id="1interface" style="text-align:left">ISO 8583</td>
	                             <td class="tdstring" id="1port" style="text-align:center">4017</td>
	                             <td class="tdstring" style="text-align:left"><div class="cycle green" ></div> CONNECTED</td>
	                             <td class="tdstring" style="text-align:center">
	                             	<button class="btn btn-primary btn-xs" onclick="editChannel('Mobile Banking','ISO 8583','4017','1')" 
	                             		data-toggle="modal" data-target="#myModal">
	                             		<i class="ace-icon fa fa-pencil align-top bigger-125"></i></button>
	                             </td>
							</tr>
							<tr>
								 <td class="tdstring" id="2channel" style="text-align:left">Vision Link</td>
	                             <td class="tdstring" id="2interface" style="text-align:left">ISO 8583</td>
	                             <td class="tdstring" id="2port" style="text-align:center">8888</td>
	                             <td class="tdstring" style="text-align:left"><div class="cycle red" ></div> LISTEN</td>
	                             <td class="tdstring" style="text-align:center">
	                             	<button class="btn btn-primary btn-xs" onclick="editChannel('Internet Banking','ISO 8583','8888','2')" data-toggle="modal" data-target="#myModal">
										<i class="ace-icon fa fa-pencil align-top bigger-125"></i></button>
	                             </td>
							</tr>
							<tr>
								 <td class="tdstring" id="3channel" style="text-align:left">Internet Banking</td>
	                             <td class="tdstring" id="3interface" style="text-align:left">JSON</td>
	                             <td class="tdstring" id="3port" style="text-align:center">8811</td>
	                             <td class="tdstring" style="text-align:left"><div class="cycle green" ></div> CONNECTED</td>
	                             <td class="tdstring" style="text-align:center">
	                             	<button class="btn btn-primary btn-xs" onclick="editChannel('Internet Banking','JSON','8811','3')" data-toggle="modal" data-target="#myModal">
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