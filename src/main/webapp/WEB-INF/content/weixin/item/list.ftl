<!DOCTYPE html>
<html lang="en">
<head>
<#include "/pub/header_wx.ftl"/>
<title>产品详情</title>
<style type="text/css">
body, html {
	background-color: #FBF9FE;
}
.a{ color: #999999;font-size: 14px;}
</style>
</head>
<body ontouchstart>
 
    <#if isnull != '0'>
    <div class="weui_cells_title" style="margin-top: 2px;font-size:16px;"><font size=3 color="green">产品子类</font></div>
	<div class="weui_cells weui_cells_access">
	        <#list data as client>
             <a class="weui_cell" href="${contextPath}/exchange/wx/item/list.do?id=${client.ID}" >
                <div class="weui_cell_bd weui_cell_primary">
                    <p>${client.NAME}</p>
                </div>
                <div class="weui_cell_ft">
                </div>
            </a>
            </#list>
    </div>	
    </#if>
 	
    <div class="weui_panel weui_panel_access">
            <div class="weui_panel_bd">
            	<div class="weui_media_box weui_media_text">
                    <h4 class="weui_media_title">产品名称</h4>
                    <p class="a">${pvo.pname}</p>
                </div>
                <div class="weui_media_box weui_media_text">
                    <h4 class="weui_media_title">产品介绍</h4>
                    <textarea class="weui_textarea a">${pvo.pdescribe}</textarea>
                </div>
                <#if pvo.pid!='0'>
                <div class="weui_media_box weui_media_text">
                    <h4 class="weui_media_title">产品话术</h4>
                    <textarea class="weui_textarea a">${pvo.pdetail}</textarea>
                </div>
                </#if>
            </div>
        </div>

		<div class="weui_btn_area" style="margin-top: 10px;">
			<#if pvo.pid??>
				<#if pvo.pid!='0'>
					<a class="weui_btn  weui_btn_default" href="${contextPath}/exchange/wx/item/list.do?id=${pvo.pid}">返 回</a>
				</#if>
				<#if pvo.pid=='0'>
					<a class="weui_btn  weui_btn_default" href="${contextPath}/exchange/wx/item/index.do">返 回</a>
				</#if>				
			</#if>
			
        </div>
	<#include "/pub/footer_wx.ftl"/>
	<script type="text/javascript">
		$("textarea").each(function(){
			  $(this).css("height",$(this).attr("scrollHeight"));
		 });
	</script>
</body>
</html>