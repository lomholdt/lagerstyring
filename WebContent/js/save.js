$(document).ready(function() {
	$("#save").click(function(){
		var id = $("input[name=sid]").val();
		console.log(id);
		var myData = $("#closeStorageForm").serialize();
		myData += "&save=true";
		console.log(myData);
		$.post("/lagerstyring/close",
				myData,
				function(data, textStatus){
					$.toaster({ priority : 'success', title : textStatus, message : 'Dine slut tal er gemt'});
			
		});
	});
});