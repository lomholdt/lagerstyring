function setDiff(){
	var mainInput = document.getElementById("inventory");
	var closeInput = mainInput.getElementsByClassName("form-control");
	
	for (var i = 0; i < closeInput.length; i++){
		closeInput[i].onkeyup = function(){
			var inputValue = this.value;
			var expectedUnits = document.getElementById(this.id + "-inventory-expected").innerHTML;
			var diffCounter = document.getElementById(this.id + "-diff");
			if(inputValue == ''){
				var result = "";
			}
			else{
				var result = inputValue - expectedUnits;
			}
			diffCounter.innerHTML = result;			
		}
	}
}


$(document).ready(function(){	
	setDiff();
});

