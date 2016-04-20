<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>手动导入对账单</title>
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
		function init(){
			// 初始化银行机构选择下拉框
		 	$.ajax({
		      url : '<%=request.getContextPath()%>/getOutErrorDzInstInfo.do',
		      type : 'post',
		      async : false,
		      success : function(msg) {
		       for (i in msg)
		    	   $("#inst_name_error").append('<option value="' + msg[i]['instId'] + "," + msg[i]['inst_type'] + '">'+ msg[i]['name'] + '</option>');
		      }
		    });
			
			var inst_id_e = $("#inst_id_hidden_e").val();
			var type_e = document.getElementById("inst_name_error");
			
			for(var i = 0;i<type_e.options.length;i++){
				if(type_e.options[i].value == inst_id_e){
					type_e.options[i].selected = 'selected';
				}
			}
			var summaryDate_e = $("#summaryErrorDate_hidden").val();
			if(summaryDate_e != null){
				$("#summaryErrorDate").html(summaryDate_e);
			}
			var return_msg_e = $("#return_msg_e").val();
			var file_msg_e = $("#file_msg_e").val();
			if(file_msg_e != ""){
				alert("请选择正确的差错对账文件格式");
			}
			if(return_msg_e != ""){
				if(return_msg_e == "true"){
					alert("手动导入差错对账文件成功");
				}else{
					alert("手动导入差错对账文件失败");
				}
			}
		}
		
	/* 差错对账处理 */
		
		function manualUploadErrorDz(){
			var inst_name_error = $("#inst_name_error").val();
			if(inst_name_error == "" || inst_name_error == null){
				alert("请选择差错扣款渠道");
				return ;
			}
			var summaryErrorDate = $("#summaryErrorDate").val();
			if(summaryErrorDate == "" || summaryErrorDate==null){
				alert("请选择差错交易日期");
				return ;
			}
			var  fileName_e = $("#file_error").val();
			if(fileName_e == "" || fileName_e == null){
				alert("请选择要上传的差错对账文件");
				return ;
			}
			
			var form = document.getElementById("manualSummaryErrorForm");
			form.action = "<%=request.getContextPath()%>/manualUploadErrorDzData.do";
			form.submit();
		}
		function manualParsingErrorDz(){
			var inst_name_error = $("#inst_name_error").val();
			if(inst_name_error == "" || inst_name_error == null){
				alert("请选择差错扣款渠道");
				return ;
			}
			var summaryErrorDate = $("#summaryErrorDate").val();
			if(summaryErrorDate == "" || summaryErrorDate==null){
				alert("请选择差错交易日期");
				return ;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/manualParsingErrorDz.do",
				data : "inst_id="+ inst_name_error +"&summaryDate="+summaryErrorDate,
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
					if(msg == '1'){
						alert("手动解析差错对账文件成功");
					}else{
						alert("手动解析差错对账文件失败");
					}
				}
			});
		}
		function manualErrorDz(){
			var inst_name_error = $("#inst_name_error").val();
			if(inst_name_error == "" || inst_name_error == null){
				alert("请选择差错扣款渠道");
				return ;
			}
			var summaryErrorDate = $("#summaryErrorDate").val();
			if(summaryErrorDate == "" || summaryErrorDate==null){
				alert("请选择差错交易日期");
				return ;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/manualErrorDz.do",
				data : "inst_id="+ inst_name_error +"&summaryDate="+summaryErrorDate,
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
						alert("手动差错对账成功");
					}else{
						alert("手动差错对账失败");
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
				当前位置：<a href="javascript:void(0)">对账单查询</a>&gt;<span>手动导入对账单</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">手动对账</h1>
				<form action="<%=request.getContextPath()%>/manualUploadErrorDzData.do" target="right" name="manualSummaryErrorForm" id = "manualSummaryErrorForm" method="post" enctype="multipart/form-data">
					<ul class="check-m">						
						<li>
							<b>扣款渠道</b>
							<span class="input_bgl" style="width: 60px;">
								<select id="inst_name_error" name="inst_name_error">
									<option value="">--请选择--</option>
								</select>
								<input type="hidden" id="inst_id_hidden_e" value="${inst_id_e }"/>
							</span>
							<font style="margin-left: 35px;" color="red">*</font>
						</li>
						<li>
							<b>差错交易日期</b>
							<span class="input_bgl" style="width: 120px;">
								<input maxlength="20" name="summaryErrorDate" id="summaryErrorDate" value="${param.summaryErrorDate }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
								<input type="hidden" id="summaryErrorDate_hidden" value="${summaryErrorDate }" />
							</span>
							<font style="margin-left: 30px;" color="red">*</font>
						</li>
						<li>
							<b>上传差错对账文件</b>
							<span class="input_bgl" style="width: 80px;">
								<input type="file" value="" id="file_error" name="file_error" />
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="上传差错对账文件" onclick="manualUploadErrorDz();"/>
							<input type="hidden" value="${return_msg_e }" id="return_msg_e" />
							<input type="hidden" value="${file_msg_e }" id="file_msg_e" />
							<input type="button" class="icon_normal" value="解析差错对账文件" onclick="manualParsingErrorDz();"/>
							<input type="button" class="icon_normal" value="确认差错对账" onclick="manualErrorDz();"/>
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
