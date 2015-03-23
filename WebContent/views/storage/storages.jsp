<%@ taglib prefix="add" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<add:wrap title="Opt�lling">
<h1>Opt�lling - Alle lagre</h1>
<c:if test="${msg != null}"><div class="alert alert-success">${msg}</div></c:if>
<c:if test="${error != null}"><div class="alert alert-danger">${error}</div></c:if>



<c:forEach var="storage" items="${storages}">
	<h3>${storage.name}</h3>
	<c:choose>
		<c:when test="${storage.isOpen()}">
			<span>�ben</span>
		</c:when>
		<c:otherwise>
			<span>Lukket</span>
		</c:otherwise>
	</c:choose>
	<form method="GET" action="open">
		<input type="hidden" value="${storage.id}" name="sid">
		<button type="submit" class="btn btn-success">�ben Lager</button>
	</form>
	<form method="GET" action="close">
		<input type="hidden" value="${storage.id}" name="sid">
		<button type="submit" class="btn btn-danger">Luk Lager</button>
	</form>
</c:forEach>



</add:wrap>