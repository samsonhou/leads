<!DOCTYPE html>
<html lang="en">
<head>
<#include "/pub/header_wx.ftl"/>
<title>重要提醒客户-${Session.WX_USER_KEY_USERNAME!"未知用户"}</title>
<style type="text/css">
body, html {
	height: 100%;
	-webkit-tap-highlight-color: transparent;
	background-color: #FBF9FE;
}
</style>
</head>
<body ontouchstart>
<form action="" method="post" name="form1">
	<div class="weui_cells weui_cells_access" style="margin-top: 2px;">
	<#list impClient as imp>
		<a class="weui_cell" href="javascript:openInfo('${imp.CLIENT_ID}',4);"> <!--openInfo(13,2);  -->
			<div class="weui_cell_hd">
				<label for="" class="weui_label">${imp_index+1}</label>
			</div>
			<div class="weui_cell_bd weui_cell_primary" style="overflow:hidden; white-space:nowrap; text-overflow:ellipsis;">
				<p>${imp.CLIENT_NAME}</p>
			</div>
			<div class="weui_cell_ft">${imp.TEL}</div>
		</a>
	</#list>	
	</div>
	</form>				
	<#include "/pub/footer_wx.ftl"/>
	<script type="text/javascript">
		Zepto(function($) {
			Zepto("#showLoadingToast").on("click",function(){
				Zepto(this).addClass("weui_btn_disabled");
				Zepto("#loadingToast").show();
				setTimeout("search()", 300);
			});
		})
		//打开详情
		function openInfo(clientId,agentType){
			form1.action="${contextPath}/exchange/wx/urge/pageInfo.do?clientId="+clientId+"&agentType="+agentType;
			form1.submit();
		}
		
	</script>
</body>
</html>