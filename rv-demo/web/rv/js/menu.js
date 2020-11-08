$(document).ready(function(){
	
    var ocultar_menu = true;
    $("#menu-toggle").click(function(){;
    
    	if(ocultar_menu){
    		
    		$("#menu-ravencloud").hide();
    		
    		rotate = 0;

    		$('#container-ravencloud').removeClass("col-md-9");
    		$('#container-ravencloud').addClass("col-md-12");
    		
    	}else{

    		rotate = -90;
    		
    		$('#container-ravencloud').removeClass("col-md-12");
    		$('#container-ravencloud').addClass("col-md-9");
    		
    		$("#menu-ravencloud").show("slow");
    		
    	}
    	
    	
    	$('#menu-toggle').animate({  borderSpacing: rotate}, {
            step: function(now,fx) {
                $(this).css('-webkit-transform','rotate(' + rotate + 'deg)'); 
                $(this).css('-moz-transform','rotate(' + rotate + 'deg)');
                $(this).css('transform','rotate(' + rotate + 'deg)');
              },
              duration:'slow'
        },'linear');
    	
    	ocultar_menu = !ocultar_menu;
    });
});