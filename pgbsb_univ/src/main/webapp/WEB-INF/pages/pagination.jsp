<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<ul class="pagination pull-right no-margin">

<!-- Get Param -->
	
	<c:set var="paginationUrl" value="${param.paginationUrl }"/>
	<c:set var="currentIndex" value="${param.currentIndex }"/>
	<c:set var="page" value="${param.page }"/>

<!-- First and Prev -->

	<c:choose>
		<c:when test="${currentIndex == 1}">
			<li class="disabled"><a href="#">&lt;&lt;</a></li>
			<li class="disabled"><a href="#">Previous</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="${paginationUrl.firstUrl}">&lt;&lt;</a></li>
			<li><a href="${paginationUrl.prevUrl}">Previous</a></li>
		</c:otherwise>
	</c:choose>

<!-- Middle -->
	
	<c:forEach var="i" items="${paginationUrl.middleUrl }">
		<c:choose>
			<c:when test="${i == currentIndex}">
				<li class="active"><a href="${i.value}"><c:out value="${i}" /></a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${i.value}"><c:out value="${i}" /></a></li>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	
<!-- Next and Last -->
	
	<c:choose>
		<c:when test="${currentIndex == page.totalPages}">
			<li class="disabled"><a href="#">Next</a></li>
			<li class="disabled"><a href="#">&gt;&gt;</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="${paginationUrl.nextUrl}">Next</a></li>
			<li><a href="${paginationUrl.lastUrl}">&gt;&gt;</a></li>
		</c:otherwise>
	</c:choose>
</ul>