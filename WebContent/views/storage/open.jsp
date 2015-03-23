<%@ taglib prefix="add" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<add:wrap title="Åben Lager">
<h1>Åben Lager - ${storage.name}</h1>
<c:if test="${msg != null}"><div class="alert alert-success">${msg}</div></c:if>
<c:if test="${error != null}"><div class="alert alert-danger">${error}</div></c:if>



<form method="POST" action="open">
<c:forEach var="inventory" items="${storage.inventory}">
	<div class="form-group">
		${inventory.name}
		<input type="number" class="form-control" placeholder="Antal" name="${inventory.id}" min="0" value="${inventory.units}">
	</div>
</c:forEach>
<input type="hidden" value="${storage.id}" name="sid">
<button type="submit" class="btn btn-success">Åben Lager</button>
</form>

</add:wrap>