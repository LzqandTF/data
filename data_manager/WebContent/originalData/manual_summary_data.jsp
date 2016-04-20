<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>手动汇总数据</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
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
		function manualSummary(){
			var bank_id = $("#bank_id").val();
			var inst_name = $("#inst_name").val();
			var summaryDate = $("#summaryDate").val();
			if (bank_id == null || bank_id == "") {
				alert("请选择银行机构！");
				return;
			}
			if(summaryDate == "" || summaryDate==null){
				alert("请选择交易日期");
				return ;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/manualSummaryData.do",
				data : "inst_name="+ inst_name +"&summaryDate="+summaryDate+"&bank_id="+bank_id,
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
						alert("手动汇总成功");
					}else{
						alert("手动汇总失败");
					}
				}
			});
		}
		
		// 初始化银行机构选择下拉框
		function initBankInst() {
			$.ajax({
	    		url : '<%=request.getContextPath()%>/queryAllBankInst.do',
	    		type : 'post',
	    		async : false,
	    		success : function(msg) {
	    			for (i in msg)
	    				$("#bank_id").append('<option value="' + msg[i]['bank_id'] + ',' + msg[i]['bank_type'] +'">'+ msg[i]['bank_name'] + '</option>');
	    		}
	    	});
			
			var bankId = $("#bankId_hidden").val();
			var bank_id = document.getElementById("bank_id");
			for (var i = 0; i < bank_id.length; i++) {
				if (bank_id.options[i].value == bankId) {
					bank_id.options[i].selected = 'selected';
				}
			}
			
			if (bankId != "") {
				getInstInfoByBankId(bankId);
			}
			
			var inst_id = $("#inst_id_hidden").val();
			var type = document.getElementById("inst_name");
			
			for(var i = 0;i<type.options.length;i++){
				if(type.options[i].value == inst_id){
					type.options[i].selected = 'selected';
				}
			}
		}
		
		// 根据银行机构获取渠道信息
		function getInstInfoByBankId(bankInst) {
			if (bankInst == null || bankInst == "") {
				alert("请选择银行机构！");
				return;
			}
			$.ajax({
	    		url : '<%=request.getContextPath()%>/getInstInfoByBankId.do',
	    		type : 'post',
	    		data : 'bankInst='+bankInst,
	    		async : false,
	    		dataType : 'text',
	    		success : function(json) {
	    			var data = eval("("+json+")");
	    			var selectObj = document.getElementById("inst_name");
					while(selectObj.firstChild) {
				        selectObj.removeChild(selectObj.firstChild);
					}
					if (data.length == 0 || data.length > 1) {
						$(selectObj).append("<option value=''>全部</option>");
					}
					for(var i=0;i<data.length;i++){
						$(selectObj).append("<option value="+data[i].instId + ',' + data[i].inst_type +">"+data[i].name+"</option>");
					}
	    		}
	    	});
		}
	</script>
</head>

<body onload="initBankInst();">
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
				当前位置：<a href="javascript:void(0)">交易数据查询</a>&gt;<span>手动汇总数据</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/manualSummaryData.do" target="right" name="manualSummaryForm" id = "manualSummaryForm" method="post">
					<ul class="check-m">
						<li>
							<b>银行机构</b>
							<span class="input_bgl" style="width: 60px;">
								<select id="bank_id" name="bank_id" onchange="getInstInfoByBankId(this.value)">
									<option value="">--请选择银行机构--</option>
								</select>
								<input type="hidden" id="bankId_hidden" value="${bankId }"/>
							</span>
							<font style="margin-left: 45px;" color="red">*</font>
						</li>					
						<li>
							<b>扣款渠道</b>
							<span class="input_bgl" style="width: 60px;">
								<select id="inst_name" name="inst_name" style="width: 150px;">
									<option value="0">全部</option>
								</select>
								<input type="hidden" id="inst_id_hidden" value="${inst_id }"/>
							</span>
						</li>
						<li>
							<b>交易日期</b>
							<span class="input_bgl" style="width: 60px;">
								<input maxlength="20" name="summaryDate" id="summaryDate" value="${param.summaryDate }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-#/{%d-1}'})" />
							</span>
							<font style="margin-left: 90px;" color="red">*</font>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="手动获取" onclick="manualSummary();"/>
						</li>
					</ul>
				</form>
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</div>
		</div>
	</div>

</body>
</html>
