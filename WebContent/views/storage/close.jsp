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
													<input type="number" class="form-control" placeholder="Antal" name="${inventory.id}" value="${inventory.units}">
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
										<button type="submit" class="btn btn-primary btn-lg">Luk lager</button>
										<a class="btn btn-default btn-lg" href="count" role="button">Annullér</a>
					               		</div>
					               </div>
					            </div>
	                        </div>
	           </form>
            </div>
	</div>
	<div class="row">
	<form action="close">
	<input type="hidden" name="sid" value="${storage.id}">
            <div class="col-sm-8 col-sm-offset-2">
                <div class="panel panel-default">
                    <div class="panel-heading">
                    <h3 class="panel-title">Logbog</h3>
                    </div>
                    <div class="panel-body">
                        <p class="lead">Søg i logbogen</p>
                            <div class="row">
                                <div class="col-sm-4 col-sm-offset-2">
                                     <div class="form-group">
                                        <label for="from">Fra</label>
                                        <input type="date" class="form-control input-lg" name="from" placeholder="dd-mm- yyy">
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <label for="to">Til</label>
                                        <input type="date" class="form-control input-lg" name="to" placeholder="dd-mm-yyy">
                                   </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-8 col-sm-offset-2">
                                <div class="form-group">
                                        <label for="inventoryName">Varenavn</label>
                                        <select class="form-control" name="inventoryName">
                                          <option value="allInventory">Alle varer</option>
                                          <c:forEach var="inventory" items="${storage.inventory}">
											<option value="${inventory.id}">${inventory.name}</option>
											</c:forEach>
                                        </select>
                                   </div>
                                    <div class="form-group">
                                        <label for="station">Station</label>
                                        <select class="form-control" name="stationName">
                                          <option value="allStations">Alle stationer</option>
   										<c:forEach var="primaryStation" items="${primaryStations}">
											<option value="${primaryStation.name}">${primaryStation.name}</option>
										</c:forEach>
										<c:forEach var="secondaryStation" items="${secondaryStations}">
											<option value="${secondaryStation.name}">${secondaryStation.name}</option>
										</c:forEach>
                                        </select>
                                   </div>
                                   <div class="form-group">
                                   <button type="submit" name="search" value="log" class="btn btn-primary btn-lg btn-block">Se lager rapport</button>
                                   </div>
                                   
                                   <c:if test="${logResults.size() gt 0}">
	                                   <c:forEach var="loggedStation" items="${logResults}">
	                                   		
	                                   		${loggedStation.station.name}
	                                   		<c:forEach var="loggedInventory" items="${loggedStation.loggedInventory}">
	                                   			<div>${loggedInventory.createdAt.time} ${loggedInventory.name}</div>
	                                   		</c:forEach>
	                                   
	                                   </c:forEach>
                                   </c:if>
                                   
                                   
                                </div>
                            </div>
                            
                    </div>    
                </div>
            </div>
            </form>
    </div>  
</add:wrap>
