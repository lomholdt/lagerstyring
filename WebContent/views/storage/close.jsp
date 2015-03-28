<%@ taglib prefix="add" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>




<add:wrap title="Luk Lager">

<c:if test="${msg != null}"><div class="alert alert-success">${msg}</div></c:if>
<c:if test="${error != null}"><div class="alert alert-danger">${error}</div></c:if>

  <div class="row">
            <div class="col-sm-8 col-sm-offset-2">
            	<form method="POST" action="close">
	                    <div class="panel panel-default">
	                        <div class="panel-heading"><h3 class="panel-title">Luk ${storage.name}</h3></div>
	                        	<div class="panel-body">
	               					<p class="lead">Antal varer på lager</p>
	            					<div class="row">
							            <div class="col-sm-8 col-sm-offset-2">
											<c:forEach var="inventory" items="${storage.inventory}">
												<div class="form-group">
													<label for="${inventory.id}">${inventory.name}</label>
													<input type="number" class="form-control" placeholder="Antal" name="${inventory.id}" min="0" value="${inventory.units}">
												</div>
											</c:forEach>
							           </div>
	                				</div>
	                			</div>
					            <div class="panel-footer">
					              	<p class="lead">Godkend lukning</p>
					                <div class="row">
					            		<div class="col-sm-8 col-sm-offset-2">
					            		<input type="hidden" value="${storage.id}" name="sid">
										<button type="submit" class="btn btn-primary">Luk lager</button>
										<a class="btn btn-default" href="count" role="button">Annullér</a>
					               		</div>
					               </div>
					            </div>
	                        </div>
	           </form>
            </div>
	</div>
</add:wrap>
