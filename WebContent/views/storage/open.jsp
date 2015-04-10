<%@ taglib prefix="add" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<add:wrap title="�ben Lager">

	<c:if test="${msg != null}">
		<div class="alert alert-success">${msg}</div>
	</c:if>
	<c:if test="${error != null}">
		<div class="alert alert-danger">${error}</div>
	</c:if>

	<div class="row">
		<div class="col-sm-8 col-sm-offset-2">
			<form class="form-horizontal" method="POST" action="open">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">�ben: ${storage.name}</h3>
					</div>
					<div class="panel-body">
						<p class="lead">Antal varer p� lager</p>
						<div class="row">
							<div class="col-sm-10 col-sm-offset-1">
								<c:forEach var="inventory" items="${storage.inventory}">
									<div class="form-group">
										<label for="${inventory.id}" class="col-sm-3 control-label">${inventory.name}</label> 
										<div class="col-sm-8">
										<input type="number" class="form-control" placeholder="Antal" name="${inventory.id}" min="0" value="${inventory.units}">
									 </div>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
					<div class="panel-footer">
						<p class="lead">Godkend �bning</p>
						<div class="row">
							<div class="col-sm-8 col-sm-offset-2">
							<a class="btn btn-default btn-lg" href="count" role="button">Annull�r</a>
								<input type="hidden" value="${storage.id}" name="sid">
								<button type="submit" class="btn btn-primary btn-lg">�ben
									Lager</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</add:wrap>