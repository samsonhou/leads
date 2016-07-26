<!DOCTYPE html>
<html lang="en">
<head>
<#include "/pub/header_wx.ftl"/>
<title>产品内容</title>
<style type="text/css">
body, html {
	height: 100%;
	-webkit-tap-highlight-color: transparent;
	background-color: #FBF9FE;
}
</style>
</head>
<body ontouchstart>

	<div class="weui_cells weui_cells_access" style="margin-top: 2px;">
	        <#list data as client>

            <a class="weui_cell" href="javascript:void(0);" onclick="getobj('${client.id}','${client.pId}','${client.name}')">
                <div class="weui_cell_bd weui_cell_primary">
                    <p>${client.name}</p>
                </div>
                <div class="weui_cell_ft">
                </div>
            </a>
            </#list>
    </div>
	
	<#include "/pub/footer_wx.ftl"/>
	<script type="text/javascript">
	  function getobj(id,pid,name){
	    window.location.href = "${contextPath}/exchange/wx/item/list.do?id="+id+"&pid="+pid+"&name="+name;
	  }
	</script>
</body>
</html>