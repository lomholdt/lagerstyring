<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String uri = request.getRequestURI();
String pageName = uri.substring(uri.lastIndexOf("/")+1);
%>



<nav class="navbar navbar-default navbar-fluid">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="count">Lager.io</a>

		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<c:if test="${user ne null}">
			
				<ul class="nav navbar-nav navbar-right">

					<!-- VI ER LOGGET IND -->
					<li
						<c:if test="${requestScope['javax.servlet.forward.request_uri'] ==('/count')}"> class="active"</c:if>><a
						href="count"><span class="glyphicon glyphicon-pencil"></span> Lageroptælling</a></li>
					<li
						<c:if test="${requestScope['javax.servlet.forward.request_uri'] ==('/move')}"> class="active"</c:if>><a
						href="move"><span class="glyphicon glyphicon-transfer"></span> Flyt Vare</a></li>
					
					<c:if test="${user.roles.contains('manager')}">
					<li
						<c:if test="${requestScope['javax.servlet.forward.request_uri'] ==('/choose')}"> class="active"</c:if>><a
						href="choose"><span class="glyphicon glyphicon-print"></span> Rapport</a></li>
					</c:if>
					
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">${user.username}
							<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<c:if test="${user.roles.contains('admin')}">
								<li><a href="admin">Admin Panel</a>
								<li class="divider"></li>
							</c:if>
							<c:if test="${user.roles.contains('manager')}">
								<li><a href="inventory">Indstillinger</a></li>
								<li class="divider"></li>
							</c:if>
							<li><a href="logout" data-toggle="tooltip" data-placement="bottom" title="${user.companyName}">Log ud</a></li>
						</ul></li>

				</ul>
			</c:if>
		</div>
		<!--/.nav-collapse -->
		
			</div>
</nav>