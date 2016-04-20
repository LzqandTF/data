<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>手动导入品牌服务费文件</title>
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
			$.ajax({
	    		url : '<%=request.getContextPath()%>/queryParsingPpfwInstList.do',
	    		type : 'post',
	    		async : false,
	    		success : function(msg) {
	    			if(msg.length != null && msg.length != ""){
	    				$("#inst_id").empty();
	    				for (i in msg){
	 	    				$("#inst_id").append('<option value="' + msg[i]['instId'] + '">'+ msg[i]['name'] + '</option>');
		    			}
	    			}
	    		}
	    	});
			
			var inst_id = $("#inst_id_hidden").val();
			var inst_info = document.getElementById("inst_id");
			
			for(var i = 0;i<inst_info.options.length;i++){
				if(inst_info.options[i].value == inst_id){
					inst_info.options[i].selected = 'selected';
				}
			}
			
			var return_msg = $("#return_msg").val();
			var file_msg = $("#file_msg").val();
			if(file_msg != ""){
				alert("请选择正确的品牌服务费文件格式");
			}
			if(return_msg != ""){
				if(return_msg == "true"){
					alert("手动导入品牌服务费文件成功");
				}else{
					alert("手动导入品牌服务费文件失败");
				}
			}
		}

		function manualUpload(){
			var inst_id = $("#inst_id").val();
			if(inst_id == "" || inst_id == null){
				alert("请选择扣款渠道");
				return ;
			}
			var  fileName = $("#file").val();
			if(fileName == "" || fileName == null){
				alert("请选择要上传的品牌服务费文件");
				return ;
			}
			
			var form = document.getElementById("ppfwForm");
			form.action = "<%=request.getContextPath()%>/manualUploadPpfwData.do";
			document.getElementById("loading-mask").style.display="";
	        document.getElementById("loading").style.display="";
			form.submit();
			document.getElementById("loading-mask").style.display="none";
	        document.getElementById("loading").style.display="none";
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
				当前位置：<a href="javascript:void(0)">对账单查询</a>&gt;<span>手动导入品牌服务费</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">手动对账</h1>
				<form action="<%=request.getContextPath()%>/manualUploadPpfwData.do" target="right" name="ppfwForm" id = "ppfwForm" method="post" enctype="multipart/form-data">
					<ul class="check-m">						
						<li>
							<b>扣款渠道</b>
							<span class="input_bgl" style="width: 60px;">
								<select id="inst_id" name="inst_id">
									<option value="">--请选择--</option>
								</select>
								<input type="hidden" id="inst_id_hidden" value="${inst_id }"/>
							</span>
							<font style="margin-left: 45px;" color="red">*</font>
						</li>
						<li>
							<b>交易日期</b>
							<span class="input_bgl" style="width: 100px;">
								<input maxlength="20" name="summaryDate" id="summaryDate" value="${param.summaryDate }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
								<input type="hidden" id="summaryDate_hidden" value="${summaryDate }" />
							</span>
							<font style="margin-left: 50px;" color="red">*</font>
						</li>
						<li>
							<b>上传品牌服务费文件</b>
							<span class="input_bgl" style="width: 80px;">
								<input type="file" value="" id="file" name="file" />
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="确定" onclick="manualUpload();"/>
							<input type="hidden" value="${return_msg }" id="return_msg" />
							<input type="hidden" value="${file_msg }" id="file_msg" />
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
