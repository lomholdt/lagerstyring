var http;
var timeout;
if (navigator.appName == "Microsoft Internet Explorer"){
	http = new ActiveXObject("Microsoft.XMLHTTP");
}
else{
	http = new XMLHttpRequest();	
}

function sendRequest(action, responseHandler){
	http.open("GET", action);
	http.onreadystatechange = responseHandler;
	http.send();
}

function getCurrentUnits(inventoryId){
	if (http.readyState == 0 || http.readyState == 4){
		// ready to rock'n'roll!
		sendRequest("/lagerstyring/api?inventoryId=" + encodeURIComponent(inventoryId), responseReceived(inventoryId));
	}
}

function responseReceived(inventoryId){
	if (http.readyState == 4){
		try {
			if (http.status == 200){
				var currentUnits = http.responseText;
				var inputUnits = document.getElementById(inventoryId).value;
				var result = inputUnits - currentUnits;
				
				var diffField = document.getElementById("diff-"+inventoryId);
				diffField.innerHTML = result;
			}
		} catch (e) {
			console.log(e);
		}
	}
}

function setAllDiffs(){
	var mainInput = document.getElementById("inventory");
	var closeInput = mainInput.getElementsByClassName("form-control");
	
	for (var i = 0; i < closeInput.length; i++){
		closeInput[i].onkeyup = function(){
			getCurrentUnits(this.id);
		}
	}
}

$(window).ready(function(){
	setAllDiffs();
});