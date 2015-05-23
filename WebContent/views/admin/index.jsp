<%@ taglib prefix="admin" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<admin:wrap title="Admin">
<script charset="UTF-8" src="${pageContext.request.contextPath}/js/admin.js"></script>

	<div class="row">
		<div class="col-sm-8 col-sm-offset-2">
			<h1>Admin</h1>
			<c:if test="${msg != null}">
				<div class="alert alert-success">${msg}</div>
			</c:if>
			<c:if test="${error != null}">
				<div class="alert alert-warning">${error}</div>
			</c:if>


		</div>
	</div>

	<div class="row">
		<div class="col-sm-2">
			<div role="tabpanel">

				<!-- Nav tabs -->
				<ul class="nav nav-pills nav-stacked" role="tablist">
					<li role="presentation" class="active"><a href="#virksomhed"
						aria-controls="home" role="tab" data-toggle="tab">
							Virksomhed</a></li>
					<li role="presentation"><a href="#bruger"
						aria-controls="profile" role="tab" data-toggle="tab">
							Bruger</a></li>
					<li role="presentation"><a href="#station"
						aria-controls="messages" role="tab" data-toggle="tab">Station</a></li>
					<li role="presentation"><a href="#lager"
						aria-controls="settings" role="tab" data-toggle="tab">Lager</a></li>
						<li role="presentation"><a href="#kategori"
						aria-controls="settings" role="tab" data-toggle="tab">Kategorier</a></li>
				</ul>
				<hr>
			</div>
		</div>

		<div class="col-sm-8">
			<!-- Tab panes -->
			<div class="tab-content">
				<div role="tabpanel" class="tab-pane active" id="virksomhed">
					<!-- TILFØJ VIRKSOMHED -->

					<div class="panel panel-default">
						<div class="panel-heading">
							<h2>Ændre virksomhed</h2>
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">
								<h3>Tilføj Virksomhed</h3>
									<form method="POST" action="admin">
										<div class="form-group">
											<input type="text" class="form-control"
												placeholder="Firmanavn" name="companyName">
										</div>
										<div class="form-group">
											<button type="submit"
												class="btn btn-primary btn-lg pull-right">Opret Ny
												Virksomhed</button>
										</div>
									</form>
								</div>
								
							</div>
							<div class="row"><hr></div>
							<div class="row">
								<div class="col-sm-10 col-sm-offset-1">						
							<h3>Virksomheder</h3>
									<table class="table table-striped table-condensed">
										<thead>
											<tr>
												<th>Virksomhed</th>
												<th>Oprettet</th>
												<th>Aktiv</th>
												<th>Slet</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="company" items="${companies}">
												<tr class="company <c:choose><c:when test="${company.active}">success</c:when><c:otherwise>danger</c:otherwise></c:choose>" id="${company.id}-company">
													<td>${company.name}</td>
													<td>${company.createdAt}</td>
													<td><span>${company.active}</span> <input type="checkbox" <c:if test="${company.active}">checked="checked"</c:if> id=${company.id}-status value="${company.id}" class="company-status"></td>
													<td><button type="button" data-toggle="modal" data-target="#deleteModal" data-companyname="${company.name}" data-company="${company.id}" class="btn btn-danger btn-xs">Slet Firma</button></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>

								</div>
							</div>
						</div>
					</div>
					<!-- Modal -->
					<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					  <div class="modal-dialog">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					        <h4 class="modal-title" id="myModalLabel">Vil du slette firmaet?</h4>
					      </div>
					      <div class="modal-body">
						      <div class="modal-dynamic"><!-- MODAL DYNAMIC TEXT --></div>
						     
								<div>Indtast firmaets navn for at slette:</div>
						      
							      <div class="form-group">
								      <input type="text" class="form-control" id="company-confirmation" placeholder="Firmanavn">
								      <input type="hidden" id="confirmation-name">
							      </div>
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-default" data-dismiss="modal">Annuller</button>
					        <button type="button" class="btn btn-danger" id="deleteConfirm">Slet Firma</button>
					      </div>
					    </div>
					  </div>
					</div>
				</div>

				<div role="tabpanel" class="tab-pane" id="bruger">
					<!-- TILFØJ BRUGER TIL VIRKSOMHED -->

					<div class="panel panel-default">
						<div class="panel-heading">
							<h2>Opret bruger</h2>
						</div>
						<div class="panel-body">
							<form method="POST" action="admin">
								<div class="row">
								<div class="col-sm-8 col-sm-offset-2">
								<h3>Tilføj ny bruger</h3>
								<div class="form-group">
									<select class="form-control" name="userCompany" id="newUserSelector">
										<c:forEach var="company" items="${companies}">
											<option id="${company.id}">${company.name}</option>
										</c:forEach>

									</select>
								</div>
								<div class="form-group">
									<select class="form-control" name="userRole">
										<option value="manager">Manager</option>
										<option value="user">Runner</option>
									</select>
								</div>

								<div class="form-group">
									<input type="text" class="form-control" placeholder="username"
										name="username">
								</div>
								<div class="form-group">
									<input type="text" class="form-control" placeholder="password"
										name="password">
								</div>

								<button type="submit" class="btn btn-primary btn-lg btn-block">Opret
									Ny Bruger</button>
									</div>
									</div>
									<div class="row"><hr></div>
									<div class="row">
								<div class="col-sm-8 col-sm-offset-2" id="users">
								<h3>Brugere</h3>
									<table class="table table-striped table-condensed">
											<thead>
												<tr>
													<th>Brugernavn</th>
													<th>Roller</th>
													<th>Firma</th>
													<th>Medlem Siden</th>
													
												</tr>
											</thead>
											<tbody id="users-overview">
												
											</tbody>
										</table>
								
									</div>
								</div>
							</form>

						</div>
					</div>
				</div>

				<!-- TILFØJ STATIONER -->
				<div role="tabpanel" class="tab-pane" id="station">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h2>Opret station</h2>
						</div>
						<div class="panel-body">
						<div class="row">
								<div class="col-sm-8 col-sm-offset-2">
								<h3>Tilføj Station</h3>
							<form method="POST" action="admin">
								<div class="form-group">
									<select class="form-control" name="stationCompany" id="newStationSelector">
										<c:forEach var="company" items="${companies}">
											<option id="${company.id}">${company.name}</option>
										</c:forEach>

									</select>
								</div>
								<div class="form-group">
									<select class="form-control" name="newStationImportance">
										<option value="primary">Primary</option>
										<option value="secondary">Secondary</option>
									</select>
								</div>

								<div class="form-group">
									<input type="text" class="form-control" placeholder="Station"
										name="newStationName">
								</div>

								<button type="submit" class="btn btn-primary btn-lg btn-block">Opret
									Ny Station</button>
								
							</form>
							</div>
							</div>
								<div class="row"><hr></div>
									<div class="row">
										<div class="col-sm-8 col-sm-offset-2">
										<h3>Stationer</h3>
										<table class="table table-striped table-condensed">
												<thead>
													<tr>
														<th>Station</th>
														<th>Prioritet</th>
													</tr>
												</thead>
												<tbody id="stations-overview">
													
												</tbody>
											</table>
										</div>
									</div>
						</div>
					</div>
				</div>

				<div role="tabpanel" class="tab-pane" id="lager">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h2>Opret lager</h2>
						</div>
						<div class="panel-body">
						<div class="row">
							<div class="col-sm-8 col-sm-offset-2">
							<h3>Tilføj Lager</h3>
								<form method="POST" action="admin">
									<div class="form-group">
										<select class="form-control" name="storageCompany" id="newStorageSelector">
											<c:forEach var="company" items="${companies}">
												<option id="${company.id}">${company.name}</option>
											</c:forEach>
										</select>
									</div>
	
									<div class="form-group">
										<input type="text" class="form-control" placeholder="Lager"
											name="newStorageName">
									</div>
	
									<button type="submit" class="btn btn-primary btn-lg btn-block">Opret
										Nyt Lager</button>
								</form>
							</div>
						</div>
							<div class="row"><hr></div>
							<div class="row">
								<div class="col-sm-8 col-sm-offset-2">
															<h3>Lagre</h3>
									<table class="table table-striped table-condensed">
										<thead>
											<tr>
												<th>Lagernavn</th>
												<th>Status</th>
												<th>Sidst Ændret</th>
												<th>Lavet</th>
												
											</tr>
										</thead>
										<tbody id="storages-overview">
											
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div role="tabpanel" class="tab-pane" id="kategori">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h2>Tilføj Kategorier</h2>
						</div>
						<div class="panel-body">
						<div class="row">
							<div class="col-sm-8 col-sm-offset-2">
							<h3>Tilføj Kategorier</h3>
								<form method="POST" action="admin">
									<div class="form-group">
										<select class="form-control" name="categoryCompany" id="newCategorySelector">
											<c:forEach var="company" items="${companies}">
												<option id="${company.id}">${company.name}</option>
											</c:forEach>
										</select>
									</div>
	
									<div class="form-group">
										<input type="text" class="form-control" placeholder="Kategorinavn"
											name="newCategoryName">
									</div>
	
									<button type="submit" class="btn btn-primary btn-lg btn-block">Tilføj Ny Kategori</button>
								</form>
							</div>
						</div>
							<div class="row"><hr></div>
								<div class="row">
									<div class="col-sm-8 col-sm-offset-2">
														<h3>Kategorier</h3>
										<table class="table table-striped table-condensed">
											<thead>
												<tr>
													<th>Kategori</th>
													<th>Slet Kategori</th>
												</tr>
											</thead>
											<tbody id="categories-overview">
												
											</tbody>
										</table>
									</div>
								</div>
								<div class="row"><hr></div>
								<div class="row">
									<div class="col-sm-8 col-sm-offset-2">
									<h3 class="lead">Inventar</h3>
										<table class="table table-striped table-condensed">
											<thead>
												<tr>
													<th>Inventar</th>
													<th>Lager</th>
													<th>Kategori</th>
												</tr>
											</thead>
											<tbody id="inventory-overview">
												
											</tbody>
										</table>
									</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


</admin:wrap>