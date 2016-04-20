<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript">
	function changeColor(data){
		document.getElementById(data).setAttribute("style","color:red");
	}
</script>
</head>
<body>
	<table>
		<tr>
			<td>
				<a id="inst_info" style="color: green;width: 100%;height: 30%;cursor:pointer;border:none; " href="<%=request.getContextPath()%>/queryInstInfo.do" onclick="changeColor(this.id);">渠道配置</a>
				<a id="shou_dan"  style="color: green;width: 100%;height: 30%;" href="#" onclick="changeColor(this.id);">收单机构</a>
			</td>
		</tr>
	</table>
</body>
</html>