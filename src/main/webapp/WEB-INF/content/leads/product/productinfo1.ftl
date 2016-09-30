<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/message.ftl"/>
			<#include "/pub/header_res.ftl"/>
				<link href="${contextPath}/res/pub/css/Pager.css" rel="stylesheet">
				<link href="${contextPath}/res/pub/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
				<link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">
				<link href="${contextPath}/res/pub/plugins/imageview/viewer.min.css" rel="stylesheet">

				<style>
					div.content_wrap {width: 600px;height:380px;}
					div.content_wrap div.left{float: left;width: 250px;}
					div.content_wrap div.right{float: right;width: 340px;}
					div.zTreeDemoBackground {width:250px;height:362px;text-align:left;}

					ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:360px;overflow-y:scroll;overflow-x:auto;}
					ul.log {border: 1px solid #617775;background: #f0f6e4;width:300px;height:170px;overflow: hidden;}
					ul.log.small {height:45px;}
					ul.log li {color: #666666;list-style: none;padding-left: 10px;}
					ul.log li.dark {background-color: #E3E3E3;}
					div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
					div#rMenu ul li{
						margin: 1px 0;
						padding: 0 5px;
						cursor: pointer;
						list-style: none outside none;
						background-color: #DFDFDF;
					}
				</style>
				<title>产品管理</title>
				<style>
					.jstree-open>.jstree-anchor>.fa-folder:before {
						content: "\f07c";
					}

					.jstree-default .jstree-icon {
						color: #F90;
					}
				</style>
	</head>
			<body class="gray-bg">
				<form action="#" method="post" name="form1" class="form-horizontal">
					<div class="container-fluid">

						<div class="panel panel-default" style="margin-top: 1px;">
							<div class="panel-heading">知识库列表</div>
							<div class="panel-body">
								<div class="col-sm-12">
									<div class="float-e-margins">
										<div class="ibox-content" style="padding: 0 0 0 0">

											<div class="row">
												<div class="col-sm-3" style="border-right:1px solid #ccc">
													<div class="ibox float-e-margins">
														<div class="ibox-content">
															<!-- <ul id="treeDemo" class="ztree" style="margin-top:0; width:240px; height: 300px;"></ul> -->
															<ul id="treeDemo" class="ztree"
																style="margin: 0px; background-color: #ffffff;height: 550px;border: 0px;padding:0px; width: 100%">
															</ul>
														</div>
													</div>
												</div>
												<div class="col-sm-9" style="display:none;" id="showInfo">
													<input type="hidden" name="nodeId" id="nodeId" />
													<div class="row">
														<div class="col-sm-5"></div>
														<div class="col-sm-3">
															<input type="text" name="keyword" id="keyword" class="form-control" placeholder="关键词">
														</div>
														<div class="col-sm-4">
															<input type="button" class="btn btn-primary btn-sm zd-btn-pd1" onclick="getQaDetail(1);" value="搜索">
														</div>
													</div>

													<div class="panel panel-default" style="margin-top:15px;">
														<div class="panel-heading">
															<h4>Q&A</h4>
														</div>
														<div class="panel-body">
															<div id="pbody"></div>
															<div id="pager" align="center"></div>
														</div>
													</div>
													<div class="panel panel-default" style="margin-top:15px;">
														<div class="panel-heading">
															<h4>文档</h4>
														</div>
														<div class="panel-body">
															<div id="fbody"></div>
															<div id="pager1" align="center"></div>
														</div>
													</div>
													<div class="panel panel-default" style="margin-top:15px;">
														<div class="panel-heading">
															<h4>图片</h4>
														</div>
														<div class="panel-body">
															<div class="row" id="picbody"></div>
															<div id="pager2" align="center"></div>
														</div>
													</div>


												</div>
											</div>

										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
				</form>


				
				<#include "/pub/footer_res_detail.ftl"/>
					<script src="${contextPath}/res/pub/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
					<script src="${contextPath}/res/pub/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
					<script src="${contextPath}/res/pub/js/jquery.pager.js" type="text/javascript"></script>
					<script src="${contextPath}/res/pub/plugins/imageview/viewer.min.js" type="text/javascript"></script>
					<script type="text/javascript">

						function onClick(e, treeId, treeNode) {
							var mymodal = $("#myModal");
							mymodal.find("#nName").val(treeNode.name);
							mymodal.find("#pId").val(treeNode.id);
							mymodal.find("#id").val(treeNode.id);
							mymodal.find("#dId").val(treeNode.id);

							mymodal.find("#rName").val(treeNode.name);
							mymodal.find("#hChild").val(treeNode.hasChild);
							
							$.each($("input[name='mroles']"),function(i,item){
								if(String(treeNode.roles).indexOf($(item).val())>=0){
									$(item).attr("checked","true");
								}
							});
							
							mymodal.modal('show');
						}

						function onCheck(e, treeId, treeNode) {
							var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
							nodes = zTree.getCheckedNodes(true),
							v = "";
							$("#nodeId").val(nodes[0].id);
							getQaDetail(1);
							$("#showInfo").show();
						}

						function getQaDetail(pagenum){
							$.ajax({
								type: "POST",
								url: "${contextPath}/leads/product/queryDetails.do?pagenum="+pagenum+"&pid="+$("#nodeId").val()+"&keyword="+encodeURI(encodeURI($("#keyword").val())),
								data: "",
								success: function(response){
									$("#pbody").html("");
									$("#fbody").html("");
									$("#picbody").html("");
									$("#pager").html("");
									$("#pager1").html("");
									$.each(response[0].list,function(i,item){
										if(item.question != null){
											$("#pbody").append("<p>问题："+item.question+"</p>");
											$("#pbody").append("<p>回答："+item.answer+"</p>");
											$("#pbody").append("<br/>")
										}
										$("#pager").pager({ pagenumber: response[0].pageNum, pagecount: response[0].pages, buttonClickCallback: pageClick });
									});


									$.each(response[1].list,function(i,item){
										$("#fbody").append("<p>"+item.fileName+"&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"${contextPath}/leads/product/downFile.do?filePath="+encodeURI(encodeURI(item.filePath))+"&fileName="+encodeURI(encodeURI(item.fileName))+"\">下载</a></p>")
										$("#pager1").pager({ pagenumber: response[1].pageNum, pagecount: response[1].pages, buttonClickCallback: pageClick1 });
									});
									/*
									$.each(response[2].list,function(i,item){
										$("#picbody").append("<img class='showimg' src='${contextPath}/res/pub/css/plugins/imageview/chakan.png' data-original='${imgPath}"+item.fileNewName+"'/>&nbsp;&nbsp;<a href=\"${contextPath}/leads/product/downFile.do?filePath="+encodeURI(encodeURI(item.filePath))+"&fileName="+encodeURI(encodeURI(item.fileName))+"\">下载</a></p>")
										$("#pager2").pager({ pagenumber: response[2].pageNum, pagecount: response[2].pages, buttonClickCallback: pageClick2 });
									});*/
									$.each(response[2],function(i,item){
										$("#picbody").append("<div class='col-sm-6 col-md-2'><img class='thumbnail' style='height:100px;' src='${imgPath}"+item.fileNewName+"'/></div>")
									});
									var viewer = new Viewer(document.getElementById('picbody'), {url:'data-original',navbar:false});
								}
							});
						}


						function pageClick(pagenum){
							$.ajax({
								type: "POST",
								url: "${contextPath}/leads/product/queryDetails.do?pagenum="+pagenum+"&pid="+$("#nodeId").val()+"&keyword="+encodeURI(encodeURI($("#keyword").val())),
								data: "",
								success: function(response){
									$("#pbody").html("");
									$("#pager").html("");
									$.each(response[0].list,function(i,item){
										if(item.question != null){
											$("#pbody").append("<p>问题："+item.question+"</p>");
											$("#pbody").append("<p>回答："+item.answer+"</p>");
											$("#pbody").append("<br/>")
										}
										$("#pager").pager({ pagenumber: response[0].pageNum, pagecount: response[0].pages, buttonClickCallback: pageClick });
									});


								}
							});
						}
						function pageClick1(pagenum){
							$.ajax({
								type: "POST",
								url: "${contextPath}/leads/product/queryDetails.do?pagenum="+pagenum+"&pid="+$("#nodeId").val()+"&keyword="+encodeURI(encodeURI($("#keyword").val())),
								data: "",
								success: function(response){
									$("#fbody").html("");
									$("#pager1").html("");
									$.each(response[1].list,function(i,item){
										$("#fbody").append("<p>"+item.fileName+"&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"${contextPath}/leads/product/downFile.do?filePath="+encodeURI(encodeURI(item.filePath))+"&fileName="+encodeURI(encodeURI(item.fileName))+"\">下载</a></p>")
										$("#pager1").pager({ pagenumber: response[1].pageNum, pagecount: response[1].pages, buttonClickCallback: pageClick1 });
									});


								}
							});
						}

						function pageClick2(pagenum){
							$.ajax({
								type: "POST",
								url: "${contextPath}/leads/product/queryDetails.do?pagenum="+pagenum+"&pid="+$("#nodeId").val()+"&keyword="+encodeURI(encodeURI($("#keyword").val())),
								data: "",
								success: function(response){
									$("#picbody").html("");
									$("#pager2").html("");
									$.each(response[2].list,function(i,item){
										$("#picbody").append("<img class='showimg' src='${contextPath}/res/pub/css/plugins/imageview/chakan.png' data-original='${imgPath}"+item.fileNewName+"'/>&nbsp;&nbsp;<a href=\"${contextPath}/leads/product/downFile.do?filePath="+encodeURI(encodeURI(item.filePath))+"&fileName="+encodeURI(encodeURI(item.fileName))+"\">下载</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"changeFile(\'"+item.id+"\')\">删除</a></p>")
										$("#pager2").pager({ pagenumber: response[2].pageNum, pagecount: response[2].pages, buttonClickCallback: pageClick2 });
									});
								}
							});
							$("#picbody").viewer({url:'data-original',navbar:false});
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
								
								onCheck: onCheck
							}
						};
						
						$(document).ready(function(){
							var zNodes;
							$.ajax({
								type : "post",
								url : "${contextPath}/leads/product/queryProducts.do",
								data : "",
								async:true,
								dataType : "text",
								success : function(data) {
									zNodes = eval(data);
									$.fn.zTree.init($("#treeDemo"), setting, zNodes);
								}
							});

							if($("#savebut").length>0){
								//alert('31');
							}else{
								$('input[name=abname]').attr("readonly","readonly")
								$('input[name=pname]').attr("readonly","readonly")
								$('textarea[name=pdescribe]').attr("readonly","readonly")
								$('textarea[name=pdetail]').attr("readonly","readonly")
							}

							var message="${message!''}";
							if(message.length>0){
								jQuery('#messageModal').modal('show');
							}
						});

					</script>
				</body>
			</html>
