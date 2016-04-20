<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>对账时间(定时任务)</title>
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
				<form action="<%=request.getContextPath()%>/dzTimingTask.do" target="right" name="timingTaskConfSearch" method="post">
				</form>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td width="20%;" align="center">扣款渠道</td>
							<td width="30%;" align="center">自动对账时间</td>
							<td width="30%;" align="center">终止对账时间</td>
							<td width="20%;" align="center" colspan="2">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(dzTimingTasklist)<=0 }">
						<tr align="center">
							<td colspan="6">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${dzTimingTasklist }" var="data">
						<c:if test="${data.dzHandlerTimeName != null }" >
							<tr>
								<td align="center">${data.dzHandlerTimeDesc }</td>
								<td align="center">${fn:substring(data.dzHandlerTime,6,8) }时${fn:substring(data.dzHandlerTime,3,5) }分</td>
								<td align="center">${fn:substring(data.dzHandlerEndTime,6,8) }时${fn:substring(data.dzHandlerEndTime,3,5) }分</td>
								<td align="center">
									<a href="javascript:deleteData(${data.instId },'${data.dzHandlerTimeDesc }')">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:selectData('${data.instId }','${data.dzHandlerTimeDesc }','${data.dzHandlerTime}','${data.dzHandlerEndTime}','${data.dzHandlerTimeName }','${data.channel_id }','${data.inst_type }')">修改</a>
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
		<input type="button" class="icon_normal" value="添加" onclick="addDzTimingTask();" disabled="disabled"/>
	</div> -->
	
	<!--===========================弹出内容============================-->
	<!--机构对账字段修改-->
	<div id="update" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">执行对账操作定时任务配置修改</span> 
				<a class="close" href="javascript:checkQuery();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<input type="hidden" id="instId_u" name="instId_u" value="" />
				<input type="hidden" id="channel_id_u" name="channel_id_u" value="" />
				<input type="hidden" id="inst_type_u" name="inst_type_u" value="" />
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
						<td align="right" bgcolor="#eeeeee">对账时间：</td>
						<td>
							<span>每日</span>
							<input type="text" id="startHour" name="startHour"  value=""   onkeyup="value=value.replace(/[^\d]/g,'')" />时
							<input type="text" id="startMinute" name="startMinute"  value=""   onkeyup="value=value.replace(/[^\d]/g,'')" />分
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">终止对账时间：</td>
						<td>
					   		<span>每日</span>
								<input type="text" id="endHour" name="endHour"  value=""   onkeyup="value=value.replace(/[^\d]/g,'')" />时
								<input type="text" id="endMinute" name="endMinute"  value=""   onkeyup="value=value.replace(/[^\d]/g,'')" />分
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="updateData()" />
							<input type="button" class="icon_normal" value="取消" onclick="javascript:checkQuery();" />
						</td>
					</tr>
					<input type="hidden" id="dzHandlerTimeName" name="dzHandlerTimeName" value="" />
				</table>
			</div>
		</div>
	</div>
<div id="insert" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">执行对账操作定时任务配置添加</span> 
				<a class="close" href="javascript:checkQuery();">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="timing_task_conf_info">	
					<tr>
						<td width="200px;" align="right" bgcolor="#eeeeee">扣款渠道：</td>
						<td>
						      <select id="inst_info" name="inst_info" >
									<c:forEach items="${instInfolist }" var="instInfo">
										<option value="${instInfo.instId },${instInfo.inst_type}">${instInfo.name }</option>
									</c:forEach>
								</select> 
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账时间：</td>
						<td>
							<span>每日</span>
							<input type="text" id="start_hour" name="start_hour"  value=""   onkeyup="value=value.replace(/[^\d]/g,'')" />时
							<input type="text" id="start_minute" name="start_minute"  value=""   onkeyup="value=value.replace(/[^\d]/g,'')" />分
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">终止对账时间：</td>
						<td>
					   		<span>每日</span>
								<input type="text" id="end_hour" name="end_hour"  value=""   onkeyup="value=value.replace(/[^\d]/g,'')" />时
								<input type="text" id="end_minute" name="end_minute"  value=""   onkeyup="value=value.replace(/[^\d]/g,'')" />分
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addDzTimingTaskSub()" />
							<input type="button" class="icon_normal" value="取消" onclick="javascript:checkQuery();" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function checkQuery(){		
			var id1 = $("#id").val(); 
			var reg = /^\d{1,}$/;		
			if(id1 != null && id1.length != 0){
				if(!reg.test(id1)){
					alert("操作员号必须为数字！");
					return;
				}
			}
			document.timingTaskConfSearch.submit();
		}
		function addDzTimingTask() {	
			$("#insert").css({display:"block"});
		}
		
		function addDzTimingTaskSub(){
			var inst_info = $("#inst_info").val();
			var start_hour = $("#start_hour").val();
			var start_minute = $("#start_minute").val();
			var end_hour = $("#end_hour").val();
			var end_minute = $("#end_minute").val();
			if(start_hour == null || start_hour == ""){
				alert("请输入对账时间");
				return;
			}
			if(start_minute == null || start_minute == ""){
				alert("请输入对账时间");
				return;
			}
			if(end_hour == null || end_hour == ""){
				alert("请输入终止对账时间");
				return;
			}
			if(end_minute == null || end_minute == ""){
				alert("请输入终止对账时间");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addDzTimingTask.do",
				data : "instInfo="+ inst_info +"&start_hour=" + start_hour + "&start_minute=" + start_minute+ "&end_hour=" + end_hour+"&end_minute=" + end_minute,
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
		function selectData(instId,instInfoName,dzHandlerTime,dzHandlerEndTime,dzHandlerTimeName,channel_id,inst_type){
			$("#instId_u").val(instId);
			$("#name_u").val(instInfoName);
			var startTimeArr = new Array();
			startTimeArr = dzHandlerTime.substring(3,8).split(" ");
			$("#startHour").val(startTimeArr[1]);
			$("#startMinute").val(startTimeArr[0]);
			var endTimeArr = new Array();
			endTimeArr = dzHandlerEndTime.substring(3,8).split(" ");
			$("#endHour").val(endTimeArr[1]);
			$("#endMinute").val(endTimeArr[0]);
			$("#dzHandlerTimeName").val(dzHandlerTimeName);
			$("#channel_id_u").val(channel_id);
			$("#inst_type_u").val(inst_type);
			$("#update").css({display:"block"});
		}
		
		function updateData(){
			var instId = $("#instId_u").val();
			var name_u = $("#name_u").val();
			var startHour = $("#startHour").val();
			var startMinute = $("#startMinute").val();
			var endHour = $("#endHour").val();
			var endMinute = $("#endMinute").val();
			var dzHandlerTimeName = $("#dzHandlerTimeName").val();
			var channel_id = $("#channel_id_u").val();
			var inst_type = $("#inst_type_u").val();
			if(startHour == null || startHour == ""){
				alert("请输入对账时间");
				return;
			}
			if(startMinute == null || startMinute == ""){
				alert("请输入对账时间");
				return;
			}
			if(endHour == null || endHour == ""){
				alert("请输入终止对账时间");
				return;
			}
			if(endMinute == null || endMinute == ""){
				alert("请输入终止对账时间");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateDzTimingTask.do",
				data : "instId="+instId+"&dzHandlerTimeDesc="+name_u+"&startHour="+startHour+"&startMinute="+startMinute+"&endHour="+endHour+"&endMinute="+endMinute+"&dzHandlerTimeName="+dzHandlerTimeName+"&channel_id="+channel_id+"&inst_type="+inst_type,
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
