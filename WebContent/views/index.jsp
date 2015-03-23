<%@ taglib prefix="index" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<index:wrap title="Velkommen">
<h1>Lager Styring</h1>
	<c:if test="${msg != null}"><div class="alert alert-success">${msg}</div></c:if>
	<c:if test="${error != null}"><div class="alert alert-danger">${error}</div></c:if>
</index:wrap>