<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>FTP文件上传管理</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/wbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
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
		function paging(pageNo) {
			var form = document.getElementById("ftpuploadrecordSearch");
			var pageSize = $("#pageSize").val();

			with (form) {
				action = "<%=request.getContextPath()%>/queryPageFtpUploadRecord.do?pageNum=" + pageNo + "&pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		}
		
		function checkQuery(){	
			document.getElementById("ftpuploadrecordSearch").submit();
		}
		
		function resetValue(){
			$("#deduct_stlm_date").val("");
			$("#object_id option[value='0']").attr("selected","selected");
			$("#upload_status option[value='0']").attr("selected","selected");
		}
		
		
		function queryByPageSize(pageSize) {
			var form = document.getElementById("ftpuploadrecordSearch");
			with (form) {
				action = "<%=request.getContextPath()%>/queryPageFtpUploadRecord.do?pageSize=" + pageSize;
				method = "post";
				form.submit();
			}
		}
		
		
		//设置每页显示条数
		function EnterPress(e){ //传入 event
			var e = e || window.event;
			if(e.keyCode == 13){
				var pageSize = $("#pageSize").val();
				if (pageSize >= 10 && pageSize <= 200) {
					queryByPageSize(pageSize);
				}else {
					queryByPageSize(10);
				}
			}
		} 
		
		function uploadDataToFtp(object_id,date,upload_content){
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/uploadDataToFtp.do",
				data : "object_id="+ object_id +"&date="+date+"&upload_content="+upload_content,
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
						alert("上传成功");
						checkQuery();
					}else{
						alert("上传失败");
						checkQuery();
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
				当前位置：<a href="javascript:void(0)">FTP文件上传管理</a>&gt;<span>FTP文件上传管理</span>
			</div>
			<div class="check clearfix">
				<h1 class="tit">查询区</h1>
				<form action="<%=request.getContextPath()%>/queryPageFtpUploadRecord.do" target="right" name="ftpuploadrecordSearch" id = "ftpuploadrecordSearch" method="post">
					<ul class="check-m">						
						<li>
							<b>清算日期</b>
							<span class="input_bgl">
								<input name="deduct_stlm_date" id="deduct_stlm_date" value="${deduct_stlm_date}"
											maxlength="10" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" />
							</span>
						</li>
						<li>
							<b>系统接口名称：</b>
							<span class="input_bgl">
								<select id="object_id" name="object_id">
									<option value="0">全部</option>
									<c:forEach items="${customObjectList }" var="customObject">
										<c:if test="${customObject.object_id == object_id}">
											<option selected="selected" value="${customObject.object_id }">${customObject.object_name }</option>
										</c:if>
										<c:if test="${customObject.object_id != object_id}">
											<option value="${customObject.object_id }">${customObject.object_name }</option>
										</c:if>
									</c:forEach>
								</select> 
							</span>
						</li>
						<li>
							<b>上传状态：</b>
							<span class="input_bgl">
								<select name="upload_status" id = "upload_status">
									<c:if test="${upload_status == 1}">
										<option value="1">成功</option>
										<option value="2">失败</option>
										<option value="0">全部</option>
									</c:if>
									<c:if test="${upload_status == 2}">
										<option value="2">失败</option>
										<option value="1">成功</option>
										<option value="0">全部</option>
									</c:if>
									<c:if test="${empty upload_status}">
										<option value="0">全部</option>
										<option value="1">成功</option>
										<option value="2">失败</option>
									</c:if>
									<c:if test="${upload_status == 0}">
										<option value="0">全部</option>
										<option value="1">成功</option>
										<option value="2">失败</option>
									</c:if>
								</select>
							</span>
						</li>
						<li class="cb mt0">
							<input type="button" class="icon_normal" value="查询" onclick="checkQuery();"/>
							<input type="button" class="icon_normal" value="重置" onclick="resetValue();"/>
						</li>
					</ul>
				</form>
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</div>
			
			
			<div style="font-size: 12px;">
			<span>
			本页共
			<font color="red">
				<c:if test="${fn:length(pageFtpUploadRecord.result) <= 0 }">0</c:if>
				<c:if test="${fn:length(pageFtpUploadRecord.result) > 0 }">${fn:length(pageFtpUploadRecord.result) }</c:if>
			</font>
			条数据
			</span>
			<span style="float: right">共<font color="red">
				<c:if test="${pageFtpUploadRecord.totalItems == null }">0</c:if>
				<c:if test="${pageFtpUploadRecord.totalItems != null }">${pageFtpUploadRecord.totalItems }</c:if>
			</font>条数据
			<font color="red">
				<c:if test="${pageFtpUploadRecord.totalPages == null}">0</c:if>
				<c:if test="${pageFtpUploadRecord.totalPages != null}">${pageFtpUploadRecord.totalPages}</c:if>
			</font>页
			&nbsp;&nbsp;&nbsp;&nbsp;
			<span>
				每页显示
				<c:if test="${not empty pageSize}">
					<input id="pageSize" name="pageSize" style="width: 40px;color: red;text-align: center;" value="${pageSize}" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="EnterPress(event)" />
				</c:if>
				<c:if test="${empty pageSize}">
					<input id="pageSize" name="pageSize" style="width: 40px;color: red;text-align: center;" value="10" onkeyup="this.value=this.value.replace(/\D/g,'')" onkeydown="EnterPress(event)" />
				</c:if>
				条
			</span>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</span>
		</div>
			<div class="table-m">
				<table width="100%" border="0" cellspacing="0" style="table-layout:fixed;">
					<thead>
						<tr>
							<td align="center" width="10%">清算日期</td>
							<td align="center" width="20%">系统接口名称</td>
							<td align="center" width="20%">上传文件内容</td>
							<td align="center" width="20%">文件生成时间</td>
							<td align="center" width="20%">上传状态</td>
							<td align="center" width="8%">操作</td>
						</tr>
					</thead>
					<c:if test="${fn:length(pageFtpUploadRecord.result)<=0 }">
						<tr align="center">
							<td colspan="11">对不起,暂无数据！</td>
						</tr>
					</c:if>
					<c:forEach items="${pageFtpUploadRecord.result }" var="ftpUploadRecord">
						<tr id="${ftpUploadRecord.id}">
							<td align="center" style="word-wrap:break-word;">${ftpUploadRecord.deduct_stlm_date }</td>
							<td align="center" style="display: none;">${ftpUploadRecord.object_id }</td>
							<td align="center" style="word-wrap:break-word;">${ftpUploadRecord.object_name }</td>
							<td align="center" style="word-wrap:break-word;">
								<c:set value="${ fn:split(ftpUploadRecord.upload_content, ';') }" var="upload_content" />
								<c:forEach items="${upload_content}" var="uploadContent">
									${uploadContent}<br />
								</c:forEach>
							</td>
							<td align="center" style="word-wrap:break-word;">
								<f:formatDate value="${ftpUploadRecord.generate_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td align="center" style="word-wrap:break-word;">
								<c:if test="${ftpUploadRecord.upload_status == 0}">
									未知
								</c:if>
								<c:if test="${ftpUploadRecord.upload_status == 1}">
									成功
								</c:if>
								<c:if test="${ftpUploadRecord.upload_status == 2}">
									失败
								</c:if>
							</td>
							<td align="center" width="60">
								<a style="color: red" href="javascript:uploadDataToFtp('${ftpUploadRecord.object_id }','${ftpUploadRecord.deduct_stlm_date }','${ftpUploadRecord.upload_content}')">手动上传</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<c:if test="${pageFtpUploadRecord.totalPages != null}">
				<div class="next">
					<c:if test="${pageFtpUploadRecord.pageNo > 1}">
						<a href="javascript:paging(${pageFtpUploadRecord.pageNo-1 })"><span>上一页</span></a>
					</c:if>
					<c:if test="${pageFtpUploadRecord.pageNo-3 > 0}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageFtpUploadRecord.pageNo-2 > 0}">
						<a href="javascript:paging(${pageFtpUploadRecord.pageNo-2 })"><span>${pageFtpUploadRecord.pageNo-2 }</span></a>
					</c:if>
					<c:if test="${pageFtpUploadRecord.pageNo-1 > 0}">
						<a href="javascript:paging(${pageFtpUploadRecord.pageNo-1 })"><span>${pageFtpUploadRecord.pageNo-1 }</span></a>
					</c:if>
					<a href="#" class="hover"><span>${pageFtpUploadRecord.pageNo }</span></a>
					<c:if test="${pageFtpUploadRecord.pageNo+1 <= pageFtpUploadRecord.totalPages}">
						<a href="javascript:paging(${pageFtpUploadRecord.pageNo+1 })"><span>${pageFtpUploadRecord.pageNo+1 }</span></a>
					</c:if>
					<c:if test="${pageFtpUploadRecord.pageNo+2 <= pageFtpUploadRecord.totalPages}">
						<a href="javascript:paging(${pageFtpUploadRecord.pageNo+2 })"><span>${pageFtpUploadRecord.pageNo+2 }</span></a>
					</c:if>
					<c:if test="${pageFtpUploadRecord.pageNo+3 <= pageFtpUploadRecord.totalPages}">
						<b><span>...</span></b>
					</c:if>
					<c:if test="${pageFtpUploadRecord.pageNo < pageFtpUploadRecord.totalPages}">
						<a href="javascript:paging(${pageFtpUploadRecord.pageNo+1 })"><span>下一页</span></a>
					</c:if>
					<b><span>共${pageFtpUploadRecord.totalPages }页 跳到第<input
							style="width: 24px; margin: 0 5px;" onblur="paging(this.value)"
							value="${pageFtpUploadRecord.pageNo }" />页
							<input type="hidden" value="${pageFtpUploadRecord.pageNo}" id="pageNo" />
					</span></b>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>
