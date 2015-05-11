<%@ taglib prefix="add" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<add:wrap title="Optælling">

	<c:if test="${msg != null}">
		<div class="alert alert-success">${msg}</div>
	</c:if>
	<c:if test="${error != null}">
		<div class="alert alert-danger">${error}</div>
	</c:if>
	
<h1>Lageroptælling</h1>
	
	
	<div class="row">
		<c:forEach var="storage" items="${storages}">
			<div class="col-sm-6">
				<div class="panel panel-default">
					<div class="panel-heading">
						<div class="row">
							<div class="col-sm-12">
								<h3 class="panel-title">${storage.name}
									<c:choose>
										<c:when test="${storage.isOpen()}">
											<span class="label label-success label-xs pull-right"> Åbent
												${storage.openedAtHtml}</span>
										</c:when>
										<c:otherwise>
											<span class="label label-danger pull-right">Lukket
												${storage.openedAtHtml}</span>
										</c:otherwise>
									</c:choose>
								</h3>
							</div>
						</div>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-6">
								<form method="POST" action="open">
									<c:if test="${storage.inventoryCount eq 0}">
										<c:set var="status" value="disabled" />									
									</c:if>
									<input type="hidden" value="${storage.id}" name="sid">
									<button type="submit" class="btn btn-primary btn-lg btn-block" ${status}>Start tal</button>
								</form>
							</div>
							<div class="col-xs-6">
								<form method="POST" action="close">
									<input type="hidden" value="${storage.id}" name="sid">
									<button type="submit" class="btn btn-default btn-lg btn-block" ${status}>Slut tal</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</add:wrap>