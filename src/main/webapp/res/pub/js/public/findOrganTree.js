/*******************************************************************************
 * 查询界面使用
 */
function showMenu() {
	var organ = jQuery("#name");
	var cityOffset = jQuery("#name").offset();
	jQuery("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + organ.outerHeight() + "px"}).slideDown("fast");
	
	jQuery("body").bind("mousedown", onBodyDown);
	
}

function hideMenu() {
	jQuery("#menuContent").fadeOut("fast");
	jQuery("body").unbind("mousedown", onBodyDown);
};
function onBodyDown(event) {
	if (!(event.target.id == "companyName" || event.target.id == "menuContent" || jQuery(
			event.target).parents("#menuContent").length > 0)) {
		hideMenu();
	}

};
var setting = {
	check : {
		enable : true,
		chkStyle : "radio",
		radioType : "all"
	},
	view : {
		dblClickExpand : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		onClick : onClick,
		onCheck : onCheck
	}
};

function onClick(e, treeId, treeNode) {
	var zTree = jQuery.fn.zTree.getZTreeObj("treeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
};

function onCheck(e, treeId, treeNode) {
	var zTree = jQuery.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
			.getCheckedNodes(true), v = "";
	// console.log(treeNode.getCheckStatus().checked);
	if (treeNode.getCheckStatus().checked) {
		jQuery("#organId").val(treeNode.id);
		jQuery("#name").val(treeNode.name);
		
	} else {
		jQuery("#organId").val("");
		jQuery("#name").val("");
	}
	callback();
};

function dblClickExpand(treeId, treeNode) {
	return treeNode.level > 0;
};

jQuery(document).ready(function() {
	var zNodes;
	jQuery.ajax({
		type : "post",
		url : BASE_PATH+"/base/organcompany/queryOrgans.do",
		data : {status : 1},
		async : true,
		dataType : "text",
		success : function(data) {
			//alert(data);
			zNodes = eval(data);
			//alert(zNodes);
			jQuery.fn.zTree.init(jQuery("#treeDemo"), setting, zNodes);
		}
	});
});
function showMenu1() {
	var organ = jQuery("#name1");
	var cityOffset = jQuery("#name1").offset();
	//alert(cityOffset.top+"---"+organ.height()+"----"+organ.innerHeight()+"-----"+organ.outerHeight());
	jQuery("#menuContent1").css({left:(cityOffset.left - organ.outerWidth())/2 + "px", top:cityOffset.top-6+ "px"}).slideDown("fast");
	
	jQuery("body").bind("mousedown", onBodyDown1);
	
}

function hideMenu1() {
	jQuery("#menuContent1").fadeOut("fast");
	jQuery("body").unbind("mousedown", onBodyDown);
};
function onBodyDown1(event) {
	if (!(event.target.id == "companyName" || event.target.id == "menuContent1" || jQuery(
			event.target).parents("#menuContent1").length > 0)) {
		hideMenu1();
	}
	
};
var setting1 = {
		check : {
			enable : true,
			chkStyle : "radio",
			radioType : "all"
		},
		view : {
			dblClickExpand1 : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : onClick1,
			onCheck : onCheck1
		}
};

function onClick1(e, treeId, treeNode) {
	var zTree = jQuery.fn.zTree.getZTreeObj("treeDemo1");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
};

function onCheck1(e, treeId, treeNode) {
	var zTree = jQuery.fn.zTree.getZTreeObj("treeDemo1"), nodes = zTree
	.getCheckedNodes(true), v = "";
	// console.log(treeNode.getCheckStatus().checked);
	if (treeNode.getCheckStatus().checked) {
		jQuery("#parentId1").val(treeNode.id);
		jQuery("#name1").val(treeNode.name);
		
	} else {
		jQuery("#organId1").val("");
		jQuery("#name1").val("");
	}
	callback();
};

function dblClickExpand1(treeId, treeNode) {
	return treeNode.level > 0;
};

jQuery(document).ready(function() {
	var zNodes;
	jQuery.ajax({
		type : "post",
		url : BASE_PATH+"/base/organcompany/queryOrgans.do",
		data : {status : 1},
		async : true,
		dataType : "text",
		success : function(data) {
			//alert(data);
			zNodes = eval(data);
			//alert(zNodes);
			jQuery.fn.zTree.init(jQuery("#treeDemo1"), setting1, zNodes);
		}
	});
});
