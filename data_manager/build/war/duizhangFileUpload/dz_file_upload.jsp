<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>对账文件生成</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript">
	function paging(pageNo) {
		var form = document.getElementById("uploadForm");
		with (form) {
			action = "<%=request.getContextPath()%>/queryPageDzFileInfo.do?pageNum=" + pageNo;
			method = "post";
			form.submit();
		}
	}
	function checkQuery(){
		document.getElementById("uploadForm").submit();
	}

	function clearForm(){
		$("#deduct_sys_date").val("");
	}
	function uploadTxt(deduct_sys_date,file_type,id){
		var deduct_time = deduct_sys_date;
		var fileType = file_type;
		var file_id = id;
		var form = document.getElementById("uploadForm");
		with (form) {
			action = "<%=request.getContextPath()%>/uploadDzFile_txt.do?deduct_sys_date="+deduct_time+"&fileType="+fileType+"&file_id="+file_id;
			method = "post";
			form.submit();
			action = "<%=request.getContextPath()%>/queryPageDzFileInfo.do";
		}
	}   
	function uploadXls(deduct_sys_date,file_type,id){
		var deduct_time = deduct_sys_date;
		var fileType = file_type;
		var file_id = id;
		var form = document.getElementById("uploadForm");
		with (form) {
			action = "<%=request.getContextPath()%>/uploadDzFile_xls.do?deduct_sys_date="+deduct_time+"&fileType="+fileType+"&file_id="+file_id;
			method = "post";
			form.submit();
			action = "<%=request.getContextPath()%>/queryPageDzFileInfo.do";
		}
	} 
	function init(){
		var object_id = $("#object_id_hidden_select").val();
		var type = document.getElementById("object_id");
		
		for(var i = 0;i<type.options.length;i++){
			if(type.options[i].value == object_id){
				type.options[i].selected = 'selected';
			}
		}
	}
</script>
</head>

<body onload="init();">
	<div class="content">
		<div class="right" style="margin: 25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">对账文件生成</a>&gt;<span>对账文件下载</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryPageDzFileInfo.do" target="right" name="uploadForm" id = "uploadForm" method="post">
					<ul class="check-m">
						<li>
							<b style="margin-left: 0px;">清算时间</b>
							<span class="input_bgl" style="width: 1%;" >
								<input maxlength="20" name="deduct_sys_date" id="deduct_sys_date" value="${param.deduct_sys_date }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
							</span>
						</li>
						<li>
							<b>系统名称:</b>
							<span class="input_bgl">
								<select id="object_id" name="object_id">
									<option value="0">全部</option>
									<c:forEach items="${customObjectList }" var="customObject">
										<option value="${customObject.object_id }">${customObject.object_name }</option>
									</c:forEach>
								</select> 
							</span>
						</li>						
						<li class="cb mt0">
							<input type="hidden" class="icon_normal" id="object_id_hidden_select" name="object_id_hidden_select" value="${object_id }" />
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery()"/>
							<input type="button" class="icon_normal" value="重置" onclick="clearForm()"/>
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
							<td align="center">序号</td>
							<td align="center">清算日期</td>
							<td align="center">对账文件类型</td>			
							<td align="center">对账文件名称</td>
							<td align="center">最终生成时间</td>
							<td align="center">系统ID</td>
							<td align="center">系统名称</td>
							<td align="center">下载</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageDzFile.result)<=0 }">
						<tr align="center">
							<td colspan="6">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageDzFile.result }" var="data">
						<tr>
							<td align="center">${data.id }</td>
							<td align="center" name="date1">${data.deduct_sys_date }</td>
							<td align="center">${data.file_type }</td>
							<td align="center">${data.file_name}</td>
							<td align="center">${data.create_last_time}</td>
							<td align="center">${data.object_id}</td>
							<td align="center">${data.object_name}</td>
							<td align="center">
								<a href="javascript:uploadTxt('${data.deduct_sys_date }','${data.file_type }','${data.id }')">下载txt文件</a> 
								<a href="javascript:uploadXls('${data.deduct_sys_date }','${data.file_type }','${data.id }')">下载xls文件</a> 
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageDzFile.totalPages != null}">
				<div class="next">
					<c:if test="${pageDzFile.pageNo > 1}">
						<a href="javascript:paging(${pageDzFile.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageDzFile.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageDzFile.pageNo-2 > 0}">
						<a href="javascript:paging(${pageDzFile.pageNo-2 })"><span>${pageDzFile.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageCupsData.pageNo-1 > 0}">
						<a href="javascript:paging(${pageDzFile.pageNo-1 })"><span>${pageDzFile.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageDzFile.pageNo }</span></a>
					<c:if test="${pageDzFile.pageNo+1 <= pageDzFile.totalPages}">
						<a href="javascript:paging(${pageDzFile.pageNo+1 })"><span>${pageDzFile.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageDzFile.pageNo+2 <= pageDzFile.totalPages}">
						<a href="javascript:paging(${pageDzFile.pageNo+2 })"><span>${pageDzFile.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageDzFile.pageNo+3 <= pageDzFile.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageDzFile.pageNo < pageDzFile.totalPages}">
						<a href="javascript:paging(${pageDzFile.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageDzFile.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${pageDzFile.pageNo }" />页
					</span></b>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>