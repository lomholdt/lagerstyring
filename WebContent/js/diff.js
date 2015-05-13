function setDiff(){
	var mainInput = document.getElementById("inventory");
	var closeInput = mainInput.getElementsByClassName("close-input");
	
	for (var i = 0; i < closeInput.length; i++){
		calc(closeInput[i]);
		closeInput[i].onkeyup = calcThis;

	}
}


function calc(event){
	var inputValue = event.value;
	
	if(inputValue == undefined) return;
	
	var expectedUnits = document.getElementById(event.id + "-inventory-expected").value;
	
	var diffCounter = document.getElementById(event.id + "-diff");
	if(inputValue == ''){
		var result = "";
	}
	else{
		var result = inputValue - expectedUnits;
	}
	diffCounter.value = result;			
}



function calcThis(){
	var inputValue = this.value;
	
	if(inputValue == undefined) return;
	
	var expectedUnits = document.getElementById(this.id + "-inventory-expected").value;
	
	var diffCounter = document.getElementById(this.id + "-diff");
	if(inputValue == ''){
		var result = "";
	}
	else{
		var result = inputValue - expectedUnits;
	}
	diffCounter.value = result;			
}



$(document).ready(function(){	
	setDiff();
});

