<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>渠道费率配置</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
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
	function init(){
		$.ajax({
			url : '<%=request.getContextPath()%>/getOuterDzInfoListOfNotInConfig.do',
			type : 'post',
			async : false,
			success : function(msg) {
				for (i in msg)
					$("#inst_name").append('<option value="' + msg[i]['instId'] + ',' + msg[i]['inst_type'] + '">'+ msg[i]['name'] + '</option>');
			},
			error : function(msg) {
				alert("获取渠道列表失败!");
			}
		});
		$.ajax({
			url : '<%=request.getContextPath()%>/queryAllMccBigType.do',
			type : 'post',
			async : false,
			success : function(msg) {
				$("#mcc_da_lei").append('<option value="">--请选择--</option>');
				for (i in msg)
					$("#mcc_da_lei").append('<option value="' + msg[i]['big_type_id'] + '">'+ msg[i]['type_name'] + '</option>');
			},
			error : function(msg) {
				alert("获取MCC大类信息失败!");
			}
		});
		
		var inst_rate_type = $("#instRate_type_hidden").val();
		var instRateTypes = document.getElementById("instRate_type");
		for(var i = 0;i<instRateTypes.options.length;i++){
			if(instRateTypes.options[i].value == inst_rate_type){
				instRateTypes.options[i].selected = 'selected';
			}
		}
	}

	function paging(pageNo) {
		var form = document.getElementById("instRateConfigSearch");
		with (form) {
			action = "<%=request.getContextPath()%>/queryPageInstRate.do?pageNum=" + pageNo;
			method = "post";
			form.submit();
		}
	}
	
	function checkQuery(){	
		document.getElementById("instRateConfigSearch").submit();
	}
	
	
	/**
		关闭指定的弹出框
	*/
	function closeThisBox(obj){
		var o = document.getElementById(obj);
		var inputs= o.getElementsByTagName('input');
		for(var i = 0;i<inputs.length;i++){
			if($(inputs[i]).attr("type") == "text"){
				$(inputs[i]).val("");
			}
		}
		
		$("input[type='checkbox']:checkbox").each(function(){ 
               $(this).attr("checked",false);
           });
		
		o.style.display = "none";
	}
	
	/**
		根据渠道费率类型，隐藏弹出框
	*/
	function hide(obj,inst_rate_type) {
		if(inst_rate_type == "null" || inst_rate_type == null){
			inst_rate_type = $("#instRateType_h").val();
		}
		var o = document.getElementById(obj);
		var inputs= o.getElementsByTagName('input');
		for(var i = 0;i<inputs.length;i++){
			if($(inputs[i]).attr("type") == "text"){
				$(inputs[i]).val("");
			}
		} 
		
		$("input[type='checkbox']:checkbox").each(function(){ 
               $(this).attr("checked",false);
           });
		
		$("#inst_rate_type").val(inst_rate_type);
		
		changeContent(inst_rate_type);
		
		changeContentForUpdate(inst_rate_type);
		
		$("#inst_name").val("");
		
		o.style.display = "none";
	}
	/**
		根据渠道费率类型，隐藏弹出框
	*/
	function hideThisBox(obj,inst_rate_type) {
		if(inst_rate_type == "null" || inst_rate_type == null){
			inst_rate_type = $("#instRateType_h").val();
		}
		var o = document.getElementById(obj);
		var inputs= o.getElementsByTagName('input');
		for(var i = 0;i<inputs.length;i++){
			if($(inputs[i]).attr("type") == "text"){
				$(inputs[i]).val("");
			}
		} 
		
		$("input[type='checkbox']:checkbox").each(function(){ 
               $(this).attr("checked",false);
           });
		
		$("#inst_rate_type").val(inst_rate_type);
		
		o.style.display = "none";
	}
	
	
	/**
		添加方法操作中，改变费率类型，显示不同元素
	*/
	function changeContent(inst_rate_type){
		if(inst_rate_type == 1){//按MCC费率计算
			 $("#inst_rate_fix_tbody > tr").each(function () {
				 var num = $("#inst_rate_fix_tbody tr").length;
				 if(num > 0){
					 this.remove(); 
				 }
			 });
			 var tr = $("#mcc_type tr").eq(0).clone();   
			 tr.appendTo("#inst_rate_fix_tbody");
			 var tr1 = $("#mcc_type tr").eq(1).clone();   
			 tr1.appendTo("#inst_rate_fix_tbody");
			 var tr2 = $("#mccBigType_h tr").eq(0).clone();
			 tr2.appendTo("#inst_rate_fix_tbody");
			 
		}else if(inst_rate_type == 3){//按固定费率计算
			 $("#inst_rate_fix_tbody > tr").each(function () {
				 var num = $("#inst_rate_fix_tbody tr").length;
				 if(num > 0){
					 this.remove(); 
				 }
			 });
			 var tr = $("#fix_type_h tr").eq(0).clone();   
			 tr.appendTo("#inst_rate_fix_tbody");
			 var tr1 = $("#fix_type_h tr").eq(1).clone();   
			 tr1.appendTo("#inst_rate_fix_tbody");
		}else{//从银行对账单中获取
			 $("#inst_rate_fix_tbody > tr").each(function () {
				 var num = $("#inst_rate_fix_tbody tr").length;
				 if(num > 0){
					 this.remove(); 
				 }
			 });
		}
	}
	
	/**
		修改方法操作中，改变费率类型，显示不同元素
	*/
	function changeContentForUpdate(inst_rate_type){
		if(inst_rate_type == 1){
			if($("#tbu tr").length == 0){
				 $("#update_tbu > tr").each(function () {
					 var num = $("#update_tbu tr").length;
					 if(num > 0){
						 this.remove(); 
					 }
				 });
				 var tr = $("#mcc_type tr").eq(0).clone();  
				 tr.appendTo("#update_tbu");
				 var tr1 = $("#mccBigType_h tr").eq(0).clone();
				 tr1.appendTo("#update_tbu");
			}else{
				$("#tbu > tr").each(function() {
					var num = $("#tbu tr").length;
					if (num > 0) {
						this.remove();
					}
				});
				var tr = $("#mcc_type tr").eq(0).clone();
				tr.appendTo("#tbu");
				var tr1 = $("#mccBigType_h tr").eq(0).clone();
				tr1.appendTo("#tbu");
			}
		} else if(inst_rate_type == 3){
			$("#tbu > tr").each(function () {
				 var num = $("#tbu tr").length;
				 if(num > 0){
					 this.remove(); 
				 }
			 });
			var inst_id = $("#instId_h").val();
			var inst_type = $("#instType_h").val();
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/queryInstMerRateConfig.do",
				data : "inst_id="+ inst_id +"&inst_type=" + inst_type + "&inst_rate_type="+inst_rate_type,
				dataType : "text",
				success : function(msg) {
					if(msg == null || msg == ''){
						alert("加载信息失败");
						return;
					}else{
						$("#updateInstRate").empty();
						$("#updateInstRate").append(msg);
					}
				}
			});
		}else {
			if($("#tbu tr").length == 0){
				$("#update_tbu > tr").each(function() {
					var num = $("#update_tbu tr").length;
					if (num > 0) {
						this.remove();
					}
				});
			}else{
				$("#tbu > tr").each(function() {
					var num = $("#tbu tr").length;
					if (num > 0) {
						this.remove();
					}
				});
			}
		}
	}

	/**
		更新渠道ID的值
	 */
	function checkInstInfo(instInfo) {
		var inst_id = instInfo.split(",");
		$("#inst_id").val(inst_id[0]);
	}

	/**
		新增渠道费率配置，显示新增栏
	 */
	function addInstRateConfig() {
		$("#methodType").val("1");
		$("#instRateInfo").css({
			display : "block"
		});
	}

	/**
		新增渠道费率,组合信息，发送请求，将信息保存到数据库
	 */
	function addInstRateCommit() {
		var inst_name = $("#inst_name").find("option:selected").text();
		var inst_id = $("#inst_id").val();
		var instInfo = $("#inst_name").val();
		var inst_type = instInfo.split(",")[1];
		var whetherReturnFee = $("#whetherReturnFee").val();
		var inst_rate_type = $("#inst_rate_type").val();
		var bank_inst_code = $("#bank_inst_code").val();
		var inst_rate_mcc = $("#inst_rate_fix_tbody").find("#inst_rate_mcc").val();
		if (inst_rate_mcc != 1 && inst_rate_mcc != 2) {
			inst_rate_mcc = 0;
		}

		if (inst_id == null || inst_id == '' || inst_type == null
				|| inst_type == '') {
			alert("请选择扣款渠道");
			return;
		}

		if (bank_inst_code == null || bank_inst_code == '') {
			alert("请填写渠道机构代码");
			return;
		}

		var mcc_flag = true;
		var mccDaLeiArr = new Array();
		var dl_index = 0;
		$("#inst_rate_fix_tbody").find("#mcc_da_lei").each(function() {
			if ($(this).val == null || $(this).val() == "") {
				alert("请选择MCC大类");
				mcc_flag = false;
				return false;
			}
			mccDaLeiArr[dl_index] = $(this).val();
			dl_index++;
		});

		var mccXiLeiArr = new Array();
		var xl_index = 0;
		$("#inst_rate_fix_tbody").find("#mcc_xi_lei").each(function() {
			if ($(this).val == null || $(this).val() == "") {
				alert("请选择MCC细类");
				mcc_flag = false;
				return false;
			}
			mccXiLeiArr[xl_index] = $(this).val();
			xl_index++;
		});

		if (!mcc_flag) {
			return;
		}

		var deductMerCodeArr = new Array();
		var mcode_index = 0;
		$("#inst_rate_fix_tbody").find("#deductMerCode_h").each(function() {
			deductMerCodeArr[mcode_index] = $(this).val();
			mcode_index++;
		});

		var cardTypeArr = new Array();
		var cType_index = 0;
		$("#inst_rate_fix_tbody").find("#cardType_h").each(function() {
			cardTypeArr[cType_index] = $(this).val();
			cType_index++;
		});

		var bankTypeArr = new Array();
		var bType_index = 0;
		$("#inst_rate_fix_tbody").find("#bankType_h").each(function() {
			bankTypeArr[bType_index] = $(this).val();
			bType_index++;
		});

		var channelFeeArr_add = "";
		$("#inst_rate_fix_tbody").find("#channelFee_h").each(function() {
			if(channelFeeArr_add == "" || channelFeeArr_add == null){
				channelFeeArr_add =  $(this).val() ;
			}else{
				channelFeeArr_add = channelFeeArr_add + ';' + $(this).val();
			}
		});

		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/addInstRate.do",
				data : "inst_name="+ inst_name +"&inst_id=" + inst_id + "&inst_type=" + inst_type 
						+"&whetherReturnFee="+whetherReturnFee+"&inst_rate_type="+inst_rate_type
						+"&bank_inst_code="+bank_inst_code+"&inst_rate_mcc="+inst_rate_mcc
						+"&mccDaLeiArr="+mccDaLeiArr+"&mccXiLeiArr="+mccXiLeiArr+"&deductMerCodeArr="+deductMerCodeArr
						+"&cardTypeArr="+cardTypeArr+"&bankTypeArr="+bankTypeArr+"&channelFeeArr="+channelFeeArr_add,
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
					if (msg > 0) {
						alert("添加成功！");
						hide("instRateInfo",inst_rate_type);
						checkQuery();
					}else {					
						alert("添加失败！");
						return;
					}
				}
			});
		}
		
		/**
			删除渠道费率配置
		*/
		function deleteInstRate(inst_id,inst_type){
			if(confirm("确定要进行此操作吗?")){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/deleteInstRate.do",
					data : "inst_id="+ inst_id+"&inst_type="+inst_type,
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
						if(msg == "1"){
							alert("操作成功");
							checkQuery();
						}else{
							alert("操作失败");
						}
					}
				});
			}
		}
		
		/**
			修改渠道费率配置，根据渠道费率类型不同，显示不同信息
		*/
		function selectData(inst_name,inst_id,whetherReturnFee,inst_rate_type,inst_type,bank_inst_code){
			
			deductMerCodeArr_update = new Array();
			mcode_update_index = 0;
			cardTypeArr_update = new Array();
			cType_update_index = 0;
			bankTypeArr_update = new Array();
			bType_update_index = 0;
			channelFeeArr = "";
			operTypeArr_update = new Array();
			operType_index = 0;
			
			$("#methodType").val("2");//修改信息模式
			
			$("#instName").html(inst_name);
			$("#instId").html(inst_id);
			
			$("#instId_h").val(inst_id);
			$("#instType_h").val(inst_type);
			$("#instName_h").val(inst_name);
			$("#instRateType_h").val(inst_rate_type);
			$("#whetherReturnFee_h").val(whetherReturnFee);
			
			$("#instRateType").val(inst_rate_type);
			$("#whether_return_fee").val(whetherReturnFee);
			
			$("#bank_inst_code_update").val(bank_inst_code);
			
			$("#updateInstRate").empty();
			
			if(inst_rate_type != 2){
				$("#tbu > tr").each(function () {
					 var num = $("#tbu tr").length;
					 if(num > 0){
						 this.remove(); 
					 }
				 });
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/queryInstMerRateConfig.do",
					data : "inst_id="+ inst_id +"&inst_type=" + inst_type + "&inst_rate_type="+inst_rate_type,
					dataType : "text",
					success : function(msg) {
						if(msg == null || msg == ''){
							alert("加载信息失败");
							return;
						}else{
							$("#updateInstRate").append(msg);
						}
					}
				});
			}else{
				$("#tbu > tr").each(function () {
					 var num = $("#tbu tr").length;
					 if(num > 0){
						 this.remove(); 
					 }
				 });
			}
			
			$("#update_inst_rate").css({display:"block"});
		}
		
		/**
			针对商户号，新增新费率方法，将信息栏显示
		*/
		function addInstMerRateConfig(methodType){
			$("#methodType").val(methodType);
			$("#fix_type").css({display:"block"});
		}
		
		/**
			将商户号新增费率信息，暂时显示到页面上，不保存到数据库；新增和修改模式，保存到页面的body不一样
		*/
		function addInstMerRateConfigCommit(){
			
			var mer_code = $("#mer_code").val();
			if(mer_code == null || mer_code == ''){
				alert("请填写扣款商户号");
				return;
			}
			
			var card_type = '';
			var i = 0;
			$("input[name='card_type']:checkbox").each(function(){ 
                if($(this).attr("checked")){
                	card_type = $(this).val();
                	i++;
                }
            });
			
			if(i == 0){
				alert("请选择卡种");
				return;
			}else if(i == 2){
				card_type = 'ALL';
			}
			
			var lineOrinter = '';
			var j = 0;
			$("input[name='lineOrinter']:checkbox").each(function(){ 
                if($(this).attr("checked")){
                	lineOrinter = $(this).val();
                	j++;
                }
            });
			
			if(j == 0){
				alert("请选择按本行/跨行 ");
				return;
			}else if(j == 2){
				lineOrinter = '3';
			}
			
			var fee_Poundage = $("#fee_Poundage").val();
			if(fee_Poundage == null || fee_Poundage == ''){
				alert("请填写渠道费率公式");
				return;
			}
			
			var methodType = $("#methodType").val();//方法类型：1-添加渠道时，添加新费率   2-修改渠道时，添加新费率
			if(methodType == 1){
				$("#fix_type_value").find("#deductMerCode").html(mer_code);
				$("#fix_type_value").find("#deductMerCode_h").val(mer_code);
				
				if(card_type == 'C'){
					$("#fix_type_value").find("#cardType").html("信用卡");
				}else if(card_type == 'D'){
					$("#fix_type_value").find("#cardType").html("借记卡");
				}else if(card_type == 'ALL'){
					$("#fix_type_value").find("#cardType").html("信用卡、借记卡");
				}else{
					$("#fix_type_value").find("#cardType").html("");
				}
				
				$("#fix_type_value").find("#cardType_h").val(card_type);
				
				if(lineOrinter == 1){
					$("#fix_type_value").find("#bankType").html("本行");
				}else if(lineOrinter == 2){
					$("#fix_type_value").find("#bankType").html("跨行");
				}else if(lineOrinter == 3){
					$("#fix_type_value").find("#bankType").html("本行、跨行");
				}else{
					$("#fix_type_value").find("#bankType").html("");
				}
				$("#fix_type_value").find("#bankType_h").val(lineOrinter);
				
				$("#fix_type_value").find("#channelFee").html(fee_Poundage);
				$("#fix_type_value").find("#channelFee_h").val(fee_Poundage);
				
				 var tr = $("#fix_type_value tr").eq(0).clone();   
				 tr.appendTo("#inst_rate_fix_tbody");
				 closeThisBox("fix_type");
				
			}else if(methodType == 2){
				
				$("#fix_type_value").find("#deductMerCode").html(mer_code);
				$("#fix_type_value").find("#deductMerCode_h").val(mer_code);
				
				if(card_type == 'C'){
					$("#fix_type_value").find("#cardType").html("信用卡");
				}else if(card_type == 'D'){
					$("#fix_type_value").find("#cardType").html("借记卡");
				}else if(card_type == 'ALL'){
					$("#fix_type_value").find("#cardType").html("信用卡、借记卡");
				}else{
					$("#fix_type_value").find("#cardType").html("");
				}
				
				$("#fix_type_value").find("#cardType_h").val(card_type);
				
				if(lineOrinter == 1){
					$("#fix_type_value").find("#bankType").html("本行");
				}else if(lineOrinter == 2){
					$("#fix_type_value").find("#bankType").html("跨行");
				}else if(lineOrinter == 3){
					$("#fix_type_value").find("#bankType").html("本行、跨行");
				}else{
					$("#fix_type_value").find("#bankType").html("");
				}
				$("#fix_type_value").find("#bankType_h").val(lineOrinter);
				
				$("#fix_type_value").find("#channelFee").html(fee_Poundage);
				$("#fix_type_value").find("#channelFee_h").val(fee_Poundage);
				
				 var tr = $("#fix_type_value tr").eq(0).clone();   
				 if($("#tbu tr").length == 0){
					 tr.appendTo("#update_tbu");
				 }else{
					 tr.appendTo("#tbu");
				 }
				 closeThisBox("fix_type");
			}else{
				return false;
			}
		}
	
		
	
	
	var deductMerCodeArr_update = new Array();
	var mcode_update_index = 0;
	var cardTypeArr_update = new Array();
	var cType_update_index = 0;
	var bankTypeArr_update = new Array();
	var bType_update_index = 0;
	var cFee_update_index = 0;
	var operTypeArr_update = new Array();
	var operType_index = 0;
	var channelFeeArr = "";
		
	/**
		删除行数据操作方法
	*/
	function deleteThisData(obj,inst_rate_type){
		
		if(inst_rate_type == 3){
			var mcode = $(obj).parent().parent().find("#deductMerCode_h").val();
			deductMerCodeArr_update[mcode_update_index] = mcode;
			mcode_update_index++;
			var cardType = $(obj).parent().parent().find("#cardType_h").val();
			cardTypeArr_update[cType_update_index] = cardType;
			cType_update_index++;
			var bankType = $(obj).parent().parent().find("#bankType_h").val();
			bankTypeArr_update[bType_update_index] = bankType;
			bType_update_index++;
			var channelFee = $(obj).parent().parent().find("#channelFee_h").val();
			if(channelFeeArr == "" || channelFeeArr == null){
				channelFeeArr = channelFee;
			}else{
				channelFeeArr = channelFeeArr + ';' + channelFee;
			}
			$(obj).parent().parent().find("#operType").val("2");
			var operType = $(obj).parent().parent().find("#operType").val();
			operTypeArr_update[operType_index] = operType;
			operType_index++;
		}
		
		$(obj).parent().parent().remove();
	}
	
	/**
		添加例外  新增一条数据
	*/
	function addOne(obj){
		var tr = $("#mccBigType_h tr").eq(0).clone();
		tr.appendTo($(obj).parent().parent().parent());
	}
		
	/**
		新增渠道费率,组合信息，发送请求，将信息保存到数据库
	*/
	function updateInstRateCommit(){
		var inst_name = $("#instName_h").val();
		var inst_id = $("#instId_h").val();
		var inst_type =  $("#instType_h").val();
		var whetherReturnFee = $("#whether_return_fee").val();
		var inst_rate_type = $("#instRateType").val();
		var bank_inst_code = $("#bank_inst_code_update").val();
		
		var inst_rate_mcc = 0;
		if(inst_rate_type == 1){
			if($("#tbu tr").length == 0){
				inst_rate_mcc = $("#update_tbu").find("#inst_rate_mcc").val();
			}else{
				inst_rate_mcc = $("#tbu").find("#inst_rate_mcc").val();
			}
		}
		
		if(inst_id == null || inst_id == '' || inst_type == null || inst_type == ''){
			alert("请选择扣款渠道");
			return;
		}
		
		if(bank_inst_code == null || bank_inst_code == ''){
			alert("请填写渠道机构代码");
			return;
		}
		
		var mccDaLeiArr_update = new Array();
		var dl_update_index = 0;
		var mcc_flag = true;
		
		if($("#tbu tr").length == 0){
			$("#update_tbu").find("#mcc_da_lei").each(function(){
				if($(this).val == null || $(this).val() == ""){
					alert("请选择MCC大类");
					mcc_flag = false;
					return false;
				}
				mccDaLeiArr_update[dl_update_index] = $(this).val();
				dl_update_index++;
			});
		}else{
			$("#tbu").find("#mcc_da_lei").each(function(){
				if($(this).val == null || $(this).val() == ""){
					alert("请选择MCC大类");
					mcc_flag = false;
					return false;
				}
				mccDaLeiArr_update[dl_update_index] = $(this).val();
				dl_update_index++;
			});
		}
		var mccXiLeiArr_update = new Array();
		var xl_update_index = 0;
		if($("#tbu tr").length == 0){
			$("#update_tbu").find("#mcc_xi_lei").each(function(){
				if($(this).val == null || $(this).val() == ""){
					alert("请选择MCC细类");
					mcc_flag = false;
					return false;
				}
				mccXiLeiArr_update[xl_update_index] = $(this).val();
				xl_update_index++;
			});
		}else{
			$("#tbu").find("#mcc_xi_lei").each(function(){
				if($(this).val == null || $(this).val() == ""){
					alert("请选择MCC细类");
					mcc_flag = false;
					return false;
				}
				mccXiLeiArr_update[xl_update_index] = $(this).val();
				xl_update_index++;
			});
		}
		
		if(!mcc_flag){
			return;
		}
		
		if($("#tbu tr").length == 0){
			$("#update_tbu").find("#deductMerCode_h").each(function(){
				deductMerCodeArr_update[mcode_update_index] = $(this).val();
				mcode_update_index++;
			});
		}else{
			$("#tbu").find("#deductMerCode_h").each(function(){
				deductMerCodeArr_update[mcode_update_index] = $(this).val();
				mcode_update_index++;
			});
		}
		
		if($("#tbu tr").length == 0){
			$("#update_tbu").find("#cardType_h").each(function(){
				cardTypeArr_update[cType_update_index] = $(this).val();
				cType_update_index++;
			});
		}else{
			$("#tbu").find("#cardType_h").each(function(){
				cardTypeArr_update[cType_update_index] = $(this).val();
				cType_update_index++;
			});
		}
		
		if($("#tbu tr").length == 0){
			$("#update_tbu").find("#bankType_h").each(function(){
				bankTypeArr_update[bType_update_index] = $(this).val();
				bType_update_index++;
			});
		}else{
			$("#tbu").find("#bankType_h").each(function(){
				bankTypeArr_update[bType_update_index] = $(this).val();
				bType_update_index++;
			});
		}
		
		
		if($("#tbu tr").length == 0){
			$("#update_tbu").find("#channelFee_h").each(function() {
				if(channelFeeArr == "" || channelFeeArr == null){
					channelFeeArr = $(this).val();
				}else{
					channelFeeArr = channelFeeArr + ';' + $(this).val();
				}
			});
		}else{
			$("#tbu").find("#channelFee_h").each(function() {
				if(channelFeeArr == "" || channelFeeArr == null){
					channelFeeArr = $(this).val();
				}else{
					channelFeeArr = channelFeeArr + ';' + $(this).val();
				}
			});
		}
		
		if($("#tbu tr").length == 0){
			$("#update_tbu").find("#operType").each(function(){
				operTypeArr_update[operType_index] = $(this).val();
				operType_index++;
			});
		}else{
			$("#tbu").find("#operType").each(function(){
				operTypeArr_update[operType_index] = $(this).val();
				operType_index++;
			});
		}
		
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/updateInstRate.do",
			data : "inst_name="+ inst_name +"&inst_id=" + inst_id + "&inst_type=" + inst_type 
						+"&whetherReturnFee="+whetherReturnFee+"&inst_rate_type="+inst_rate_type+"&bank_inst_code="+bank_inst_code
						+"&inst_rate_mcc="+inst_rate_mcc+"&mccDaLeiArr="+mccDaLeiArr_update
						+"&mccXiLeiArr="+mccXiLeiArr_update+"&deductMerCodeArr="+deductMerCodeArr_update
						+"&cardTypeArr="+cardTypeArr_update+"&bankTypeArr="+bankTypeArr_update+"&channelFeeArr="+channelFeeArr+"&operTypeArr="+operTypeArr_update,
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
				if (msg >= 0) {
					alert("修改成功！");
					hide("instRateInfo",inst_rate_type);
					checkQuery();
				}else {					
					alert("修改失败！");
					return;
				}
			}
		});
	}
		
		/**
			查询手续费公式请求
		*/
		function queryFeePoundage(){
			var url ="<%=request.getContextPath()%>/queryFeePoundage.do";
			window.open(url,'XX',' left=0,top=0,width='+ (screen.availWidth - 10) +',height='+ (screen.availHeight-50) +',scrollbars,resizable=yes,toolbar=no');
		}
		
		
		/**
			通过MCC大类 加载 MCC细类方法
		*/
		function loadMccXiLeiData(obj){
			var big_type_id = $(obj).val();
			$(obj).parent().parent().find("#mcc_xi_lei").empty();
			$.ajax({
	    		url : '<%=request.getContextPath()%>/queryAllMccTypeByParentId.do',
	    		data : "big_type_id="+ big_type_id,
	    		type : 'post',
	    		async : false,
	    		success : function(msg) {
	    			if(msg.length != null && msg.length != ""){
	    				for (i in msg){
	 	    				$(obj).parent().parent().find("#mcc_xi_lei").append('<option value="' + msg[i]['id'] + '">'+ msg[i]['type_name'] + '</option>');
		    			}
	    			}
	    		}
	    	});
		}
		
	</script>
</head>

<body onload="init();">
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
				当前位置：<a href="javascript:void(0)">系统配置</a>&gt;<span>渠道费率配置</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryPageInstRate.do" target="right" name="instRateConfigSearch" id = "instRateConfigSearch" method="post">
					<ul class="check-m">						
						<li>
							<b>渠道名称</b>
							<span class="input_bgl">
								<input maxlength="8" type="text" name="inst_name_select" id = "inst_name_select" value="${param.inst_name_select }" />
							</span>
						</li>
						<li>
							<b>渠道费率类型</b>
							<span class="input_bgl">
								<select id="instRate_type" name="instRate_type">
									<option value="">全部</option>
									<option value="1">按MCC</option>
									<option value="2">银行对账单获取</option>
									<option value="3">按固定费率</option>
								</select>
								<input type="hidden" id="instRate_type_hidden" value="${inst_rate_type }" />
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery();"/>
							<input type="button" class="icon_normal" value="添加" onclick="addInstRateConfig();"/>
						</li>
					</ul>
				</form>
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</div>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td align="center" width="20%">渠道名称</td>
							<td align="center" width="10%">渠道号</td>
							<td align="center" width="20%">是否退还手续费</td>
							<td align="center" width="20%">渠道机构代码</td>
							<td align="center" width="20%">渠道费率类型</td>
							<td align="center" width="10%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageDataLst.result)<=0 }">
						<tr align="center">
							<td colspan="6">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageDataLst.result }" var="instRateConfig">
						<tr>
							<td align="center">${instRateConfig.inst_name }</td>
							<td align="center">${instRateConfig.inst_id }</td>
							<td align="center">
								<c:if test="${instRateConfig.whetherReturnFee == 0 }">不退还</c:if>
								<c:if test="${instRateConfig.whetherReturnFee == 1 }">退还</c:if>
							</td>
							<td align="center">${instRateConfig.bank_inst_code }</td>
							<td align="center">
								<c:if test="${instRateConfig.inst_rate_type == 1 }">按MCC</c:if>
								<c:if test="${instRateConfig.inst_rate_type == 2 }">银行对账单获取</c:if>
								<c:if test="${instRateConfig.inst_rate_type == 3 }">按固定费率</c:if>
							</td>
							<td align="center" width="60">
								<a href="javascript:selectData('${instRateConfig.inst_name }','${instRateConfig.inst_id }','${instRateConfig.whetherReturnFee }','${instRateConfig.inst_rate_type }','${instRateConfig.inst_type }','${instRateConfig.bank_inst_code }')">修改</a>
								<a href="javascript:deleteInstRate('${instRateConfig.inst_id }','${instRateConfig.inst_type }')">删除</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageDataLst.totalPages != null }">
				<div class="next">
					<c:if test="${pageDataLst.pageNo > 1}">
						<a href="javascript:paging(${pageDataLst.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageDataLst.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageDataLst.pageNo-2 > 0}">
						<a href="javascript:paging(${pageDataLst.pageNo-2 })"><span>${pageDataLst.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageDataLst.pageNo-1 > 0}">
						<a href="javascript:paging(${pageDataLst.pageNo-1 })"><span>${pageDataLst.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageDataLst.pageNo }</span></a>
					<c:if test="${pageDataLst.pageNo+1 <= pageDataLst.totalPages}">
						<a href="javascript:paging(${pageDataLst.pageNo+1 })"><span>${pageDataLst.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageDataLst.pageNo+2 <= pageDataLst.totalPages}">
						<a href="javascript:paging(${pageDataLst.pageNo+2 })"><span>${pageDataLst.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageDataLst.pageNo+3 <= pageDataLst.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageDataLst.pageNo < pageDataLst.totalPages}">
						<a href="javascript:paging(${pageDataLst.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageDataLst.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${pageDataLst.pageNo }" />页
							<input type="hidden" value="${pageDataLst.pageNo}" id="pageNo" />
					</span></b>
				</div>
			</c:if>
		</div>
	</div>
	
	<!--===========================弹出内容============================-->
	<div id="instRateInfo" class="pop" style="display: none;OVERFLOW: auto;">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">渠道费率配置</span> 
				<a class="close" href="javascript:hide('instRateInfo',2);">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0">					
					<tr>
						<td align="right" bgcolor="#eeeeee">渠道名称：</td>
						<td colspan="5">
							<span class="input_bgl" style="float: inherit;"> 
								<select id="inst_name" name="inst_name" onchange="checkInstInfo(this.value);">
									<option value="">--请选择--</option>
								</select> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">渠道ID：</td>
						<td colspan="5">
							<span class="input_bgl"> 
								<input type="text" id="inst_id" name="inst_id"  value="" readonly="readonly"/> 
							</span>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">渠道机构代码：</td>
						<td colspan="5">
							<span class="input_bgl"> 
								<input type="text" id="bank_inst_code" name="bank_inst_code" value="" onkeyup="value=value.replace(/[^\d,]/g,'')" maxlength="40" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;">*如有多个机构代码以英文逗号分隔</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">是否退还手续费：</td>
						<td colspan="5">
							<span class="input_bgl" style="float: inherit;"> 
								<select id="whetherReturnFee" name="whetherReturnFee">
									<option value="0">不退还</option>
									<option value="1">退还</option>
								</select> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">渠道费率类型：</td>
						<td colspan="5">
							<span class="input_bgl" style="float: inherit;"> 
								<select id="inst_rate_type" name="inst_rate_type" onchange="changeContent(this.value)">
									<option value="2">银行对账单获取</option>
									<option value="1">按MCC</option>
									<option value="3">按固定费率</option>
								</select> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;">*</font>
						</td>
					</tr>
					<!-- <tbody id="inst_rate_config_tbody">
						<tr>
							<td align="right" bgcolor="#eeeeee">渠道费率：</td>
							<td colspan="4">
								<span class="input_bgl" style="float: inherit;"> 
									<select id="inst_rate" name="inst_rate">
										<option value="1">标准MCC费率(发卡行+转接清算方)</option>
										<option value="2">标准MCC费率(全部)</option>
									</select> 
								</span>
								<font color='red' size="4" style="margin-left: 2px;">*</font>
							</td>
						</tr>
					</tbody> -->
					<tbody id="inst_rate_fix_tbody"></tbody>
					<tr>
						<td colspan="6" align="center">
							<input type="button" class="icon_normal" value="确认" onclick="addInstRateCommit()" />
							<input type="button" class="icon_normal" value="取消" onclick="javascript:hide('instRateInfo',2);" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id="update_inst_rate" class="pop" style="display: none;OVERFLOW: auto;">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">渠道费率配置</span> 
				<a class="close" href="javascript:hide('update_inst_rate',null);">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0">
					<tr>
						<td style="width: 20%;left: inherit;">渠道名称:</td>
						<td id="instName" style="width: 20%;left: inherit;"></td>
						<td style="width: 15%;left: inherit;" align="right">渠道ID:</td>
						<td id="instId" style="width: 15%;left: inherit;"></td>
						<td style="width: 15%;left: inherit;" align="right">是否退还手续费:</td>
						<td style="width: 15%;left: inherit;">
							<select id="whether_return_fee">
								<option value="0">不退还</option>
								<option value="1">退还</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="width: 20%;left: inherit;">渠道机构代码:</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="bank_inst_code_update" name="bank_inst_code_update" value="" onkeyup="value=value.replace(/[^\d,]/g,'')" maxlength="40" /> 
							</span>
						</td>
						<td>渠道费率类型:</td>
						<td colspan="3">
							<select id="instRateType" name="instRateType" onchange="changeContentForUpdate(this.value)">
									<option value="1">按MCC</option>
									<option value="2">银行对账单获取</option>
									<option value="3">按固定费率</option>
							</select> 
						</td>
					</tr>
					<tbody id="update_tbu">
					</tbody>
				</table>
				<input type="hidden" id="instId_h" value="" />
				<input type="hidden" id="instType_h" value="" />
				<input type="hidden" id="instName_h" value="" />
				<input type="hidden" id="whetherReturnFee_h" value="" />
				<input type="hidden" id="instRateType_h" value="" />
				<input type="hidden" id="methodType" value="" />
			</div>
			<div class="table_2" id="updateInstRate"></div>
			<div>
				<input type="button" style="margin-left: 40%" class="icon_normal" value="确认" onclick="updateInstRateCommit()" />
			</div>
		</div>
	</div>
	<div style="display: none">
		<table>
			<tbody id="mcc_type">
				<tr>
					<td>渠道费率：</td>
					<td colspan="5">
						<span class="input_bgl" style="float: inherit;"> 
							<select id="inst_rate_mcc" name="inst_rate_mcc">
								<option value="1">标准MCC费率(发卡行+转接清算方)</option>
								<option value="2">标准MCC费率(全部)</option>
							</select> 
						</span>
						<font color='red' size="4" style="margin-left: 2px;">*</font>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="mccBigType_hide" style="display:none;">
		<table>
			<tbody id="mccBigType_h">
				<tr>
					<td>添加例外</td>
					<td align="center" colspan="2">MCC大类
						<select id="mcc_da_lei" name="mcc_da_lei" onchange="loadMccXiLeiData(this);">
							<c:forEach items="${mccBigTypeList }" var="mccBigType">
								<option value="${mccBigType.big_type_id }">${mccBigType.type_name }</option>
							</c:forEach>
						</select>
					</td>
					<td align="center" colspan="2">MCC细类
						<select id="mcc_xi_lei" name="mcc_xi_lei">
							<option value="">--请选择--</option>
						</select>
					</td>
					<td align="center">
						<input type="button" onclick="javascript:addOne(this);" value=" + " />
						<input type="button" onclick="javascript:deleteThisData(this,1);" value=" - " />
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div style="display: none">
		<table>
			<tbody id="fix_type_h">
				<tr>
					<td colspan="6"><input type="button"  value="添加新费率" onclick="addInstMerRateConfig(1);"/></td> 
				</tr>
				<tr>
					<td align="center" bgcolor="#eeeeee">扣款商户号</td>
					<td align="center" bgcolor="#eeeeee">卡种</td>
					<td align="center" bgcolor="#eeeeee">本行/跨行</td>
					<td align="center" bgcolor="#eeeeee">渠道费率公式</td>
					<td align="center" colspan="2" bgcolor="#eeeeee">操作</td>
				</tr>
				
			</tbody>
		</table>
	</div>
	<div style="display: none">
		<table>
			<tbody id="fix_type_value">
				<tr>
					<td align="center" id="deductMerCode"></td>
					<td align="center" id="cardType"></td>
					<td align="center" id="bankType"></td>
					<td align="center" id="channelFee"></td>
					<td colspan="2" align="center"><input type="hidden" id="operType" value="1" /><input type="button" onclick="javascript:deleteThisData(this,3);" value="删除" /></td>
					<input type="hidden" id="deductMerCode_h" value="" />
					<input type="hidden" id="cardType_h" value="" />
					<input type="hidden" id="bankType_h" value="" />
					<input type="hidden" id="channelFee_h" value="" />
				</tr>
			</tbody>
		</table>
	</div>
	<div id="fix_type" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">添加新费率</span> 
				<a class="close" href="javascript:hideThisBox('fix_type',3);">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0">
					<tr>
						<td align="right" bgcolor="#eeeeee">扣款商户号：</td>
						<td>
							<span class="input_bgl" style="float: inherit;"> 
								<input id="mer_code" name="mer_code" value="" onkeyup="value=value.replace(/[^\d]/g,'')" type="text"/>
							</span>
							<font color='red' size="4" style="margin-left: 2px;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">按卡种：</td>
						<td>
							<span> 
								<input type="checkbox" id="card_type" name="card_type" value="C" />信用卡
							</span>
							<span> 
								<input type="checkbox" id="card_type" name="card_type" value="D" />借记卡
							</span>
							<font color='red' size="4" style="margin-left: 2px;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">按本行/跨行：</td>
						<td>
							<span> 
								<input type="checkbox" id="lineOrinter" name="lineOrinter" value="1" />本行
							</span>
							<span> 
								<input type="checkbox" id="lineOrinter" name="lineOrinter" value="2" />跨行
							</span>
							<font color='red' size="4" style="margin-left: 2px;">*若不区分本行与跨行，请全部勾选</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">渠道费率公式：</td>
						<td>
							<span class="input_bgl" style="float: inherit;"> 
								<input id="fee_Poundage" name="fee_Poundage" value="" type="text" />
							</span>
							<font color='red' size="4" style="margin-left: 2px;">*</font>
							<span>
								<a href="javascript:queryFeePoundage()" >查看公式</a>
							</span>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="icon_normal" value="确认" onclick="addInstMerRateConfigCommit()" />
							<input type="button" class="icon_normal" value="取消" onclick="hideThisBox('fix_type',3);" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
