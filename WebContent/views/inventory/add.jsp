<%@ taglib prefix="add" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<add:wrap title="Tilføj Ny Vare">

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
					<h3 class="panel-title">Tilføj vare</h3>
				</div>

				<div class="panel-body">
					<p class="lead">Indsæt ny vare</p>
					<div class="row">
						<div class="col-sm-8 col-sm-offset-2">
							<form method="POST" action="inventory">
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
									<input type="number" class="form-control" placeholder="Antal"
										name="units">
								</div>
								<div class="form-group">
									<button type="submit" class="btn btn-primary btn-lg">Indsæt
										Ny Vare</button>
								</div>
							</form>
						</div>
					</div>
				</div>

				<div class="panel-footer">
					<p class="lead">Slet Vare</p>
					<div class="row">
						<div class="col-sm-8 col-sm-offset-2">
							<form action="delete" method="POST">
								<c:forEach var="inventory" items="${allInventory}">

									<div class="checkbox">
										<label> <input type="checkbox" name="i"
											value="${inventory.id}"> ${inventory.name}
										</label>
									</div>
								</c:forEach>
								<div class="form-group">
									<input type="submit" class="btn btn-danger" value="Slet">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</add:wrap>