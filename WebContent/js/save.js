$(document).ready(function() {
	//var url = $(location).attr('pathname');
	$("#save").click(function(){
		var id = $("input[name=sid]").val();
		var myData = $("#closeStorageForm").serialize();
		myData += "&save=true";
		$.post("/lagerstyring/save",
				myData,
				function(data, textStatus){
					$.toaster({ priority : 'success', title : textStatus, message : 'Dine slut tal er gemt'});
			
		});
	});
});



