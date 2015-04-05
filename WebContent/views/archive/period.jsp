<%@ taglib prefix="period" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<period:wrap title="V�lg Periode">


	<form method="GET" action="period">

		<div class="row">
			<hr>
		</div>
		<p class="lead">V�lg periode</p>
		<div class="row">
			<div class="col-sm-4 col-sm-offset-2">
				<div class="form-group">
					<label for="from">Fra</label> <input type="date"
						class="form-control" name="from" value="${storage.openedAtHtml}">
				</div>
			</div>
			<div class="col-sm-4">
				<div class="form-group">
					<label for="to">Til</label> <input type="date" class="form-control"
						name="to" value="${storage.openedAtHtml}">
				</div>
				<input type="hidden" value="${param.storageId}" name="storageId">
			</div>
			<div class="col-sm-8 col-sm-offset-2">
				<div class="form-group">
					<button type="submit" name="search" value="log"
						class="btn btn-primary btn-lg btn-block">S�g</button>
				</div>
			</div>
		</div>
	</form>


	<form method="POST" action="report">
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2">
				<select class="form-control" name="periods">
					<c:forEach var="logBook" items="${logBooks}">
						<option
							value="${logBook.openedAt.toString()}&${logBook.closedAt.toString()}">${logBook.openedAt.toString()}
							- ${logBook.closedAt.toString()}</option>

					</c:forEach>
				</select> <input type="hidden" value="${param.storageId}" name="storageId">
			</div>
		</div>
		<div class="row">
			<hr>
		</div>
		<p class="lead">Filter</p>
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2">
				<div class="form-group">
					<label for="inventoryName">Varenavn</label> <select
						class="form-control" name="inventoryName">
						<option value="allInventory">Alle varer</option>
						<c:forEach var="inventory" items="${allInventory}">
							<option value="${inventory.name}">${inventory.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label for="station">Station</label> <select class="form-control"
						name="stationName">
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
					<button type="submit" name="search" value="log"
						class="btn btn-primary btn-lg btn-block">Se lager rapport</button>
				</div>
			</div>
		</div>
	</form>



</period:wrap>