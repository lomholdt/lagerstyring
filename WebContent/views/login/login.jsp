<%@ taglib prefix="login" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<login:wrap title="Login">


<c:if test="${msg != null}"><div class="alert alert-success">${msg}</div></c:if>
<c:if test="${error != null}"><div class="alert alert-warning">${error}</div></c:if>

 <div class="row">
   <div class="col-sm-8 col-sm-offset-2">
       <div class="panel panel-default">
             <div class="panel-heading"><h3 class="panel-title">Log ind</h3></div>
               <div class="panel-body">
            	<div class="row">
            		<div class="col-sm-8 col-sm-offset-2">       
						<form method="POST" action="login">
						<div class="form-group">
							<label for="text">Brugernavn:</label>
							<input type="text" id="username" class="form-control" placeholder="Username" name="username"><br>
							</div>
						<div class="form-group">
							<label for="password">Adgangskode:</label>
							<input type="password" id="password" class="form-control" placeholder="Password" name="password"><br>
							</div>
						<div class="form-group">
							<input type="submit" value="Log ind" class="btn btn-primary">
							</div>
						</form>
              		</div>
                 </div>
               </div>
         </div>
    </div>
 </div>
</login:wrap>