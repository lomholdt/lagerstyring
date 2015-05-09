$( document ).ready(function() {
	$(".company-status").click(toggleCompanyStatus);
	
	$("#newUserSelector").change(getCompanyUsers);
	getCompanyUsers();
	
	$("#newStationSelector").change(getCompanyStations);
	getCompanyStations();
	
	$("#newStorageSelector").change(getCompanyStorages);
	getCompanyStorages();
	
	$("#newCategorySelector").change(getCompanyCategories);
	getCompanyCategories();
	
});

function toggleCompanyStatus(event){
	var id = event.target.value; // $(this).attr('value');
    $.post("/lagerstyring/togglecompanystatus",
    {
        companyId: id
    },
    function(){
		var company = document.getElementById(id + "-status");
		var companyRow = document.getElementById(id + "-company");
		if(company.checked == true){
			companyRow.className = "success";
		}
		else{
			companyRow.className = "danger";
		}
    });
}

function getCompanyUsers(){
	var selectedId = $("#newUserSelector").find(":selected").attr("id");
	$.post("/lagerstyring/getcompanyusers",
			{
				companyId: selectedId
			},
			function(data){
				var json = JSON.parse(data);
				var usersOverview = document.getElementById("users-overview");
				$(usersOverview).empty();
				for (var i = 0; i < json.length; i++) {
					var row = $("<tr></tr>").appendTo(usersOverview);
					$("<td>" + json[i].username + "</td>").appendTo(row);
					$("<td>" + json[i].companyName + "</td>").appendTo(row);
					$("<td>" + json[i].memberSince + "</td>").appendTo(row);
				}
	});
}

function getCompanyStations(){
	var selectedId = $("#newStationSelector").find(":selected").attr("id");
	$.post("/lagerstyring/getcompanystations",
			{
				companyId: selectedId
			},
			function(data){
				var json = JSON.parse(data);
				var stationsOverview = document.getElementById("stations-overview");
				$(stationsOverview).empty();
				for (var i = 0; i < json.length; i++) {
					var row = $("<tr></tr>").appendTo(stationsOverview);
					$("<td>" + json[i].name + "</td>").appendTo(row);
					$("<td>" + json[i].importance + "</td>").appendTo(row);
				}
	});
}

function getCompanyStorages(){
	var selectedId = $("#newStorageSelector").find(":selected").attr("id");
	
	$.post("/lagerstyring/getcompanystorages",
			{
				companyId: selectedId
			},
			function(data){
				var json = JSON.parse(data);
				var storageOverview = $("#storages-overview");
				$(storageOverview).empty();
				for (var i = 0; i < json.length; i++) {
					var row = $("<tr></tr>").appendTo(storageOverview);
					$("<td>" + json[i].name + "</td>").appendTo(row);
					var isOpen = (json[i].isOpen == true) ? "Åben" : "Lukket"; 
					$("<td>" + isOpen + "</td>").appendTo(row);
					$("<td>" + json[i].openedAt + "</td>").appendTo(row);
					$("<td>" + json[i].createdAt + "</td>").appendTo(row);
				}
			});

}


function getCompanyCategories(){
	var selectedId = $("#newCategorySelector").find(":selected").attr("id");
	
	$.post("/lagerstyring/getcompanycategories",
			{
				companyId: selectedId
			},
			function(data){
				var json = JSON.parse(data);
				var categoryOverview = $("#categories-overview");
				$(categoryOverview).empty();
				for (var i = 0; i < json.length; i++) {
					var row = $("<tr></tr>").appendTo(categoryOverview);
					$("<td>" + json[i].category + "</td>").appendTo(row);
					
				}
			});

}
