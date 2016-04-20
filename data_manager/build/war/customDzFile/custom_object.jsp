<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统接口配置查询</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery_sp.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/js.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<style type="text/css">
	#loading-mask{
        position:absolute;
        left:0;
        top:0;
        width:100%;
        height:100%;
        z-index:20000;
        filter:alpha(opacity=80);
    }
    #loading{
        position:absolute;
        left:40%;
        top:50%;
        padding:2px;
        z-index:20001;
        height:auto;
 }
    #loading .loading-indicator{
        font:bold 20px tahoma,arial,helvetica;
        padding:10px;
        margin:0;
        height:auto;
    }
    #loading-msg {
        font: normal 18px arial,tahoma,sans-serif;
    }
</style>
<script type="text/javascript">
		function paging(pageNo) {
			var form = document.getElementById("customObjectSearch");
			with (form) {
				action = "<%=request.getContextPath()%>/queryCustomObject.do?pageNum=" + pageNo;
				method = "post";
				form.submit();
			}
		}
		
		function checkQuery(){	
			var object_id = $("#object_id_").val(); 
			var reg = /^\d{1,}$/;
			if(object_id != null && object_id != ""){
				var i = new Number(object_id);
				if(i == 0){
					alert("系统接口ID不能为0！");
					return;
				}
			}
			if(object_id != null && object_id.length != 0){
				if(!reg.test(object_id)){
					alert("系统接口ID必须为数字！");
					return;
				}
			}
			document.getElementById("customObjectSearch").submit();
		}
		
		function hide(obj) {
			var o = document.getElementById(obj);
			var inputs= o.getElementsByTagName('input');
			for(var i = 0;i<inputs.length;i++){
				if($(inputs[i]).attr("type") == "text"){
					$(inputs[i]).val("");
				}
			} 
			o.style.display = "none";
		}
		
		function addCustomObject() {
			$("#typeSelect").css({display:"block"});
		}
		
		
		function uniqueObjectName(obj){
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/unionCheckobjectName.do",
				data : "object_name=" + obj.value,
				dataType : "text",
				success : function(msg) {
					if(msg == "true"){
						alert("系统接口名称已经存在不能再次添加");
						$(obj).val("");
						$(obj).select();
					}
				}
			});
		}
		
		function uniqueFileAddress(obj){
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/unionCheckfileAddress.do",
				data : "file_address=" + obj.value,
				dataType : "text",
				success : function(msg) {
					if(msg == "true"){
						alert("文件路径已经存在不能再次添加");
						$(obj).val("");
						$(obj).select();
					}
				}
			});
		}
		
		function addCustomObjectSub(){
			var object_name = $("#object_name").val();
			var file_address = $("#file_address").val();
			var dz_file_name = $("#dz_file_name").val();
			var error_file_name = $("#error_file_name").val();
			var file_type = 1;//对账总表
			var data_type = 2;//已对账数据
			
			if(object_name == null || object_name == ""){
				alert("系统接口名称不能为空！");
				return;
			}
			if(file_address == "" || file_address == null){
				alert("对账文件路径不能为空！");
				return;
			}
			if(dz_file_name == "" || dz_file_name == null){
				alert("对账文件名称不能为空！");
				return;
			}
			
			var ftp_ip = $("#ftp_ip").val();
			var ftp_address = $("#ftp_address").val();
			var ftp_port = $("#ftp_port").val();
			var ftp_username = $("#ftp_username").val();
			var ftp_password = $("#ftp_password").val();
			var file_suffix = $("#file_suffix").val();
			var whether_upload = $("#whether_upload").val();
			if(whether_upload == 1){
				if(ftp_ip == null || ftp_ip == ""){
					alert("FTP主机IP不能为空！");
					return;
				}
				if(ftp_address == "" || ftp_address == null){
					alert("FTP存放文件地址不能为空！");
					return;
				}
				if(ftp_port == "" || ftp_port == null){
					alert("FTP端口号不能为空！");
					return;
				}
				if(ftp_username == "" || ftp_username == null){
					alert("FTP用户名不能为空！");
					return;
				}
				if(ftp_password == "" || ftp_password == null){
					alert("FTP密码不能为空！");
					return;
				}
				if(file_suffix == "" || file_suffix == null){
					alert("对账后缀不能为空！");
					return;
				}
				if(!(file_suffix == ".xls" || file_suffix == ".txt")){
					alert("对账文件后缀必须是.txt或者.xls格式");
					return;
				}
			}
			var generate_number = $("#generate_number").val();
			var file_need_online_data = $("#file_need_online_data").val();
			var whether_create_error_file = $("#whether_create_error_file").val();
			var whether_create_file_by_range = $("#whether_create_file_by_range").val();
			
			
			var whether_create_file_by_inst = $("#whether_create_file_by_inst").val();//是否按照渠道生成文件
			
			var selectedColumn = new Array();
			
			if(whether_create_file_by_inst == 1){
				var j =0;
				$("#inst_info_config").find("#selectedColumn").find("option").each(function(){
					selectedColumn[j] = $(this).val();
					j++;
				});
			}
			
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addCustomObject.do",
				data : "object_name=" + object_name + "&file_address=" + file_address +"&dz_file_name="+dz_file_name+"&error_file_name="+error_file_name+"&ftp_ip="+ftp_ip+"&ftp_address="+ftp_address+"&ftp_port="+ftp_port+"&ftp_username="+ftp_username+"&ftp_password="+ftp_password+"&file_suffix="+file_suffix+"&whether_upload="+whether_upload+"&generate_number="+generate_number+"&file_need_online_data="+file_need_online_data+"&data_type="+data_type+"&whether_create_error_file="+whether_create_error_file+"&file_type="+file_type+"&whether_create_file_by_range="+whether_create_file_by_range+"&data_type="+data_type+"&whether_create_file_by_inst="+whether_create_file_by_inst+"&selectedColumn="+selectedColumn,
				dataType : "text",
				beforeSend:function(){
					document.getElementById("loading-mask").style.display="";
			        document.getElementById("loading").style.display="";
				},
				complete:function(){
					document.getElementById("loading-mask").style.display="none";
			        document.getElementById("loading").style.display="none";
				},
				success : function(msg) {
					if (msg == "1") {
						alert("添加成功！");
						hide("insert");
						checkQuery();
					}else {					
						alert("添加失败！");
						return;
					}
				}
			});
		}
		
		function selectData(object_id,object_name,file_address,dz_file_name,error_file_name,whether_upload,ftp_ip,ftp_address,ftp_port,ftp_username,ftp_password,file_suffix,generate_number,file_need_online_data,data_type,whether_create_error_file,file_type,whether_create_file_by_range,whether_create_file_by_inst){
			
			$("#operate_type").val("2");
			
			$("#object_id_h").val(object_id);
			
			$("#file_type").val(file_type);
			
			if(file_type != 2){
				if(whether_create_file_by_inst == 1){
					$("#selectedColumn").empty();
					$.ajax({
			    		url : '<%=request.getContextPath()%>/queryCustomObjectInstInfoList.do',
			    		data : "object_id="+ object_id,
			    		type : 'post',
			    		dataType: 'text',
			    		async : false,
			    		success : function(msg) {
			    			msg = eval("(" + msg + ")");
			    			if(msg != null){
			    				for(i in msg){
				    				$("#selectedColumn").append('<option value="' + msg[i]['inst_id'] + '-' + msg[i]['inst_type']+'">'+ msg[i]['inst_name'] + '</option>');
				    			}
			    			}
			    		}
			    	});
				}
			}
			
			
			if(file_type == 2){//结算文件
				$("#object_id_settle_u").val(object_id);
				$("#object_name_settle_u").val(object_name);
				$("#file_address_settle_u").val(file_address);
				$("#settle_file_name_u").val(dz_file_name);
				
				var whether_upload_ = document.getElementById("whether_upload_settle_u");
				
				for(var i = 0;i<whether_upload_.options.length;i++){
					if(whether_upload_.options[i].value == whether_upload){
						whether_upload_.options[i].selected = 'selected';
					}
				}
				var generate_number_ = document.getElementById("generate_number_settle_u");
				
				for(var i = 0;i<generate_number_.options.length;i++){
					if(generate_number_.options[i].value == generate_number){
						generate_number_.options[i].selected = 'selected';
					}
				}
				
				var whether_create_file_by_range_settle_ = document.getElementById("whether_create_file_by_range_settle_u");
				
				for(var i = 0;i<generate_number_.options.length;i++){
					if(whether_create_file_by_range_settle_.options[i].value == whether_create_file_by_range){
						whether_create_file_by_range_settle_.options[i].selected = 'selected';
					}
				}
				
				$("#ftp_ip_settle_u").val(ftp_ip);
				$("#ftp_address_settle_u").val(ftp_address);
				$("#ftp_port_settle_u").val(ftp_port);
				$("#ftp_username_settle_u").val(ftp_username);
				$("#ftp_password_settle_u").val(ftp_password);
				$("#file_suffix_settle_u").val(file_suffix);
				
				changeFTPInfoOfSettle(whether_upload);
				
				$("#updateSettle").css({display:"block"});
			}else if(file_type == 3){//内部清算文件
				$("#inner_object_id_u").val(object_id);
				$("#inner_object_name_u").val(object_name);
				$("#inner_file_address_u").val(file_address);
				$("#inner_file_name_u").val(dz_file_name);
				
				var whether_upload_ = document.getElementById("inner_whether_upload_u");
				
				for(var i = 0;i<whether_upload_.options.length;i++){
					if(whether_upload_.options[i].value == whether_upload){
						whether_upload_.options[i].selected = 'selected';
					}
				}
				var generate_number_ = document.getElementById("inner_generate_number_u");
				
				for(var i = 0;i<generate_number_.options.length;i++){
					if(generate_number_.options[i].value == generate_number){
						generate_number_.options[i].selected = 'selected';
					}
				}
				
				var whether_create_file_by_range_inner_ = document.getElementById("inner_whether_create_file_by_range_u");
				
				for(var i = 0;i<whether_create_file_by_range_inner_.options.length;i++){
					if(whether_create_file_by_range_inner_.options[i].value == whether_create_file_by_range){
						whether_create_file_by_range_inner_.options[i].selected = 'selected';
					}
				}
				
				var whether_create_file_by_inst_inner_ = document.getElementById("inner_whether_create_file_by_inst_u");
				
				for(var i = 0;i<whether_create_file_by_inst_inner_.options.length;i++){
					if(whether_create_file_by_inst_inner_.options[i].value == whether_create_file_by_inst){
						whether_create_file_by_inst_inner_.options[i].selected = 'selected';
					}
				}
				
				showInstInfoConfig(whether_create_file_by_inst_inner_);
				
				$("#inner_ftp_ip_u").val(ftp_ip);
				$("#inner_ftp_address_u").val(ftp_address);
				$("#inner_ftp_port_u").val(ftp_port);
				$("#inner_ftp_username_u").val(ftp_username);
				$("#inner_ftp_password_u").val(ftp_password);
				$("#inner_file_suffix_u").val(file_suffix);
				
				changeFTPInfoOfInner(whether_upload);
				
				$("#updateInner").css({display:"block"});
			}else if(file_type == 1 ){//对账总表
				$("#object_id_u").val(object_id);
				$("#object_name_u").val(object_name);
				$("#file_address_u").val(file_address);
				$("#dz_file_name_u").val(dz_file_name);
				$("#error_file_name_u").val(error_file_name);
				
				var whether_upload_ = document.getElementById("whether_upload_u");
				
				for(var i = 0;i<whether_upload_.options.length;i++){
					if(whether_upload_.options[i].value == whether_upload){
						whether_upload_.options[i].selected = 'selected';
					}
				}
				
				var whether_create_error_file_ = document.getElementById("whether_create_error_file_u");
				
				for(var i = 0;i<whether_create_error_file_.options.length;i++){
					if(whether_create_error_file_.options[i].value == whether_create_error_file){
						whether_create_error_file_.options[i].selected = 'selected';
					}
				}
				
				showErrorFileNameText(whether_create_error_file,2);//调整差错文件名称文本框可编辑状态
				
				var generate_number_ = document.getElementById("generate_number_u");
				
				for(var i = 0;i<generate_number_.options.length;i++){
					if(generate_number_.options[i].value == generate_number){
						generate_number_.options[i].selected = 'selected';
					}
				}
				
				var file_need_online_data_ = document.getElementById("file_need_online_data_u");
				
				for(var i = 0;i<file_need_online_data_.options.length;i++){
					if(file_need_online_data_.options[i].value == file_need_online_data){
						file_need_online_data_.options[i].selected = 'selected';
					}
				}
				
				var whether_create_file_by_range_ = document.getElementById("whether_create_file_by_range_u");
				
				for(var i = 0;i<whether_create_file_by_range_.options.length;i++){
					if(whether_create_file_by_range_.options[i].value == whether_create_file_by_range){
						whether_create_file_by_range_.options[i].selected = 'selected';
					}
				}
				
				var whether_create_file_by_inst_ = document.getElementById("whether_create_file_by_inst_u");
				
				for(var i = 0;i<whether_create_file_by_inst_.options.length;i++){
					if(whether_create_file_by_inst_.options[i].value == whether_create_file_by_inst){
						whether_create_file_by_inst_.options[i].selected = 'selected';
					}
				}
				
				showInstInfoConfig(whether_create_file_by_inst_);
				
				$("#ftp_ip_u").val(ftp_ip);
				$("#ftp_address_u").val(ftp_address);
				$("#ftp_port_u").val(ftp_port);
				$("#ftp_username_u").val(ftp_username);
				$("#ftp_password_u").val(ftp_password);
				$("#file_suffix_u").val(file_suffix);
				
				changeFTPInfo(whether_upload);
				
				$("#update").css({display:"block"});
			}
		}
		
		function updateData(){
			var object_id_u = $("#object_id_u").val();
			var object_name_u = $("#object_name_u").val();
			var file_address_u = $("#file_address_u").val();
			var dz_file_name_u = $("#dz_file_name_u").val();
			var error_file_name_u = $("#error_file_name_u").val();
			var file_type = 1;//对账总表
			var data_type = 2;//已对账数据
			
			if(object_name_u == null || object_name_u == ""){
				alert("系统接口名称不能为空！");
				return;
			}
			if(file_address_u == "" || file_address_u == null){
				alert("对账文件路径不能为空！");
				return;
			}
			if(dz_file_name_u == "" || dz_file_name_u == null){
				alert("对账文件名称不能为空！");
				return;
			}
			
			var ftp_ip = $("#ftp_ip_u").val();
			var ftp_address = $("#ftp_address_u").val();
			var ftp_port = $("#ftp_port_u").val();
			var ftp_username = $("#ftp_username_u").val();
			var ftp_password = $("#ftp_password_u").val();
			var file_suffix = $("#file_suffix_u").val();
			var whether_upload = $("#whether_upload_u").val();

			if(whether_upload == 1){
				if(ftp_ip == null || ftp_ip == ""){
					alert("FTP主机IP不能为空！");
					return;
				}
				if(ftp_address == "" || ftp_address == null){
					alert("FTP存放文件地址不能为空！");
					return;
				}
				if(ftp_port == "" || ftp_port == null){
					alert("FTP端口号不能为空！");
					return;
				}
				if(ftp_username == "" || ftp_username == null){
					alert("FTP用户名不能为空！");
					return;
				}
				if(ftp_password == "" || ftp_password == null){
					alert("FTP密码不能为空！");
					return;
				}
				if(file_suffix == "" || file_suffix == null){
					alert("对账后缀不能为空！");
					return;
				}
				if(!(file_suffix == ".xls" || file_suffix == ".txt")){
					alert("对账文件后缀必须是.txt或者.xls格式");
					return;
				}
			}
			var generate_number = $("#generate_number_u").val();
			var file_need_online_data = $("#file_need_online_data_u").val();
			
			var whether_create_error_file = $("#whether_create_error_file_u").val();
			var whether_create_file_by_range = $("#whether_create_file_by_range_u").val();
			
			var whether_create_file_by_inst = $("#whether_create_file_by_inst_u").val();//是否按照渠道生成文件
			
			var selectedColumn = new Array();
			
			if(whether_create_file_by_inst == 1){
				var j =0;
				$("#inst_info_config").find("#selectedColumn").find("option").each(function(){
					selectedColumn[j] = $(this).val();
					j++;
				});
			}
			
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateCustomObject.do",
				data : "object_id="+ object_id_u +"&object_name="+object_name_u+"&file_address="+file_address_u+"&dz_file_name="+dz_file_name_u+"&error_file_name="+error_file_name_u+"&ftp_ip="+ftp_ip+"&ftp_address="+ftp_address+"&ftp_port="+ftp_port+"&ftp_username="+ftp_username+"&ftp_password="+ftp_password+"&file_suffix="+file_suffix+"&whether_upload="+whether_upload+"&generate_number="+generate_number+"&file_need_online_data="+file_need_online_data+"&data_type="+data_type+"&whether_create_error_file="+whether_create_error_file+"&file_type="+file_type+"&whether_create_file_by_range="+whether_create_file_by_range+"&data_type="+data_type+"&whether_create_file_by_inst="+whether_create_file_by_inst+"&selectedColumn="+selectedColumn,
				dataType : "text",
				success : function(msg) {
					if(msg == "1"){
						alert("修改成功");
						checkQuery();
					}else{
						alert("修改失败");
						hide("update");
					}
				}
			});
		}
		
		function deleteData(id,active){
			if(confirm("确定要进行此操作吗?")){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/deleteCustomObject.do",
					data : "object_id="+ id,
					dataType : "text",
					success : function(msg) {
						if(msg == "1"){
							alert("删除成功");
							checkQuery();
						}else{
							alert("操作失败");
						}
					}
				});
			}
		}
		
		function changeFTPInfo(whether_upload){
			if(whether_upload == 0){
				$("#ftp_ip").attr("disabled",true);
				$("#ftp_address").attr("disabled",true);
				$("#ftp_port").attr("disabled",true);
				$("#ftp_username").attr("disabled",true);
				$("#ftp_password").attr("disabled",true);
				$("#file_suffix").attr("disabled",true);
				$("#ftp_ip_u").attr("disabled",true);
				$("#ftp_address_u").attr("disabled",true);
				$("#ftp_port_u").attr("disabled",true);
				$("#ftp_username_u").attr("disabled",true);
				$("#ftp_password_u").attr("disabled",true);
				$("#file_suffix_u").attr("disabled",true);
				$("#ftp_ip").val("");
				$("#ftp_address").val("");
				$("#ftp_port").val("");
				$("#ftp_username").val("");
				$("#ftp_password").val("");
				$("#file_suffix").val("");
				$("#ftp_ip_u").val("");
				$("#ftp_address_u").val("");
				$("#ftp_port_u").val("");
				$("#ftp_username_u").val("");
				$("#ftp_password_u").val("");
				$("#file_suffix_u").val("");
			}else{
				$("#ftp_ip").attr("disabled",false);
				$("#ftp_address").attr("disabled",false);
				$("#ftp_port").attr("disabled",false);
				$("#ftp_username").attr("disabled",false);
				$("#ftp_password").attr("disabled",false);
				$("#file_suffix").attr("disabled",false);
				$("#ftp_ip_u").attr("disabled",false);
				$("#ftp_address_u").attr("disabled",false);
				$("#ftp_port_u").attr("disabled",false);
				$("#ftp_username_u").attr("disabled",false);
				$("#ftp_password_u").attr("disabled",false);
				$("#file_suffix_u").attr("disabled",false);
			}
		}
		
		function showAddCustomObject(){
			var file_type = $("#file_type").val();
			if(file_type == 1){
				$("#insert").css({display:"block"});
			}else if(file_type == 2){
				$("#insertSettle").css({display:"block"});
			}else{
				$("#insertInner").css({display:"block"});
			}
		}
		
		
		function addCustomObjectOfSettleSub(){
			var object_name_settle = $("#object_name_settle").val();
			var file_address_settle = $("#file_address_settle").val();
			var settle_file_name = $("#settle_file_name").val();
			
			
			if(object_name == null || object_name == ""){
				alert("系统接口名称不能为空！");
				return;
			}
			if(file_address == "" || file_address == null){
				alert("对账文件路径不能为空！");
				return;
			}
			if(dz_file_name == "" || dz_file_name == null){
				alert("对账文件名称不能为空！");
				return;
			}
			var ftp_ip = $("#ftp_ip_settle").val();
			var ftp_address = $("#ftp_address_settle").val();
			var ftp_port = $("#ftp_port_settle").val();
			var ftp_username = $("#ftp_username_settle").val();
			var ftp_password = $("#ftp_password_settle").val();
			var file_suffix = $("#file_suffix_settle").val();
			var whether_upload = $("#whether_upload_settle").val();
			if(whether_upload == 1){
				if(ftp_ip == null || ftp_ip == ""){
					alert("FTP主机IP不能为空！");
					return;
				}
				if(ftp_address == "" || ftp_address == null){
					alert("FTP存放文件地址不能为空！");
					return;
				}
				if(ftp_port == "" || ftp_port == null){
					alert("FTP端口号不能为空！");
					return;
				}
				if(ftp_username == "" || ftp_username == null){
					alert("FTP用户名不能为空！");
					return;
				}
				if(ftp_password == "" || ftp_password == null){
					alert("FTP密码不能为空！");
					return;
				}
				if(file_suffix == "" || file_suffix == null){
					alert("结算单后缀不能为空！");
					return;
				}
				if(!(file_suffix == ".xls" || file_suffix == ".txt")){
					alert("结算单后缀必须是.txt或者.xls格式");
					return;
				}
			}
			var generate_number = $("#generate_number_settle").val();
			
			var file_type = 2;//结算文件
			
			var whether_create_file_by_range_settle = $("#whether_create_file_by_range_settle").val();
			
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addCustomObject.do",
				data : "object_name=" + object_name_settle + "&file_address=" + file_address_settle +"&dz_file_name="+settle_file_name+"&ftp_ip="+ftp_ip+"&ftp_address="+ftp_address+"&ftp_port="+ftp_port+"&ftp_username="+ftp_username+"&ftp_password="+ftp_password+"&file_suffix="+file_suffix+"&whether_upload="+whether_upload+"&generate_number="+generate_number+"&file_type="+file_type+"&whether_create_file_by_range="+whether_create_file_by_range_settle,
				dataType : "text",
				beforeSend:function(){
					document.getElementById("loading-mask").style.display="";
			        document.getElementById("loading").style.display="";
				},
				complete:function(){
					document.getElementById("loading-mask").style.display="none";
			        document.getElementById("loading").style.display="none";
				},
				success : function(msg) {
					if (msg == "1") {
						alert("添加成功！");
						hide("insert");
						checkQuery();
					}else {					
						alert("添加失败！");
						return;
					}
				}
			});
		}
		
		function updateSettleData(){
			var object_id_u = $("#object_id_settle_u").val();
			var object_name_u = $("#object_name_settle_u").val();
			var file_address_u = $("#file_address_settle_u").val();
			var dz_file_name_u = $("#settle_file_name_u").val();
			
			var file_type = 2;//结算文件
			
			if(object_name_u == null || object_name_u == ""){
				alert("系统接口名称不能为空！");
				return;
			}
			if(file_address_u == "" || file_address_u == null){
				alert("结算单路径不能为空！");
				return;
			}
			if(dz_file_name_u == "" || dz_file_name_u == null){
				alert("结算单名称不能为空！");
				return;
			}
			var ftp_ip = $("#ftp_ip_settle_u").val();
			var ftp_address = $("#ftp_address_settle_u").val();
			var ftp_port = $("#ftp_port_settle_u").val();
			var ftp_username = $("#ftp_username_settle_u").val();
			var ftp_password = $("#ftp_password_settle_u").val();
			var file_suffix = $("#file_suffix_settle_u").val();
			var whether_upload = $("#whether_upload_settle_u").val();

			if(whether_upload == 1){
				if(ftp_ip == null || ftp_ip == ""){
					alert("FTP主机IP不能为空！");
					return;
				}
				if(ftp_address == "" || ftp_address == null){
					alert("FTP存放文件地址不能为空！");
					return;
				}
				if(ftp_port == "" || ftp_port == null){
					alert("FTP端口号不能为空！");
					return;
				}
				if(ftp_username == "" || ftp_username == null){
					alert("FTP用户名不能为空！");
					return;
				}
				if(ftp_password == "" || ftp_password == null){
					alert("FTP密码不能为空！");
					return;
				}
				if(file_suffix == "" || file_suffix == null){
					alert("文件后缀不能为空！");
					return;
				}
				if(!(file_suffix == ".xls" || file_suffix == ".txt")){
					alert("文件后缀必须是.txt或者.xls格式");
					return;
				}
			}
			var generate_number = $("#generate_number_settle_u").val();
			var whether_create_file_by_range = $("#whether_create_file_by_range_settle_u").val();
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateCustomObject.do",
				data : "object_id="+ object_id_u +"&object_name="+object_name_u+"&file_address="+file_address_u+"&dz_file_name="+dz_file_name_u+"&error_file_name="+error_file_name_u+"&ftp_ip="+ftp_ip+"&ftp_address="+ftp_address+"&ftp_port="+ftp_port+"&ftp_username="+ftp_username+"&ftp_password="+ftp_password+"&file_suffix="+file_suffix+"&whether_upload="+whether_upload+"&generate_number="+generate_number+"&file_type="+file_type+"&whether_create_file_by_range="+whether_create_file_by_range,
				dataType : "text",
				success : function(msg) {
					if(msg == "1"){
						alert("修改成功");
						checkQuery();
					}else{
						alert("修改失败");
						hide("update");
					}
				}
			});
		}
		function changeFTPInfoOfSettle(whether_upload){
			if(whether_upload == 0){
				$("#ftp_ip_settle").attr("disabled",true);
				$("#ftp_address_settle").attr("disabled",true);
				$("#ftp_port_settle").attr("disabled",true);
				$("#ftp_username_settle").attr("disabled",true);
				$("#ftp_password_settle").attr("disabled",true);
				$("#file_suffix_settle").attr("disabled",true);
				$("#ftp_ip_settle_u").attr("disabled",true);
				$("#ftp_address_settle_u").attr("disabled",true);
				$("#ftp_port_settle_u").attr("disabled",true);
				$("#ftp_username_settle_u").attr("disabled",true);
				$("#ftp_password_settle_u").attr("disabled",true);
				$("#file_suffix_settle_u").attr("disabled",true);
				$("#ftp_ip_settle").val("");
				$("#ftp_address_settle").val("");
				$("#ftp_port_settle").val("");
				$("#ftp_username_settle").val("");
				$("#ftp_password_settle").val("");
				$("#file_suffix_settle").val("");
				$("#ftp_ip_settle_u").val("");
				$("#ftp_address_settle_u").val("");
				$("#ftp_port_settle_u").val("");
				$("#ftp_username_settle_u").val("");
				$("#ftp_password_settle_u").val("");
				$("#file_suffix_settle_u").val("");
			}else{
				$("#ftp_ip_settle").attr("disabled",false);
				$("#ftp_address_settle").attr("disabled",false);
				$("#ftp_port_settle").attr("disabled",false);
				$("#ftp_username_settle").attr("disabled",false);
				$("#ftp_password_settle").attr("disabled",false);
				$("#file_suffix_settle").attr("disabled",false);
				$("#ftp_ip_settle_u").attr("disabled",false);
				$("#ftp_address_settle_u").attr("disabled",false);
				$("#ftp_port_settle_u").attr("disabled",false);
				$("#ftp_username_settle_u").attr("disabled",false);
				$("#ftp_password_settle_u").attr("disabled",false);
				$("#file_suffix_settle_u").attr("disabled",false);
			}
		}
		
		function changeFTPInfoOfInner(whether_upload){
			if(whether_upload == 0){
				$("#ftp_ip_inner").attr("disabled",true);
				$("#ftp_address_inner").attr("disabled",true);
				$("#ftp_port_inner").attr("disabled",true);
				$("#ftp_username_inner").attr("disabled",true);
				$("#ftp_password_inner").attr("disabled",true);
				$("#file_suffix_inner").attr("disabled",true);
				$("#inner_ftp_ip_u").attr("disabled",true);
				$("#inner_ftp_address_u").attr("disabled",true);
				$("#inner_ftp_port_u").attr("disabled",true);
				$("#inner_ftp_username_u").attr("disabled",true);
				$("#inner_ftp_password_u").attr("disabled",true);
				$("#inner_file_suffix_u").attr("disabled",true);
				$("#ftp_ip_inner").val("");
				$("#ftp_address_inner").val("");
				$("#ftp_port_inner").val("");
				$("#ftp_username_inner").val("");
				$("#ftp_password_inner").val("");
				$("#file_suffix_inner").val("");
				$("#inner_ftp_ip_u").val("");
				$("#inner_ftp_address_u").val("");
				$("#inner_ftp_port_u").val("");
				$("#inner_ftp_username_u").val("");
				$("#inner_ftp_password_u").val("");
				$("#inner_file_suffix_u").val("");
			}else{
				$("#ftp_ip_inner").attr("disabled",false);
				$("#ftp_address_inner").attr("disabled",false);
				$("#ftp_port_inner").attr("disabled",false);
				$("#ftp_username_inner").attr("disabled",false);
				$("#ftp_password_inner").attr("disabled",false);
				$("#file_suffix_inner").attr("disabled",false);
				$("#inner_ftp_ip_u").attr("disabled",false);
				$("#inner_ftp_address_u").attr("disabled",false);
				$("#inner_ftp_port_u").attr("disabled",false);
				$("#inner_ftp_username_u").attr("disabled",false);
				$("#inner_ftp_password_u").attr("disabled",false);
				$("#inner_file_suffix_u").attr("disabled",false);
			}
		}
		
		function addCustomObjectOfInnerSub(){
			var object_name_inner = $("#object_name_inner").val();
			var file_address_inner = $("#file_address_inner").val();
			var inner_file_name = $("#inner_file_name").val();
			
			
			if(object_name == null || object_name == ""){
				alert("系统接口名称不能为空！");
				return;
			}
			if(file_address == "" || file_address == null){
				alert("文件路径不能为空！");
				return;
			}
			if(dz_file_name == "" || dz_file_name == null){
				alert("文件名称不能为空！");
				return;
			}
			var ftp_ip = $("#ftp_ip_inner").val();
			var ftp_address = $("#ftp_address_inner").val();
			var ftp_port = $("#ftp_port_inner").val();
			var ftp_username = $("#ftp_username_inner").val();
			var ftp_password = $("#ftp_password_inner").val();
			var file_suffix = $("#file_suffix_settle").val();
			var whether_upload = $("#whether_upload_inner").val();
			if(whether_upload == 1){
				if(ftp_ip == null || ftp_ip == ""){
					alert("FTP主机IP不能为空！");
					return;
				}
				if(ftp_address == "" || ftp_address == null){
					alert("FTP存放文件地址不能为空！");
					return;
				}
				if(ftp_port == "" || ftp_port == null){
					alert("FTP端口号不能为空！");
					return;
				}
				if(ftp_username == "" || ftp_username == null){
					alert("FTP用户名不能为空！");
					return;
				}
				if(ftp_password == "" || ftp_password == null){
					alert("FTP密码不能为空！");
					return;
				}
				if(file_suffix == "" || file_suffix == null){
					alert("文件后缀不能为空！");
					return;
				}
				if(!(file_suffix == ".xls" || file_suffix == ".txt")){
					alert("文件后缀必须是.txt或者.xls格式");
					return;
				}
			}
			var generate_number = $("#generate_number_inner").val();
			
			var file_type = 3;//内部清算文件
			var data_type = 1;//未对账数据
			
			var whether_create_file_by_range_inner = $("#whether_create_file_by_range_inner").val();
			
			var whether_create_file_by_inst = $("#whether_create_file_by_inst_inner").val();//是否按照渠道生成文件
			
			var selectedColumn = new Array();
			
			if(whether_create_file_by_inst == 1){
				var j =0;
				$("#inst_info_config").find("#selectedColumn").find("option").each(function(){
					selectedColumn[j] = $(this).val();
					j++;
				});
			}
			
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addCustomObject.do",
				data : "object_name=" + object_name_inner + "&file_address=" + file_address_inner +"&dz_file_name="+inner_file_name+"&ftp_ip="+ftp_ip+"&ftp_address="+ftp_address+"&ftp_port="+ftp_port+"&ftp_username="+ftp_username+"&ftp_password="+ftp_password+"&file_suffix="+file_suffix+"&whether_upload="+whether_upload+"&generate_number="+generate_number+"&file_type="+file_type+"&whether_create_file_by_range="+whether_create_file_by_range_inner+"&data_type="+data_type+"&whether_create_file_by_inst="+whether_create_file_by_inst+"&selectedColumn="+selectedColumn,
				dataType : "text",
				beforeSend:function(){
					document.getElementById("loading-mask").style.display="";
			        document.getElementById("loading").style.display="";
				},
				complete:function(){
					document.getElementById("loading-mask").style.display="none";
			        document.getElementById("loading").style.display="none";
				},
				success : function(msg) {
					if (msg == "1") {
						alert("添加成功！");
						hide("insert");
						checkQuery();
					}else {					
						alert("添加失败！");
						return;
					}
				}
			});
		}
		
		function updateInnerData(){
			var object_id_u = $("#inner_object_id_u").val();
			var object_name_u = $("#inner_object_name_u").val();
			var file_address_u = $("#inner_file_address_u").val();
			var dz_file_name_u = $("#inner_file_name_u").val();
			
			var file_type = 3;//内部清算文件
			var data_type = 1;//未对账数据
			
			if(object_name_u == null || object_name_u == ""){
				alert("系统接口名称不能为空！");
				return;
			}
			if(file_address_u == "" || file_address_u == null){
				alert("文件路径不能为空！");
				return;
			}
			if(dz_file_name_u == "" || dz_file_name_u == null){
				alert("文件名称不能为空！");
				return;
			}
			var ftp_ip = $("#inner_ftp_ip_u").val();
			var ftp_address = $("#inner_ftp_address_u").val();
			var ftp_port = $("#inner_ftp_port_u").val();
			var ftp_username = $("#inner_ftp_username_u").val();
			var ftp_password = $("#inner_ftp_password_u").val();
			var file_suffix = $("#inner_file_suffix_u").val();
			var whether_upload = $("#inner_whether_upload_u").val();

			if(whether_upload == 1){
				if(ftp_ip == null || ftp_ip == ""){
					alert("FTP主机IP不能为空！");
					return;
				}
				if(ftp_address == "" || ftp_address == null){
					alert("FTP存放文件地址不能为空！");
					return;
				}
				if(ftp_port == "" || ftp_port == null){
					alert("FTP端口号不能为空！");
					return;
				}
				if(ftp_username == "" || ftp_username == null){
					alert("FTP用户名不能为空！");
					return;
				}
				if(ftp_password == "" || ftp_password == null){
					alert("FTP密码不能为空！");
					return;
				}
				if(file_suffix == "" || file_suffix == null){
					alert("文件后缀不能为空！");
					return;
				}
				if(!(file_suffix == ".xls" || file_suffix == ".txt")){
					alert("文件后缀必须是.txt或者.xls格式");
					return;
				}
			}
			var generate_number = $("#inner_generate_number_u").val();
			var whether_create_file_by_range = $("#inner_whether_create_file_by_range_u").val();
			
			var whether_create_file_by_inst = $("#inner_whether_create_file_by_inst_u").val();//是否按照渠道生成文件
			
			var selectedColumn = new Array();
			
			if(whether_create_file_by_inst == 1){
				var j =0;
				$("#inst_info_config").find("#selectedColumn").find("option").each(function(){
					selectedColumn[j] = $(this).val();
					j++;
				});
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateCustomObject.do",
				data : "object_id="+ object_id_u +"&object_name="+object_name_u+"&file_address="+file_address_u+"&dz_file_name="+dz_file_name_u+"&error_file_name="+error_file_name_u+"&ftp_ip="+ftp_ip+"&ftp_address="+ftp_address+"&ftp_port="+ftp_port+"&ftp_username="+ftp_username+"&ftp_password="+ftp_password+"&file_suffix="+file_suffix+"&whether_upload="+whether_upload+"&generate_number="+generate_number+"&file_type="+file_type+"&whether_create_file_by_range="+whether_create_file_by_range+"&data_type="+data_type+"&whether_create_file_by_inst="+whether_create_file_by_inst+"&selectedColumn="+selectedColumn,
				dataType : "text",
				success : function(msg) {
					if(msg == "1"){
						alert("修改成功");
						checkQuery();
					}else{
						alert("修改失败");
						hide("update");
					}
				}
			});
		}
		
		function showErrorFileNameText(obj,op_type){//下拉框值,操作类型,1-新增,2-修改
			if(obj == 2){
				if(op_type == 1){
					$("#error_file_name").val("");
					$("#error_file_name").attr("disabled",true);
				}else{
					$("#error_file_name_u").val("");
					$("#error_file_name_u").attr("disabled",true);
				}
			}else{
				if(op_type == 1){
					$("#error_file_name").attr("disabled",false);
				}else{
					$("#error_file_name_u").attr("disabled",false);
				}
			}
		}
		
		function configureInstInfo(){
			changeSelect("");
			var object_id = $('#object_id_h').val();
			$("#selectedColumn").empty();
			
			var file_type = $("#file_type").val();
			var operate_type = $("#operate_type").val();
			
			var dataSource = '';
			
			if(file_type == 1){//对账总表
				if(operate_type == 1){
					dataSource = $("#file_need_online_data").val();
				}else{
					dataSource = $("#file_need_online_data_u").val();
				}
			}else if(file_type == 3){//内部清算文件
				dataSource = "1";//写死为线下渠道来源
			}
			
			$.ajax({
	    		url : '<%=request.getContextPath()%>/queryCustomObjectInstInfoList.do',
	    		data : "object_id="+ object_id + "&dataSource="+dataSource,
	    		type : 'post',
	    		dataType: 'text',
	    		success : function(msg) {
	    			if(msg != null){
	    				msg = eval("(" + msg + ")");
	    				for(i in msg){
		    				$("#selectedColumn").append('<option value="' + msg[i]['inst_id'] + '-' + msg[i]['inst_type']+'">'+ msg[i]['inst_name'] + '</option>');
		    			}
	    			}
	    		}
	    	});
			$("#inst_info_config").css({display:"block"});
		}
		
		jQuery(function ($) {
			//上移
			var right=$("#selectedColumn");
			$("#moveUp").click(function(){
				moveUp(right);
			});
			//下移
			$("#moveDown").click(function(){
				moveDown();
			});
			
			//上移
			function moveUp(right){
				var so = right.find("option[selected]");
				if(so.length!=0){
					if(so.get(0).index!=0){
						so.each(function(){
							$(this).insertBefore($(this).prev());
						});
					}
				}
			}
			
			 /**单元素向下移动*/  
			function  moveDown(){     
			    var selectRight=document.getElementsByName("selectedColumn")[0];  
			    var i = document.getElementsByName("selectedColumn")[0].selectedIndex;
			    if (i != selectRight.length-1){     
			        var j = i+1;     
			        if(i < selectRight.length){     
			            var Temp_Text = selectRight[j].text;     
			            var Temp_ID = selectRight[j].value;     
			      
			            selectRight[j].text = selectRight[i].text;     
			            selectRight[j].value = selectRight[i].value;     
			      
			            selectRight[i].text = Temp_Text;     
			            selectRight[i].value = Temp_ID;     
			      
			            selectRight.selectedIndex=j;    
			        }     
			    }     
			}
	    }); 
	    
	    jQuery.noConflict();
		jQuery(function ($) {
			var right=$("#selectedColumn");
			$(document).ready(function () {
				//左移右边选中的
				$("#delete_bt").click(function(){
					removeFromRight(right);
				});
				//右移左边选中的
				$("#select_bt").click(function(){
					leftToRight();
				});
				//右移左边全部的
				$("#selectAll_bt").click(function(){
					moveAllOption();
				});
				//右移左边全部的
				$("#deleteAll_bt").click(function(){
					clearAllOption();
				});
				
			});
		});
		function changeSelect(inst_name){
			$("#allColumn").empty();
			var file_type = $("#file_type").val();
			var operate_type = $("#operate_type").val();
			
			var dataSource = '';
			
			if(file_type == 1){//对账总表
				if(operate_type == 1){
					dataSource = $("#file_need_online_data").val();
				}else{
					dataSource = $("#file_need_online_data_u").val();
				}
			}else if(file_type == 3){//内部清算文件
				dataSource = "1";//写死为线下渠道来源
			}
			
			$.ajax({
	    		url : '<%=request.getContextPath()%>/loadInstInfoOfCustomObject.do',
	    		data : "inst_name="+ inst_name+"&file_type="+file_type+"&dataSource="+dataSource,
	    		type : 'post',
	    		async : false,
	    		success : function(msg) {
	    			for (i in msg){
	    				$("#allColumn").append('<option value="' + msg[i]['instId'] + '-' + msg[i]['inst_type']+'">'+ msg[i]['name'] + '</option>');
	    			}
	    		}
	    	});
		}
		
		function showInstInfoConfig(obj){
			var value = $(obj).val();
			if(value == 0){
				if($(obj).parent().parent().parent().find("#button_config").length == 0){
					$(obj).parent().parent().parent().parent().find("#button_config").attr("disabled",true);
				}else{
					$(obj).parent().parent().parent().find("#button_config").attr("disabled",true);
				}
			}else{
				if($(obj).parent().parent().parent().find("#button_config").length == 0){
					$(obj).parent().parent().parent().parent().find("#button_config").attr("disabled",false);
				}else{
					$(obj).parent().parent().parent().find("#button_config").attr("disabled",false);
				}
			}
		}

	</script>
</head>
<body>
<div id='loading-mask' style="display: none"></div>
<div id="loading" style="display: none">
    <div class="loading-indicator">
       <img src="<%=request.getContextPath()%>/images/wait-1.gif" width="60" height="60" style="margin-right:8px;float:left;vertical-align:top;"/>
       <br/><span id="loading-msg"></span>
    </div>
</div>
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">自定义对账文件管理</a>&gt;<span>系统接口配置</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryCustomObject.do" target="right" name="customObjectSearch" id = "customObjectSearch" method="post">
					<ul class="check-m">						
						<li>
							<b>系统接口ID：</b>
							<span class="input_bgl">
								<input maxlength="8" type="text" name="object_id_" onkeyup="value=value.replace(/[\s]/g,'')" id="object_id_" value="${param.object_id }"/>
							</span>
						</li>
						<li>
							<b>系统接口名称：</b>
							<span class="input_bgl">
								<input maxlength="8" type="text" name="object_name_" onkeyup="value=value.replace(/[\s]/g,'')" id = "object_name_" value="${param.object_name }" />
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery();"/>
							<input type="button" class="icon_normal" value="添加" onclick="addCustomObject();"/>
						</li>
					</ul>
				</form>
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</div>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0" style="table-layout:fixed;">
					<thead>
						<tr>
							<td align="center" width="8%">系统接口ID</td>
							<td align="center" width="15%">系统接口名称</td>
							<td align="center" width="20%">文件路径</td>
							<td align="center" width="20%">是否创建结算文件</td>
							<td align="center" width="12%">文件名称</td>
							<td align="center" width="10%">是否上传到FTP</td>
							<td align="center" width="10%">接口数据范围</td>
							<td align="center" width="10%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageCustomObject.result)<=0 }">
						<tr align="center">
							<td colspan="11">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageCustomObject.result }" var="customObject">
						<tr id="${customObject.object_id }" class="ssss">
							<td align="center" style="word-wrap:break-word;">${customObject.object_id }</td>
							<td align="center" style="word-wrap:break-word;">${customObject.object_name }</td>
							<td align="center" style="word-wrap:break-word;">${customObject.file_address }</td>
							<td align="center" style="word-wrap:break-word;">
								<c:if test="${customObject.file_type == 1 }">对账总表</c:if>
								<c:if test="${customObject.file_type == 2 }">结算文件</c:if>
								<c:if test="${customObject.file_type == 3 }">内部清算文件</c:if>
							</td>
							<td align="center" style="word-wrap:break-word;">${customObject.dz_file_name }</td>
							<td align="center" style="word-wrap:break-word;">
								<c:if test="${customObject.whether_upload ==1 }">需要</c:if>
								<c:if test="${customObject.whether_upload ==0 }">不需要</c:if>
							</td>
							<td align="center" style="word-wrap:break-word;">
								<c:if test="${customObject.generate_number ==1 }">全部</c:if>
								<c:if test="${customObject.generate_number ==2 }">按商户号统计</c:if>
								<c:if test="${customObject.generate_number ==3 }">按交易码统计</c:if>
							</td>
							<td align="center" width="60">
								<a style="color: red" href="javascript:selectData('${customObject.object_id}','${customObject.object_name}','${customObject.file_address}','${customObject.dz_file_name}','${customObject.error_file_name}','${customObject.whether_upload}','${customObject.ftp_ip}','${customObject.ftp_address}','${customObject.ftp_port}','${customObject.ftp_username}','${customObject.ftp_password}','${customObject.file_suffix}','${customObject.generate_number}','${customObject.file_need_online_data }','${customObject.data_type }','${customObject.whether_create_error_file }','${customObject.file_type }','${customObject.whether_create_file_by_range }','${customObject.whether_create_file_by_inst }')">修改</a>
								<a style="color: red" href="javascript:deleteData('${customObject.object_id }')">删除</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageCustomObject.totalPages != null}">
				<div class="next">
					<c:if test="${pageCustomObject.pageNo > 1}">
						<a href="javascript:paging(${pageCustomObject.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageCustomObject.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageCustomObject.pageNo-2 > 0}">
						<a href="javascript:paging(${pageCustomObject.pageNo-2 })"><span>${pageCustomObject.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageCustomObject.pageNo-1 > 0}">
						<a href="javascript:paging(${pageCustomObject.pageNo-1 })"><span>${pageCustomObject.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageCustomObject.pageNo }</span></a>
					<c:if test="${pageCustomObject.pageNo+1 <= pageCustomObject.totalPages}">
						<a href="javascript:paging(${pageCustomObject.pageNo+1 })"><span>${pageCustomObject.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageCustomObject.pageNo+2 <= pageCustomObject.totalPages}">
						<a href="javascript:paging(${pageCustomObject.pageNo+2 })"><span>${pageCustomObject.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageCustomObject.pageNo+3 <= pageCustomObject.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageCustomObject.pageNo < pageCustomObject.totalPages}">
						<a href="javascript:paging(${pageCustomObject.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageCustomObject.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${pageCustomObject.pageNo }" />页
							<input type="hidden" value="${pageCustomObject.pageNo}" id="pageNo" />
					</span></b>
				</div>
			</c:if>
		</div>
	</div>
	
	<!--===========================弹出内容============================-->
	<div id="typeSelect" class="pop" style="display: none">
		<div class="pop_body" >
			<h1 class="pop_tit">
				<span class="fl">系统接口信息添加</span> 
				<a class="close" href="javascript:hide('typeSelect');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="customObject">
					<tr>
						<td align="right" bgcolor="#eeeeee">文件类型：</td>
						<td>
							<select id="file_type" name="file_type">
					        	<option value="1">对账总表</option>
					       		<option value="2">结算单</option>
					       		<option value="3">内部清算文件</option>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="下一步" onclick="showAddCustomObject();"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id="insert" class="pop" style="display: none;OVERFLOW: auto;">
		<div class="pop_body" >
			<h1 class="pop_tit">
				<span class="fl">系统接口信息添加</span> 
				<a class="close" href="javascript:hide('insert');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="customObject">					
					<tr>
						<td align="right" bgcolor="#eeeeee">系统接口名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="object_name" maxlength="20" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" name="object_name" onblur="uniqueObjectName(this);"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee"></td>
						<td></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">文件路径：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="file_address" maxlength="80" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" name="file_address" onblur="uniqueFileAddress(this);"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">对账文件名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="dz_file_name" maxlength="20" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" name="dz_file_name"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否生成差错文件：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="whether_create_error_file" name="whether_create_error_file" onchange="showErrorFileNameText(this.value,1)">
						        	<option value="1">是</option>
						       		<option value="2">否</option>
								</select>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">差错文件名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="error_file_name" maxlength="20" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" name="error_file_name"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否上传至FTP：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="whether_upload" name="whether_upload" onclick="changeFTPInfo(this.value);">
						        	<option value="1">需要上传</option>
						       		<option value="0">无需上传</option>
								</select>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP主机IP：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_ip" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_ip"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">FTP上传文件路径：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_address" maxlength="20" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" name="ftp_address"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP端口号：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_port" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_port"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">FTP用户名：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_username" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_username"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP密码：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_password" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_password"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">上传至FTP的文件后缀：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="file_suffix" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="file_suffix"/>
						   </span>
						</td>
					
						<td align="right" bgcolor="#eeeeee">生成对账文件中数据来源：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="file_need_online_data" name="file_need_online_data" onchange="loadInstInfo(this.value);">
						        	<option value="1">线下数据</option>
						       		<option value="2">线下数据与线上数据</option>
						       		<option value="3">线上数据</option>
								</select>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">接口数据范围：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="generate_number" name="generate_number">
						        	<option value="1">全部</option>
						       		<option value="2">按商户号统计</option>
						       		<option value="3">按交易码统计</option>
								</select>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">是否按接口数据范围生成文件：</td>
						<td colspan="3">
						   <span class="input_bgl"> 
						        <select id="whether_create_file_by_range" name="whether_create_file_by_range">
						        	<option value="1">是</option>
						       		<option selected="selected" value="0">否</option>
								</select>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否按渠道生成文件：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="whether_create_file_by_inst" name="whether_create_file_by_inst" onchange="showInstInfoConfig(this);">
						        	<option value="1">是</option>
						        	<option selected="selected" value="0">否</option>
								</select>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">所选渠道配置：</td>
						<td colspan="3">
						   <input type="button" id="button_config" value="配置" disabled="disabled" onclick="configureInstInfo()" />
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addCustomObjectSub()" />
							<input type="button" class="icon_normal" value="上一步" onclick="javascript:hide('insert');" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id="insertSettle" class="pop" style="display: none">
		<div class="pop_body" >
			<h1 class="pop_tit">
				<span class="fl">系统接口信息添加</span> 
				<a class="close" href="javascript:hide('insertSettle');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="customObject">					
					<tr>
						<td align="right" bgcolor="#eeeeee">系统接口名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="object_name_settle" maxlength="20" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" name="object_name_settle" onblur="uniqueObjectName(this);"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee"></td>
						<td></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">结算单名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="settle_file_name" maxlength="20" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" name="settle_file_name"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">结算单路径：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="file_address_settle" maxlength="80" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" name="file_address_settle" onblur="uniqueFileAddress(this);"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否上传至FTP：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="whether_upload_settle" name="whether_upload_settle" onclick="changeFTPInfoOfSettle(this.value);">
						        	<option value="1">需要上传</option>
						       		<option value="0">无需上传</option>
								</select>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP主机IP：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_ip_settle" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_ip_settle"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">FTP上传文件路径：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_address_settle" maxlength="20" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" name="ftp_address_settle"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP端口号：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_port_settle" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_port_settle"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">FTP用户名：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_username_settle" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_username_settle"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP密码：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_password_settle" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_password_settle"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">上传至FTP的文件后缀：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="file_suffix_settle" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="file_suffix_settle"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">接口数据范围：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="generate_number_settle" name="generate_number_settle">
						        	<option value="1">全部</option>
						       		<option value="2">按商户号统计</option>
								</select>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否按接口数据范围生成文件：</td>
						<td colspan="3">
						   <span class="input_bgl"> 
						        <select id="whether_create_file_by_range_settle" name="whether_create_file_by_range_settle">
						        	<option value="1">是</option>
						       		<option selected="selected" value="0">否</option>
								</select>
						   </span>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addCustomObjectOfSettleSub()" />
							<input type="button" class="icon_normal" value="上一步" onclick="javascript:hide('insertSettle');" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<!--机构对账字段修改-->
	<div id="update" class="pop" style="display: none">
		<div class="pop_body" >
			<h1 class="pop_tit">
				<span class="fl">系统接口信息修改</span> 
				<a class="close" href="javascript:checkQuery();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td align="right" bgcolor="#eeeeee">系统接口名称：</td>
						<td>
						   <span class="input_bgl">
						   	   <input style="display: none" id="object_id_u" name="object_id_u" value=""/> 
						       <input type="text" id="object_name_u" name="object_name_u" />
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee"></td>
						<td></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">文件路径：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="file_address_u" name="file_address_u" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" onblur="uniqueFileAddress(this);" />
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">对账文件名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="dz_file_name_u" name="dz_file_name_u"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否生成差错文件：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="whether_create_error_file_u" name="whether_create_error_file_u"  onchange="showErrorFileNameText(this.value,2)">
						        	<option value="1">是</option>
						       		<option value="2">否</option>
								</select>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">差错文件名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="error_file_name_u" name="error_file_name_u"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否上传至FTP：</td>
						<td>
						   <span class="input_bgl"> 
						       <select id="whether_upload_u" name="whether_upload_u" onclick="changeFTPInfo(this.value);">
						       		<option value="0">无需上传</option>
						       		<option value="1">需要上传</option>
								</select>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP主机IP：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_ip_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_ip_u"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">FTP上传文件路径：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_address_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_address_u"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP端口号：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_port_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_port_u"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">FTP用户名：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_username_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_username_u"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP密码：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_password_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_password_u"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">上传至FTP的文件后缀：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="file_suffix_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="file_suffix_u"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">生成对账文件中是否需要线上数据：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="file_need_online_data_u" name="file_need_online_data_u">
						        	<option value="1">线下数据</option>
						       		<option value="2">线下数据与线上数据</option>
						       		<option value="3">线上数据</option>
								</select>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">接口数据范围：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="generate_number_u" name="generate_number_u">
						        	<option value="1">全部</option>
						       		<option value="2">按商户号统计</option>
						       		<option value="3">按交易码统计</option>
								</select>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">是否按接口数据范围生成文件：</td>
						<td colspan="3">
						   <span class="input_bgl"> 
						        <select id="whether_create_file_by_range_u" name="whether_create_file_by_range_u">
						        	<option value="1">是</option>
						       		<option value="0">否</option>
								</select>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否按渠道生成文件：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="whether_create_file_by_inst_u" name="whether_create_file_by_inst_u" onchange="showInstInfoConfig(this);">
						        	<option value="1">是</option>
						        	<option value="0">否</option>
								</select>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">所选渠道配置：</td>
						<td colspan="3">
						   <input type="button" id="button_config" value="配置" onclick="configureInstInfo()" />
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="updateData()" />
							<input type="button" class="icon_normal" value="取消" onclick="checkQuery()" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id="updateSettle" class="pop" style="display: none">
		<div class="pop_body" >
			<h1 class="pop_tit">
				<span class="fl">系统接口信息修改</span> 
				<a class="close" href="javascript:checkQuery();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td align="right" bgcolor="#eeeeee">系统接口名称：</td>
						<td>
						   <span class="input_bgl">
						   	   <input style="display: none" id="object_id_settle_u" name="object_id_settle_u" value=""/> 
						       <input type="text" id="object_name_settle_u" name="object_name_settle_u" />
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">结算单名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="settle_file_name_u" name="settle_file_name_u"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">结算单路径：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="file_address_settle_u" name="file_address_settle_u" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" onblur="uniqueFileAddress(this);"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否上传至FTP：</td>
						<td>
						   <span class="input_bgl"> 
						       <select id="whether_upload_settle_u" name="whether_upload_settle_u" onclick="changeFTPInfoOfSettle(this.value);">
						       		<option value="0">无需上传</option>
						       		<option value="1">需要上传</option>
								</select>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP主机IP：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_ip_settle_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_ip_settle_u"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">FTP上传文件路径：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_address_settle_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_address_settle_u"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP端口号：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_port_settle_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_port_settle_u"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">FTP用户名：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_username_settle_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_username_settle_u"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP密码：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_password_settle_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_password_settle_u"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">上传至FTP的文件后缀：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="file_suffix_settle_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="file_suffix_settle_u"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">接口数据范围：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="generate_number_settle_u" name="generate_number_settle_u">
						        	<option value="1">全部</option>
						       		<option value="2">按商户号统计</option>
								</select>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否按接口数据范围生成文件：</td>
						<td colspan="3">
						   <span class="input_bgl"> 
						        <select id="whether_create_file_by_range_settle_u" name="whether_create_file_by_range_settle_u">
						        	<option value="1">是</option>
						       		<option value="0">否</option>
								</select>
						   </span>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="updateSettleData()" />
							<input type="button" class="icon_normal" value="取消" onclick="checkQuery()" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<!-- 内部清算文件类型 -->
	<div id="insertInner" class="pop" style="display: none">
		<div class="pop_body" >
			<h1 class="pop_tit">
				<span class="fl">系统接口信息添加</span> 
				<a class="close" href="javascript:hide('insertInner');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="customObject">					
					<tr>
						<td align="right" bgcolor="#eeeeee">系统接口名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="object_name_inner" maxlength="20" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" name="object_name_inner" onblur="uniqueObjectName(this);"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee"></td>
						<td></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">文件名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="inner_file_name" maxlength="20" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" name="inner_file_name"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">文件路径：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="file_address_inner" maxlength="80" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" name="file_address_inner" onblur="uniqueFileAddress(this);"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否上传至FTP：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="whether_upload_inner" name="whether_upload_inner" onclick="changeFTPInfoOfInner(this.value);">
						        	<option value="1">需要上传</option>
						       		<option value="0">无需上传</option>
								</select>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP主机IP：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_ip_inner" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_ip_inner"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">FTP上传文件路径：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_address_inner" maxlength="20" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" name="ftp_address_inner"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP端口号：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_port_inner" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_port_inner"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">FTP用户名：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_username_inner" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_username_inner"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP密码：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="ftp_password_inner" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="ftp_password_inner"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">上传至FTP的文件后缀：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="file_suffix_inner" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="file_suffix_inner"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">接口数据范围：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="generate_number_inner" name="generate_number_inner">
						        	<option value="1">全部</option>
						       		<option value="2">按商户号统计</option>
								</select>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否按接口数据范围生成文件：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="whether_create_file_by_range_inner" name="whether_create_file_by_range_inner">
						        	<option value="0">否</option>
						        	<option value="1">是</option>
								</select>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">是否按渠道生成文件：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="whether_create_file_by_inst_inner" name="whether_create_file_by_inst_inner" onchange="showInstInfoConfig(this);">
						        	<option value="1">是</option>
						        	<option selected="selected" value="0">否</option>
								</select>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">所选渠道配置：</td>
						<td colspan="3">
						   <input type="button" id="button_config" value="配置" disabled="disabled" onclick="configureInstInfo()" />
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addCustomObjectOfInnerSub()" />
							<input type="button" class="icon_normal" value="上一步" onclick="javascript:hide('insertInner');" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<div id="updateInner" class="pop" style="display: none">
		<div class="pop_body" >
			<h1 class="pop_tit">
				<span class="fl">系统接口信息修改</span> 
				<a class="close" href="javascript:checkQuery();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="login">					
					<tr>
						<td align="right" bgcolor="#eeeeee">系统接口名称：</td>
						<td>
						   <span class="input_bgl">
						   	   <input style="display: none" id="inner_object_id_u" name="inner_object_id_u" value=""/> 
						       <input type="text" id="inner_object_name_u" name="inner_object_name_u" />
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee"></td>
						<td></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">文件路径：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="inner_file_address_u" name="inner_file_address_u" onkeyup="value=value.replace(/[\s.,，。?!@]/g,'')" onblur="uniqueFileAddress(this);" />
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">文件名称：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="inner_file_name_u" name="inner_file_name_u"/>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否上传至FTP：</td>
						<td>
						   <span class="input_bgl"> 
						       <select id="inner_whether_upload_u" name="inner_whether_upload_u" onclick="changeFTPInfoOfInner(this.value);">
						       		<option value="0">无需上传</option>
						       		<option value="1">需要上传</option>
								</select>
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP主机IP：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="inner_ftp_ip_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="inner_ftp_ip_u"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">FTP上传文件路径：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="inner_ftp_address_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="inner_ftp_address_u"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP端口号：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="inner_ftp_port_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="inner_ftp_port_u"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">FTP用户名：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="inner_ftp_username_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="inner_ftp_username_u"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">FTP密码：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="inner_ftp_password_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="inner_ftp_password_u"/>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">上传至FTP的文件后缀：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="inner_file_suffix_u" maxlength="20" onkeyup="value=value.replace(/[\s,，。?!@]/g,'')" name="inner_file_suffix_u"/>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">接口数据范围：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="inner_generate_number_u" name="inner_generate_number_u">
						        	<option value="1">全部</option>
						       		<option value="2">按商户号统计</option>
						       		<option value="3">按交易码统计</option>
								</select>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否按接口数据范围生成文件：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="inner_whether_create_file_by_range_u" name="inner_whether_create_file_by_range_u">
						        	<option value="1">是</option>
						       		<option value="0">否</option>
								</select>
						   </span>
						</td>
						<td align="right" bgcolor="#eeeeee">是否按渠道生成文件：</td>
						<td>
						   <span class="input_bgl"> 
						        <select id="inner_whether_create_file_by_inst_u" name="inner_whether_create_file_by_inst_u" onchange="showInstInfoConfig(this);">
						        	<option value="1">是</option>
						        	<option value="0">否</option>
								</select>
						   </span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">所选渠道配置：</td>
						<td colspan="3">
						   <input type="button" id="button_config" value="配置" onclick="configureInstInfo()" />
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="updateInnerData()" />
							<input type="button" class="icon_normal" value="取消" onclick="checkQuery()" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<!-- 隐藏域 -->
	<div>
		<input id="object_id_h" value="" type="hidden" />
		<input id="operate_type" value="1" type="hidden" />
	</div>
	
		<!--===========================文件中所需渠道配置============================-->
	<div id="inst_info_config" class="pop" style="display: none">
		<div class="pop_body" style="margin-top: 10px;">
			<h1 class="pop_tit">
				<span class="fl">配置渠道</span> 
				<a class="close" href="javascript:hide('inst_info_config');">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="login">  
					<tr>  
					   <td>
					   		<span>可选扣款渠道:</span><br/>
					   		<span>
					   			<input type="text" id="inst_name" name="inst_name" value=""/>
					   			<input type="button" id="inst_info_select" name="inst_info_select" value="查询" onclick='changeSelect($("#inst_name").val());'/>
					   		</span><br/>
						   	<select id="allColumn" name="allColumn" size="15" multiple style="width: 200px;	height:	300px">
						    </select>
					   </td>  
					   <td>
					    	<input type="button" name="select_bt" id="select_bt" style = "width :	80;" value="添加 " /><br/>
					    	<input type="button" name="delete_bt" id="delete_bt" style = "width :	80;" value="删除 " /><br/>
					    	<input type="button" name="selectAll_bt" id="selectAll_bt" style = "width :	80;" value="添加全部 " /><br/>
					    	<input type="button" name="deleteAll_bt" id="deleteAll_bt" style = "width :	80;" value="删除全部 " /><br/>
						</td>
						<td>
							<span>已选扣款渠道</span><br/>
							<select	id="selectedColumn" name="selectedColumn" size="3" style="width: 200px;	height:	300px" >
									<%-- <c:forEach items="${pageObjectRelevanceColumn.result }" var="object_relevance">
										<option value="${object_relevance.dz_column_id }">${object_relevance.attribute_name }</option>
									</c:forEach> --%>
							</select>
						</td>
						<td>
							<input type="button" name="moveUp" id="moveUp" value="上移" /><br/>
							<input type="button" name="moveDown" id="moveDown" value="下移" /><br/>
						</td>
					</tr>  
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="icon_normal" value="确定" onclick="hide('inst_info_config');" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
