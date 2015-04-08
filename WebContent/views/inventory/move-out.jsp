<%@ taglib prefix="moveOut" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<moveOut:wrap title="Lagerafgang">

<c:if test="${msg != null}"><div class="alert alert-success">${msg}</div></c:if>
<c:if test="${error != null}"><div class="alert alert-danger">${error}</div></c:if>

  <div class="row">
            <div class="col-sm-8 col-sm-offset-2">
            	<form method="POST" action="moveout">
	                    <div class="panel panel-default">
	                        <div class="panel-heading"><h3 class="panel-title">Afgang: ${storage.name}</h3></div>
	                        	<div class="panel-body">
	               					<p class="lead">Antal varer til afgang</p>
	            					<div class="row">
							            <div class="col-sm-8 col-sm-offset-2">
											<c:forEach var="inventory" items="${storage.inventory}">
												<div class="form-group">
													<label for="${inventory.id}">${inventory.name}</label>
													<input type="number" class="form-control" name="${inventory.id}" min="0">
												</div>
											</c:forEach>
							           </div>
	                				</div>
	                			</div>
					            <div class="panel-footer">
					              	<p class="lead">Godkend station</p>
					                <div class="row">
					            		<div class="col-sm-8 col-sm-offset-2">
					            		<input type="hidden" value="${storage.id}" name="sid">
					            		<div class="form-inline">
										<c:forEach var="primaryStation" items="${primaryStations}">
										<div class="form-group">
											<button type="submit" class="btn btn-primary btn-lg btn-block" name="stationId" value="${primaryStation.id}">${primaryStation.name}</button>
										</div>
										</c:forEach>
					               		</div>
					               		</div>
					               </div>
					           </div>
					            <div class="panel-footer">
					              	<p class="lead">Diverse</p>
					                <div class="row">
					            		<div class="col-sm-8 col-sm-offset-2">
					            		<input type="hidden" value="${storage.id}" name="sid">
					            		<div class="form-inline">
										<c:forEach var="secondaryStation" items="${secondaryStations}">
										<div class="form-group">
											<button type="submit" class="btn btn-default btn-lg btn-block" name="stationId" value="${secondaryStation.id}">${secondaryStation.name}</button>
										</div>
										</c:forEach>
										</div>
					               		</div>
					               </div>
					            </div>
	                        </div>
	                        <div class="form-group">
										<a class="btn btn-default btn-lg" href="move" role="button">Annullér</a>
										</div>
	           </form>
            </div>
	</div>
</moveOut:wrap>
