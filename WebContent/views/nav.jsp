<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%

String uri = request.getRequestURI();
String pageName = uri.substring(uri.lastIndexOf("/")+1);

%>



<nav class="navbar navbar-default navbar-fluid">
     <div class="container">
       <div class="navbar-header">
         <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
           <span class="sr-only">Toggle navigation</span>
           <span class="icon-bar"></span>
           <span class="icon-bar"></span>
           <span class="icon-bar"></span>
         </button>
         <a class="navbar-brand" href="${pageContext.request.contextPath}">LAGERSTYRING</a>
         
       </div>
       <div id="navbar" class="collapse navbar-collapse">
         <ul class="nav navbar-nav navbar-right">
         <c:if test="${user ne null}"> <!-- VI ER LOGGET IND -->
         	<li <c:if test="${requestScope['javax.servlet.forward.request_uri'] ==('/lagerstyring/count')}"> class="active"</c:if>><a href="count">Lageroptælling</a></li>
         	<li <c:if test="${requestScope['javax.servlet.forward.request_uri'] ==('/lagerstyring/move')}"> class="active"</c:if>><a href="move">Flyt Vare</a></li>
         	<c:if test="${user.roles.contains('manager')}">
         	<li <c:if test="${requestScope['javax.servlet.forward.request_uri'] ==('/lagerstyring/inventory')}"> class="active"</c:if>><a href="inventory">Indstillinger</a></li>
         	</c:if>
        	<li class="dropdown">
          	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">${user.username} <span class="caret"></span></a>
          	<ul class="dropdown-menu" role="menu">
            <c:if test="${user.roles.contains('admin')}">
         		<li><a href="admin">Admin Panel</a>
         	</c:if>
            <li class="divider"></li>
            <li><a href="logout">Log ud</a></li>
          </ul>
        </li>
         </c:if>
         
         </ul>
       </div><!--/.nav-collapse -->
     </div>
</nav>

