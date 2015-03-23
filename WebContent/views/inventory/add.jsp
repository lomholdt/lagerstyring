<%@ taglib prefix="add" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<add:wrap title="Tilføj Ny Vare">
<h1>Tilføj Ny Vare</h1>
<c:if test="${msg != null}"><div class="alert alert-success">${msg}</div></c:if>
<c:if test="${error != null}"><div class="alert alert-danger">${error}</div></c:if>

<form method="POST" action="inventory">
	<div class="form-group">
		<select class="form-control" name="storage">
  			<c:forEach var="storage" items="${storages}">
  				<option>${storage.name}</option>
			</c:forEach>
		</select>
	</div>
	<div class="form-group">
		<input type="text" class="form-control" placeholder="Varenavn" name="name">
	</div>
	<div class="form-group">
		<input type="number" class="form-control" placeholder="Antal" name="units">
	</div>
<button type="submit" class="btn btn-success">Indsæt Ny Vare</button>
</form>

</add:wrap>