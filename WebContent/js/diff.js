function setDiff(){
	var mainInput = document.getElementById("inventory");
	var closeInput = mainInput.getElementsByClassName("close-input");
	
	for (var i = 0; i < closeInput.length; i++){
		closeInput[i].onkeyup = function(){
			var inputValue = this.value;
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
	}
}


$(document).ready(function(){	
	setDiff();
});

