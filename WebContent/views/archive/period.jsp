<%@ taglib prefix="period" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<period:wrap title="Vælg Periode">
<script src="${pageContext.request.contextPath}/js/jquery.table2excel.js"></script>

	<div id="search">
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="row">
							<div class="col-md-2">
								<div class="form-group">
									<h4>Lager</h4>

									<a class="btn btn-default btn-sm" href="choose" role="button">${storage.name}
										<span class="glyphicon glyphicon-remove"></span>
									</a>
								</div>
							</div>
							<div class="col-md-5">

								<form class="form-inline" method="GET" action="period">
									<h4>Rapport arkiv</h4>
									<div class="form-group">
										<label for="from">Fra</label> <input type="date"
											class="form-control input-sm" name="from" value="${fromTime}">
									</div>
									<div class="form-group form-group-sm">
										<label for="to">Til</label> <input type="date"
											class="form-control input-sm" name="to" value="${toTime}">
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
												<option
													value="${logBook.openedAt.toString()}&${logBook.closedAt.toString()}">${logBook.openedAt.toString()}
													- ${logBook.closedAt.toString()}</option>
											</c:forEach>
										</select> 
										<input type="hidden" value="${param.storageId}" name="storageId"> 
											<input type="hidden" value="allInventory" name="inventoryName"> 
											<input type="hidden" value="allStations" name="stationName">
											<input type="hidden" value="report" name="report">
									</div>
									<div class="form-group">
										<button type="submit" name="search" value="log"
											class="btn btn-primary btn-sm">Se rapport</button>
									</div>
								</form>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>



	<!-- RAPPORT -->
	<c:if test="${loggedInventory.size() eq 0 && param.report ne null}"> 
	<div><h3>Der er ingen rapporter tilgængelige i det angivne interval.</h3></div>
	</c:if>
	<c:if test="${loggedInventory.size() gt 0}">
		<div id="rapport">
			<!-- OVERSIGT START -->
			<h1>Rapport - ${storage.name}</h1>
			<div class="row">
				<div class="col-sm-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">
								Oversigt <span onclick="window.print()"
									class="btn btn-default btn-xs pull-right udskriv">Udskriv</span> <span id="export" class="btn btn-default btn-xs pull-right">Excel</span>
							</h3>
						</div>
						<div class="panel-body">
							<table id="overview" class="table table-striped" data-sortable>
								<thead>
									<tr>
										<th>Vare</th>
										<th>Start</th>
										<th>Afgang</th>
										<th>Slut</th>
										<th>Diff.</th>
										<th>Salg</th>
										<th>Indkøbspris</th>
										<th>Omsætningspris</th>
										<th>Indkøb-kr.</th>
										<th>Omsætning-kr.</th>
									</tr>
								</thead>
								<c:forEach var="loggedInventory" items="${loggedInventory}">
									<tr>
										<td>${loggedInventory.name}</td>
										<td>${loggedInventory.inventoryStartValue}</td>
										<td>
											<c:forEach var="move" items="${loggedInventory.moves}">
												${move},
											</c:forEach>
										</td>
										<td>${loggedInventory.closedAt}</td>
										<td>${loggedInventory.diff}</td>
										<td>${loggedInventory.totalUnitsWithDiff}</td>
										<td>${loggedInventory.unitPrice}</td>
										<td>${loggedInventory.unitSalesPrice}</td>
										<td>${loggedInventory.totalValueWithDiff}</td>
										<td>${loggedInventory.totalSalesValueWithDiff}</td>
									</tr>
									<c:set var="inventoryTotal"
										value="${inventoryTotal+loggedInventory.totalValueWithDiff}" />
									<c:set var="inventorySalesTotal"
										value="${inventorySalesTotal+loggedInventory.totalSalesValueWithDiff}" />
								</c:forEach>
								<thead>
									<tr class="success">
										<th>TOTAL:</th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th><c:out value="${inventoryTotal}" /></th>
										<th><c:out value="${inventorySalesTotal}" /></th>
									</tr>
								</thead>
							</table>
							<!-- OVERSIGT SLUT -->
						</div>
					</div>
				</div>
			</div>
			<c:forEach var="loggedStorage" items="${loggedStations}">
				<div class="row">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">
									Stationer<span onclick="window.print()"
										class="btn btn-default btn-xs pull-right udskriv">Udskriv</span>
								</h3>
							</div>
							<div class="panel-body">
								<c:forEach var="loggedStation"
									items="${loggedStorage.loggedStations}">
									<c:set var="stationTotal" value="0" />
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
														<th>Vare</th>
														<th>Afgang</th>
														<th>Salg</th>
														<th>Indkøbspris</th>
														<th>Indkøb-kr.</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="loggedInventory"
														items="${loggedStation.loggedInventory}">
														<tr>
															<td>${loggedInventory.name}</td>
															<td><c:forEach var="move"
																	items="${loggedInventory.moves}">
																	${move},
																</c:forEach></td>
															<td>${loggedInventory.totalUnits}</td>
															<td>${loggedInventory.unitPrice}</td>
															<td>${loggedInventory.totalValue}</td>
														</tr>
														<c:set var="stationTotal"
															value="${stationTotal+loggedInventory.totalValue}" />
													</c:forEach>
												</tbody>
												<tr class="success">
													<th>TOTAL:</th>
													<th></th>
													<th></th>
													<th></th>
													<th><c:out value="${stationTotal}" /></th>
												</tr>
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

<script>
		$("#export").click(function(){
	  $("#overview").table2excel({
	    // exclude CSS class
	    exclude: ".noExl",
	    name: "Rapport",
	    filename: "Rapport"
	  });
	});
</script>

</period:wrap>