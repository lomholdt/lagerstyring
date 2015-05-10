<%@ taglib prefix="add" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<add:wrap title="Åben Lager">

	<c:if test="${msg != null}">
		<div class="alert alert-success">${msg}</div>
	</c:if>
	<c:if test="${error != null}">
		<div class="alert alert-danger">${error}</div>
	</c:if>
	<div class="row">
		<div class="col-sm-8 col-sm-offset-2">
		<h1>${storage.name}</h1>
			<form class="form-horizontal" method="POST" action="open" id="openStorageForm">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Start tal</h3>
					</div>
					<div class="panel-body"> <!-- INVENTORY START  -->
						<br>
						<div class="row"> 
							<div class="col-sm-10 col-sm-offset-1">
								<c:set var="category" value="${storage.inventory.get(0).category}" />
								<p class="lead">${category}</p>
								<c:forEach var="inventory" items="${storage.inventory}">
								
								<c:if test="${category ne inventory.category}">
								<hr>
									<p class="lead">${inventory.category}</p>
									<c:set var="category" value="${inventory.category}" />
								</c:if>
								
									<div class="form-group">
										<label for="${inventory.id}" class="col-sm-3 control-label">${inventory.name}</label> 
										<div class="col-sm-8">
										<input type="number" step="any" class="form-control" placeholder="Antal" name="${inventory.id}" min="0" value="${inventory.units}">
									 </div>
									</div>
								</c:forEach>
							</div>
						</div>
					</div> <!-- INVENTORY END  -->
					<div class="panel-footer-grey">
						<div class="row">
							<div class="col-sm-12">
							<a class="btn btn-default btn-lg pull-left" href="count" role="button">Annullér</a>
								<input type="hidden" value="${storage.id}" name="sid">
								<input type="hidden" value="update" name="update">
								<button type="button" class="btn btn-primary btn-lg pull-right" data-toggle="modal" data-target="#saveAndOpen">Godkend start-tal</button>
							</div>
						</div>
					</div>
					<!-- MODAL START -->
					<div class="modal fade" id="saveAndOpen" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					  <div class="modal-dialog">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					        <h4 class="modal-title" id="myModalLabel">Åben ${storage.name}</h4>
					      </div>
					      <div class="modal-body">
					        Vil du åbne lageret?
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-default btn-lg" data-dismiss="modal">Annullér</button>
					        <button type="submit" class="btn btn-primary btn-lg">Åben Lager</button>
					      </div>
					    </div>
					  </div>
					</div>
					<!-- MODAL END -->
				</div>
			</form>
		</div>
	</div>
</add:wrap>