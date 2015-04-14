<%@ taglib prefix="admin" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<admin:wrap title="Admin">

	<div class="row">
		<div class="col-md-6 col-md-offset-2">

			<h1>Admin</h1>
			<c:if test="${msg != null}">
				<div class="alert alert-success">${msg}</div>
			</c:if>
			<c:if test="${error != null}">
				<div class="alert alert-warning">${error}</div>
			</c:if>




			<div role="tabpanel">

				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active"><a href="#virksomhed"
						aria-controls="home" role="tab" data-toggle="tab">Ny Virksomhed</a></li>
					<li role="presentation"><a href="#bruger"
						aria-controls="profile" role="tab" data-toggle="tab">Tilf�j ny bruger</a></li>
					<li role="presentation"><a href="#station"
						aria-controls="messages" role="tab" data-toggle="tab">Tilf�j ny station</a></li>
					<li role="presentation"><a href="#lager"
						aria-controls="settings" role="tab" data-toggle="tab">Tilf�j nyt lager</a></li>
				</ul>

				<!-- Tab panes -->
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane active" id="virksomhed">
						<!-- TILF�J VIRKSOMHED -->
						<h3>Opret ny virksomhed:</h3>
						<form method="POST" action="admin">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="Firmanavn"
									name="companyName">
							</div>
							<button type="submit" class="btn btn-primary btn-lg">Opret
								Ny Virksomhed</button>
						</form>

						<table class="table table-borded">
							<thead>
								<tr>
									<th>Virksomheder</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="company" items="${companies}">
									<tr>
										<td>${company}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<hr>

					</div>

					<div role="tabpanel" class="tab-pane" id="bruger">
						<!-- TILF�J BRUGER TIL VIRKSOMHED -->

						<h3>Tilf�j bruger til virksomhed:</h3>
						<form method="POST" action="admin">

							<div class="form-group">
								<select class="form-control" name="userCompany">
									<c:forEach var="company" items="${companies}">
										<option>${company}</option>
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

							<button type="submit" class="btn btn-primary btn-lg">Opret
								Ny Bruger</button>
						</form>

						<hr>



					</div>

					<div role="tabpanel" class="tab-pane" id="station">
						<h3>Tilf�j station til virksomhed</h3>
						<form method="POST" action="admin">
							<div class="form-group">
								<select class="form-control" name="stationCompany">
									<c:forEach var="company" items="${companies}">
										<option>${company}</option>
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

							<button type="submit" class="btn btn-primary btn-lg">Opret
								Ny Station</button>
						</form>
					</div>

					<div role="tabpanel" class="tab-pane" id="lager">

						<h3>Tilf�j lager til virksomhed</h3>

						<form method="POST" action="admin">
							<div class="form-group">
								<select class="form-control" name="storageCompany">
									<c:forEach var="company" items="${companies}">
										<option>${company}</option>
									</c:forEach>
								</select>
							</div>

							<div class="form-group">
								<input type="text" class="form-control" placeholder="Lager"
									name="newStorageName">
							</div>

							<button type="submit" class="btn btn-primary btn-lg">Opret
								Nyt Lager</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>


</admin:wrap>