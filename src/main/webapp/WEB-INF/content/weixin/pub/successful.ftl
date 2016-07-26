<!DOCTYPE html>
<html>
<head>
	<#include "/pub/header_wx.ftl"/>
	<title>成功提示信息</title>
</head>
<body>
	 <div class="weui_msg">
	    <div class="weui_icon_area"><i class="weui_icon_safe weui_icon_safe_success"></i></div>
	    <div class="weui_text_area">
	        <h2 class="weui_msg_title">${msg!'操作成功'}</h2>
	    </div>
	    
	   <#if url?? && url !='' >
	    <div class="weui_opr_area">
	        <p class="weui_btn_area">
	            <a href="${url!''}" class="weui_btn weui_btn_primary">返 回</a>
	        </p>
	    </div>
	    </#if>
	    
	</div>
	<#include "/pub/footer_wx.ftl"/>
</body>
</html>
