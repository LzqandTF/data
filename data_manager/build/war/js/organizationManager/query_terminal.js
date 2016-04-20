/** *************************query_terminal************************** */
function queryChannelsDetail(id) {
	jQuery.ajax({
		type : "post",
		url : "queryChannelsDetail.do",
		data : "id=" + id,
		dataType : "text",
		success : function(smg) {
			var obj = eval("(" + smg + ")");
			var tr = jQuery("#channelsDetail").find("tr");
			var typeName = "终端";
			var statusName = "正常";
			jQuery(tr[0]).find("td:eq(1)").html(obj.cha.orgId);
			jQuery(tr[0]).find("td:eq(3)").html(obj.cha.orgName);
			jQuery(tr[1]).find("td:eq(1)").html(obj.cha.chaId);
			jQuery(tr[1]).find("td:eq(3)").html(obj.createDate);
			jQuery(tr[2]).find("td:eq(1)").html(obj.cha.channel);
			if (obj.cha.type == "1") {
				typeName = "商户";
			} else if (obj.cha.type == "2") {
				typeName = "渠道";
			}
			jQuery(tr[2]).find("td:eq(3)").html(typeName);
			jQuery(tr[3]).find("td:eq(1)").html(obj.start);
			jQuery(tr[3]).find("td:eq(3)").html(obj.end);
			if(obj.cha.status == "2"){
				statusName="关闭";
			}
			jQuery(tr[4]).find("td:eq(1)").html(statusName);
			jQuery(tr[5]).find("td:eq(1)").html(obj.cha.sysName);
		}

	});
	document.getElementById("pop2").style.display = "block";
}