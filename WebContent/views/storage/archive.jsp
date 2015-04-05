<%@ taglib prefix="archive" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<archive:wrap title="Arkiv">
	<div class="row">
		<form action="archive" method="POST">
			<div class="col-sm-8 col-sm-offset-2">
			<h1>Arkiv</h1>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Arkiv</h3>
					</div>
					<div class="panel-body">
						<p class="lead">Vælg lager</p>
						<div class="row">
							<div class="col-sm-8 col-sm-offset-2">
								<div class="form-group">
									<label for="inventoryName">Lager</label> <select
										class="form-control" name="storageId">
										<option value="allStorages">Alle Lagre</option>
										<c:forEach var="storage" items="${storages}">
											<option value="${storage.id}">${storage.name}</option>
										</c:forEach>
									</select>
								</div>
								</div>
							</div>
							<div class="row"><hr></div>
							<p class="lead">Vælg periode</p>
						<div class="row">
							<div class="col-sm-4 col-sm-offset-2">
								<div class="form-group">
									<label for="from">Fra</label> 
									<input type="date"
										class="form-control" name="from"
										value="${storage.openedAtHtml}">
								</div>
							</div>
							<div class="col-sm-4">
								<div class="form-group">
									<label for="to">Til</label> <input type="date"
										class="form-control" name="to"
										value="${storage.openedAtHtml}">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-8 col-sm-offset-2">
							<div class="btn-group-vertical btn-block" role="group">
							  <button type="button" class="btn btn-logbog btn-sm">01/04/2015 - 02/04/2015</button>
							  <button type="button" class="btn btn-logbog btn-sm">02/04/2015 - 03/04/2015</button>
							  <button type="button" class="btn btn-logbog btn-sm">06/04/2015 - 07/04/2015</button>
							</div>
							</div>
						</div>
						<div class="row"><hr></div>
						<p class="lead">Filter</p>
						<div class="row">
							<div class="col-sm-8 col-sm-offset-2">
								<div class="form-group">
									<label for="inventoryName">Varenavn</label> <select
										class="form-control" name="inventoryName">
										<option value="allInventory">Alle varer</option>
										<c:forEach var="inventory" items="${allInventory}">
											<option value="${inventory.name}">${inventory.name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group">
									<label for="station">Station</label> <select
										class="form-control" name="stationName">
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
										class="btn btn-primary btn-lg btn-block">Se lager rapport</button>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
		</form>
	</div>
	<c:if test="${logResults.size() gt 0}">
	<c:forEach var="loggedStorage" items="${logResults}">
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Lager rapport: ${loggedStorage.storage.name}</h3>
					</div>
					<div class="panel-body">
						<c:forEach var="loggedStation" items="${loggedStorage.loggedStations}">
							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
									<h4>${loggedStation.station.name}</h4>
								</div>
							</div>

							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
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
							<div class="row">
								<hr>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		</c:forEach>
	</c:if>
</archive:wrap>