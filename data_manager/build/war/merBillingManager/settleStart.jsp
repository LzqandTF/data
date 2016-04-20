<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../tag.tag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>账户头寸调拨</title>
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/wbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
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
	function hide(obj) {
		var o = document.getElementById(obj);
		o.style.display = "none";
	}
	//跳到指定页码操作
	function queryByPageForDetail(e) {
		var e = e || window.event;
		if (e.keyCode == 13) {
			var pageNum = $("#pageNum").val();
			if (pageNum >= 1) {
				pagingForDetail(pageNum);
			} else {
				pagingForDetail(1);
			}
		}
	}
	
	//上一页下一页操作
	function pagingForDetail(pageNo) {
		var pageSize = $("#pageSize").val();
		var mer_abbreviation_ = $("#mer_abbreviation_").val();
		var mer_code_ = $("#mer_code_").val();
		var mer_type=$("#mer_type_hide").val();
		var endTime = $("#endTime").val();
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/querySettleMerInfoDetail.do",
			data : "mer_type=" + mer_type + "&endTime=" + endTime + "&mer_abbreviation_=" + mer_abbreviation_ + "&mer_code_=" + mer_code_ + "&pageNum=" + pageNo + "&pageSize=" + pageSize,
			dataType : "text",
			success : function(msg) {
				$("#settleMerInfoDetail_").empty();
			    $("#settleMerInfoDetail_").append(msg);
				$("#settleMerInfoDetail").css({display:"block"});
			}
		});
	}
	//回车按钮操作，设置每页显示数据条数
	function EnterPress(e){ //传入 event
		var e = e || window.event;
		if(e.keyCode == 13){
			var pageSize = $("#pageSize").val();
			if (pageSize >= 10 && pageSize <= 200) {
				queryByPageSizeForDetail(pageSize);
			}else {
				queryByPageSizeForDetail(10);
			}
		}
	}
	//根据每页显示条数，EnterPress方法的附和操作
	function queryByPageSizeForDetail(pageSize) {
		var mer_abbreviation_ = $("#mer_abbreviation_").val();
		var mer_code_ = $("#mer_code_").val();
		var mer_type=$("#mer_type_hide").val();
		var endTime = $("#endTime").val();
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/querySettleMerInfoDetail.do",
			data : "mer_type=" + mer_type + "&endTime=" + endTime + "&mer_abbreviation_=" + mer_abbreviation_ + "&mer_code_=" + mer_code_ + "&pageSize=" + pageSize,
			dataType : "text",
			success : function(msg) {
				$("#settleMerInfoDetail_").empty();
			    $("#settleMerInfoDetail_").append(msg);
				$("#settleMerInfoDetail").css({display:"block"});
			}
		});
	}
	//全选、取消全选的事件  
	function selectAll(){  
	    if ($("#SelectAll").attr("checked")) {  
	        $("input[type='checkbox'][name='subcheck']").attr("checked", true);  
	    } else {  
	        $("input[type='checkbox'][name='subcheck']").attr("checked", false);  
	    }  
	}  
	//子复选框的事件  
	function setSelectAll(){  
	    var chsub = $("input[type='checkbox'][name='subcheck']").length; //获取subcheck的个数  
	    var checkedsub = $("input[type='checkbox'][name='subcheck']:checked").length; //获取选中的subcheck的个数  
	    if (checkedsub == chsub) {  
	        $("#SelectAll").attr("checked", true);  
	    }else{
	    	$("#SelectAll").attr("checked", false);
	    } 
	}
	function selectAllDetail(){  
	    if ($("#SelectAllDetail").attr("checked")) {  
	        $("input[type='checkbox'][name='subcheckDetail']").attr("checked", true);  
	    } else {  
	        $("input[type='checkbox'][name='subcheckDetail']").attr("checked", false);  
	    }  
	}  
	//子复选框的事件  
	function setSelectAllDetail(){  
	    var chsub = $("input[type='checkbox'][name='subcheckDetail']").length; //获取subcheck的个数  
	    var checkedsub = $("input[type='checkbox'][name='subcheckDetail']:checked").length; //获取选中的subcheck的个数  
	    if (checkedsub == chsub) {  
	        $("#SelectAllDetail").attr("checked", true);  
	    }else{
	    	$("#SelectAllDetail").attr("checked", false);
	    } 
	}
	//显示需要结算的商户类型统计信息
	function showRequireSettleMerInfo(){
		var endTime = $("#endTime").val();
		if(endTime == null || endTime == ""){
			alert("请选择结算截止日期");
			return;
		}
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/querySettleMerchantInfo.do",
			data : "endTime=" + endTime ,
			dataType : "text",
			success : function(msg) {
				if(msg != null){
					$("#requireSettleMerInfo").empty();
					$("#requireSettleMerInfo").css({display:"block"});
					$("#requireSettleMerInfo").append(msg);
				}else{
					alert("提示错误信息");
				}
				
			}
		});
	}
	
	//详情框中的查询方法
	function checkQuery(){	
		var pageSize = $("#pageSize").val();
		var mer_abbreviation_ = $("#mer_abbreviation_").val();
		var mer_code_ = $("#mer_code_").val();
		var mer_type=$("#mer_type_hide").val();
		var endTime = $("#endTime").val();
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/querySettleMerInfoDetail.do",
			data : "mer_type=" + mer_type + "&endTime=" + endTime + "&mer_abbreviation_=" + mer_abbreviation_ + "&mer_code_=" + mer_code_ + "&pageSize=" + pageSize,
			dataType : "text",
			success : function(msg) {
				$("#settleMerInfoDetail_").empty();
			    $("#settleMerInfoDetail_").append(msg);
				$("#settleMerInfoDetail").css({display:"block"});
			}
		});
	}

	
	
	function querySettleMerInfoDetail(mer_type){
		var endTime = $("#endTime").val();
		$("#mer_type_hide").val(mer_type);
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/querySettleMerInfoDetail.do",
			data : "mer_type=" + mer_type + "&endTime=" + endTime ,
			dataType : "text",
			success : function(msg) {
				$("#settleMerInfoDetail_").empty();
			    $("#settleMerInfoDetail_").append(msg);
				$("#settleMerInfoDetail").css({display:"block"});
			}
		});
	}
	
	
	function settle(mer_code,lastSettleDate,mer_category,bil_cycle,mer_abbreviation,bil_way,bil_smallamt,endDate,bil_status,bil_bank,bank_branch,bil_accountname,bil_bankaccount,bil_object,bil_manual,whtherFz,bil_account){
		if(confirm("确定要进行结算操作吗?")){
			var endTime = $("#endTime").val();
			var settleInfo = new Array();
			settleInfo[0] = mer_code + ";" + lastSettleDate + ";" + mer_category + ";"+ bil_cycle + ";" + mer_abbreviation + ";" + bil_way + ";" + bil_smallamt + ";" + endDate + ";" + bil_status + ";" + bil_bank + ";" +bank_branch + ";" + bil_accountname + ";" + bil_bankaccount + ";" + bil_object + ";" + bil_manual + ";" + whtherFz + ";" + bil_account ;
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/batchSettleMerInfo.do",
				data : "settleInfo=" + settleInfo+"&endTime="+endTime,
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
					if(msg != null){
						var totalNum = msg.split(";")[0];
						var sucessNum = msg.split(";")[1];
						var errorNum = msg.split(";")[2];
						var repeatNum = msg.split(";")[3];
						var noSettleOrderNum = msg.split(";")[4];
						alert("操作结果: 共发起结算"+totalNum+"个商户,其中成功"+sucessNum+"个,失败"+errorNum+"个,重复结算"+repeatNum+"个,无可结算订单"+noSettleOrderNum+"个");
					}
					hide("settleMerInfoDetail");
				}
			});
		}
	}
	
	function batchSettle(){
		if(confirm("确定要进行结算操作吗?")){
			var checkedSub = $("input[type='checkbox'][name='subcheckDetail']:checked").length;
			if(checkedSub == 0){
				alert("请勾选要结算的商户！");
				return ;
			}
			var endTime = $("#endTime").val();
			var batchSettleInfo = new Array();
			var i =0 ;
			$("input[type='checkbox'][name='subcheckDetail']:checked").each(function(){
				batchSettleInfo[i] = $(this).val();
				i++;
			});
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/batchSettleMerInfo.do",
				data : "settleInfo=" + batchSettleInfo+"&endTime="+endTime,
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
					if(msg != null){
						var totalNum = msg.split(";")[0];
						var sucessNum = msg.split(";")[1];
						var errorNum = msg.split(";")[2];
						var repeatNum = msg.split(";")[3];
						var noSettleOrderNum = msg.split(";")[4];
						alert("操作结果: 共发起结算"+totalNum+"个商户,其中成功"+sucessNum+"个,失败"+errorNum+"个,重复结算"+repeatNum+"个,无可结算订单"+noSettleOrderNum+"个");
					}
					hide("settleMerInfoDetail");
				}
			});
		}
	}
	
	function settleMerInfoByMerType(merType){
		if(confirm("确定要进行结算操作吗?")){
			var endTime = $("#endTime").val();
			var mer_type = new Array();
			mer_type[0] = merType ;
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/batchSettleMerInfo.do",
				data : "mer_type=" + mer_type+"&endTime="+endTime,
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
					if(msg != null){
						var totalNum = msg.split(";")[0];
						var sucessNum = msg.split(";")[1];
						var errorNum = msg.split(";")[2];
						var repeatNum = msg.split(";")[3];
						var noSettleOrderNum = msg.split(";")[4];
						alert("操作结果: 共发起结算"+totalNum+"个商户,其中成功"+sucessNum+"个,失败"+errorNum+"个,重复结算"+repeatNum+"个,无可结算订单"+noSettleOrderNum+"个");
					}
				}
			});
		}
	}
	
	function batchSettleMerInfoByMerType(){
		if(confirm("确定要进行结算操作吗?")){
			var checkedSub = $("input[type='checkbox'][name='subcheck']:checked").length;
			if(checkedSub == 0){
				alert("请勾选要结算的商户类型！");
				return ;
			}
			var endTime = $("#endTime").val();
			var merType = new Array();
			var i =0 ;
			$("input[type='checkbox'][name='subcheck']:checked").each(function(){
				merType[i] = $(this).val();
				i++;
			});
			$.ajax({
				type : "post",
				url : "<%=request.getContextPath()%>/batchSettleMerInfo.do",
				data : "mer_type=" + merType+"&endTime="+endTime,
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
					if(msg != null){
						var totalNum = msg.split(";")[0];
						var sucessNum = msg.split(";")[1];
						var errorNum = msg.split(";")[2];
						var repeatNum = msg.split(";")[3];
						var noSettleOrderNum = msg.split(";")[4];
						alert("操作结果: 共发起结算"+totalNum+"个商户,其中成功"+sucessNum+"个,失败"+errorNum+"个,重复结算"+repeatNum+"个,无可结算订单"+noSettleOrderNum+"个");
					}
				}
			});
		}
	}
	
</script>

</head>

<body>
<div id='loading-mask' style="display: none"></div>
<div id="loading" style="display: none">
    <div class="loading-indicator">
       <img src="<%=request.getContextPath()%>/images/wait-1.gif" width="60" height="60" style="margin-right:8px;float:left;vertical-align:top;"/>
       <br/><span id="loading-msg"></span>
    </div>
</div>
	<div class="content">
		<div class="right" style="margin: -25px 5px;">
			<div class="position">
				当前位置：<a href="javascript:void(0)">结算管理</a>&gt;<span>结算发起</span>
			</div>
			<div id="tab_content">
				<div class="check clearfix" style="margin-top: -15px;">
					<h1 class="tit">查询区</h1>
					<form action="<%=request.getContextPath()%>/" target="right" name="" id="" method="post">
						<div class="table_2" style="background:  #dcdfe1; border: none;">
							<center>
							<table width="90%" border="0" cellspacing="0">
						            <tr>
						            	<td align="right">结算截止日期</td>
						            	<td>
											<span class="input_bgl"> 
												<input  id="endTime" name="endTime" value="${param.endTime }"readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'\%y-%M-%d\'}'});" />
											</span>
										</td>
										<td align="left">
											<span>
												<input type="button" class="icon_normal" value="下一步" onclick="showRequireSettleMerInfo();" />
											</span>
							            </td>
						            </tr>
						        </table>
					        </center>
						</div>
					</form>
					
					<span class="red-radius-rt"></span> 
					<span class="red-radius-lb"></span>
					<span class="red-radius-rb"></span>
				</div>
			</div>
			<div style="display: none" id="requireSettleMerInfo"></div>
			<div id="settleMerInfoDetail" class="pop" style="display: none;OVERFLOW: auto; ">
				<div class="pop_body">
					<h1 class="pop_tit">
						<span class="fl">满足结算发起的商户列表</span> 
						<a class="close" href="javascript:hide('settleMerInfoDetail');">&nbsp;</a>
					</h1>
					<div class="table_2" id="settleMerInfoDetail_">
						
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
