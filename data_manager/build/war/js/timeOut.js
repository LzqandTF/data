var timeOut = 0;
	
setInterval(function(){
	timeOut = timeOut + 1;
},1000);

$(document).ready(function(){  
    $(document).click(function(){  
    	if(timeOut >= 1500){
    		JavaScript:window.location.href = "http://192.168.2.43:8004/data_manager/exit.do";
    	}else{
    		timeOut = 0;
    	}
    });
});