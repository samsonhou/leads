<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "/pub/message.ftl"/>
			<#include "/pub/header_res.ftl"/>
				<link href="${contextPath}/res/pub/css/Pager.css" rel="stylesheet">
				<link href="${contextPath}/res/pub/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
				<link href="${contextPath}/res/pub/css/plugins/iCheck/custom.css" rel="stylesheet">

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
															<input type="button" class="btn btn-primary btn-sm zd-btn-pd1" onclick="getQaDetail(1);" value="搜索">&nbsp;&nbsp;&nbsp;&nbsp;
															<input type="button" class="btn btn-primary btn-sm" onclick="addq();" value="创建Q&A">&nbsp;&nbsp;&nbsp;&nbsp;
															<input type="button" class="btn btn-primary btn-sm" onclick="showImport();" value="上传文件">
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



												</div>
											</div>

										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
				</form>


				<!-- Modal -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog" role="document">
						<div class="modal-content" style="width:500px;">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">节点操作</h4>
							</div>
							<form name='saveform1' id='saveform1'  class="form-search" method="post" >
								<div class="modal-body" style="height:250px;">
									<!--高度根据表单高度相应调整-->
									<ul id="myTab" class="nav nav-tabs">
										<li class="active">
											<a href="#rename" data-toggle="tab">修改</a>
										</li>
										<li>
											<a href="#add" data-toggle="tab">新增</a>
										</li>
										<li>
											<a href="#delete" data-toggle="tab">删除</a>
										</li>
									</ul>
									<div id="myTabContent" class="tab-content">
										<div class="tab-pane fade in active" id="rename">
											<div class="ibox-content ">

												<div class="form-group ziding-ibox-modal">
													<label class="col-sm-4 control-label">节点新名称</label>
													<div class="col-sm-6 ">
														<input type="hidden" name="id" id="id"/>
														<input type="text" name="rName" id="rName" class="form-control" datatype="*" />
													</div>
												</div>
												<div class="form-group ziding-ibox-modal">
													<label class="col-sm-4 control-label">是否有子节点</label>
													<div class="col-sm-6 ">
														<@select type='0' codeType="1000" defValue="" fieldId="hChild" fieldName="hChild"  props=" class='form-control' datatype='*' " />
													</div>
													<div class="form-group ziding-ibox-modal">
														<label class="col-sm-4 control-label">角色</label>
														<div class="col-sm-6 ">
															<label>
															<input type="checkbox" name="mroles" datatype="*" nullmsg="请选择角色" value="1"> 管理员</label>&nbsp;&nbsp;<label>
															<input type="checkbox" name="mroles" value="3">客服</label>&nbsp;&nbsp;<label>
															<input type="checkbox" name="mroles" value="2"> 销售</label>
														</div>
													</div>
													<div class="col-sm-4 col-sm-offset-10">
														<input type="button" onclick="modifyNode();" value="保存" class="btn btn-primary btn-sm zd-btn-pd1">
													</div>
												</div>
											</div>
										</div>
										<div class="tab-pane fade" id="add">
											<div class="ibox-content ">

												<div class="form-group ziding-ibox-modal">
													<label class="col-sm-4 control-label">新节点名称</label>
													<div class="col-sm-6 ">
														<input type="hidden" name="pId" id="pId" class="form-control" />
														<input type="text" name="newName" id="newName" class="form-control" datatype="*"/>
													</div>
												</div>
												<div class="form-group ziding-ibox-modal">
													<label class="col-sm-4 control-label">是否有子节点</label>
													<div class="col-sm-6 ">
														<@select type='0' codeType="1000" defValue="" fieldId="hasChild" fieldName="hasChild"  props=" class='form-control' datatype='*' " />
													</div>

													<div class="form-group ziding-ibox-modal">
														<label class="col-sm-4 control-label">角色</label>
														<div class="col-sm-6 ">
															<label>
															<input type="checkbox" id="checkbox" name="nroles" datatype="*" value="1"> 管理员</label>&nbsp;&nbsp;<label>
															<input type="checkbox" name="nroles" value="3"> 客服</label>&nbsp;&nbsp;<label>
															<input type="checkbox" name="nroles" value="2"> 销售</label>
														</div>
													</div>

													<div class="col-sm-4 col-sm-offset-10">
														<input type="button" onclick="saveNode();" value="保存" class="btn btn-primary btn-sm zd-btn-pd1">
													</div>
												</div>
											</div>
										</div>
										<div class="tab-pane fade" id="delete">
											<div class="ibox-content ">

												<div class="form-group ziding-ibox-modal model_alert_1">
													<label class="col-sm-3 control-label">节点名称</label>
													<div class="col-sm-4 ">
														<input type="hidden" name="dId" id="dId" class="form-control" />
														<input type="text" name="nName" id="nName" readonly class="form-control" />
													</div>
													<div class="col-sm-3 col-sm-offset-4">
														<input type="button" onclick="deleteNode();" value="确认删除" class="btn btn-primary btn-sm zd-btn-pd1">
													</div>
												</div>
											</div>
										</div>

									</div>
								</div>
							</form>

							<div class="modal-footer"></div>

						</div>
					</div>
				</div>


				<!-- Modal -->
				<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog" role="document">
						<div class="modal-content" style="width:500px;">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">创建Q&A</h4>
							</div>
							<form name='saveform2' id='saveform2'  class="form-search" method="post" >
								<div class="modal-body" style="height:150px;">
									<!--高度根据表单高度相应调整-->

									<div class="ibox-content ">
										<div class="form-group ziding-ibox-modal">
											<label class="col-sm-3 control-label">问题</label>
											<input type="hidden" id="pid" name="pid" />
											<input type="hidden" id="questionId" name="questionId" />
											<div class="col-sm-8 ">
												<input type="text" name="question" id="question" class="form-control" />
											</div>
										</div>
										<div class="form-group ziding-ibox-modal">
											<label class="col-sm-3 control-label">答案</label>
											<div class="col-sm-8 ">
												<textarea type="text" name="answer" id="answer" class="form-control" ></textarea>
											</div>
										</div>
									</div>

								</div>
							</form>

							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
								<button type="button" class="btn btn-primary" onclick="saveQuestion();">保存</button>
								<input type="hidden" id="updateFlag" value="N">

							</div>

						</div>
					</div>
				</div>


				<!-- import Modal -->
				<div class="modal fade" id="importModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="importModalLabel">文档上传</h4>
							</div>
							<form name='importform' id='importform'  class="form-search" method="post" enctype="multipart/form-data">
								<input type="hidden" id="filePid" name="filePid" />
								<div class="modal-body" style="height:150px;">
									<!--高度根据表单高度相应调整-->
									<div class="ibox-content ">

										<div class="form-group ziding-ibox-modal">
											<label class="col-sm-2 control-label">文件</label>
											<div class="col-sm-10 ">
												<input type="file" readonly name="file" id="file" class="form-control layer-date">
											</div>
										</div>
									</div>
								</div>
							</form>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
								<button type="button" class="btn btn-primary" onclick="doImport()">上传</button>
							</div>
						</div>
					</div>
				</div>
				<#include "/pub/footer_res_detail.ftl"/>
					<script src="${contextPath}/res/pub/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
					<script src="${contextPath}/res/pub/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
					<script src="${contextPath}/res/pub/js/jquery.pager.js" type="text/javascript"></script>
					<script type="text/javascript">
						var subForm=$("#saveform1").Validform({
							showAllError:true
						});
						function saveNode(){
							subForm.ignore("#rName,#hChild");
							subForm.submitForm(false,"${contextPath}/leads/product/add.do");
						}
						function modifyNode(){
							subForm.ignore("#newName,#hasChild,#checkbox");
							subForm.submitForm(false,"${contextPath}/leads/product/modify.do");
						}
						function deleteNode(){

							subForm.submitForm(true,"${contextPath}/leads/product/delete.do");
						}

						function addq(){
							var mymodal = $("#myModal1");
							mymodal.find("#pid").val($("#nodeId").val());
							mymodal.find("#question").val("");
							mymodal.find("#answer").val("");
							mymodal.find("#questionId").val("");
							mymodal.find("#updateFlag").val("N");
							mymodal.modal('show');
						}

						function saveQuestion(){
							var q = encodeURI(encodeURI($("#question").val()));
							var a = encodeURI(encodeURI($("#answer").val()));
							if(q == ""){
								layer.alert("请填写问题");
								return;
							}
							if(a == ""){
								layer.alert("请填写答案");
								return;
							}
							
							var url = "${contextPath}/leads/product/saveQuestion.do?question="+q+"&answer="+a+"&pid="+$("#nodeId").val();
							if($("#updateFlag").val() == "Y"){
								url = "${contextPath}/leads/product/modifyQuestion.do?question="+q+"&answer="+a+"&id="+$("#questionId").val()
							}
							$.ajax({
								type: "POST",
								url: url,
								success: function(response){
									$("#myModal1").modal('hide');
									getQaDetail(1);
								}
							});
							//saveform2.action = "${contextPath}/leads/product/saveQuestion.do";
							//saveform2.submit();

						}

						function deleteQuestion(id){
							layer.confirm('确定删除?', {icon: 3, title:'提示'}, function(index){
								$.ajax({
									type: "POST",
									url: "${contextPath}/leads/product/deleteQuestion.do?id="+id,
									success: function(response){
										getQaDetail(1);
									}
								});

								layer.close(index);
							});

						}


						function editQuestion(question,answer,id){
							questionModal = $("#myModal1");
							questionModal.find("#myModalLabel").html("修改Q&A")
							questionModal.find("#question").val(question);
							questionModal.find("#answer").val(answer);
							questionModal.find("#questionId").val(id);
							questionModal.find("#updateFlag").val("Y");
							questionModal.modal('show');


						}


						function showImport(){
							var mymodal = $("#importModal");
							mymodal.find("#filePid").val($("#nodeId").val());
							mymodal.modal('show');
						}

						//导入操作
						function doImport(){
							importform.action = "${contextPath}/leads/product/importFile.do";
							importform.submit();
						}




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
									$(item).prop("checked",true);
								}else{
									$(item).prop("checked",false);
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
									$("#pager").html("");
									$("#pager1").html("");
									$.each(response[0].list,function(i,item){
										if(item.question != null){
											$("#pbody").append("<p>问题："+item.question+"&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=\"editQuestion(\'"+item.question.replace(/\s+/g,"").replace(/\"/g, "&quot;")+"\',\'"+item.answer.replace(/\s+/g,"").replace(/\"/g, "&quot;")+"\',\'"+item.id+"\')\">编辑</a>"+"&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=\"deleteQuestion(\'"+item.id+"\')\">删除</a></p>");
											$("#pbody").append("<p>回答："+item.answer+"</p>");
											$("#pbody").append("<br/>")
										}
										$("#pager").pager({ pagenumber: response[0].pageNum, pagecount: response[0].pages, buttonClickCallback: pageClick });
									});


									$.each(response[1].list,function(i,item){
										$("#fbody").append("<p>"+item.fileName+"&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"${contextPath}/leads/product/downFile.do?filePath="+encodeURI(encodeURI(item.filePath))+"&fileName="+encodeURI(encodeURI(item.fileName))+"\">下载</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"changeFile(\'"+item.id+"\')\">删除</a></p>")
										$("#pager1").pager({ pagenumber: response[1].pageNum, pagecount: response[1].pages, buttonClickCallback: pageClick1 });
									});





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
											$("#pbody").append("<p>问题："+item.question+"&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=\"editQuestion(\'"+item.question+"\',\'"+item.answer+"\',\'"+item.id+"\')\">编辑</a>"+"&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=\"deleteQuestion(\'"+item.id+"\')\">删除</a></p>");
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
										$("#fbody").append("<p>"+item.fileName+"&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"${contextPath}/leads/product/downFile.do?filePath="+encodeURI(encodeURI(item.filePath))+"&fileName="+encodeURI(encodeURI(item.fileName))+"\">下载</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"changeFile(\'"+item.id+"\')\">删除</a></p>")
										$("#pager1").pager({ pagenumber: response[1].pageNum, pagecount: response[1].pages, buttonClickCallback: pageClick1 });
									});


								}
							});
						}

						function changeFile(fileId){
							layer.confirm("确定删除？",{icon: 3, title:'提示'},function(index){
								$.ajax({
									type:"post",
									url:"${contextPath}/leads/product/changeFile.do?id="+fileId,
									data:"",
									success:function(resp){
										if(resp.msg == 'Y'){
											getQaDetail(1);
										}else{
											layer.alert("删除失败！");
										}

									}
								});
								layer.close(index);
							});
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
								onRightClick: onClick,
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
