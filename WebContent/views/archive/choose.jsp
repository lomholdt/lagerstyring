<%@ taglib prefix="chooser" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<chooser:wrap title="Vælg Lager">


	<form method="GET" action="period">
		<div class="form-group">
			<label for="inventoryName">Lager</label> <select class="form-control" name="storageId">
				<!--  <option value="allStorages">Alle Lagre</option> -->
				<c:forEach var="storage" items="${storages}">
					<option value="${storage.id}">${storage.name}</option>
				</c:forEach>
			</select>
		</div>
		
		<div class="form-group">
		<input type="submit" class="btn btn-success">
		</div>
	</form>


</chooser:wrap>