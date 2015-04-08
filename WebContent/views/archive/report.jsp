<%@ taglib prefix="report" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<report:wrap title="Rapport">
<script src="${pageContext.request.contextPath}/js/print.js"></script>
	<c:if test="${logResults.size() gt 0}">
		<div id="rapport">
			<c:forEach var="loggedStorage" items="${logResults}">
				<div class="row">
					<div class="col-sm-8 col-sm-offset-2">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">Lager rapport:
									${loggedStorage.storage.name}</h3>
								<a onclick="getPrint()">Udskriv</a>
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
		</div>
	</c:if>
</report:wrap>