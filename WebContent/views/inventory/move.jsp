<%@ taglib prefix="move" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<move:wrap title="Flyt Vare">

<c:if test="${msg != null}"><div class="alert alert-success">${msg}</div></c:if>
<c:if test="${error != null}"><div class="alert alert-danger">${error}</div></c:if>

	<div class="page-header"><h1>Flyt Vare</h1></div>
		<div class="row">
				<c:forEach var="storage" items="${storages}">
					<div class="col-sm-6">
						<div class="panel panel-default">
		                    <div class="panel-heading">
		                        <div class="row">
		                           <div class="col-sm-12"><h3 class="panel-title">${storage.name} 
			                           <c:choose>
											<c:when test="${storage.isOpen()}">
												<span class="label label-success pull-right">Åbent</span>
											</c:when>
											<c:otherwise>
												<span class="label label-danger pull-right">Lukket</span>
											</c:otherwise>
										</c:choose> </h3> 
									</div>
		                       </div>
		                   </div>
							<div class="panel-body">
								<div class="row">
									<div class="col-xs-6">
										<form method="GET" action="moveout">
												<input type="hidden" value="${storage.id}" name="sid">
												<button type="submit" class="btn btn-primary btn-lg btn-block">Afgang <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span></button>
										</form>
									</div>
									<div class="col-xs-6">
									<form method="GET" action="movein">
												<input type="hidden" value="${storage.id}" name="sid">
												<button type="submit" class="btn btn-default btn-lg btn-block">Tilgang <span class="glyphicon glyphicon-log-in" aria-hidden="true"></span></button>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
		</div>
</move:wrap>