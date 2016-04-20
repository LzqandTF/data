<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>获取对账文件获取时间(定时任务)</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>

</head>

<body>
<jsp:include page="timing_task_title.jsp" ></jsp:include>
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<form action="<%=request.getContextPath()%>/dzDataTime.do" target="right" name="timingTaskConfSearch" method="post">
			</form>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td width="10%;" align="center">扣款渠道</td>
							<td width="20%;" align="center">对账文件类型</td>
							<td width="20%;" align="center">获取时间</td>
							<td width="20%;" align="center">时间间隔(分)</td>
							<td width="10%;" align="center">最多获取次数</td>
							<td width="20%;" align="center" colspan="2">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(dzDataTimingTaskList)<=0 }">
						<tr align="center">
							<td colspan="6">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${dzDataTimingTaskList }" var="data">
					<c:if test="${data.acquisitionTimeName != null }" >
						<tr>
							<td align="center">${data.acquisitionTimeDesc }</td>
							<td align="center">${data.dzFileType }</td>
							<td align="center">${fn:substring(data.acquisitionTime,6,8) }时${fn:substring(data.acquisitionTime,3,5) }分</td>
							<td align="center">${data.acquisitionIntervalTime }</td>
							<td align="center">${data.acquisitionMostTimes }</td>
							<td align="center">
								<a href="javascript:deleteData(${data.instId },'${data.acquisitionTimeDesc }')">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:selectData('${data.instId }','${data.acquisitionTimeDesc }','${data.acquisitionTime}','${data.dzFileType}','${data.acquisitionIntervalTime }','${data.acquisitionMostTimes }','${data.acquisitionTimeName }')">修改</a>
							</td>
						</tr>
					</c:if>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
		</div>
	</div>
	<!-- <div align="center">
		<input type="button" class="icon_normal" value="添加" onclick="addDzDataTimingTask();" disabled="disabled"/>
	</div> -->
	
	<!--===========================弹出内容============================-->
	<!--对账文件获取时间配置修改-->
	<div id="update" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">对账文件获取时间配置修改</span> 
				<a class="close" href="javascript:checkQuery();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<input type="hidden" id="instId_u" name="instId_u" value="" />
				<table width="100%" border="0" cellspacing="0" id="login">	
					<tr>
						<td width="120" align="center" bgcolor="#eeeeee">扣款渠道：</td>
						<td>
							<span class="input_bgl"> 
						       <input type="text" id="name_u" name="name_u" readonly="readonly"/>
						   </span>
							<font color='red' size="4" style="margin-left: 2px;float:inherit;">*</font>
						</td>
					</tr>	
					<tr>
						<td width="200px;" align="right" bgcolor="#eeeeee">文件类型：</td>
						<td>
						      <select id="fileType" name="fileType" >
									<option value="一般对账文件">一般对账文件</option>
									<option value="差错文件">差错文件</option>
								</select> 
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>			
					<tr>
						<td align="right" bgcolor="#eeeeee">获取时间：</td>
						<td>
							<span>每日</span>
							<input type="text" id="startHour" name="startHour"  value=""   onkeyup="value=value.replace(/[^\d]/g,'')" />时
							<input type="text" id="startMinute" name="startMinute"  value=""   onkeyup="value=value.replace(/[^\d]/g,'')" />分
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">间隔时间：</td>
						<td>
					   		<span>获取失败后，每隔</span>
								<input type="text" id="intervalTime" name="intervalTime"  value=""   onkeyup="value=value.replace(/[^\d]/g,'')" />分钟获取一次，最多获取
								<input type="text" id="mostTimes" name="mostTimes"  value=""   onkeyup="value=value.replace(/[^\d]/g,'')" />次
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="updateData()" />
						</td>
					</tr>
					<input type="hidden" id="acquisitionTimeName" name="acquisitionTimeName" value="" />
				</table>
			</div>
		</div>
	</div>
<div id="insert" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">对账文件获取时间配置添加</span> 
				<a class="close" href="javascript:checkQuery();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="timing_task_conf_info">	
					<tr>
						<td width="200px;" align="right" bgcolor="#eeeeee">扣款渠道：</td>
						<td>
						      <select id="inst_info" name="inst_info" >
									<c:forEach items="${instInfolist }" var="instInfo">
										<option value="${instInfo.instId },${instInfo.inst_type }">${instInfo.name }</option>
									</c:forEach>
								</select> 
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td width="200px;" align="right" bgcolor="#eeeeee">文件类型：</td>
						<td>
						      <select id="file_type" name="file_type" >
									<option value="一般对账文件">一般对账文件</option>
									<option value="差错文件">差错文件</option>
								</select> 
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">获取时间：</td>
						<td>
							<span>每日</span>
							<input type="text" id="start_hour" name="start_hour"  value=""   onkeyup="value=value.replace(/[^\d]/g,'')" />时
							<input type="text" id="start_minute" name="start_minute"  value=""   onkeyup="value=value.replace(/[^\d]/g,'')" />分
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">间隔时间：</td>
						<td>
					   		<span>获取失败后，每隔</span>
								<input type="text" id="interval_time" name="interval_time"  value=""   onkeyup="value=value.replace(/[^\d]/g,'')" />分钟获取一次，最多获取
								<input type="text" id="most_times" name="most_times"  value=""   onkeyup="value=value.replace(/[^\d]/g,'')" />次
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addDzDataTimingTaskSub()" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function checkQuery(){		
			document.timingTaskConfSearch.submit();
		}
		function addDzDataTimingTask() {	
			$("#insert").css({display:"block"});
		}
		
		function addDzDataTimingTaskSub(){
			var inst_info = $("#inst_info").val();
			var start_hour = $("#start_hour").val();
			var start_minute = $("#start_minute").val();
			var interval_time = $("#interval_time").val();
			var most_times = $("#most_times").val();
			var file_type = $("#file_type").val();
			if(start_hour == null || start_hour == ""){
				alert("请输入对账时间");
				return;
			}
			if(start_minute == null || start_minute == ""){
				alert("请输入对账时间");
				return;
			}
			if(interval_time == null || interval_time == ""){
				alert("请输入间隔时间");
				return;
			}
			if(most_times == null || most_times == ""){
				alert("请输入最多获取次数");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addDzDataTimingTask.do",
				data : "inst_info="+ inst_info +"&start_hour=" + start_hour + "&start_minute=" + start_minute+ "&acquisitionIntervalTime=" + interval_time+"&acquisitionMostTimes=" + most_times+"&dzFileType="+file_type,
				dataType : "text",
				success : function(msg) {
					if (msg == "1") {
						alert("添加成功！");
						checkQuery();
					}else {					
						alert("添加失败！");
						hide("insert");
						return;
					}
				}
			});
		}
		function selectData(instId,instInfoName,acquisitionTime,dzFileType,acquisitionIntervalTime,acquisitionMostTimes,acquisitionTimeName){
			$("#instId_u").val(instId);
			$("#name_u").val(instInfoName);
			var startTimeArr = new Array();
			startTimeArr = acquisitionTime.substring(3,8).split(" ");
			$("#startHour").val(startTimeArr[1]);
			$("#startMinute").val(startTimeArr[0]);
			$("#intervalTime").val(acquisitionIntervalTime);
			$("#mostTimes").val(acquisitionMostTimes);
			$("#acquisitionTimeName").val(acquisitionTimeName);
			
			var type = document.getElementById("fileType");
			for(var i = 0;i<type.options.length;i++){
				if(type.options[i].value == dzFileType){
					type.options[i].selected = 'selected';
				}
			}
			$("#update").css({display:"block"});
		}
		
		function updateData(){
			var instId = $("#instId_u").val();
			var name_u = $("#name_u").val();
			var startHour = $("#startHour").val();
			var startMinute = $("#startMinute").val();
			var intervalTime = $("#intervalTime").val();
			var mostTimes = $("#mostTimes").val();
			var fileType = $("#fileType").val();
			var acquisitionTimeName = $("#acquisitionTimeName").val();
			if(startHour == null || startHour == ""){
				alert("请输入对账时间");
				return;
			}
			if(startMinute == null || startMinute == ""){
				alert("请输入对账时间");
				return;
			}
			if(interval_time == null || interval_time == ""){
				alert("请输入间隔时间");
				return;
			}
			if(most_times == null || most_times == ""){
				alert("请输入最多获取次数");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateDzDataTimingTask.do",
				data : "instId="+instId+"&acquisitionTimeDesc="+ name_u +"&start_hour=" + startHour + "&start_minute=" + startMinute+ "&acquisitionIntervalTime=" + intervalTime+"&acquisitionMostTimes=" + mostTimes+"&dzFileType="+fileType+"&acquisitionTimeName="+acquisitionTimeName,
				dataType : "text",
				success : function(msg) {
					if(msg == "1"){
						alert("修改成功");
						checkQuery();
					}else{
						alert("修改失败");
						hide("update");
						return;
					}
				}
			});
		}
		function deleteData(instId,name){
			if(confirm("确定要删除"+name+"机构信息数据吗?")){
				$.ajax({
					type : "post",
					url : "<%=request.getContextPath()%>/delTimingTaskConf.do",
					data : "instId="+ instId,
					dataType : "text",
					success : function(msg) {
						if(Boolean(msg)){
							alert("删除成功");
							checkQuery();
						}else{
							alert("删除失败");
						}
					}
				});
			}
		}
		
		function hide(obj) {
			var o = document.getElementById(obj);
			o.style.display = "none";
		}
		
	</script>
</body>
</html>
