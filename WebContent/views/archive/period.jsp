<%@ taglib prefix="period" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<period:wrap title="Vælg Periode">

	<div id="search">
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="row">
							<div class="col-md-2">
								<div class="form-group">
									<h4>Lager</h4>

									<a class="btn btn-default btn-sm" href="choose" role="button">Skift <span
										class="glyphicon glyphicon-remove"></span></a>
								</div>
							</div>
							<div class="col-md-5">

								<form class="form-inline" method="GET" action="period">
									<h4>Rapport arkiv</h4>
									<div class="form-group">
										<label for="from">Fra</label> <input type="date"
											class="form-control input-sm" name="from"
											value="${storage.openedAtHtml}">
									</div>
									<div class="form-group form-group-sm">
										<label for="to">Til</label> <input type="date"
											class="form-control input-sm" name="to"
											value="${storage.openedAtHtml}">
									</div>
									<input type="hidden" value="${param.storageId}"
										name="storageId">
									<button type="submit" name="search" value="log"
										class="btn btn-primary btn-sm">Søg</button>
							</form>
							</div>
							<div class="col-md-5">
								<form class="form-inline" method="POST" action="period">
									<h4>Vælg rapport</h4>
									<div class="form-group">
										<select class="form-control input-sm" name="periods">
											<c:forEach var="logBook" items="${logBooks}">
												<option value="${logBook.openedAt.toString()}&${logBook.closedAt.toString()}">${logBook.openedAt.toString()}
													- ${logBook.closedAt.toString()}</option>
											</c:forEach>
										</select> 
										<input type="hidden" value="${param.storageId}" name="storageId"> 
										<input type="hidden" value="allInventory" name="inventoryName"> 
										<input type="hidden" value="allStations" name="stationName">
									</div>
									<div class="form-group">
										<button type="submit" name="search" value="log" class="btn btn-primary btn-sm">Se rapport</button>
									</div>
								</form>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>



	<c:if test="${logResults.size() gt 0}">
		<div id="rapport">
			<c:forEach var="loggedStorage" items="${logResults}">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">${loggedStorage.storage.name}<span
										onclick="window.print()"
										class="btn btn-default btn-xs pull-right udskriv">Udskriv</span>
								</h3>
							</div>
							<div class="panel-body">
								<c:forEach var="loggedStation"
									items="${loggedStorage.loggedStations}">
									<div class="row">
										<div class="col-sm-10 col-sm-offset-1">
											<h4>${loggedStation.station.name}</h4>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-10 col-sm-offset-1">
											<table class="table table-striped table-condensed" data-sortable>
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
		</div>
	</c:if>



</period:wrap>