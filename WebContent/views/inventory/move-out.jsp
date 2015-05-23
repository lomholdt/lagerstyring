<%@ taglib prefix="moveOut" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<moveOut:wrap title="Lagerafgang">

	<c:if test="${msg != null}">
		<div class="alert alert-success">${msg}</div>
	</c:if>
	<c:if test="${error != null}">
		<div class="alert alert-danger">${error}</div>
	</c:if>

	<c:if test="${storage.inventory.size() > 0}">
		<div class="row">
			<div class="col-sm-6 col-sm-offset-1">
				<h1>${storage.name}</h1>
			</div>
		</div>
		<div class="row">
			<!-- Form start -->
			<form class="form-horizontal" method="POST" action="moveout">
				<!-- Lager afgangspanel -->
				<div class="col-sm-6 col-sm-offset-1">

					<div class="panel panel-default">
						<div class="panel-heading">
							<h2>Afgang</h2>
						</div>
						<div class="panel-body">
<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
						
							<c:set var="category"
								value="${storage.inventory.get(0).category}" />
							<h3>${category}</h3>
							<c:forEach var="inventory" items="${storage.inventory}">
								<c:if test="${category ne inventory.category}">
									<hr>
									<h3>${inventory.category}</h3>
									<c:set var="category" value="${inventory.category}" />
								</c:if>
								<div class="form-group">
									<label for="${inventory.id}" class="col-sm-3 control-label">${inventory.name}</label>
									<div class="col-sm-8">
										<input type="number" step="any" class="form-control"
											name="${inventory.id}" min="0">
									</div>
								</div>
							</c:forEach>
							</div>
							</div>
						</div>
					</div>
				</div>
				<!-- stationspanel -->
				<div class="col-sm-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h2>Godkend station</h2>
						</div>
						<div class="panel-body">
						<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
						
							<h3>Primære stationer</h3>
							<input type="hidden" value="${storage.id}" name="sid">
							<c:forEach var="primaryStation" items="${primaryStations}">
								<button type="submit" class="btn btn-primary btn-lg btn-block"
									name="stationId" value="${primaryStation.id}">${primaryStation.name}</button>
							</c:forEach>
							<h3>Diverse</h3>
							<input type="hidden" value="${storage.id}" name="sid"> <input
								type="hidden" value="update" name="update">
							<c:forEach var="secondaryStation" items="${secondaryStations}">
								<button type="submit" class="btn btn-default btn-lg btn-block"
									name="stationId" value="${secondaryStation.id}">${secondaryStation.name}</button>
							</c:forEach>
							<h3>Annullér</h3>
							<a class="btn btn-default btn-lg btn-block" href="move"
								role="button">Tilbage</a>
						</div>
						</div>		
						</div>
					</div>
				</div>
			</form>
			<!-- Form slut-->
		</div>
	</c:if>
</moveOut:wrap>
