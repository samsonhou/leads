<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/header_res.ftl"/>
		<link href="${contextPath}/res/pub/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
		 <title>工作交接</title>
	</head>
	<body>
	<form action="" method="post" name="form1" class="form-horizontal">
		<input type="hidden" id="assginItems" name="assginItems">
		<div class="container-fluid">
			<div class="panel panel-default" style="margin-top: 1px;">
			  <div class="panel-heading">工作交接</div>
			  <div class="panel-body" style="padding-bottom: 0px;">
			  	 <div class="col-sm-12">
			  	 	 <div class="float-e-margins">
			  	 	 	 <div class="ibox-content" style="padding:0 0 0 0">
			  	 	 	 
			  	 	 	 	<div class="form-group">
                                <label class="col-sm-2 control-label">客户经理</label>
                                <div class="col-sm-2">
                                  <input type="text" name="sidName" id="sidName" onclick="showUser();" readonly class="form-control" >
                                  <input type="hidden" name="sid" id="sid"/>
                                  <input type="hidden" name="companyid" id="companyid"/>
                                </div>
                                <div class="col-sm-2">
                                  <span style="color:red;font-weight:bold;">将此人名下的所有线索平均分配给本机构其他客户经理！！！</span>
                                </div>
                            </div>
                            
                            <div class="form-group">
	                            <div class="col-sm-4 col-sm-offset-6">
	                                 <input type="button" onclick="handover();" value="交接" class="btn btn-primary btn-sm zd-btn-pd1">
	                             </div>
                            </div>
			  	 	 	 </div>
			  	 	 </div>
			  	 </div>
			  </div>
			</div>
	</form>
	<div id="userTree" class="userTree" style="display:none;  position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:250px; height: 300px;"></ul>
	</div>
	<#include "/pub/footer_res_detail.ftl"/>
	<script src="${contextPath}/res/pub/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
    <script src="${contextPath}/res/pub/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
	<script type="text/javascript">
		function handover(){
			if($("#sid").val() ==""){
				layer.alert("请选择要交接的客户经理！");
				return;
			}
			layer.confirm("确认交接！",{icon:3},function(idx){
				$.post(
					"${contextPath}/leads/handover/dealHandover.do",
					{sid:$("#sid").val(),companyid:$("#companyid").val()},
					function(result){
						layer.msg(result);
					}
				);
			});
		}
		$(document).ready(function(){
			var zNodes;
			jQuery.ajax({
				type : "post",
				url : "${contextPath}/base/user/userTree.do?roles=2",
				data : "",
				async:true,
				dataType : "text",
				success : function(data) {
					zNodes = eval(data);
					jQuery.fn.zTree.init(jQuery("#treeDemo"), setting, zNodes);
					
				}
			});
		});
		
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
				v += nodes[i].id.substring(1,nodes[i].id.length) + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			jQuery("#sid").val(v);

			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].pId+ ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			jQuery("#companyid").val(v);
			hideMenu();
		}

		function showUser() {
			var cityObj = jQuery("#sidName");
			var cityOffset = jQuery("#sidName").offset();
			jQuery("#userTree").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			
			jQuery("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			jQuery("#userTree").fadeOut("fast");
			jQuery("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "sidName" || event.target.id == "userTree" || jQuery(event.target).parents("#userTree").length>0)) {
				hideMenu();
			}
		}
	</script>
	</body>
</html>