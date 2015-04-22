var http;
if (navigator.appName == "Microsoft Internet Explorer"){
	http = new ActiveXObject("Microsoft.XMLHTTP");
}
else{
	http = new XMLHttpRequest();	
}

function sendRequest(action, responseHandler){
	http.open("POST", action);
	http.onreadystatechange = responseHandler;
	http.send(null);
}

function checkValidEmail(){
	if (http.readyState == 0 || http.readyState == 4){
		// ready to rock'n'roll!
		var email = document.getElementById("email").value;
		if(isValidEmail(email)){
			sendRequest("email-is-available?email=" + encodeURIComponent(email), responseReceived);			
		}
		else{
			
		}
	}
}

function responseReceived(){
	if (http.readyState == 4){
		try {
			if (http.status == 200){
				var result = http.responseText;	
			}
		} catch (e) {
			alert(e);
		}
	}
}