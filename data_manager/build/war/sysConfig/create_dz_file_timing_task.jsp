<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>对账文件生成时间(定时任务)</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	function saveData(){
		var hour = $("#hour").val();
		var minute = $("#minute").val();
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/addCreateDzFileTimingTask.do",
			data : "hour="+ hour +"&minute=" + minute,
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
</script>
<style type="text/css">
.content{
	margin-left: 50px;
	margin-top: 0px;
}
</style>
</head>
<body>
<jsp:include page="timing_task_title.jsp" ></jsp:include>
	<div class="content">
		<div>
			<form target="right" id="timingTaskConfOfCreate" name="timingTaskConfOfCreate" method="post">
				<span>每日</span>
				<input type="text" size="5" id="hour" name="hour" value="${hour }"  onkeyup="value=value.replace(/[^\d]/g,'')" />时
				<input type="text" size="5" id="minute" name="minute" value="${minute }"  onkeyup="value=value.replace(/[^\d]/g,'')" />分 生成对账文件
				</br>
				<div>
					<input type="button" class="icon_normal" value="保存" onclick="saveData()" />
				</div>
			</form>
		</div>
	</div>
</body>

</html>
