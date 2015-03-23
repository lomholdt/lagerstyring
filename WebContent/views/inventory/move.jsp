<%@ taglib prefix="move" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<move:wrap title="Flyt Vare">
<h1>Flyt Vare</h1>
<c:if test="${msg != null}"><div class="alert alert-success">${msg}</div></c:if>
<c:if test="${error != null}"><div class="alert alert-danger">${error}</div></c:if>


<c:forEach var="storage" items="${storages}">
	<h3>${storage.name}</h3>
	<c:choose>
		<c:when test="${storage.isOpen()}">
			<span>Åben</span>
		</c:when>
		<c:otherwise>
			<span>Lukket</span>
		</c:otherwise>
	</c:choose>
	<form method="GET" action="open">
		<input type="hidden" value="${storage.id}" name="sid">
		<button type="submit" class="btn btn-success">Afgang</button>
	</form>
	<form method="GET" action="close">
		<input type="hidden" value="${storage.id}" name="sid">
		<button type="submit" class="btn btn-danger">Tilgang</button>
	</form>
</c:forEach>

</move:wrap>