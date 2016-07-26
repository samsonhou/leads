<!DOCTYPE html>
<html lang="en">
<head>
<#include "/pub/header_wx.ftl"/>
<title>用户中心-${Session.WX_USER_KEY_USERNAME!"未知用户"}</title>
<style type="text/css">
body, html {
	height: 100%;
	-webkit-tap-highlight-color: transparent;
	background-color: #FBF9FE;
}
</style>
</head>
<body ontouchstart>
	<div class="weui_cells_title">个人信息</div>
					<div class="weui_cells">
						<div class="weui_cell">
							<div class="weui_cell_bd weui_cell_primary">
								<p>本月战败转成交加分</p>
							</div>
							<div class="weui_cell_ft"><font size=4 color="green">${add}</font>分</div>
						</div>
						<div class="weui_cell">
							<div class="weui_cell_bd weui_cell_primary">
								<p>本月超时线索减分</p>
							</div>
							<div class="weui_cell_ft"><font size=4 color="red">${subtract}</font>分</div>
						</div>
					</div>
					
					<div class="weui_cells_title">任务提醒</div>
					<div class="weui_cells">
						<div class="weui_cell">
							<div class="weui_cell_bd weui_cell_primary">
								<p>本月任务量</p>
							</div>
							<div class="weui_cell_ft"><font size=4>${plan_num}</font></div>
						</div>
						<div class="weui_cell">
							<div class="weui_cell_bd weui_cell_primary">
								<p>已完成数量</p>
							</div>
							<div class="weui_cell_ft"><font size=4 color="green">${perform!"0"}</font></div>
						</div>
						<div class="weui_cell">
							<div class="weui_cell_bd weui_cell_primary">
								<p>未完成数量</p>
							</div>
							<div class="weui_cell_ft"><font size=4 color="red">${num!"0"}</font></div>
						</div>
					</div>
					
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
		//查询
		function search(){
			form1.action="${contextPath}/exchange/wx/vist/queryList.do";
			form1.submit();
		}
		
	</script>
</body>
</html>