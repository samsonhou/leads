<!DOCTYPE html>
<HTML>
<HEAD>
	<TITLE> ZTREE DEMO - reAsyncChildNodes</TITLE>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<#include "/pub/message.ftl"/>
    <#include "/pub/header_res.ftl"/>
    <#include "/pub/footer_res_detail.ftl"/>
	<link href="${contextPath}/res/pub/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
	<link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
	<script src="${contextPath}/res/pub/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
    <script src="${contextPath}/res/pub/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
	<SCRIPT type="text/javascript">
		
		$(document).ready(function(){
            var zNodes;
			$.ajax({
				type : "post",
				url : "${contextPath}/leads/search/getParent.do",
				data : "",
				async:true,
				dataType : "text",
				success : function(data) {
					zNodes = eval(data);				
					$.fn.zTree.init($("#treeDemo"), setting, zNodes);
				}
			});
		});
		
		
		var setting = {
			view: {
				selectedMulti: false
			},
			async: {
				enable: true,
				url:"${contextPath}/leads/search/getChild.do",
				otherParam:{"otherParam":"zTreeAsyncTest"},
				dataFilter: filter
			},
			callback: {
				onAsyncError: onAsyncError,
				onAsyncSuccess: onAsyncSuccess
			}
		};

		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}
		var log, className = "dark";
		function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
			showLog("[ "+getTime()+" onAsyncError ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root") );
		}
		function onAsyncSuccess(event, treeId, treeNode, msg) {
			showLog("[ "+getTime()+" onAsyncSuccess ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root") );
		}	
		function showLog(str) {
			if (!log) log = $("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}	
	</SCRIPT>
</HEAD>

<BODY>
<ul id="treeDemo" class="ztree"></ul>
</BODY>
</HTML>