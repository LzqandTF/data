//从左向右边添加
function leftToRight(){
	var left=document.getElementById("allColumn");
	var right=document.getElementById("selectedColumn");
	var flag=false;	
	try{
		for(var i=0;i<left.options.length;i++){		
			var left1=left.options[i];				
			if(left1.selected){	
				var key=left1.value;
				if(!isExist(key)){
					right.options[right.length]=new Option(left1.text,left1.value,false,false);
				}else{
				}	
				flag=true;			
			}						
			if(i==left.options.length-1 && !flag){
				return;
			}				
		}	
	}catch(e){}
}
			
//判断key是否存在
function isExist(key){
	var right=document.getElementById("selectedColumn");
	var len=right.options.length; 
	for(var i=0;i<len;i++){
		var right_value = right.options[i].value;
		var arrays = right_value.split("-");
		if(key.indexOf("-") > -1){
			if(key==right_value){
				return true;
			}
		}else{
			if(key==arrays[0]){
				return true;
			}
		}
		
	}
	return false;
}
						
//移去右边的元素
function removeFromRight(right){
	var key=right.val();		
	if(key == null || key==""){
		return ;
	}else{						
		right.find("option[selected]").remove();
	}
}
//清除所有
function clearAllOption(){
	var e2=document.getElementById("allColumn");
	var e1=document.getElementById("selectedColumn");
	try{ 
		if(e1.options.length==0){
			return;
		}
		for(var i = 0;i < e1.options.length; i++){ 
			var e = e1.options[i];
			e1.remove(i); 
			i = i - 1; 
		}
	}catch(e){}	
} 

//向右边添加所有
function moveAllOption(){
	var e1=document.getElementById("allColumn");//左边
	var e2=document.getElementById("selectedColumn");//右边
	if(e1.options.length==0){
		return;
	}
	
	for(var i = 0;i < e1.options.length; i++){ 
		var e = e1.options[i];
		if(!isExist(e.value)){
			e2.options.add(new Option(e.text, e.value));
		}else{
		//	
		} 	
	}
}

