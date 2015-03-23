<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default navbar-fixed-top">
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
         <ul class="nav navbar-nav">
         <c:if test="${user ne null}"> <!-- VI ER LOGGET IND -->
         	<li><a href="count">Optælling</a></li>
         	<li><a href="move">Flyt Vare</a></li>
         	<c:if test="${user.roles.contains('manager')}">
         	<li><a href="inventory">Tilføj Ny Vare</a></li>
         	</c:if>
         	<li><a href="logout">Log ud</a></li>
         	<li><p class="navbar-text">Logged in as ${user.username}</p></li>         
         </c:if>
         </ul>
       </div><!--/.nav-collapse -->
     </div>
</nav>