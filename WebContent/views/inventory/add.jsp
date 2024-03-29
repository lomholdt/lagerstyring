<%@ taglib prefix="add" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<add:wrap title="Tilf�j Ny Vare">
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
					<h2>Opret vare</h2>
				</div>

				<div class="panel-body">
					<div class="row">
						<div class="col-sm-8 col-sm-offset-2">
						<h3>Tilf�j vare</h3>
							<form method="POST" action="inventory#vareliste">
								<div class="form-group">
									<select class="form-control" name="storage">
										<c:forEach var="storage" items="${storages}">
											<option>${storage.name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group">
									<select class="form-control" name="category">
									<option value="none" selected>V�lg kategori</option>
										<c:forEach var="category" items="${categories}">
											<option value="${category.id}">${category.category}</option>
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
									<input type="number" class="form-control" placeholder="Indk�bspris*"
										name="price" step="any">
								</div>
								<div class="form-group">
									<input type="number" class="form-control" placeholder="Oms�tningspris*"
										name="salesPrice" step="any">
								</div>
								<div class="form-group">
									<button type="submit" class="btn btn-primary btn-lg btn-block">Tilf�j vare</button>
								</div>
							</form>
							
						</div>
					</div>
					<hr>
						<a id="vareliste"></a>
					<div class="row">
						<div class="col-sm-12">
						<h3 >Vareliste</h3>
							<form action="delete" method="POST">
								<table class="table table-condensed table-striped table-hover">
										<thead>
											<tr>
												<th>Vare</th>
												<th>Lager</th>
												<th>Indk�bspris</th>
												<th>Salgspris</th>
												<th>Kategori</th>
												<th>Marker</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="inventory" items="${allInventory}">
											
											<c:choose>
												<c:when test="${inventory.storageOpen}">
													<c:set var="status" value="disabled" />
												</c:when>
												<c:otherwise>
													<c:set var="status" value="" />
												</c:otherwise>
											</c:choose>
											
											<input type="hidden" value="${inventory.id}">
												<tr>
													<td>
														<label for="${inventory.name}">${inventory.name}</label>
													</td>
													<td>
														<span>${inventory.belongsToStorage}</span>
													</td>
													<td>
														<input type="number" class="form-control input-sm" placeholder="Indk�bspris"
										name="uPrice" value="${inventory.price}" onblur="update(${inventory.id}, 'uPrice', '${inventory.name}')" id="uPrice-${inventory.id}" step="any" ${status}>
										
													</td>
													<td>
														<input type="number" class="form-control input-sm" placeholder="Oms�tningspris"
										name="uSalesPrice" value="${inventory.salesPrice}" onblur="update(${inventory.id}, 'uSalesPrice', '${inventory.name}')" id="uSalesPrice-${inventory.id}" step="any" ${status}>
													</td>
													<td>
														<span>${inventory.category}</span>
													</td>
													<td>
														<input type="checkbox" id="${inventory.name}" name="i" value="${inventory.id}" ${status}>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								<div class="form-group">
									<input type="submit" class="btn btn-danger btn-lg pull-right" value="Slet valgte">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</add:wrap>