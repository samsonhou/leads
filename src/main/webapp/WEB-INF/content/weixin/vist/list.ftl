<!DOCTYPE html>
<html lang="en">
<head>
<#include "/pub/header_wx.ftl"/>
<title>跟踪查询-${Session.WX_USER_KEY_USERNAME!"未知用户"}</title>
<style type="text/css">
body, html {
	height: 100%;
	-webkit-tap-highlight-color: transparent;
	background-color: #FBF9FE;
}
</style>
</head>
<body ontouchstart>
	<form action="${contextPath}/exchange/wx/vist/queryList.do" method="post" name="form1">
	<div class="weui_cells" style="margin-top: 2px;">
		<div class="weui_cell weui_cell_select weui_select_after">
			<div class="weui_cell_hd">线索等级</div>
			<div class="weui_cell_bd weui_cell_primary">
			<@select type='0' codeType="1026" defValue="${clientVO.rank!''}" fieldId="rank" fieldName="rank"  props=" class='weui_select' " />
			</div>
		</div>
		<div class="weui_cell weui_cell_select weui_select_after">
			<div class="weui_cell_hd">是否紧急</div>
			<div class="weui_cell_bd weui_cell_primary">
			<@select type='0' codeType="1000" defValue="${clientVO.ifurgent!''}" fieldId="ifurgent" fieldName="ifurgent"  props=" class='weui_select' " />
			</div>
		</div>
	</div>
	
	<div class="weui_btn_area">
		<a class="weui_btn  weui_btn_primary" id="showLoadingToast" href="javascript:">查 询</a>
	</div>
	<div class="weui_cells weui_cells_access">
	<#list page.list as client>
		<a class="weui_cell" href="javascript:openInfo('${client.ID}',2);"> <!--openInfo(13,2);  -->
			<div class="weui_cell_hd">
				<label for="" class="weui_label">${client_index+1}</label>
			</div>
			<div class="weui_cell_bd weui_cell_primary" style="overflow:hidden; white-space:nowrap; text-overflow:ellipsis;">
				<p>${client.NAME}</p>
			</div>
			<div class="weui_cell_ft">${client.TEL}</div>
		</a>
	</#list>	
	</div>
	<div id="loadingToast" class="weui_loading_toast"
		style="display: none;">
		<div class="weui_mask_transparent"></div>
		<div class="weui_toast">
			<div class="weui_loading">
				<div class="weui_loading_leaf weui_loading_leaf_0"></div>
				<div class="weui_loading_leaf weui_loading_leaf_1"></div>
				<div class="weui_loading_leaf weui_loading_leaf_2"></div>
				<div class="weui_loading_leaf weui_loading_leaf_3"></div>
				<div class="weui_loading_leaf weui_loading_leaf_4"></div>
				<div class="weui_loading_leaf weui_loading_leaf_5"></div>
				<div class="weui_loading_leaf weui_loading_leaf_6"></div>
				<div class="weui_loading_leaf weui_loading_leaf_7"></div>
				<div class="weui_loading_leaf weui_loading_leaf_8"></div>
				<div class="weui_loading_leaf weui_loading_leaf_9"></div>
				<div class="weui_loading_leaf weui_loading_leaf_10"></div>
				<div class="weui_loading_leaf weui_loading_leaf_11"></div>
			</div>
			<p class="weui_toast_content">拼命加载中</p>
		</div>
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
		//查询
		function search(){
			form1.action="${contextPath}/exchange/wx/vist/queryList.do";
			form1.submit();
		}
		
	</script>
</body>
</html>