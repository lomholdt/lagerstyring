<%@ taglib prefix="add" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<add:wrap title="Tilføj Ny Vare">
<script src="${pageContext.request.contextPath}/js/price.js"></script>

	<c:if test="${msg != null}">
		<div class="alert alert-success">${msg}</div>
	</c:if>
	<c:if test="${error != null}">
		<div class="alert alert-danger">${error}</div>
	</c:if>


	<div class="row">
		<div class="col-sm-8 col-sm-offset-2">
			<h1>Indstillinger</h1>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Opret vare</h3>
				</div>

				<div class="panel-body">
					<p class="lead">Tilføj vare</p>
					<div class="row">
						<div class="col-sm-8 col-sm-offset-2">
							<form method="POST" action="inventory#vareliste">
								<div class="form-group">
									<select class="form-control" name="storage">
										<c:forEach var="storage" items="${storages}">
											<option>${storage.name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group">
									<input type="text" class="form-control" placeholder="Varenavn"
										name="name">
								</div>
								<div class="form-group">
									<input type="hidden" class="form-control" placeholder="Antal"
										name="units" value="0">
								</div>
								<div class="form-group">
									<input type="text" class="form-control" placeholder="Indkøbspris"
										name="price">
								</div>
								<div class="form-group">
									<input type="text" class="form-control" placeholder="Omsætningspris"
										name="salesPrice">
								</div>
								<div class="form-group">
									<button type="submit" class="btn btn-primary btn-lg">Tilføj vare</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<a id="vareliste"></a>
				<div class="panel-footer">
					<p class="lead">Vareliste</p>
					<div class="row">
						<div class="col-sm-8 col-sm-offset-2">
							<form action="delete" method="POST">
								<table class="table table-condensed table-striped table-hover">
										<thead>
											<tr>
												<th>Vare</th>
												<th>Indkøbspris</th>
												<th>Omsætningspris</th>
												<th>Marker</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="inventory" items="${allInventory}">
											<input type="hidden" value="${inventory.id}">
												<tr>
													<td><label for="${inventory.name}">${inventory.name}</label></td>
													<td><input type="text" class="form-control input-sm" placeholder="Indkøbspris"
										name="uPrice" value="${inventory.price}" onblur="update(${inventory.id}, 'uPrice')" id="uPrice-${inventory.id}"></td>
													<td><input type="text" class="form-control input-sm" placeholder="Omsætningspris"
										name="uSalesPrice" value="${inventory.salesPrice}" onblur="update(${inventory.id}, 'uSalesPrice')" id="uSalesPrice-${inventory.id}"></td>
													<td><input type="checkbox" id="${inventory.name}" name="i" value="${inventory.id}"></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								<div class="form-group">
									<input type="submit" class="btn btn-danger btn-lg" value="Slet valgte">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</add:wrap>