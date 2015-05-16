<%@ taglib prefix="add" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>




<add:wrap title="Luk Lager">
	<script src="${pageContext.request.contextPath}/js/save.js"
		type="text/javascript"></script>
	<c:if test="${msg != null}">
		<div class="alert alert-success">${msg}</div>
	</c:if>
	<c:if test="${error != null}">
		<div class="alert alert-danger">${error}</div>
	</c:if>

	<div class="row">
		<div class="col-sm-8 col-sm-offset-2">
			<h1>${storage.name}</h1>
			<form class="form-horizontal" method="POST" action="close"
				id="closeStorageForm">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Slut tal</h3>
					</div>
					<div class="panel-body">
						<br>
						<div class="row">
							<div class="col-sm-10 col-sm-offset-1" id="inventory">


								<c:forEach var="inventory" items="${storage.inventory}">
									<c:if test="${category ne inventory.category}">
									<hr>
										<p class="lead">${inventory.category}</p>
										<c:set var="category" value="${inventory.category}" />
									</c:if>

									<div class="form-group inventory">

										<label for="${inventory.id}" class="control-label col-sm-2">${inventory.name}</label>
										<div class="col-md-4">
											<input type="number" step="any" class="form-control close-input"
												placeholder="Antal" id="${inventory.id}"
												name="${inventory.id}" min="0"
												value="<c:if test="${inventory.tempUnitsSet}">${inventory.tempUnits}</c:if>">
										</div>
										<div class="col-md-3">
											<input type="number" disabled="disabled"
												data-toggle="tooltip" data-placement="bottom" title="Forventet antal" id="${inventory.id}-inventory-expected" class="form-control"
												value="${inventory.unitsAtOpen + inventory.movesSoFar}">
										</div>

										<div class="col-md-3">
											<input type="number" class="form-control" disabled="disabled" data-toggle="tooltip" data-placement="bottom" title="Difference" id="${inventory.id}-diff">
										</div>

									</div>
								</c:forEach>
							</div>
						</div>
					</div>
					<div class="panel-footer-grey">
						<div class="row">
							<div class="col-sm-12">
								<a class="btn btn-default btn-lg pull-left" href="count" role="button">Tilbage</a>
								<input type="hidden" value="${storage.id}" name="sid"> <input
									type="hidden" value="update" name="update">
									<div class="pull-right">
								<button type="button" class="btn btn-primary btn-lg" id="save">Gem</button>
								<button type="button" class="btn btn-primary btn-lg"
									data-toggle="modal" data-target="#saveAndClose">Gem og luk</button>
									</div>
							</div>
						</div>
					</div>
					<!-- MODAL START -->
					<div class="modal fade" id="saveAndClose" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					  <div class="modal-dialog">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					        <h4 class="modal-title" id="myModalLabel">Luk ${storage.name}</h4>
					      </div>
					      <div class="modal-body">
					        Vil du lukke lageret?
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-default btn-lg" data-dismiss="modal">Annuller</button>
					        <button type="submit" class="btn btn-primary btn-lg">Luk Lager</button>
					      </div>
					    </div>
					  </div>
					</div>
					<!-- MODAL END -->
				</div>
			</form>
		</div>
	</div>
	<div class="row">
		<form method="POST" action="close#report">
			<input type="hidden" name="sid" value="${storage.id}">
			<div class="col-sm-8 col-sm-offset-2">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Logbog</h3>
					</div>
					<div class="panel-body">
						<p class="lead">
							Søg i logbogen
							<c:if test="${fromTimestamp ne null}">(fra ${fromTimestamp} til ${toTimestamp})</c:if>
						</p>
						<div class="row">
							<div class="col-sm-8 col-sm-offset-2">
								<div class="form-group">
									<select class="form-control" name="inventoryName">
										<option value="allInventory">Alle varer</option>
										<c:forEach var="inventory" items="${storage.inventory}">
											<option value="${inventory.name}">${inventory.name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group">
									<select class="form-control" name="stationName">
										<option value="allStations">Alle stationer</option>
										<c:forEach var="primaryStation" items="${primaryStations}">
											<option value="${primaryStation.name}">${primaryStation.name}</option>
										</c:forEach>
										<c:forEach var="secondaryStation" items="${secondaryStations}">
											<option value="${secondaryStation.name}">${secondaryStation.name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group">
									<button type="submit" name="search" value="log"
										class="btn btn-primary btn-lg btn-block">Se lager
										rapport</button>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
		</form>
	</div>
	<c:if test="${logResults.size() gt 0}">
		<div class="row" id="report">
			<div class="col-sm-8 col-sm-offset-2">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Lager rapport</h3>
					</div>
					<div class="panel-body">
						<c:forEach var="loggedStation" items="${logResults}">
							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
									<h4>${loggedStation.station.name}</h4>
								</div>
							</div>

							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
								<div class="table-responsive">
									<table class="table table-striped" data-sortable>
										<thead>
											<tr>
												<th>Tidspunkt</th>
												<th>Varenavn</th>
												<th>Antal</th>
												<th>Handling</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="loggedInventory"
												items="${loggedStation.loggedInventory}">
												<tr>
													<td>${loggedInventory.createdAt.time}</td>
													<td>${loggedInventory.name}</td>
													<td>${loggedInventory.units}</td>
													<td>${loggedInventory.performedAction}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>	
								</div>
							</div>
							<div class="row">
								<hr>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</c:if>	
	
	<script src="${pageContext.request.contextPath}/js/diff.js"
		type="text/javascript"></script>
</add:wrap>
