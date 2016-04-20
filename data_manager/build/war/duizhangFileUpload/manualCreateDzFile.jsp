<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>手动导入对账文件</title>
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
		function manualCreateDzFile(){
			var object_id = $("#object_id").val();
			if(object_id == "" || object_id == null || object_id == 0){
				alert("请选择指定系统 ");
				return ;
			}
			var summaryDate = $("#summaryDate").val();
			if(summaryDate == "" || summaryDate==null){
				alert("请选择交易日期");
				return ;
			}
			var fileType = $("#fileType").val();
			if(fileType == "" || fileType == null){
				alert("请选择文件类型");
				return;
			}
			
			/* $("#creatFile").attr("disabled","disabled"); */
			
			var clickFlag = $("#clickFlag").val();
			if(clickFlag == "" || clickFlag == null){
				$("#clickFlag").val("true");
			}else{
				alert("不能重复点击");
				return ;
			}
			
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/createDzFile.do",
				data : "summaryDate="+summaryDate+"&fileType="+fileType+"&object_id="+object_id,
/* 				data : "inst_id="+ inst_name +"&summaryDate="+summaryDate, */
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
						alert("手动生成文件成功");
						$("#clickFlag").val("");
						/* $("#creatFile").attr("disabled",false); */
					}else{
						alert("手动生成文件失败");
						$("#clickFlag").val("");
						/* $("#creatFile").attr("disabled",false); */
					}
				}
			});
		}
		
		function changeFileTypeInfo(obj){
			$("#fileType").empty();
			$.ajax({
		      url : '<%=request.getContextPath()%>/queryCustomObjectById.do',
		      data : "object_id="+ obj,
		      type : 'post',
		      async : false,
		      success : function(msg) {
		    	  if(msg != null){
		    		  if(msg.file_type == 1){
		    			  if(msg.whether_create_error_file == 1){
		    				  $("#fileType").append('<option value="1">对账文件总表</option>');
		    				  $("#fileType").append('<option value="2">差错文件总表</option>');
		    			  }else{
		    				  $("#fileType").append('<option value="1">对账文件总表</option>');
		    			  }
		    		  }else if(msg.file_type == 3){
		    			  $("#fileType").append('<option value="3">内部清算文件</option>');
		    		  }
		    	  }
		      }
		    });
		}
		function init(){
			$.ajax({
		      url : '<%=request.getContextPath()%>/queryCustomObjectList.do',
		      type : 'post',
		      async : false,
		      success : function(msg) {
		       for (i in msg)
		        $("#object_id").append('<option value="' + msg[i]['object_id'] +'">'+ msg[i]['object_name'] + '</option>');
		      }
		    });
			/* var inst_id = $("#inst_id_hidden").val();
			var type = document.getElementById("inst_name");
			for(var i = 0;i<type.options.length;i++){
				if(type.options[i].value == inst_id){
					type.options[i].selected = 'selected';
				}
			} */
			/* var summaryDate = $("#summaryDate_hidden").val();
			if(summaryDate != null){
				$("#summaryDate").html(summaryDate);
			} */
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
				当前位置：<a href="javascript:void(0)">对账文件生成</a>&gt;<span>手动生成对账总表</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">手动生成对账文件</h1>
				<form action="#" target="right" name="manualForm" id = "manualForm" method="post">
					<ul class="check-m">						
						<li>
							<b>系统名称:</b>
							<span class="input_bgl">
								<select id="object_id" name="object_id" onchange="changeFileTypeInfo(this.value)">
									<option value="">--请选择--</option>
									<%-- <c:forEach items="${customObjectList }" var="customObject">
										<c:if test="${customObject.object_id == object_id}">
											<option selected="selected" value="${customObject.object_id }">${customObject.object_name }</option>
										</c:if>
										<c:if test="${customObject.object_id != object_id}">
											<option value="${customObject.object_id }">${customObject.object_name }</option>
										</c:if>
									</c:forEach> --%>
								</select> 
							</span>
							<font style="margin-left: 50px;" color="red">*</font>
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
							<b>文件类型</b>
							<span class="input_bgl" style="width: 100px;">
								<select name="fileType" id="fileType">
								</select>
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" id="creatFile" name="createFile" value="生成文件" onclick="manualCreateDzFile();"/>
						</li>
						<li>
							<span style="color:red">注意：1)在自动生成对账文件的时间点之后，执行手动生成对账文件总表或差错文件表时，文件不会自动传送给融易付。请手动下载后再做处理。<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2)生成文件类型为差错文件时，交易日期即审批日期。
							</span>
						</li>
						<input type="hidden" id="clickFlag" name="clickFlag" value="" />
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
