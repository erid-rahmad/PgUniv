<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="sidebar">
<!-- #section:basics/sidebar -->
	<div id="sidebar" class="sidebar responsive ace-save-state">
		<script type="text/javascript">
			try{ace.settings.loadState('sidebar')}catch(e){}
		</script>
		
		
		<ul class="nav nav-list">
           	<li id="lireports" class="open">
				<a href="<c:url  value='/channel' />">
					<i class="menu-icon fa fa-credit-card bigger-150"></i>
					<span class="menu-text">Menu</span>
				</a>
				<ul class="submenu">
              			<li><a href="<c:url  value='/channel' />"> Channel </a><b class="arrow"></b></li>
              			<li><a href="<c:url  value='/trx' />"> Transaksi </a><b class="arrow"></b></li>
      				</ul>
			</li>
		</ul>
	</div>
</div>