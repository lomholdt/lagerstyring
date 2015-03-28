<%@ taglib prefix="moveOut" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<moveOut:wrap title="Lagerafgang">

<c:if test="${msg != null}"><div class="alert alert-success">${msg}</div></c:if>
<c:if test="${error != null}"><div class="alert alert-danger">${error}</div></c:if>

  <div class="row">
            <div class="col-sm-8 col-sm-offset-2">
            	<form method="POST" action="moveout">
	                    <div class="panel panel-default">
	                        <div class="panel-heading"><h3 class="panel-title">${storage.name} - afgang</h3></div>
	                        	<div class="panel-body">
	               					<p class="lead">Antal varer til afgang</p>
	            					<div class="row">
							            <div class="col-sm-8 col-sm-offset-2">
											<c:forEach var="inventory" items="${storage.inventory}">
												<div class="form-group">
													<label for="${inventory.id}">${inventory.name}</label>
													<input type="number" class="form-control" placeholder="Antal" name="${inventory.id}" min="0" value="0">
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
										<c:forEach var="station" items="${stations}">
											<button type="submit" class="btn btn-primary" name="stationId" value="${station.id}">${station.name}</button>
										</c:forEach>
										<a class="btn btn-default" href="count" role="button">Annullér</a>
					               		</div>
					               </div>
					            </div>
	                        </div>
	           </form>
            </div>
	</div>
</moveOut:wrap>
