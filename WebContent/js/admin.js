//var http;
//var timeout;
//
//if (navigator.appName == "Microsoft Internet Explorer"){
//	http = new ActiveXObject("Microsoft.XMLHTTP");
//}
//else{
//	http = new XMLHttpRequest();
//	
//}
//
//
//
//function sendRequest(action, responseHandler){
//	http.open("POST", "/lagerstyring/adminapi", true);
//	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
//	http.onreadystatechange = responseHandler;
//	http.send(action);
//}
//
//function update(companyId){
//	if (http.readyState == 0 || http.readyState == 4){
//		console.log("updating " + companyId + "-status");
//		var companyId = document.getElementById(companyId + "-status").value;
//		// ready to rock'n'roll!
//		sendRequest("companyId=" + encodeURIComponent(companyId), responseReceived(companyId));
//	}
//	else{
//        window.clearTimeout(timeout);
//        timeout = window.setTimeout(update, 500);
//        console.log("Trying again")
//	}
//}
//
//
//function responseReceived(companyId){
//	if (http.readyState == 4){
//		try {
//			if (http.status == 200){
//				var company = document.getElementById(companyId + "-status");
//				var companyRow = document.getElementById(companyId + "-company");
//				if(company.checked == true){
//					console.log("Company is active");
//					companyRow.className = "success";
//				}
//				else{
//					console.log("company is inactive");
//					companyRow.className = "danger";
//				}
//				
//			}
//		} catch (e) {
//			console.log(e);
//			console.log(http.readyState)
//		}
//	}
//	else{
//		console.log("readyState: " + http.readyState + " " + http.statusText);
//	}
//}
//
//
//
//function addEventHandler(){
//	var companies = document.getElementsByClassName("company-status");
//	console.log(companies.length);
//	for (var i = 0; i < companies.length; i++) {
//		companies[i].onclick = function(){
//			update(this.value);
//		}
//	}
//}


$( document ).ready(function() {	
	$(".company-status").click(function(event){
		var id = $(this).attr('value');
	    $.post("/lagerstyring/adminapi",
	    {
	        companyId: id
	    },
	    function(){
			var company = document.getElementById(id + "-status");
			var companyRow = document.getElementById(id + "-company");
			if(company.checked == true){
				console.log("Company is active");
				companyRow.className = "success";
			}
			else{
				console.log("company is inactive");
				companyRow.className = "danger";
			}
	    });
	});
});