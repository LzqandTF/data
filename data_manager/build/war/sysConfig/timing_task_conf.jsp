<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>定时任务配置查询</title>
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
				<form action="<%=request.getContextPath()%>/queryPageTimingTaskConf.do" target="right" name="timingTaskConfSearch" method="post">
				</form>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0">
					<thead>
						<tr>
							<td width="50px;" align="center">扣款渠道号</td>
							<td width="150px;" align="center">对账文件获取时间</td>
							<td width="150px;" align="center">汇总核心交易数据时间</td>
							<td width="150px;" align="center">对账处理时间</td>	
							<td width="150px;" align="center">对账文件获取定时时间任务名称</td>
							<td width="150px;" align="center">汇总核心交易数据定时任务名称</td>
							<td width="150px;" align="center">对账处理时间定时任务名称</td>				
							<td width="50px;" align="center" colspan="2">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(timingTaskConfPage.result)<=0 }">
						<tr align="center">
							<td colspan="6">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${timingTaskConfPage.result }" var="data">
						<tr>
							<td align="center">${data.instId }</td>
							<td align="center">${data.acquisitionTimeDesc }</td>
							<td align="center">${data.gatherDataTimeDesc }</td>
							<td align="center">${data.dzHandlerTimeDesc }</td>
							<td align="center">${data.acquisitionTimeName }</td>
							<td align="center">${data.gatherDataTimeName }</td>
							<td align="center">${data.dzHandlerTimeName }</td>
							<td align="center">
								<a href="javascript:deleteData(${data.instId })">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:selectData('${data.instId }','${data.acquisitionTimeDesc}','${data.gatherDataTimeDesc}','${data.dzHandlerTimeDesc }','${data.acquisitionTimeName }','${data.gatherDataTimeName }','${data.dzHandlerTimeName }')">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${timingTaskConfPage.totalPages != null}">
				<div class="next">
					<c:if test="${timingTaskConfPage.pageNo > 1}">
						<a href="javascript:paging(${timingTaskConfPage.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${timingTaskConfPage.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${timingTaskConfPage.pageNo-2 > 0}">
						<a href="javascript:paging(${timingTaskConfPage.pageNo-2 })"><span>${timingTaskConfPage.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${timingTaskConfPage.pageNo-1 > 0}">
						<a href="javascript:paging(${timingTaskConfPage.pageNo-1 })"><span>${timingTaskConfPage.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${timingTaskConfPage.pageNo }</span></a>
					<c:if test="${timingTaskConfPage.pageNo+1 <= timingTaskConfPage.totalPages}">
						<a href="javascript:paging(${timingTaskConfPage.pageNo+1 })"><span>${timingTaskConfPage.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${timingTaskConfPage.pageNo+2 <= timingTaskConfPage.totalPages}">
						<a href="javascript:paging(${timingTaskConfPage.pageNo+2 })"><span>${timingTaskConfPage.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${timingTaskConfPage.pageNo+3 <= timingTaskConfPage.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${timingTaskConfPage.pageNo < timingTaskConfPage.totalPages}">
						<a href="javascript:paging(${timingTaskConfPage.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${timingTaskConfPage.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${timingTaskConfPage.pageNo }" />页
					</span></b>
				</div>
			</c:if>
		</div>
	</div>
	<!--===========================弹出内容============================-->
	<!--机构对账字段修改-->
	<div id="update" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">定时任务配置信息修改</span> 
				<a class="close" href="javascript:hide('update')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<input type="hidden" id="instId_u" name="instId_u" value="" />
				<table width="100%" border="0" cellspacing="0" id="login">	
					<tr>
						<td align="right" bgcolor="#eeeeee">对账文件获取定时时间任务名称：</td>
						<td>
							<span class="input_bgl"> 
									<input type="text" id="acquisitionTimeName_u" name="acquisitionTimeName_u"  value="" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>				
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">对账文件获取时间：</td>
						<td>
							<span class="input_bgl"> 
								 <input type="text" id="acquisitionTime_u" name="acquisitionTime_u" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})"/>
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">汇总核心交易数据定时任务名称：</td>
						<td>
							<span class="input_bgl"> 
									<input type="text" id="gatherDataTimeName_u" name="gatherDataTimeName_u"  value="" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">汇总核心交易数据时间：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="gatherDataTime_u" name="gatherDataTime_u" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})"/>
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账处理时间定时任务名称：</td>
						<td>
							<span class="input_bgl"> 
									<input type="text" id="dzHandlerTimeName_u" name="dzHandlerTimeName_u"  value="" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账处理时间：</td>
						<td>
							<span class="input_bgl"> 
								<input type="text" id="dzHandlerTime_u" name="dzHandlerTime_u" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})"/>
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="updateData()" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
<div id="insert" class="pop" style="display: none">
		<div class="pop_body">
			<h1 class="pop_tit">
				<span class="fl">交易金额配置信息添加</span> 
				<a class="close" href="javascript:hide('insert')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0" id="timing_task_conf_info">	
					<tr>
						<td width="200px;" align="right" bgcolor="#eeeeee">扣款渠道号：</td>
						<td>
						   <span class="input_bgl"> 
						       <input type="text" id="instId" name="instId" />
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账文件获取定时时间任务名称：</td>
						<td>
							<span class="input_bgl"> 
									<input type="text" id="acquisitionTimeName" name="acquisitionTimeName"  value="" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td width="120" align="right" bgcolor="#eeeeee">对账文件获取时间：</td>
						<td>
						   <span class="input_bgl"> 
						   		<input maxlength="20" name="acquisitionTime" id="acquisitionTime" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})" />
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">汇总核心交易数据定时任务名称：</td>
						<td>
							<span class="input_bgl"> 
									<input type="text" id="gatherDataTimeName" name="gatherDataTimeName"  value="" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">汇总核心交易数据时间：</td>
						<td>
						   <span class="input_bgl">
								<input maxlength="20" name="gatherDataTime" id="gatherDataTime" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})" />
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账处理时间定时任务名称：</td>
						<td>
							<span class="input_bgl"> 
									<input type="text" id="dzHandlerTimeName" name="dzHandlerTimeName"  value="" /> 
							</span>
							<font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td align="right" bgcolor="#eeeeee">对账处理时间：</td>
						<td>
						   <span class="input_bgl"> 
						   		<input maxlength="20" name="dzHandlerTime" id="dzHandlerTime" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})" />
						   </span>
						   <font color='red' size="4" style="margin-left: 2px;float:left;">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="icon_normal" value="提交" onclick="addTimingTaskConfSub()" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function paging(pageNo) {
			var form = document.getElementsByName("timingTaskConfSearch")[0];
			with (form) {
				action = "<%=request.getContextPath()%>/queryPageTimingTaskConf.do?pageNum=" + pageNo;
				method = "post";
				form.submit();
			}
		}
		
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
		function addTradeAmountConf() {	
			$("#insert").css({display:"block"});
		}
		
		function addTimingTaskConfSub(){
			var instId = $("#instId").val();
			var acquisitionTime = $("#acquisitionTime").val();
			var gatherDataTime = $("#gatherDataTime").val();
			var dzHandlerTime = $("#dzHandlerTime").val();
			var dzHandlerTimeName = $("#dzHandlerTimeName").val();
			var acquisitionTimeName = $("#acquisitionTimeName").val();
			var gatherDataTimeName = $("#gatherDataTimeName").val();
			if(acquisitionTimeName == null || acquisitionTimeName == ""){
				alert("请输入对账文件获取定时时间任务名称");
				return;
			}
			if(acquisitionTime == null || acquisitionTime == ""){
				alert("请选择对账文件获取时间");
				return;
			}
			if(gatherDataTimeName == null || gatherDataTimeName == ""){
				alert("请输入汇总核心交易数据定时任务名称");
				return;
			}
			if(gatherDataTime == null || gatherDataTime == ""){
				alert("请选择汇总核心交易数据时间");
				return;
			}
			if(dzHandlerTimeName == null || dzHandlerTimeName == ""){
				alert("请输入对账处理时间定时任务名称");
				return;
			}
			if(dzHandlerTime == null || dzHandlerTime == ""){
				alert("请选择对账处理时间");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/addTimingTaskConf.do",
				data : "instId="+ instId +"&acquisitionTime=" + acquisitionTime + "&gatherDataTime=" + gatherDataTime+ "&dzHandlerTime=" + dzHandlerTime+"&acquisitionTimeName=" + acquisitionTimeName + "&gatherDataTimeName=" + gatherDataTimeName+ "&dzHandlerTimeName=" + dzHandlerTimeName,
				dataType : "text",
				success : function(msg) {
					if (msg == "1") {
						alert("添加成功！");
						paging($("#pageNo").val());
					}else {					
						alert("添加失败！");
						return;
					}
				}
			});
		}
		function selectData(instId,acquisitionTime,gatherDataTime,dzHandlerTime,acquisitionTimeName,gatherDataTimeName,dzHandlerTimeName){
			$("#instId_u").val(instId);
			$("#acquisitionTime_u").val(acquisitionTime);
			$("#gatherDataTime_u").val(gatherDataTime);
			$("#dzHandlerTime_u").val(dzHandlerTime);
			$("#acquisitionTimeName_u").val(acquisitionTimeName);
			$("#gatherDataTimeName_u").val(gatherDataTimeName);
			$("#dzHandlerTimeName_u").val(dzHandlerTimeName);
			$("#update").css({display:"block"});
		}
		
		function updateData(){
			var instId_u = $("#instId_u").val();
			var gatherDataTime_u = $("#gatherDataTime_u").val();
			var acquisitionTime_u = $("#acquisitionTime_u").val();
			var dzHandlerTime_u = $("#dzHandlerTime_u").val();
			var gatherDataTimeName_u = $("#gatherDataTimeName_u").val();
			var acquisitionTimeName_u = $("#acquisitionTimeName_u").val();
			var dzHandlerTimeName_u = $("#dzHandlerTimeName_u").val();
			if(acquisitionTimeName_u == null || acquisitionTimeName_u == ""){
				alert("请输入对账文件获取定时时间任务名称");
				return;
			}
			if(acquisitionTime_u == null || acquisitionTime_u == ""){
				alert("请选择对账文件获取时间");
				return;
			}
			if(gatherDataTimeName_u == null || gatherDataTimeName_u == ""){
				alert("请输入汇总核心交易数据定时任务名称");
				return;
			}
			if(gatherDataTime_u == null || gatherDataTime_u == ""){
				alert("请选择汇总核心交易数据时间");
				return;
			}
			if(dzHandlerTimeName_u == null || dzHandlerTimeName_u == ""){
				alert("请输入对账处理时间定时任务名称");
				return;
			}
			if(dzHandlerTime_u == null || dzHandlerTime_u == ""){
				alert("请选择对账处理时间");
				return;
			}
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/updateTimingTaskConf.do",
				data : "instId="+instId_u+"&acquisitionTime="+acquisitionTime_u+"&gatherDataTime="+gatherDataTime_u+"&dzHandlerTime="+dzHandlerTime_u,
				dataType : "text",
				success : function(msg) {
					if(Boolean(msg)){
						alert("修改成功");
						checkQuery();
					}else{
						alert("修改失败");
						hide("update");
					}
				}
			});
		}
		function deleteData(instId){
			if(confirm("确定要删除"+instId+"机构信息数据吗?")){
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
