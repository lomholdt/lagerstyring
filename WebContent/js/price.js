function update(inventoryId, priceType, inventoryName){
	var price = document.getElementById(priceType + "-" + inventoryId);
	var typeValue = priceType;
	$.post("/lagerstyring/updateprice",
			{
				inventoryId: encodeURIComponent(inventoryId),
				type: typeValue,
				newValue: price.value
			},
			function(data, textStatus){
				if(textStatus == "success"){
					$.toaster({ priority : 'success', title : 'Pris Opdateret', message : "Prisen på " + inventoryName + " blev opdateret til " + price.value});					
				}
				else{
					$.toaster({ priority : 'danger', title : 'Fejl', message : "Kunne ikke opdatere prisen på " + inventoryName + " til " + inventoryPrice});
				}
	});
}
