<%@ taglib prefix="moveIn" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<moveIn:wrap title="Lagertilgang">

<c:if test="${msg != null}"><div class="alert alert-success">${msg}</div></c:if>
<c:if test="${error != null}"><div class="alert alert-danger">${error}</div></c:if>





<div class="row">
		<!-- Form start -->
		<form class="form-horizontal" method="POST" action="movein">
			<!-- Lager afgangspanel -->
			<div class="col-sm-6 col-sm-offset-1">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Tilgang - ${storage.name}</h3>
					</div>
					<div class="panel-body">
						<p class="lead">Antal varer til tilgang</p>

						<div class="col-sm-10 col-sm-offset-1">
							<c:forEach var="inventory" items="${storage.inventory}">
									<div class="form-group">
										<label for="${inventory.id}" class="col-sm-3 control-label">${inventory.name}</label> 
										<div class="col-sm-8">
											<input
												type="number" step="any" class="form-control" name="${inventory.id}" min="0">
										</div>
									</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
			<!-- stationspanel -->
			<div class="col-sm-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Godkend station</h3>
					</div>
					<div class="panel-body">
						<p class="lead">Primære stationer</p>
						<div class="col-sm-10 col-sm-offset-1">
						<input type="hidden" value="${storage.id}" name="sid">
							<c:forEach var="primaryStation" items="${primaryStations}">
										<div class="form-group">
											<button type="submit"
												class="btn btn-primary btn-lg btn-block" name="stationId"
												value="${primaryStation.id}">${primaryStation.name}</button>
										</div>
							</c:forEach>
						</div>
						
						<p class="lead">Diverse</p>
						<div class="col-sm-10 col-sm-offset-1">
						<input type="hidden" value="${storage.id}" name="sid">
	
								<c:forEach var="secondaryStation" items="${secondaryStations}">
										<div class="form-group">
											<button type="submit"
												class="btn btn-default btn-lg btn-block" name="stationId"
												value="${secondaryStation.id}">${secondaryStation.name}</button>
										</div>
							</c:forEach>
						</div>
						
						<p class="lead">Annullér</p>
						<div class="col-sm-10 col-sm-offset-1">
							<div class="form-group">
								<a class="btn btn-default btn-lg btn-block" href="move" role="button">Annullér</a>	
							</div>
						</div>
						
					</div>
				</div>
			</div>
		</form>
		<!-- Form slut-->
	</div>
</moveIn:wrap>