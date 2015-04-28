var http;
var timeout;
if (navigator.appName == "Microsoft Internet Explorer"){
	http = new ActiveXObject("Microsoft.XMLHTTP");
}
else{
	http = new XMLHttpRequest();	
}

function sendRequest(action, responseHandler){
	http.open("POST", "/lagerstyring/updateprice");
	
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	http.onreadystatechange = responseHandler;
	http.send(action);
}

function update(inventoryId, priceType){
	if (http.readyState == 0 || http.readyState == 4){
		var price = document.getElementById(priceType + "-" + inventoryId);
		// ready to rock'n'roll!
		sendRequest("inventoryId=" + encodeURIComponent(inventoryId) + "&" + priceType + "=" + price.value , responseReceived(inventoryId));
	}
}

function responseReceived(inventoryId){
	if (http.readyState == 4){
		try {
			if (http.status == 200){
				var currentUnits = http.responseText;
			}
		} catch (e) {
			console.log(e);
		}
	}
}
