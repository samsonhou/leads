<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		<link href="${contextPath}/res/pub/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
		<link href="${contextPath}/res/pub/css/demo.css" rel="stylesheet">
		<title>线索分配</title>
	</head>
	<body class="gray-bg">
	<form action="${contextPath}/leads/assign/query.do" method="post" name="form1" class="form-horizontal">
		<input type="hidden" id="assginItems" name="assginItems">
		
		<@token />
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">线索分配</div>
			  <div class="panel-body">
			  	 <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 	<#list list as client>
			  	 	 	 	<div class="form-group">
                                <label class="col-sm-2 control-label">${client_index+1}-客户名称</label>
                                <input type="hidden" name="clientId"  value="${client.ID!'0'}">
                                <div class="col-sm-4 ">
                                    <input type="text" readonly value="${client.NAME!''}" class="form-control">
                                </div>
                                <label class="col-sm-2 control-label ">当前客服</label>
                                <div class="col-sm-4">
                                  <input type="text" readonly value="${client.REAL_NAME!''}" class="form-control" >
                                </div>
                            </div>
						 </#list>
						 <#if list?? >
						 <div class="hr-line-dashed"></div>
						 <div class="form-group"> 
                                <label class="col-sm-2 control-label">新分配客服</label>
                                <div class="col-sm-1 "><input type="text" id= "filName"  size="6" onblur="setOpen();" placeholder="定位公司" class="form-control"></div>
                                <div class="col-sm-3 ">    
                                    <input type="text"   id="sidName"    name="newUserCode" readonly  onclick="showMenu();"  class="form-control" placeholder="请选择人员">
                                    <input type="hidden" id="sid"      name="newUserId" >
                                    <input type="hidden" id="companyid" name="newUserOrganId">
                                </div>
                                <label class="col-sm-2 control-label">&nbsp;</label>
                                <div class="col-sm-4">
                                  <input type="button" onclick="assign();" value="分 配" class="btn btn-primary btn-sm zd-btn-pd1">
                                  &nbsp; &nbsp;
                                  <input type="button" onclick="closeTab();" value="关 闭" class="btn btn-primary btn-sm zd-btn-pd1">
                                </div>
                           </div>
                           </#if>
			  	 	 	 </div>
			  	 	 </div>
			  	 </div>
			  </div>
			</div>
		</div>
	</form>
	<div id="menuContent" class="menuContent" style="display:none;  position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:340px; height: 300px;"></ul>
	</div>
	<#include "/pub/message.ftl"/>
	<#include "/pub/footer_res_detail.ftl"/>
	<script src="${contextPath}/res/pub/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
    <script src="${contextPath}/res/pub/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
	<script type="text/javascript">
		function assign(){
			if(jQuery("#sidName").val()==''){
				$("#showAlertInfo").text('请选择新的处理人员,再分配!');
	    		$('#messageModal').modal('show');
			}else{
				form1.action="${contextPath}/leads/assign/wait/assgin.do";
				form1.submit();
			}
		}
		function filter(node) {
			if(jQuery("#filName").val()=="")
				return false;
		    return node.name.indexOf(jQuery("#filName").val())>-1;
		}
		function setOpen(){
			var zTree = jQuery.fn.zTree.getZTreeObj("treeDemo");
			zTree.expandAll(false);
			
			//总公司一层全部打开。
			var nodes = zTree.getNodes();
			//nodes[0].children
			zTree.expandNode(nodes[0], true, false, true);
			
			
			//var node = zTree.getNodeByParam("name", "北京分公司", null);alert(node);
			var node = zTree.getNodesByFilter(filter, true); // 仅查找一个节点
			zTree.expandNode(node, true, true, true);
			showMenu();
		}
		var setting = {
				check: {
					enable: true,
					chkStyle: "radio",
					radioType: "all"
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: onClick,
					onCheck: onCheck
				}
		};
		jQuery(function() {
			var zNodes;
			jQuery.ajax({
				type : "post",
				url : "${contextPath}/leads/client/queryOrgPerson.do?from=server",
				data : "",
				dataType : "text",
				success : function(data) {
					zNodes = eval(data);
					jQuery.fn.zTree.init(jQuery("#treeDemo"), setting, zNodes);
				}
			});
		});
		function onClick(e, treeId, treeNode) {
			var zTree = jQuery.fn.zTree.getZTreeObj("treeDemo");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
		function onCheck(e, treeId, treeNode) {
			var zTree = jQuery.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			jQuery("#sidName").val(v);
			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].id.substring(2,nodes[i].id.length) + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			jQuery("#sid").val(v);
			
			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].pId+ ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			jQuery("#companyid").val(v);
		}
		function showMenu() {
			var cityObj = jQuery("#sidName");
			var cityOffset = jQuery("#sidName").offset();
			jQuery("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			jQuery("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			jQuery("#menuContent").fadeOut("fast");
			jQuery("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "sidName" || event.target.id == "menuContent" || jQuery(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}
		jQuery(document).ready(function(){
			var message="${message!''}";
			if(message.length>0){
				layer.alert("${message}",function(index){
					form1.action="${contextPath}/leads/assign/wait/queryList.do";
					form1.submit();
				});
				
			}
			jQuery('#messageModal').on('hidden.bs.modal', function (e) {
				if(message.length>1){
					closeTab();
				}
			})
		});
	</script>
	</body>
</html>