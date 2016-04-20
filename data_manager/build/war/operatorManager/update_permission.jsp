<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统操作员权限修改</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
<link href="<%=request.getContextPath()%>/css/wbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/timeOut.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/js.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />

<style> 
*{ font-size:14px;}
body { background:#DCDFE1;font-family: "微软雅黑",Arial,Helvetica,sans-serif;font-size: 14px;margin: 0; padding: 20px;}
a{ text-decoration:none;}
.tree {background:#fff; border-radius: 0 0 5px 5px;box-shadow: 1px 1px 3px #999;list-style-type: none;margin: 0;overflow: hidden;padding: 0;}
.tree li {border-bottom: 1px solid #DCDFE1;white-space: nowrap;}
.tree li ul {list-style-type: none; margin: 0 0 -1px;padding: 0;}
.tree-node-hover { background: #be1d21; color: #fff;}
.tree-node {cursor: pointer;padding: 5px;white-space: nowrap;}
.tree-title {display: inline-block;font-size: 14px;height: 18px;line-height: 18px;padding: 0 2px;text-decoration: none;vertical-align: top;white-space: nowrap;}
.tree-expanded, .tree-collapsed, .tree-folder, .tree-file, .tree-checkbox, .tree-indent {
display: inline-block;height: 18px;margin-right: 2px;overflow: hidden;vertical-align: top;width: 16px;}
.tree-node-selected {background:#BE1D21;color: #FEFCFC;}
.tree li ul li ul{ overflow:hidden;background:#EFEFEF;}
.tree li ul li ul li{ width:50%; float:left;}
 
.position{ margin-bottom:12px;}
.position a{ margin-right:5px; color:#000;}
.position span{ color:#be1d21; text-decoration:underline; margin-left:5px;}
</style>

</head>
<body>
	<div class="right" style="margin: 25px 5px;">
		<div class="position">
			当前位置：<a href="#">权限管理</a>&gt;<span>系统用户权限配置</span>
		</div>
		<div class="check clearfix">
			<h1 class="tit">查询区</h1>
			<form action="<%=request.getContextPath()%>/initPermission.do" method="post">
				<ul class="check-m">
					<li>
						<b>操作员</b> 
						<span id="uboxstyle" class="input_bgl">	
							<select name="id" id="id">
								<c:forEach items="${sessionScope.logins }" var="login">
									<option value="${login.id },${login.chineseName}" <c:if test="${login.id == loginId }">selected</c:if>>${login.loginName }</option>
								</c:forEach>
							</select>
						</span>
						<span>					
							&nbsp;&nbsp;&nbsp;操作员名称:&nbsp;&nbsp;
						</span>
						<span id="chineseName">${login.chineseName}</span>
					</li>
					<li class="cb mt02" style="clear: none;margin-top: -10px;">
						<input class="icon_normal" value="查询" onclick="queryPermission()" type="button">
					</li>
				</ul>			
				<span class="red-radius-rt"></span> 
				<span class="red-radius-lb"></span>
				<span class="red-radius-rb"></span>
			</form>
		</div>
			
		<div id="permission" style="display: none">
			<h2 class="tit_h2">系统菜单权限分配</h2>
			<div class="table-m">		
				<ul id="tt" class="easyui-tree" data-options="animate:true,checkbox:true"></ul>			
				<span class="contect-lt"></span> 
				<span class="contect-rt"></span> 
				<span class="contect-lb"></span> 
				<span class="contect-rb"></span>
			</div>
			<a style="display: inline-block; text-align: center;"
				class="ml15 mt15 icon_normal" href="javascript:void(0);" onclick="show('pop1')">确认分配</a>
		</div>
	</div>
	
	<!--===========================弹出内容============================-->
	<div id="pop1" class="pop" style="display: none">
		<div class="pop_body" style="width: 260px;">
			<h1 class="pop_tit">
				<span class="fl">提示</span> <a class="close"
					href="javascript:void(0);" onclick="hide('pop1')">&nbsp;</a>
			</h1>
			<div class="table_2">
				<table width="100%" border="0" cellspacing="0">
					<tr>
						<td class="f16 tc"><br /> <img width="25"
							src="images/icon-hook.png" />&nbsp;分配成功!<br /> <br /></td>
					</tr>
				</table>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	
		function queryPermission(){
			var id = jQuery("#id").val();
			var name = id.split(",")[1];
			id = id.split(",")[0];
			jQuery("#chineseName").text("");
			jQuery("#chineseName").text(name);
			jQuery.ajax({
				type : "POST",
				url : "<%=request.getContextPath()%>/initPermission.do",
				data : "id="+id,
				success : function(msg){
					var jsonObj = eval("("+msg+")");
					jQuery("#tt").tree({data:jsonObj,animate:true,checkbox:true});
					jQuery("#permission").show();
				}
			});
		}
	
		var url = '';
		function show(obj) {
			var id = jQuery("#id").val();
			id = id.split(",")[0];
			var nodes = jQuery('#tt').tree('getChecked');
			var nodes2 = jQuery(".tree-checkbox2");
			var funcId = "";
			for(var i=0; i<nodes.length; i++){		
				if (funcId != '') 
					funcId += ',';			
				funcId += nodes[i].id;
			}
			for(var j=0; j<nodes2.length; j++){	
				if (funcId != '') 
					funcId += ',';
				funcId += jQuery(nodes2[j]).parent().attr("node-id");
			}

			jQuery.ajax({
				type : "POST",
				url : "<%=request.getContextPath()%>/updatePermission.do",
				data : "id="+id+"&&funcId="+funcId,
				success : function(msg){
					var jsonObj = eval("("+msg+")");
					if(jsonObj == "0")
						alert("修改失败！");
					else{
						var o = document.getElementById(obj);
						o.style.display = "";
						jQuery("#permission").hide();
					}
				}
			});												
		}
		
		function hide(obj) {
			var o = document.getElementById(obj);
			o.style.display = "none";
		}
	</script>
</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/select.js"></script>
</html>
