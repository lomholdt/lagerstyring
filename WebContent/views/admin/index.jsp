<%@ taglib prefix="admin" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<admin:wrap title="Admin">
<h1>Admin</h1>
<c:if test="${msg != null}"><div class="alert alert-success">${msg}</div></c:if>
<c:if test="${error != null}"><div class="alert alert-warning">${error}</div></c:if>

<!-- TILFØJ VIRKSOMHED -->

<h3>Opret ny virksomhed: </h3>
<form method="POST" action="admin">
	<div class="form-group">
		<input type="text" class="form-control" placeholder="Firmanavn" name="companyName">
	</div>
<button type="submit" class="btn btn-primary">Opret Ny Virksomhed</button>
</form>

<h5>Alle virksomheder: </h5>
<c:forEach var="company" items="${companies}">
	<div><b>${company}</b></div>
</c:forEach>


<!-- TILFØJ BRUGER TIL VIRKSOMHED -->

<h3>Tilføj bruger til virksomhed: </h3>
<form method="POST" action="admin">
	
	<div class="form-group">
		<select class="form-control" name="storage">
		<c:forEach var="company" items="${companies}">
			<option>${company}</option>
		</c:forEach>

		</select>
	</div>

	<div class="form-group">
		<select class="form-control" name="role">
			<option value="manager">Manager</option>
			<option value="user">Runner</option>
		</select>
	</div>

	<div class="form-group">
		<input type="text" class="form-control" placeholder="username" name="username">
	</div>
	<div class="form-group">
		<input type="text" class="form-control" placeholder="password" name="password">
	</div>

<button type="submit" class="btn btn-primary">Opret Ny Bruger</button>
</form>



</admin:wrap>