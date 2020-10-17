/**
 * ravencloud.js
 */
if (rv == null) var rv = {};

rv.executeAction = function(element,application, module, action) {

	$.blockUI();
	
	var values = new Object();
	
	$("#" + module + "_message_error").addClass("hidden");
	
	$("#Module_" + module).find(':input:not([type=submit])').each(function() {
		
		var input = $(this);
		
		if(input.hasClass('reference')) {
			
			values[this.id.replace(module + "_","")]  = new Object();
			
			values[this.id.replace(module + "_","")].id = this.value;
			
		} else {
			values[this.id.replace(module + "_","")] = this.value;
		}
	});
	 
	var url = "/" + application + "/rest/action/" + module + "/" + action;
	
	console.log("URL" + url);
	console.log("Parameters ->" + JSON.stringify(values));
	
	$.ajax(url, {
		type : "POST",
		data : JSON.stringify(values),
		contentType : "application/json",
		success : function(data) {
			
			var response = JSON.parse(data);
			
			console.log(response);
			
			if(!response.error) {
				
				eval(response.script);
				
			} else {
				
				console.log("#" + module + "_message_error p");
				
				$("#" + module + "_message_error p").html(response.message);
				$("#" + module + "_message_error").removeClass("hidden");
			}
			
			$.unblockUI();
		},
		error: function(jqXHR, textStatus, errorThrown) {
		     console.log(jqXHR);
		     console.log(textStatus);
		     console.log(errorThrown);
		     
		     $.unblockUI();
		}
	});
} 

rv.initSwagger = function(applicationPath) {
	window.onload = function() {
		const ui = SwaggerUIBundle({
	        url: applicationPath + "rest/openapi.json",
	        dom_id: '#swagger-ui',
	        deepLinking: true,
	        presets: [
	          SwaggerUIBundle.presets.apis,
	          SwaggerUIStandalonePreset
	        ],
	        plugins: [
	          SwaggerUIBundle.plugins.DownloadUrl
	        ],
	        layout: "StandaloneLayout"
	      })

	      window.ui = ui
	}
}