<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="navbar" class="navbar navbar-default  ace-save-state">
	<div class="navbar-container ace-save-state" id="navbar-container">
		<!-- /section:basics/sidebar.mobile.toggle -->
		<div class="navbar-header pull-left">
			<!-- #section:basics/navbar.layout.brand -->
			<img src="<c:url  value='/images/logo.png' />" width="100px"
				height="45px" style="margin: 0px 10px;" />
		</div>


		<!-- #section:basics/navbar.dropdown -->
		<div class="navbar-buttons navbar-header pull-right" role="navigation">
			<ul class="nav ace-nav">
				<!-- #section:basics/navbar.user_menu -->
				<li class="light-blue"><a readonly class="dropdown-toggle">
						<i class="fa fa-user bigger-120"></i> <span class="user-info">
							<small>Welcome,</small> <c:choose>
								<c:when test="${empty pageContext.request.remoteUser}">
									<script type="text/javascript">
                                             window.location.href='/cms/error';
                                         </script>
								</c:when>
								<c:otherwise>
									<c:out value="${pageContext.request.remoteUser}" />
								</c:otherwise>
							</c:choose>
					</span>
				</a></li>

				<li class="light-blue"><a href="<c:url value="/logout" />">
						<i class="fa fa-power-off bigger-120"></i> Logout
				</a></li>

				<!-- /section:basics/navbar.user_menu -->
			</ul>
		</div>

		<!-- /section:basics/navbar.dropdown -->
	</div>
	<!-- /.navbar-container -->
</div>


<jsp:include page="/WEB-INF/pages/include2.jsp" />