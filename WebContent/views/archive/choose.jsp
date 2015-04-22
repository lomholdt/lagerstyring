<%@ taglib prefix="chooser" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<chooser:wrap title="Vælg Lager">

	<div class="row">
		<div class="col-sm-8 col-sm-offset-2">
			<h1>Rapport</h1>
			<c:if test="${msg != null}">
				<div class="alert alert-success">${msg}</div>
			</c:if>
			<c:if test="${error != null}">
				<div class="alert alert-danger">${error}</div>
			</c:if>
			<form method="GET" action="period">
				<div class="panel panel-default">
					<div class="panel-body">
						<p class="lead">Vælg lager</p>
						<div class="row">
							<div class="col-sm-8 col-sm-offset-2">
								<div class="form-group">
									<select class="form-control" name="storageId">
										<!--  <option value="allStorages">Alle Lagre</option> -->
										<c:forEach var="storage" items="${storages}">
											<option value="${storage.id}">${storage.name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group">
									<button type="submit" class="btn btn-primary btn-lg btn-block">Godkend
										lager</button>
								</div>

							</div>
						</div>
					</div>

				</div>
			</form>
		</div>
	</div>

</chooser:wrap>