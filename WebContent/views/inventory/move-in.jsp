<%@ taglib prefix="moveIn" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<moveIn:wrap title="Tilgang">
<h1>Flyt Varer til: ${storage.name}</h1>
<c:if test="${msg != null}"><div class="alert alert-success">${msg}</div></c:if>
<c:if test="${error != null}"><div class="alert alert-danger">${error}</div></c:if>



<form method="POST" action="movein">
<c:forEach var="inventory" items="${storage.inventory}">
	<div class="form-group">
		${inventory.name}
		<input type="number" class="form-control" placeholder="Antal" name="${inventory.id}" min="0"  value="0">
	</div>
</c:forEach>

<!-- STATIONER HER -->

<input type="hidden" name="sid" value="${storage.id}">

<c:forEach var="station" items="${stations}">
	<button type="submit" class="btn btn-success" name="stationId" value="${station.id}">${station.name}</button>
</c:forEach>


</form>

</moveIn:wrap>