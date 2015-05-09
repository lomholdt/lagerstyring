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
	
	$(".delete-category").click(deleteCategory);
	
	$("#newCategorySelector").bind("change", getCompanyInventory);
	getCompanyInventory();
	
	
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
					var data = $("<td></td>");
					var deleteButton = $("<button class='btn btn-default btn-xs delete-category' id=" + json[i].id + ">Slet</button>");
					$(deleteButton).click(deleteCategory);
					data.appendTo(row);
					deleteButton.appendTo(data);
					
				}
			});
}

function deleteCategory(event){
	var id = event.target.id;
	$.post("/lagerstyring/getcompanycategories",
			{
				deleteCategory: "delete",
				deleteId: id
			},
			function(data){
				$(event.target).parent().parent().remove();
				$.toaster({ priority : 'success', title : 'Kategori slettet', message : "Kategorien blev slettet."});
			});
	
}


function getCompanyInventory(){
	var selectedId = $("#newCategorySelector").find(":selected").attr("id");
	
	console.log(selectedId);
	
	$.post("/lagerstyring/getcompanyinventory",
			{
				companyId: selectedId
			},
			function(data){
				var json = JSON.parse(data);
				var inventoryOverview = $("#inventory-overview");
				$(inventoryOverview).empty();
				for (var i = 0; i < json.length; i++) {
					var current = json[i];
					var row = $("<tr></tr>").appendTo(inventoryOverview);
					$(row).attr("id", current.id);
					
					$("<td>" + current.name + "</td>").appendTo(row);
					var category = $("<td></td>").appendTo(row);
					$(category).append(getDropdownOfCategories(current.category));
					
				}
			});
	
}

function getDropdownOfCategories(selectedCategory){
	var selectedId = $("#newCategorySelector").find(":selected").attr("id");
	var selector = document.createElement('select');
	$(selector).addClass("category-selector");
	$(selector).append("<option value='none'>Ingen</option>");

	$.post("/lagerstyring/getcompanycategories",
			{
				companyId: selectedId
			},
			function(data){
				var json = JSON.parse(data);
					
				for (var i = 0; i < json.length; i++) {
					var current = json[i];
					var option = new Option(current.category, current.id);
					if(current.category == selectedCategory){
						$(option).prop('selected', true);
					}
					else if(current.category == undefined){
						
					}
					option.id = current.id;
					selector.onchange = updateCategory;
					$(selector).append(option);
				}
			});
	
	return selector;
}


function updateCategory(event){
	console.log("Trying to update.");
	var id = this.id;
	var iid = $(this).parent().attr("id");
	
	console.log("Id: " + id);
	console.log("iid: " + iid);
	
	$.post("/lagerstyring/updatecategory",
			{
				inventoryId: iid,
				categoryId: id
			},
			function(data){
				//$(event.target).parent().parent().remove();
				$.toaster({ priority : 'success', title : 'Vare opdateret', message : "Varen er blevet opdateret."});
			});
	
}
